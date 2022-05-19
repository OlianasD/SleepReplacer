package sleeptowait.testexecution;

public class TestProcess {
	
	
	public static void main(String[] args) {
		String testClass = args[0];
		if(args.length > 1) {
			String testCase = args[1];
			new TestRunner(testClass, testCase).run();
		}
		else {
			new TestRunner(testClass).run();
		}
	}
}
