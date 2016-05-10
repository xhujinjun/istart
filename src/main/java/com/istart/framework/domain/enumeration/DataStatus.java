package com.istart.framework.domain.enumeration;


/**
 * The DataStatus enumeration.
 */
public enum DataStatus {
    VALIDATE("有效",1), NOVALIDATE("无效",0);
    
    private String name;
	
	private int index;
	
	private DataStatus(String name,int index){
		this.name = name;
		this.index = index;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}
	
}
