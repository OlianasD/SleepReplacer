package sleeptowait;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.json.JSONObject;

import javassist.NotFoundException;
import sleeptowait.core.SleepToWaitConverter;
import sleeptowait.core.SleepToWaitCore;
import sleeptowait.model.ModelBuilder;
import sleeptowait.model.PageObjectClass;
import sleeptowait.model.PageObjectMethod;
import sleeptowait.model.TestClass;
import sleeptowait.model.TestMethod;
import sleeptowait.model.TestSuiteModel;
import sleeptowait.model.ThreadSleep;
import sleeptowait.testexecution.TestCommander;
import sleeptowait.testexecution.TestResult;
import sleeptowait.testexecution.TestSuiteManager;
import sleeptowait.testexecution.WholeTestSuiteCommander;
import sleeptowait.testexecution.WholeTestSuiteRunner;

public class Main {
	

	
	public static String testsSubPath = "src/main/java/tests";
	
	public static String sourceProjectPath;
	public static String projectClassPath;
	public static String targetProjectClassPath;
	public static String targetProjectPath;
	public static String targetLaunchClassPath;
	public static String dockerHome;
	public static String dockerEntryPoint;
	
	
	public static void getAppCfg(String app) {
		JSONObject cfg = Utils.loadJSONObject("/media/sf_winhome/Dropbox/Dario%20PhD/SleepToWait%20tool/sleeptowait-noPO/sleeptowait/"+app+"_cfg.json");
		sourceProjectPath = cfg.getString("sourceProjectPath");
		projectClassPath = cfg.getString("sourceProjectClassPath");
		targetProjectClassPath = cfg.getString("targetProjectClassPath");
		targetProjectPath = cfg.getString("targetProjectPath");
		targetLaunchClassPath = cfg.getString("targetLaunchClassPath");
		dockerHome = cfg.getString("dockerHome");
		dockerEntryPoint = cfg.getString("dockerEntryPoint");
	}
	
	
	public static void main(String[] args) throws ClassNotFoundException, NotFoundException, FileNotFoundException, IOException, InterruptedException {
		System.out.println("start");
		String appName = args[0];
		int iterations = Integer.parseInt(args[1]);
		String mode = args[2];
		String runMode = args[3];
		getAppCfg(appName);
		if(mode.equals("--noPO") || mode.equals("--nopo")) {
			TestSuiteModel model = buildTestSuiteModel();
			System.out.println(model.getTestClassNames());
			List<TestClass> toRun = new ArrayList<>();
			if(runMode.equals("--all")) {
				toRun.addAll(model.getTestClasses());
			}
			else {
				toRun.add(model.getTestClass(runMode));
			}
			for(TestClass target : toRun) {
				long start = System.currentTimeMillis();
				SleepToWaitCore core = new SleepToWaitCore(model, 
						new SleepToWaitConverter(), 
						new TestSuiteManager(targetProjectPath), 
						targetProjectPath, 
						targetProjectClassPath, 
						targetLaunchClassPath,
						dockerHome,
						dockerEntryPoint,
						appName);
				core.replaceSleepsForTestClass(iterations, target);
				long end = System.currentTimeMillis();
				System.out.println("Time spent: "+new ExecutionTime()
		    			.computeExecutionTime(Arrays.asList((end - start))));
				printConversionResultsForClass(target);
			}
		}
		else if(mode.equals("--po") || mode.equals("--PO")) {
			TestSuiteModel model = buildPOModel();
			String[] pos = model.getPONames();
			SleepToWaitCore core = new SleepToWaitCore(model, 
					new SleepToWaitConverter(), 
					new TestSuiteManager(targetProjectPath), 
					targetProjectPath, 
					targetProjectClassPath, 
					targetLaunchClassPath,
					dockerHome,
					dockerEntryPoint,
					appName);
			for(String poName : pos) {
				System.out.println("Start page object "+poName);
				PageObjectClass poClass = model.getPageObject("org.gaslini.printo.pageobject.user."+poName);
				long start = System.currentTimeMillis();
				core.replaceSleepsForPageObjectClass(poClass);
				long end = System.currentTimeMillis();
				System.out.println("Time spent for PO "+poName+": "+new ExecutionTime()
		    			.computeExecutionTime(Arrays.asList((end - start))));
				printConversionResultsForPO(poClass);
				System.out.println("!! Page object "+poName+" finished\n\n\n");
				
			}
		}
		System.out.println("bye");
	}


	private static TestSuiteModel buildTestSuiteModel() throws IOException, FileNotFoundException,
			MalformedURLException, ClassNotFoundException, NotFoundException {
		String[] sleepsLocs = Utils.txtFileToLines(sourceProjectPath+"/sleeplist.txt");
		TestSuiteModel model = new ModelBuilder(sourceProjectPath, testsSubPath, projectClassPath)
				.buildTestModel()
				.addThreadSleeps(sleepsLocs)
				.addWarrantedSchedules(sourceProjectPath+"/wtds")
				.build();
		return model;
	}
	
	
	public static void printConversionResultsForClass(TestClass tc) {
		int total = 0;
		int fixed = 0;
		for(TestMethod m : tc.getTests()) {
			for(ThreadSleep ts : m.getSleeps()) {
				total++;
				if(ts.isFixed()) fixed++;
				System.out.println(tc.getName()+"."+m.getName()+","+ts.getLocation()+","+ts.isFixed());
			}
		}
		System.out.println(fixed+" thread sleeps fixed out of "+total);
	}
	
	public static void printConversionResultsForTestSuite(List<TestClass> suite) {
		int total = 0;
		int fixed = 0;
		for(TestClass tc : suite) {
 		for(TestMethod m : tc.getTests()) {
				for(ThreadSleep ts : m.getSleeps()) {
					total++;
					if(ts.isFixed()) fixed++;
					System.out.println(tc.getName()+"."+m.getName()+","+ts.getLocation()+","+ts.isFixed());
				}
			}
		}
		System.out.println(fixed+" thread sleeps fixed out of "+total);
	}
	
	private static TestSuiteModel buildPOModel() throws IOException, FileNotFoundException,
	MalformedURLException, ClassNotFoundException, NotFoundException {
		String[] sleepsLocs = Utils.txtFileToLines("sleeplist.txt");
		String[] sleepTrace = Utils.txtFileToLines("sleeptrace.log");
		TestSuiteModel model = new ModelBuilder(sourceProjectPath, testsSubPath, projectClassPath)
				.buildPOModel()
				.buildTestModel()
				.addThreadSleeps(sleepsLocs)
				.addUsages(sleepTrace)
				.build();
		return model;
	}
	
	public static void printConversionResultsForPO(PageObjectClass po) {
		int total = 0;
		int fixed = 0;
		for(PageObjectMethod m : po.getMethods()) {
			for(ThreadSleep ts : m.getSleeps()) {
				total++;
				if(ts.isFixed()) fixed++;
				System.out.println(po.getName()+"."+m.getName()+","+ts.getLocation()+","+ts.isFixed());
			}
		}
		System.out.println(fixed+" thread sleeps fixed out of "+total);
	}
}
