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

public class MultiTestRunner {
	
	private String[] testClasses;
	
	public MultiTestRunner(String[] testClasses) {
		this.testClasses = testClasses;
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
		params.put("host", "127.0.0.1");
		params.put("port", "3000");
		params.put("browserPort", "4445");
		suite.setParameters(params);
		List<XmlClass> classes = new ArrayList<XmlClass>();
		for(String tc : testClasses) {
			XmlClass currClass = new XmlClass(tc);
			classes.add(currClass);
		}
		test.setXmlClasses(classes);
		
		List<XmlSuite> suites = new ArrayList<XmlSuite>();
		suites.add(suite);
		testng.setXmlSuites(suites);
		testng.addListener(tla);
		testng.run();
		List<ITestResult> passed = tla.getPassedTests();
		List<ITestResult> failed = tla.getFailedTests();
		List<ITestResult> skipped = tla.getSkippedTests();
		List<ITestResult> configFails = tla.getConfigurationFailures();
		int exit_code = 1;
		if(!configFails.isEmpty()) {
			for(ITestResult currRes : configFails) {
				System.out.print("<failure>");
				for(StackTraceElement e : currRes.getThrowable().getStackTrace()) {
					if(e.getClassName().contains("tests.")) {
						System.out.print(e.getClassName()+":"+e.getLineNumber());
					}
				}
				System.out.print("</failure>\n");
			}
			exit_code = 1;
		}
		if(!failed.isEmpty()) {
			for(ITestResult currRes : failed) {
				System.out.print("<failure>");
				for(StackTraceElement e : currRes.getThrowable().getStackTrace()) {
					if(e.getClassName().contains("tests.")) {
						System.out.print(e.getClassName()+":"+e.getLineNumber());
					}
				}
				System.out.print("</failure>\n");
			}
			//failed.get(0).getThrowable().printStackTrace();
			//System.exit(1);
			exit_code = 1;
		}
		else if(!skipped.isEmpty()) {
			System.out.println(skipped.get(0).getName()+" skipped");
			//System.exit(1);
			exit_code = 1;
		}
		if(configFails.isEmpty() && failed.isEmpty() && skipped.isEmpty() && !passed.isEmpty()) {
			System.out.println("Passed");
			//System.exit(0);
			exit_code = 0;
		}
		System.exit(exit_code);
	}
	
}
