package org.au.requisitor.common.requirements;

/**
 * Possible states of a requirement
 * 
 * @author James Whelan, Neil Hoskins
 *
 */
public enum RequirementStatus {
	
	INVALID("I"), COMPLETE("C"), VERIFIED("V");
	
	RequirementStatus(String status) {
		this.status = status;
	}
	
	private String status;
	
	public boolean isInvalid() {
		return "I".equals(status);
	}
	
	public boolean isComplete() {
		return "C".equals(status);
	}
	
	public boolean isVerified() {
		return "V".equals(status);
	}
}