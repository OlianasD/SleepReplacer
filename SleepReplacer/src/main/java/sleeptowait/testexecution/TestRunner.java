package sleeptowait.testexecution;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.ITestResult;
import org.testng.TestListenerAdapter;
import org.testng.TestNG;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlInclude;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

public class TestRunner {
	
	private String testClass;
	private String testMethod;
	
	public TestRunner(String testClass, String testMethod) {
		this.testClass = testClass;
		this.testMethod = testMethod;
	}
	
	public TestRunner(String testClass) {
		this.testClass = testClass;
	}
	
	public void run() {
		TestNG testng = new TestNG();
		TestListenerAdapter tla = new TestListenerAdapter();
		testng.setUseDefaultListeners(false);
		XmlSuite suite = new XmlSuite();
		suite.setName("printotest"); 
		XmlTest test = new XmlTest(suite);
		test.setName("TmpTest");
		Map<String, String> params = new HashMap<>();
		params.put("browser", "GoogleChrome");
		XmlClass xmlClass = new XmlClass(testClass);
		xmlClass.setParameters(params);
		suite.setParameters(params);
		List<XmlClass> classes = new ArrayList<XmlClass>();
		classes.add(xmlClass);
		test.setXmlClasses(classes);
		if(testMethod != null) {
			List<XmlInclude> includeMethods = new ArrayList<>();
			includeMethods.add(new XmlInclude(testMethod));
			xmlClass.setIncludedMethods(includeMethods);
		}
		List<XmlSuite> suites = new ArrayList<XmlSuite>();
		suites.add(suite);
		testng.setXmlSuites(suites);
		testng.addListener(tla);
		testng.run();
		List<ITestResult> passed = tla.getPassedTests();
		List<ITestResult> failed = tla.getFailedTests();
		List<ITestResult> skipped = tla.getSkippedTests();
		List<ITestResult> configFails = tla.getConfigurationFailures();
		if(!configFails.isEmpty()) {
			for(StackTraceElement e : configFails.get(0).getThrowable().getStackTrace()) {
				if(e.getClassName().contains(testClass)) {
					System.out.println("<failure>"+e.getClassName()+":"+e.getLineNumber()+"</failure>");
				}
			}
			System.exit(1);
		}
		if(!failed.isEmpty()) {
			for(StackTraceElement e : failed.get(0).getThrowable().getStackTrace()) {
				if(e.getClassName().contains(testClass)) {
					System.out.println("<failure>"+e.getClassName()+":"+e.getLineNumber()+"</failure>");
				}
			}
			System.out.println(failed.get(0).getThrowable().getStackTrace()[0]);
			System.exit(1);
		}
		else if(!skipped.isEmpty()) {
			System.out.println(skipped.get(0).getName()+" skipped");
			System.exit(1);
		}
		if(!passed.isEmpty()) {
			System.out.println("Passed");
			System.exit(0);
		}
	}
	
}
