package sleeptowait.testexecution;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

public class WholeTestSuiteCommander {
	
	private String classpath = "/media/sf_winhome/Dropbox/Dario PhD/SleepToWait tool/sleeptowait-noPO/sleeptowait/target/classes:/media/sf_winhome/Dropbox/Dario PhD/SleepToWait tool/sleeptowait-noPO/target/testsuite-collabtive-testng/target/classes:/home/dario/.m2/repository/org/javassist/javassist/3.27.0-GA/javassist-3.27.0-GA.jar:/home/dario/.m2/repository/org/seleniumhq/selenium/selenium-java/3.141.59/selenium-java-3.141.59.jar:/home/dario/.m2/repository/org/seleniumhq/selenium/selenium-api/3.141.59/selenium-api-3.141.59.jar:/home/dario/.m2/repository/org/seleniumhq/selenium/selenium-chrome-driver/3.141.59/selenium-chrome-driver-3.141.59.jar:/home/dario/.m2/repository/org/seleniumhq/selenium/selenium-edge-driver/3.141.59/selenium-edge-driver-3.141.59.jar:/home/dario/.m2/repository/org/seleniumhq/selenium/selenium-firefox-driver/3.141.59/selenium-firefox-driver-3.141.59.jar:/home/dario/.m2/repository/org/seleniumhq/selenium/selenium-ie-driver/3.141.59/selenium-ie-driver-3.141.59.jar:/home/dario/.m2/repository/org/seleniumhq/selenium/selenium-opera-driver/3.141.59/selenium-opera-driver-3.141.59.jar:/home/dario/.m2/repository/org/seleniumhq/selenium/selenium-remote-driver/3.141.59/selenium-remote-driver-3.141.59.jar:/home/dario/.m2/repository/org/seleniumhq/selenium/selenium-safari-driver/3.141.59/selenium-safari-driver-3.141.59.jar:/home/dario/.m2/repository/org/seleniumhq/selenium/selenium-support/3.141.59/selenium-support-3.141.59.jar:/home/dario/.m2/repository/net/bytebuddy/byte-buddy/1.8.15/byte-buddy-1.8.15.jar:/home/dario/.m2/repository/org/apache/commons/commons-exec/1.3/commons-exec-1.3.jar:/home/dario/.m2/repository/com/google/guava/guava/25.0-jre/guava-25.0-jre.jar:/home/dario/.m2/repository/com/google/code/findbugs/jsr305/1.3.9/jsr305-1.3.9.jar:/home/dario/.m2/repository/org/checkerframework/checker-compat-qual/2.0.0/checker-compat-qual-2.0.0.jar:/home/dario/.m2/repository/com/google/errorprone/error_prone_annotations/2.1.3/error_prone_annotations-2.1.3.jar:/home/dario/.m2/repository/com/google/j2objc/j2objc-annotations/1.1/j2objc-annotations-1.1.jar:/home/dario/.m2/repository/org/codehaus/mojo/animal-sniffer-annotations/1.14/animal-sniffer-annotations-1.14.jar:/home/dario/.m2/repository/com/squareup/okhttp3/okhttp/3.11.0/okhttp-3.11.0.jar:/home/dario/.m2/repository/com/squareup/okio/okio/1.14.0/okio-1.14.0.jar:/home/dario/.m2/repository/org/junit/jupiter/junit-jupiter/5.5.1/junit-jupiter-5.5.1.jar:/home/dario/.m2/repository/org/junit/jupiter/junit-jupiter-api/5.5.1/junit-jupiter-api-5.5.1.jar:/home/dario/.m2/repository/org/apiguardian/apiguardian-api/1.1.0/apiguardian-api-1.1.0.jar:/home/dario/.m2/repository/org/opentest4j/opentest4j/1.2.0/opentest4j-1.2.0.jar:/home/dario/.m2/repository/org/junit/platform/junit-platform-commons/1.5.1/junit-platform-commons-1.5.1.jar:/home/dario/.m2/repository/org/junit/jupiter/junit-jupiter-params/5.5.1/junit-jupiter-params-5.5.1.jar:/home/dario/.m2/repository/org/junit/jupiter/junit-jupiter-engine/5.5.1/junit-jupiter-engine-5.5.1.jar:/home/dario/.m2/repository/org/junit/platform/junit-platform-engine/1.5.1/junit-platform-engine-1.5.1.jar:/home/dario/.m2/repository/org/testng/testng/6.8.8/testng-6.8.8.jar:/home/dario/.m2/repository/org/beanshell/bsh/2.0b4/bsh-2.0b4.jar:/home/dario/.m2/repository/com/beust/jcommander/1.27/jcommander-1.27.jar:/home/dario/.m2/repository/net/lingala/zip4j/zip4j/2.6.4/zip4j-2.6.4.jar:/home/dario/.m2/repository/ru/yandex/qatools/ashot/ashot/1.5.4/ashot-1.5.4.jar:/home/dario/.m2/repository/com/google/code/gson/gson/2.6.2/gson-2.6.2.jar:/home/dario/.m2/repository/org/hamcrest/hamcrest-core/1.3/hamcrest-core-1.3.jar:/home/dario/.m2/repository/commons-io/commons-io/2.7/commons-io-2.7.jar:/home/dario/.m2/repository/com/googlecode/json-simple/json-simple/1.1.1/json-simple-1.1.1.jar:/home/dario/.m2/repository/junit/junit/4.10/junit-4.10.jar:/home/dario/.m2/repository/org/apache/commons/commons-lang3/3.8.1/commons-lang3-3.8.1.jar:/home/dario/.m2/repository/org/reflections/reflections/0.9.11/reflections-0.9.11.jar";
	
	
	public List<TestResult> runTestSuite() throws IOException, InterruptedException {
		List<String> cmdArgs = new ArrayList<>();
		cmdArgs.add("java");
		cmdArgs.add("-Dfile.encoding=ISO-8859-1");
		cmdArgs.add("-classpath");
		cmdArgs.add(classpath);
		cmdArgs.add("sleeptowait.testexecution.WholeTestSuiteProcess");
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
	
}
