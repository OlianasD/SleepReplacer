package sleeptowait.testexecution;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TestSuiteManager {
	
	private String path;
	
	public TestSuiteManager(String path) {
		this.path = path;
	}
	
	
	public boolean compile() throws IOException, InterruptedException {
		List<String> cmdArgs = new ArrayList<>();
		cmdArgs.add("/opt/apache-maven-3.8.1/bin/mvn");
		cmdArgs.add("clean");
		cmdArgs.add("compile");
		//cmdArgs.add("test-compile");
		ProcessBuilder procBuilder = new ProcessBuilder(cmdArgs);
		procBuilder.directory(new File(path));
		Process p = procBuilder.start();
		return p.waitFor() == 0;
	}
	
	public boolean startSUT() throws IOException, InterruptedException {
		List<String> cmdArgs = new ArrayList<>();
		cmdArgs.add("./run-docker.sh");
		cmdArgs.add("-p");
		cmdArgs.add("yes");
		cmdArgs.add("-n");
		cmdArgs.add("collabtive");
		//cmdArgs.add("test-compile");
		ProcessBuilder procBuilder = new ProcessBuilder(cmdArgs);
		procBuilder.directory(new File(path));
		Process p = procBuilder.start();
		Thread.sleep(5000);
		return p.waitFor() == 0;
	}
	
	public boolean stopSUT() throws IOException, InterruptedException {
		List<String> cmdArgs = new ArrayList<>();
		cmdArgs.add("./teardown-docker.sh");
		cmdArgs.add("collabtive");
		//cmdArgs.add("test-compile");
		ProcessBuilder procBuilder = new ProcessBuilder(cmdArgs);
		procBuilder.directory(new File(path));
		Process p = procBuilder.start();
		Thread.sleep(5000);
		return p.waitFor() == 0;
	}
	
	public List<TestResult> runTestSuite() throws IOException, InterruptedException {
		return new WholeTestSuiteCommander().runTestSuite();
	}
	
	
}
