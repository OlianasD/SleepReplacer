package sleeptowait.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ThreadSleep {
	
	private int location;
	private int blockStart;
	private int blockEnd;
	private boolean fixed;
	private String[] originalTryBlock;
	private String replacedWith;
	private List<PageAccess> pageAccesses;
	
	public ThreadSleep(int location, int start, int end) {
		this.location = location;
		this.blockStart =start;
		this.blockEnd = end;
		this.fixed = false;
		this.pageAccesses = new ArrayList<>();
	}

	public int getLocation() {
		return location;
	}

	public void setLocation(int location) {
		this.location = location;
	}

	public int getBlockStart() {
		return blockStart;
	}

	public void setBlockStart(int blockStart) {
		this.blockStart = blockStart;
	}

	public int getBlockEnd() {
		return blockEnd;
	}

	public void setBlockEnd(int blockEnd) {
		this.blockEnd = blockEnd;
	}

	public List<PageAccess> getPageAccesses() {
		return pageAccesses;
	}

	public void addPageAccess(PageAccess pa) {
		pageAccesses.add(pa);
	}

	public boolean isFixed() {
		return fixed;
	}

	public void setFixed(boolean fixed) {
		this.fixed = fixed;
	}

	public String getReplacedWith() {
		return replacedWith;
	}

	public void setReplacedWith(String replacedWith) {
		this.replacedWith = replacedWith;
	}

	public String[] getOriginalTryBlock() {
		return originalTryBlock;
	}

	public void setOriginalTryBlock(String[] originalTryBlock) {
		this.originalTryBlock = originalTryBlock;
	}
	
	public void restore(String[] po) {
		po[location-1] = originalTryBlock[0];
	}
}
