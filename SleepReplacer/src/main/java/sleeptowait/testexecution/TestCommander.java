package sleeptowait.testexecution;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

public class TestCommander {
	
	private String classpath;
	
	public TestCommander(String classpath) {
		this.classpath = classpath;
	}
	
	public List<TestResult> runTestCase(String testCase) throws IOException, InterruptedException {
		List<String> cmdArgs = new ArrayList<>();
		cmdArgs.add("java");
		cmdArgs.add("-Dfile.encoding=ISO-8859-1");
		cmdArgs.add("-classpath");
		cmdArgs.add(classpath);
		cmdArgs.add("sleeptowait.testexecution.MultiTestProcess");
		if(testCase != null)
			cmdArgs.add(testCase);
		ProcessBuilder procBuilder = new ProcessBuilder(cmdArgs);
		Process p = procBuilder.start();
		BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
	    String tmpLine;
		String output = "";
	    while ((tmpLine = in.readLine()) != null) {
	        output += tmpLine+"\n";
	    }
	    in.close();
		int exitCode = p.waitFor();
		System.out.println(output);
		System.out.println("Exit code: "+exitCode);
		String[] lines = output.split("\n");
		List<TestResult> res = new ArrayList<>();
		for(String line : lines) {
			if(line.contains("failure")) {
				String failStr = StringUtils.substringBetween(line, "<failure>", "</failure>");
				String[] parts = failStr.split("\\:");
			    TestResult currRes = new TestResult(false, parts[0]);
			    currRes.setFailureLine(Integer.parseInt(parts[1]));
			    res.add(currRes);
			}
		}
		return res;
	}
	
	public TestResult runTestSchedule(String testClass, String testCase) throws IOException, InterruptedException {
		List<String> cmdArgs = new ArrayList<>();
		cmdArgs.add("C:\\Program Files\\Java\\jdk1.8.0_301\\bin\\javaw.exe");
		cmdArgs.add("-Dfile.encoding=Cp1252");
		cmdArgs.add("-classpath");
		cmdArgs.add(classpath);
		cmdArgs.add("sleeptowait.testexecution.MultiTestProcess");
		cmdArgs.add(testClass);
		if(testCase != null)
			cmdArgs.add(testCase);
		ProcessBuilder procBuilder = new ProcessBuilder(cmdArgs);
		Process p = procBuilder.start();
		BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
	    String tmpLine;
		String line = "";
	    while ((tmpLine = in.readLine()) != null) {
	        line += tmpLine+"\n";
	    }
	    in.close();
		int exitCode = p.waitFor();
		if(exitCode == 0) {
			return new TestResult(true);
		}
		else {
			try {
				String failStr = StringUtils.substringBetween(line, "<failure>", "</failure>");
				String[] parts = failStr.split("\\:");
			    TestResult res = new TestResult(false);
			    res.setFailureLine(Integer.parseInt(parts[1]));
			    return res;
			} catch(Exception e) {
				return new TestResult(false);
			}
		    
		    
		}
		
	}
	
}
