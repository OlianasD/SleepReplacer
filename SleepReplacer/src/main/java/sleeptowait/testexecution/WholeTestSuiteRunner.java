package sleeptowait.testexecution;

import java.util.ArrayList;
import java.util.List;

import org.testng.ITestResult;
import org.testng.TestListenerAdapter;
import org.testng.TestNG;
import org.testng.xml.XmlSuite;

public class WholeTestSuiteRunner {
	
	public void run() {
		TestNG testng = new TestNG();
		TestListenerAdapter tla = new TestListenerAdapter();
		testng.setUseDefaultListeners(false);
		XmlSuite suite = new XmlSuite();
		suite.setName("collabtive"); 
		List<String> suiteXmlFile = new ArrayList<>();
		suiteXmlFile.add("/media/sf_winhome/Dropbox/Dario PhD/SleepToWait tool/sleeptowait-noPO/target/testsuite-collabtive-testng/testng.xml");
		suite.setSuiteFiles(suiteXmlFile);
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
			//configFails.get(0).getThrowable().printStackTrace();
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
