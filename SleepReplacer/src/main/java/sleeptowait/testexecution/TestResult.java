package sleeptowait.testexecution;

public class TestResult {
	
	private boolean passed;
	private int failureLine;
	private String testName;
	
	public TestResult(boolean passed) {
		this.passed = passed;
		this.failureLine = -1;
	}
	
	public TestResult(boolean passed, String testName) {
		this.passed = passed;
		this.testName = testName;
		this.failureLine = -1;
	}

	public boolean isPassed() {
		return passed;
	}

	public void setPassed(boolean passed) {
		this.passed = passed;
	}

	public int getFailureLine() {
		return failureLine;
	}

	public void setFailureLine(int failureLine) {
		this.failureLine = failureLine;
	}

	public String getTestName() {
		return testName;
	}

	public void setTestName(String testName) {
		this.testName = testName;
	}
}
