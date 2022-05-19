package sleeptowait.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestSuiteModel {
	private List<PageObjectClass> pageObjects;
	private List<TestClass> testClasses;
	public Map<String, PageObjectClass> pageObjectMap;
	public Map<String, TestClass> testClassMap;
	public Map<String, String[]> testClassBodies;
	
	public TestSuiteModel(List<PageObjectClass> pageObjects, List<TestClass> testClasses, 
			Map<String, PageObjectClass> pageObjectMap, Map<String, TestClass> testClassMap, Map<String, String[]> testClassBodies) {
		this.pageObjects = pageObjects;
		this.testClasses = testClasses;
		this.pageObjectMap = pageObjectMap;
		this.testClassMap = testClassMap;
		this.testClassBodies = testClassBodies;
	}
	
	public void printPoMethodsList() {
		for(PageObjectClass po : pageObjects) {
			for(PageObjectMethod m : po.getMethods()) {
				System.out.println(m.getName());
			}
		}
	}
	
	
	public List<TestClass> getTestClasses() {
		return testClasses;
	}
	
	public List<PageObjectClass> getPageObjects() {
		return pageObjects;
	}
	
	public String[] getTestClassBody(String name) {
		return testClassBodies.get(name);
	}
	
	public void setTestClassBody(String name, String[] body) {
		testClassBodies.put(name, body);
	}
	
	public PageObjectClass getPageObject(String name) {
		return pageObjectMap.get(name);
	}
	
	public TestClass getTestClass(String name) {
		return testClassMap.get(name);
	}
	
	public String[] getPoBody(String name) {
		return testClassBodies.get(name);
	}
	
	public String[] getTestClassNames() {
		List<String> namesLst = new ArrayList<>();
		String[] namesArr = new String[] {};
		for(TestClass tc : testClasses) {
			namesLst.add(tc.getName());
		}
		return namesLst.toArray(namesArr);
	}
	
	public String[] getPONames() {
		List<String> namesLst = new ArrayList<>();
		String[] namesArr = new String[] {};
		for(PageObjectClass po : pageObjects) {
			namesLst.add(po.getName());
		}
		return namesLst.toArray(namesArr);
	}
	
}
