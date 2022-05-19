package sleeptowait.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PageObjectClass {
	
	private String name;
	private List<PageObjectMethod> methods;
	private Map<String, PageObjectMethod> methodMap;
	
	
	
	public PageObjectClass(String name) {
		this.name = name;
		methods = new ArrayList<>();
		methodMap = new HashMap<>();
	}
	
	public void addMethod(PageObjectMethod m) {
		m.setParentClass(this);
		methods.add(m);
		methodMap.put(m.getName(), m);
	}
	
	public List<PageObjectMethod> getMethods() {
		return methods;
	}
	
	public PageObjectMethod getMethodByName(String name) {
		return methodMap.get(name);
	}
	
	public PageObjectMethod getMethodByLine(int line) {
		for(int i=0; i<methods.size(); i++) {
			if(i < methods.size() -1) {
				if(line >= methods.get(i).getStartLine()-1 && line < methods.get(i+1).getStartLine()-1) {
					return methods.get(i);
				}
			}
			else {
				if(line >= methods.get(i).getStartLine()-1) {
					return methods.get(i);
				}
			}
		}
		throw new IllegalStateException("Cannot find test case corresponding to line "+line+" of class "+name);
	}

	public String getName() {
		return name;
	}
	
}
