package org.au.requisitor.common.dependencies;

import java.io.Serializable;

/**
 * Possible types of dependency.
 * 
 * @author James Whelan, Neil Hoskins
 *
 */
public enum DependencyType implements Serializable {
	DEV("D"), TEST("T");
	
	private String type;
	
	DependencyType(String type) {
		this.type = type;
	}
	
	public boolean isDevelopment() {
		return "D".equals(type);
	}
	
	public boolean isTest() {
		return "T".equals(type);
	}
}
