package sleeptowait.core;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import sleeptowait.model.ThreadSleep;

public class SleepToWaitConverter {
	
	
	public enum AccessType {READ, WRITE, ALERT}
	public static String[] readAccesses = {".getText()", ".isEnabled()", ".isDisplayed()", ".getAttribute("};
	public static String[] writeAccesses = {".click()", ".clear()", ".sendKeys(", ".selectByVisibleText(", ".selectByIndex("};
	public static String[] alertAccesses = {".alert()"};
	public static String dotNotationAccessRegex = "(?<obj>[a-zA-Z0-9_]+)\\.[a-zA-Z0-9]+\\(.*\\)";
	public static String argumentAccessRegex = "driver\\.findElement(s?)\\((?<obj>.*)\\)";
	public static String[] accessRgxs = {"\\.clear\\(", "\\.click\\(", "\\.getText\\(.*", "\\.sendKeys\\(.*", "\\.selectByVisibleText\\(.*", "\\.selectByIndex\\(.*", "\\.getAttribute\\(.*"};
	
	
	public AccessType getAccessType(String access) {
		for(String candidate : alertAccesses) {
			if(access.contains(candidate)) {
				return AccessType.ALERT;
			}
		}
		for(String candidate : readAccesses) {
			if(access.contains(candidate)) {
				return AccessType.READ;
			}
		}
		for(String candidate : writeAccesses) {
			if(access.contains(candidate)) {
				return AccessType.WRITE;
			}
		}
		throw new IllegalStateException("Cannot detect type of access "+access);
	}
	
	public String getAccessObject(String access) {
		System.out.println("Searching object for access "+access);
		Pattern pattern = Pattern.compile(dotNotationAccessRegex);
		Matcher matcher = pattern.matcher(access);
		if(!matcher.find()) {
			throw new IllegalStateException("Cannot find object for page access "+access);
		}
		String obj = matcher.group("obj");
		if(obj.equals("driver")) {
			pattern = Pattern.compile(argumentAccessRegex);
			matcher = pattern.matcher(access);
			matcher.find();
			String res = matcher.group("obj");
			for(String rgx : accessRgxs) 
				res = res.replaceFirst(rgx, "");
			if(res.contains("sendKeys"))
				System.out.println("Returning "+res);
			res = res.replace(")))", "))");
			return res;
		}
		else {
			return obj;
		}
	}
	
	public String protectAccess(String access) {
		String obj = null;
		if(getAccessType(access) == AccessType.READ) {
			obj = getAccessObject(access);
			if(obj.contains("By.")) 
				return "wait.until(ExpectedConditions.visibilityOfElementLocated("+obj+");\n";
			else
				return "wait.until(ExpectedConditions.visibilityOf("+obj+");\n";
		}
		else if(getAccessType(access) == AccessType.WRITE) {
			obj = getAccessObject(access);
			return "wait.until(ExpectedConditions.elementToBeClickable("+obj+");\n";
		}
		else if(getAccessType(access) == AccessType.ALERT) {
			return "wait.until(ExpectedConditions.alertIsPresent());\n";
		}
		else throw new IllegalStateException("Cannot detect access type for "+access);
	}
	
	public String[] replaceThreadSleep(String[] po, ThreadSleep ts) {
		String explicitWait = protectAccess(po[ts.getPageAccesses().get(0).getLocation()]);
		System.out.println("\t \t Replacing with "+explicitWait);
		po[ts.getLocation()-1] = explicitWait;
		ts.setReplacedWith(explicitWait);
		return po;
	}
	
	public String[] removeSleep(String[] po, ThreadSleep ts) {
		po[ts.getLocation()-1] = "";
		return po;
	}
}
