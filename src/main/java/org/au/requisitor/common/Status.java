package org.au.requisitor.common;

/**
 * Possible states of a requirement
 * 
 * @author James Whelan, Neil Hoskins
 *
 */
//TODO this should be consolidated into a generic Status class that 
// can be used by other types
public enum Status {
	

	Planned("PL"),
	WaitingForRequirements("WFR"),
	NeedsUpdating("NU"),
	Updating("U"),
	Incomplete("I"),
	CompleteNV("CNV"),
	CompleteV("CV"),
	CompleteRV("CRV"),

	NeedsDevelopment("NDEV"),
	UnderDevelopment("UDEV"),
	ReadyForBuild("RFB"),
	UpToDate("UTD"),


	WaitingForDevelopment("WFD"),
	ReadyForTesting("RFT"),
	Testing("TEST"),
	VerifiedToDate("VER"),
	FailedTesting("FAIL"),

	;

	
	Status(String status) {
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
