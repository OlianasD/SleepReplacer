package sleeptowait.model;

import java.util.ArrayList;
import java.util.List;

public class TestClass {
	private String name;
	private List<TestMethod> tests;
	private String warranted;
	private boolean dependent;
	
	public TestClass(String name) {
		this.name = name;
		tests = new ArrayList<>();
	}
	
	public TestMethod getTestByName(String name) {
		for(TestMethod m : tests) {
			if(m.getName().equals(name)) {
				return m;
			}
		}
		return null;
	}
	
	public void addTest(TestMethod m) {
		tests.add(m);
	}
	
	public TestMethod getMethodByLine(int line) {
		for(int i=0; i<tests.size(); i++) {
			if(i < tests.size() -1) {
				if(line >= tests.get(i).getStart()-1 && line < tests.get(i+1).getStart()-1) {
					return tests.get(i);
				}
			}
			else {
				if(line >= tests.get(i).getStart()-1) {
					return tests.get(i);
				}
			}
		}
		throw new IllegalStateException("Cannot find test case corresponding to line "+line+" of class "+name);
	}
	
	public List<TestMethod> getTests() {
		return tests;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public boolean equals(Object o) {
		if(this == o)
			return true;
		if(o == null)
			return false;
		if(getClass() != o.getClass()) 
			return false;
		TestClass tc = (TestClass) o;
		return tc.getName().equals(name);
	}

	public String getWarrantedSchedule() {
		return warranted;
	}

	public void setWarrantedSchedule(String warranted) {
		this.warranted = warranted;
	}

	public boolean isDependent() {
		return dependent;
	}

	public void setDependent(boolean dependent) {
		this.dependent = dependent;
	}
	
}
