package sleeptowait.model;

import java.util.ArrayList;
import java.util.List;

public class PageObjectMethod {
	
	private String name;
	private int startLine;
	private PageObjectClass parentClass;
	private boolean login;
	private List<TestMethod> usages;
	private List<ThreadSleep> sleeps;
	
	public PageObjectMethod(String name) {
		this.name = name;
		usages = new ArrayList<>();
		sleeps = new ArrayList<>();
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void addUsage(TestMethod m) {
		usages.add(m);
	}
	
	public List<TestMethod> getUsages() {
		return usages;
	}

	public Integer getStartLine() {
		return startLine;
	}

	public void setStartLine(int startLine) {
		this.startLine = startLine;
	}
	
	public PageObjectClass getParentClass() {
		return parentClass;
	}
	
	public void setParentClass(PageObjectClass parentClass) {
		this.parentClass = parentClass;
	}
	
	public void addSleep(ThreadSleep ts) {
		sleeps.add(ts);
	}
	
	public List<ThreadSleep> getSleeps() {
		return sleeps;
	}

	public boolean isLogin() {
		return login;
	}

	public void setLogin(boolean login) {
		this.login = login;
	}
	
}
