package sleeptowait.model;

public class PageAccess {
	
	private String line;
	private int location;
	
	
	public PageAccess(String line, int location) {
		this.line = line;
		this.location = location;
	}


	public String getLine() {
		return line;
	}


	public void setLine(String line) {
		this.line = line;
	}


	public int getLocation() {
		return location;
	}


	public void setLocation(int location) {
		this.location = location;
	}
	
	
}
