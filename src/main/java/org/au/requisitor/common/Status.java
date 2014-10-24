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

	// priority: NeedsUpdate > InProgress > CompleteNV > CompleteRV > CompleteV > others
	NeedsUpdating("NU", 10),
	InProgress("I", 9),
	CompleteNV("CNV", 8),
	CompleteRV("CRV", 7),
	CompleteV("CV", 6),
	UpToDate("UTD", 5),
	
	Planned("PL"),
	WaitingForRequirements("WFR"),
	Updating("U"),

	NeedsDevelopment("NDEV"),
	UnderDevelopment("UDEV"),
	ReadyForBuild("RFB"),

	WaitingForDevelopment("WFD"),
	ReadyForTesting("RFT"),
	Testing("TEST"),
	VerifiedToDate("VER"),
	FailedTesting("FAIL"),

	;

	private final String status;
	private final int priority;
	
	Status(String status) {
		this.status = status;
		this.priority = 0;
	}
	
	Status(String status, int priority) {
		this.status = status;
		this.priority = priority;
	}
	

	public Status getPrioritisedStatus(Status newStatus) {
		return (newStatus.priority > priority)? newStatus : this;
	}
}
