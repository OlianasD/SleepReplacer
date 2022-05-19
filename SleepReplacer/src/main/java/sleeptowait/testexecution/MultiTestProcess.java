package sleeptowait.testexecution;

public class MultiTestProcess {
	
	
	public static void main(String[] args) {
		String[] testCases = args[0].split(",");
		new MultiTestRunner(testCases).run();
	}
}
