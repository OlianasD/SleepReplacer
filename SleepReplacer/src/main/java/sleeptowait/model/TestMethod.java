package sleeptowait.model;

import java.util.ArrayList;
import java.util.List;

public class TestMethod {
	private String name;
	private int start;
	private int end;
	private boolean fixture;
	private boolean test;
	private boolean dataProvider;
	private TestClass parentClass;
	private List<PageObjectMethod> usedMethods;
	private List<ThreadSleep> sleeps;
	
	public TestMethod(String name) {
		this.name = name;
		usedMethods = new ArrayList<>();
		sleeps = new ArrayList<>();
	}

	public Integer getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getEnd() {
		return end;
	}

	public void setEnd(int end) {
		this.end = end;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public void setFixture(boolean isFixture) {
		this.fixture = isFixture;
	}
	
	public boolean isFixture() {
		return fixture;
	}

	public boolean isTest() {
		return test;
	}

	public void setTest(boolean test) {
		this.test = test;
	}
	
	public void addUsedMethod(PageObjectMethod m) {
		usedMethods.add(m);
	}
	
	public List<PageObjectMethod> getUsedMethods() {
		return usedMethods;
	}

	public TestClass getParentClass() {
		return parentClass;
	}

	public void setParentClass(TestClass parentClass) {
		this.parentClass = parentClass;
	}
	
	@Override
	public boolean equals(Object o) {
		if(this == o)
			return true;
		if(o == null)
			return false;
		if(getClass() != o.getClass()) 
			return false;
		TestMethod tm = (TestMethod) o;
		return tm.getName().equals(name) && tm.getParentClass().equals(parentClass);
	}

	public boolean isDataProvider() {
		return dataProvider;
	}

	public void setDataProvider(boolean dataProvider) {
		this.dataProvider = dataProvider;
	}
	
	public List<ThreadSleep> getSleeps() {
		return sleeps;
	}
	
	public void addSleep(ThreadSleep ts) {
		sleeps.add(ts);
	}
}
