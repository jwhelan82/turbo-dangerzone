package org.au.requisitor.common;

import java.io.Serializable;

/**
 * Possible types of dependency.
 * 
 * @author James Whelan, Neil Hoskins
 *
 */
public enum ChildType implements Serializable {
	REQ("R"), DEV("D"), TEST("T");
	
	private String type;
	
	ChildType(String type) {
		this.type = type;
	}
	
	public boolean isRequirement() {
		return "R".equals(type);
	}
	
	public boolean isDevelopment() {
		return "D".equals(type);
	}
	
	public boolean isTest() {
		return "T".equals(type);
	}
}
