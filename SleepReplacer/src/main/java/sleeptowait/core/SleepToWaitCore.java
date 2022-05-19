package sleeptowait.core;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javassist.NotFoundException;
import sleeptowait.Utils;
import sleeptowait.model.PageObjectClass;
import sleeptowait.model.PageObjectMethod;
import sleeptowait.model.TestClass;
import sleeptowait.model.TestMethod;
import sleeptowait.model.TestSuiteModel;
import sleeptowait.model.ThreadSleep;
import sleeptowait.model.ModelBuilder;
import sleeptowait.testexecution.TestCommander;
import sleeptowait.testexecution.TestResult;
import sleeptowait.testexecution.TestSuiteManager;
import sleeptowait.testexecution.db.ResetAppState;
import sleeptowait.testexecution.docker.DockerContainer;
import sleeptowait.testexecution.docker.DockerManager;

public class SleepToWaitCore {
	
	public String targetProjectPath;
	public String testDir = "src/main/java";
	public String targetProjectClassPath;
	public String targetLaunchClassPath;
	private TestSuiteModel model;
	private SleepToWaitConverter converter;
	private TestSuiteManager manager;
	private TestSuiteModel targetModel;
	private TestSuiteManager targetMgr;
	private DockerManager docker;
	public String pageObjectsPath = "src\\main\\java\\";
	private String appName = "addressbook";
	
	public SleepToWaitCore(TestSuiteModel model, SleepToWaitConverter converter, TestSuiteManager manager, String targetProjectPath, String targetProjectClassPath, String targetLaunchClassPath, String dockerHome, String dockerEntryPoint, String appName) {
		this.model = model;
		this.converter = converter;
		this.manager = manager;
		this.targetProjectPath = targetProjectPath;
		this.targetProjectClassPath = targetProjectClassPath;
		this.targetLaunchClassPath = targetLaunchClassPath;
		this.docker = new DockerManager("127.0.0.1", dockerHome, dockerEntryPoint, 3000, 3306, 80, 3306);
	}
	
	public void loadTargetTestSuiteModel() throws FileNotFoundException, MalformedURLException, ClassNotFoundException, IOException, NotFoundException {
		targetModel = new ModelBuilder(targetProjectPath, testDir+"/tests", targetProjectClassPath)
				.buildTestModel()
				.loadTestClassBodies()
				.build();
		targetMgr = new TestSuiteManager(targetProjectPath);
	}
	
	public void replaceInOneStep() throws IOException {
		for(TestClass tc : model.getTestClasses()) {
			String fnam = tc.getName().replace(".", "/")+".java";
			for(TestMethod tm : tc.getTests()) {
				for(ThreadSleep ts : tm.getSleeps()) {
					replaceThreadSleep(fnam, model.getTestClassBody(tc.getName()), ts);
				}
			}
		}
	}
	
	public void replaceStepByStep(String[] originalOrder, int validationSteps) throws IOException, InterruptedException, ClassNotFoundException, NotFoundException {
		for(String tcnam : originalOrder) {
			TestClass tc = model.getTestClass(tcnam);
			replaceSleepsForTestClass(validationSteps, tc);
			Utils.killOrphanProcesses();
		}
	}

	public void replaceSleepsForTestClass(int validationSteps, TestClass tc) throws IOException, FileNotFoundException,
			MalformedURLException, ClassNotFoundException, NotFoundException, InterruptedException {
		System.out.println("Processing class "+tc.getName());
		String fnam = tc.getName().replace(".", "/")+".java";
		for(TestMethod tm : tc.getTests()) {
			for(ThreadSleep ts : tm.getSleeps()) {
				System.out.println("\t Processing sleep at line "+ts.getLocation()+" of class "+tc.getName());
				replaceThreadSleep(fnam, model.getTestClassBody(tc.getName()), ts);
				loadTargetTestSuiteModel();
				if(!targetMgr.compile()) {
					throw new IllegalStateException("Compilation failed!!");
				}
				List<TestResult> validationRes = validateTestClass(tc, validationSteps);
				if(validationRes != null) {
					System.out.println("\t Validation failed, restoring thread sleep");
					ts.restore(model.getTestClassBody(tc.getName()));
					Utils.strArrayToTxtFile(model.getTestClassBody(tc.getName()), targetProjectPath+"/"+testDir+"/"+fnam);
				}
				else {
					System.out.println("\t Validation OK");
					ts.setFixed(true);
				}
			}
		}
	}
	
	public List<TestResult> validateTestClass(TestClass tc, int times) throws IOException, InterruptedException {
		System.out.println("\t \t Validating test class "+tc.getName());
		for(int i=0; i<times; i++) {
			System.out.println("Run "+i+" of "+times);
			DockerContainer container = null;
			if(tc.isDependent()) {
				System.out.println("Creating docker container for test");
				String imgName = appName+"-"+tc.getName().replace("tests.", "").toLowerCase();
				container = docker.runContainerFromImage(imgName);
			} else {
				targetMgr.startSUT();
			}
			List<TestResult> res = new TestCommander(targetLaunchClassPath).runTestCase(tc.getName());
			if(tc.isDependent()) {
				docker.killContainer(container);
				docker.removeContainer(container);
			}
			else
				targetMgr.stopSUT();
			if(!res.isEmpty()) {
				System.err.println("Failed tests:");
				for(TestResult tr : res ) {
					System.err.println(tr.getTestName());
				}
				return res;
			}
		}
		return null;
	}
	
	public List<TestResult> validateTestSuite(int times) throws IOException, InterruptedException {
		for(int i=0; i<times; i++) {
			targetMgr.compile();
			targetMgr.startSUT();
			List<TestResult> res = targetMgr.runTestSuite();
			if(!res.isEmpty()) {
				return res;
			}
			targetMgr.stopSUT();
		}
		return null;
	}
	
	
	public void replaceThreadSleep(String fnam, String[] po, ThreadSleep ts) throws IOException {
		if(ts.getPageAccesses().isEmpty()) {
			po = converter.removeSleep(po, ts);
		}
		else {
			po = converter.replaceThreadSleep(po, ts);
		}
		Utils.strArrayToTxtFile(po, targetProjectPath+"/"+testDir+"/"+fnam);
	}
	
	
	public Map<String, List<TestMethod>> prepareValidationSchedule(Set<TestMethod> usages) {
		Map<String, Set<TestMethod>> groupedUsages = new HashMap<>();
		//map each usage to its class
		for(TestMethod usage : usages) {
			if(groupedUsages.keySet().contains(usage.getParentClass().getName())) {
				groupedUsages.get(usage.getParentClass().getName()).add(usage);
			}
			else {
				groupedUsages.put(usage.getParentClass().getName(), new HashSet<>());
				groupedUsages.get(usage.getParentClass().getName()).add(usage);
			}
		}
		Map<String, List<TestMethod>> res = new HashMap<>();
		//remove test fixtures
		for(String cls : groupedUsages.keySet()) {
			List<TestMethod> actualUsages = new ArrayList<>();
			for(TestMethod t : groupedUsages.get(cls)) {
				if(t.isTest()) {
					actualUsages.add(t);
				}
			}
			//all usages for this class were test fixture, add a random test for validation
			if(actualUsages.isEmpty()) {
				TestClass currCls = model.getTestClass(cls);
				for(TestMethod m : currCls.getTests())  {
					if(m.isTest()) {
						actualUsages.add(m);
						break;
					}
				}
			}
			res.put(cls, actualUsages);
		}
		return res;
	}
	

	
	public void simulatePageObjectReplacement(String[] po, PageObjectMethod method) {
		String fnam = method.getParentClass().getName().replace(".", "\\")+".java";
		for(ThreadSleep ts : method.getSleeps()) {
			System.out.println("\t Replacing sleep in method "+method.getName()+" at line "+ts.getLocation());

			TestMethod test = getPoMethodUsage(method);
			if(test != null) System.out.println("\t Validating change with test "+test.getParentClass().getName()+"."+test.getName());
			else System.out.println("\t \n\n\n!!WARNING: no usage found for method "+method.getName()+"\n\n");
		}
	}
	
	public TestMethod getPoMethodUsage(PageObjectMethod pom) {
		//get the first usage that is a test script
		if(pom.getUsages().isEmpty())
			return null;
		for(TestMethod candidate : pom.getUsages()) {
			if(candidate.isTest()) 
				return candidate;
		}
		//if all usages are test fixtures, take any test script of the class
		for(TestMethod tm : pom.getUsages().get(0).getParentClass().getTests()) {
			if(tm.isTest()) 
				return tm;
		}
		return null;
	}


	public String scheduleToString(List<TestMethod> schedule) {
		String strSched = "";
		for(int i=0; i<schedule.size(); i++) {
			System.out.println("\t \t \t"+schedule.get(i).getName());
			strSched += schedule.get(i).getName();
			if(i<schedule.size()-1)
				strSched += ",";
		}
		return strSched;
	}

	public TestSuiteModel getTargetModel() {
		return targetModel;
	}

	public void setTargetModel(TestSuiteModel targetModel) {
		this.targetModel = targetModel;
	}

	public TestSuiteManager getTargetManager() {
		return targetMgr;
	}

	public void setTargetManager(TestSuiteManager targetMgr) {
		this.targetMgr = targetMgr;
	}
	
	public boolean validate(PageObjectMethod method) throws IOException, InterruptedException {
		Set<TestMethod> usages = new HashSet<>(method.getUsages());
		Map<String, List<TestMethod>> validationSchedules = new HashMap<>();
		if(method.isLogin()) {
			System.out.println("\t \t Validating login method...");
			validationSchedules.put("org.gaslini.printo.AddSubjectTest", new ArrayList<TestMethod>());
			validationSchedules.put("org.gaslini.printo.CleaningDB", new ArrayList<TestMethod>());
			TestMethod addsubjvalid = model.getTestClass("org.gaslini.printo.AddSubjectTest").getTestByName("testAddSubjectValidNoNationalID");
			TestMethod cleaningdb = model.getTestClass("org.gaslini.printo.CleaningDB").getTestByName("cleanDBFromAllSubjectsAddedUntilNow");
			validationSchedules.get("org.gaslini.printo.AddSubjectTest").add(addsubjvalid);
			validationSchedules.get("org.gaslini.printo.CleaningDB").add(cleaningdb);
		}
		else {
			validationSchedules = prepareValidationSchedule(usages);
		}
		boolean validation = false;
		for(String testClass : validationSchedules.keySet()) {
			System.out.println("Validating usages in class "+testClass);
			validation = validateSchedule(testClass, validationSchedules.get(testClass));
			if(!validation)
				break;
		}
		return validation;
	}
	
	public boolean validateSchedule(String cls, List<TestMethod> schedule) throws IOException, InterruptedException {
		String strSched = scheduleToString(schedule);
		TestResult res = new TestCommander(targetLaunchClassPath).runTestSchedule(cls, strSched);
		return res.isPassed();
	}
	
	public void replaceSleepsForPageObjectClass(PageObjectClass poc) throws IOException, InterruptedException {
		String[] poBody = model.getPoBody(poc.getName());
		for(PageObjectMethod method : poc.getMethods()) {
			System.out.println("Replacing sleeps for method "+method.getName());
			replaceSleepsForPageObjectMethod(poBody, method);
		}
	}
	
	public void replaceSleepsForPageObjectMethod(String[] po, PageObjectMethod method) throws IOException, InterruptedException {
		String fnam = method.getParentClass().getName().replace(".", "\\")+".java";
		for(ThreadSleep ts : method.getSleeps()) {
			System.out.println("\t Replacing sleep at line "+ts.getLocation());
			replaceThreadSleep(fnam, po, ts);
			System.out.println("\t Compiling the test suite...");
			if(!manager.compile()) {
				System.out.println("\t Compilation failed: restoring the thread sleep");
				ts.restore(po);
				Utils.strArrayToTxtFile(po, targetProjectPath+"\\"+pageObjectsPath+"\\"+fnam);
				continue;
			}
			boolean validation = validate(method);
			if(!validation) {				
				System.out.println("\t Validation failed.");
				System.out.println("\t \t Restoring app status...");
				if(!restoreAppStatus()) {
					throw new IllegalStateException("Restore failed. Exiting.");
				}
				System.out.println("\t \t Restoring thread sleep");
				ts.restore(po);
				Utils.strArrayToTxtFile(po, targetProjectPath+"\\"+pageObjectsPath+"\\"+fnam);
			}
			else {
				ts.setFixed(true);
			}
		}
	}
	
	public boolean restoreAppStatus() throws IOException, InterruptedException {
		TestResult res = new TestCommander(targetLaunchClassPath).runTestSchedule("org.gaslini.printo.CleaningDB", "cleanDBFromAllSubjectsAddedUntilNow");
		return res.isPassed();
	}
}
