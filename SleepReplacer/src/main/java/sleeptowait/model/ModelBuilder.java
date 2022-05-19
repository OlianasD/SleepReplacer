package sleeptowait.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.NotFoundException;
import sleeptowait.Utils;

public class ModelBuilder {
	
	public String sourceProjectPath = "/media/sf_winhome/Dropbox/Dario PhD/SleepToWait tool/tedd_test_suites/testsuite-collabtive-testng";
	public String testsSubPath = "src/main/java/tests";
	public String projectClassPath = "/media/sf_winhome/Dropbox/Dario PhD/SleepToWait tool/tedd_test_suites/testsuite-collabtive-testng/target/classes";
	public static String pageObjectsSubPath = "src\\main\\java\\org\\gaslini\\printo\\pageobject";
	public Map<String, String[]> pageObjectBodies = new HashMap<>();
	public static String poPackage = "org.gaslini.printo.pageobject";
	public static String testPackage = "org.gaslini.printo";
	
	public static String[] keywords = {".click()", ".getText()", ".clear()", ".sendKeys(", ".isEnabled()", ".isDisplayed()", ".getAttribute(", "findElement"};

	public Map<String, PageObjectClass> pageObjectMap = new HashMap<>();
	public Map<String, PageObjectClass> pageObjectSimpleNameMap = new HashMap<>();
	public Map<String, String[]> testClassBodies = new HashMap<>();
	public Map<String, TestClass> testClassMap = new HashMap<>();
	public List<PageObjectClass> pageObjectModel = new ArrayList<>();
	public List<TestClass> testModel = new ArrayList<>();
	
	public ModelBuilder(String sourceProjectPath, String testsSubPath, String projectClassPath) {
		this.sourceProjectPath = sourceProjectPath;
		this.testsSubPath = testsSubPath;
		this.projectClassPath = projectClassPath;
	}
	
	public ModelBuilder buildPOModel() throws ClassNotFoundException, NotFoundException, FileNotFoundException, IOException {
		URL projUrl = new File(projectClassPath).toURI().toURL();
		URL[] urls = new URL[] {projUrl};
		ClassLoader cl = new URLClassLoader(urls);
		ClassPool pool = ClassPool.getDefault();
        pool.insertClassPath(projectClassPath);
		String[] subdirs = {"admin", "common", "user"};
		for(String subdir : subdirs) {
			File pos = new File(projectClassPath+"\\org\\gaslini\\printo\\pageobject\\"+subdir);
			for(File file : pos.listFiles()) {
				if(file.getName().contains(".class")) {
					Class<?> cls = cl.loadClass(poPackage+"."+subdir+"."+file.getName().replace(".class", ""));
					CtClass cc = pool.get(cls.getCanonicalName());
					PageObjectClass currClass = new PageObjectClass(cls.getName());
					pageObjectModel.add(currClass);
					pageObjectMap.put(cls.getName(), currClass);
					pageObjectSimpleNameMap.put(file.getName().replace(".class", ""), currClass);
					for(Method m : cls.getDeclaredMethods()) {
						PageObjectMethod currMethod = new PageObjectMethod(m.getName());
						currMethod.setStartLine(getLineNumber(cc, m.getName()));
						currClass.addMethod(currMethod);
					}
					//sort methods according to line number
					currClass.getMethods().sort((o1, o2) -> o1.getStartLine().compareTo(o2.getStartLine()));
				}
			}
		}
		return this;
	}
	
	public ModelBuilder buildTestModel() throws MalformedURLException, ClassNotFoundException, NotFoundException {
		URL testUrl = new File(projectClassPath+"/tests").toURI().toURL();
		URL[] urls = new URL[] {testUrl};
		ClassLoader cl = new URLClassLoader(urls);
		ClassPool pool = ClassPool.getDefault();
		File tests = new File(projectClassPath+"/tests");
		for(File file : tests.listFiles()) {
			if(file.getName().contains(".class")) {
				Class<?> cls = cl.loadClass("tests."+file.getName().replace(".class", ""));
				CtClass cc = pool.get(cls.getCanonicalName());
				TestClass currClass = new TestClass(cls.getName());
				testModel.add(currClass);
				testClassMap.put(cls.getName(), currClass);
				for(Method m : cls.getDeclaredMethods()) {
					TestMethod currMethod = new TestMethod(m.getName());
					currMethod.setParentClass(currClass);
					Annotation[] annots = m.getDeclaredAnnotations();
					for(Annotation a : annots) {
						if(a.annotationType().getName().contains("Before")
							|| a.annotationType().getName().contains("After")) {
							currMethod.setFixture(true);
						}
						else if(a.annotationType().getName().contains("Test")) {
							currMethod.setTest(true);
						}
						else if(a.annotationType().getName().contains("DataProvider")) {
							currMethod.setDataProvider(true);
						}
					}
					currMethod.setStart(getLineNumber(cc, m.getName()));
					currClass.addTest(currMethod);
				}
				currClass.getTests().sort((o1, o2) -> o1.getStart().compareTo(o2.getStart()));
			}
		}
		return this;
	}
	
	public ModelBuilder addThreadSleeps(String[] sleepLocs) throws FileNotFoundException, IOException {
		for(String line : sleepLocs) {
			String[] parts = line.split("\\:");
			String className = "tests."+parts[0].replace(".java", "").replace("/", ".");
			String fileName = parts[0];
			int lineNo = Integer.parseInt(parts[1]);
			String[] poFile = Utils.txtFileToLines(sourceProjectPath+"/"+testsSubPath+"/"+fileName);
			testClassBodies.put(className, poFile);
			TestClass testClass = testClassMap.get(className);
			TestMethod testMethod = testClass.getMethodByLine(lineNo);
			ThreadSleep ts = new ThreadSleep(lineNo, lineNo-1, lineNo);
			ts.setOriginalTryBlock(new String[] {poFile[lineNo-1]});
			PageAccess pa = findSubsequentPageAccess(poFile, lineNo);
			if(pa != null) {
				ts.addPageAccess(pa);
			}
			else {
				System.out.println("No page access found for threadsleep at line "+lineNo+" in method "+testMethod.getName()+" in class "+className);
			}
			testMethod.addSleep(ts);
			
		}
		
		return this;
	}
	
	public ModelBuilder addThreadSleepsForPO(String[] sleepLocs) throws FileNotFoundException, IOException {
		for(String line : sleepLocs) {
			String[] parts = line.split("\\:");
			String className = poPackage+"."+parts[0].replace(".java", "").replace("/", ".");
			String fileName = parts[0];
			int lineNo = Integer.parseInt(parts[1]);
			String[] poFile = Utils.txtFileToLines(sourceProjectPath+"\\"+pageObjectsSubPath+"\\"+fileName);
			pageObjectBodies.put(className, poFile);
			PageObjectClass poClass = pageObjectMap.get(className);
			PageObjectMethod poMethod = poClass.getMethodByLine(lineNo);
			int[] enclosingTryBlock = getTryBlock(poFile, lineNo);
			ThreadSleep ts = new ThreadSleep(lineNo, enclosingTryBlock[0], enclosingTryBlock[1]);
			ts.setOriginalTryBlock(Arrays.copyOfRange(poFile, enclosingTryBlock[0], enclosingTryBlock[1]+1));
			PageAccess pa = findSubsequentPageAccess(poFile, enclosingTryBlock[1]);
			if(pa != null) {
				ts.addPageAccess(pa);
			}
			else {
				System.out.println("No page access found for threadsleep at line "+lineNo+" in method "+poMethod.getName()+" in class "+className);
			}
			poMethod.addSleep(ts);
			
		}
		
		return this;
	}
	
	private int[] getTryBlock(String[] po, int line) {
		int blockStart = 0, blockEnd = 0;
		boolean catchFound = false;
		for(int i=line; i>0; i--) {
			if(po[i].contains("try")) {
				blockStart = i;
				break;
			}
		}
		
		for(int i=line; i<po.length; i++) {
			if(po[i].contains("catch")) {
				catchFound = true;
			}
			if(catchFound && po[i].contains("}")) {
				blockEnd = i;
				break;
			}
		}
		
		return new int[] {blockStart, blockEnd};
		
	}
	
	private PageAccess findSubsequentPageAccess(String[] po, int startLine) {
		for(int i=startLine; i<po.length; i++) {
			for(String keyword : keywords) {
				if(po[i].contains(keyword)) {
					return new PageAccess(po[i], i);
				}
			}
			if(po[i].contains("return ") || po[i].contains("Thread.sleep")) break;
		}
		return null;
	}
	
	public ModelBuilder addUsages(String[] sleepTrace) {
		TestMethod currTest = null;
		PageObjectMethod currPoMethod = null;
		boolean isLogin = false;
		for(String line : sleepTrace) {
			String[] parts = null;
			if(line.contains("STARTFIXTURE") || line.contains("STARTTEST")) {
				isLogin = false;
				parts = line.split("\\:")[1].split("\\.");
				currTest = testClassMap.get(testPackage+"."+parts[0]).getTestByName(parts[1]);
			}
			else if(line.contains("THREADSLEEP")) {
				parts = line.split("\\:")[1].split("\\.");
				currPoMethod = pageObjectSimpleNameMap.get(parts[0]).getMethodByName(parts[1]);
				PageObjectMethod usageByLine = pageObjectSimpleNameMap.get(parts[0]).getMethodByLine(Integer.parseInt(parts[2]));
				if(!currPoMethod.equals(usageByLine)) {
					System.out.println(" !!! MISMATCH for line "+line);
				}
				if(isLogin || parts[1].contains("AdminLoginPO")) currPoMethod.setLogin(true);
				else {
					currPoMethod.addUsage(currTest);
					currTest.addUsedMethod(currPoMethod);
				}
				
			}
			else if(line.contains("INFO: Detected dialect: W3C")) {
				isLogin = true;
			}
		}
		return this;
	}
	
	public ModelBuilder addWarrantedSchedules(String path) throws FileNotFoundException, IOException {
		for(String tcName : testClassMap.keySet()) {
			TestClass tc = testClassMap.get(tcName);
			String[] wa = Utils.txtFileToLines(path+"/"+tc.getName().replace("tests.", "")+".wtd");
			if(wa[0].contains(","))
				tc.setDependent(true);
			tc.setWarrantedSchedule(wa[0]);
		}
		return this;
	}
	
	public int getLineNumber(CtClass cls, String method) throws NotFoundException {
		return cls.getDeclaredMethod(method).getMethodInfo().getLineNumber(0);
	}
	
	public ModelBuilder loadTestClassBodies() throws FileNotFoundException, IOException {
		File tests = new File(sourceProjectPath+"/"+testsSubPath);
		for(File file : tests.listFiles()) {
			String[] poFile = Utils.txtFileToLines(sourceProjectPath+"/"+testsSubPath+"/"+file.getName());
			String className = "tests."+file.getName().replace(".java", "");
			testClassBodies.put(className, poFile);
		}
		return this;
	}
	
	
	public TestSuiteModel build() {
		return new TestSuiteModel(pageObjectModel, testModel, pageObjectMap, testClassMap, testClassBodies);
	}
	
	public TestSuiteModel buildForPO() {
		return new TestSuiteModel(pageObjectModel, testModel, pageObjectMap, testClassMap, pageObjectBodies);
	}

	
}
