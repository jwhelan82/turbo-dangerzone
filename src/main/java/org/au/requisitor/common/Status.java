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
	WaitingForParent("WFP", 11), // a parent is being updated
	NeedsUpdating("NU", 10), // there is a higher parent version
	InProgress("I", 9), // there is a lower child version
	CompleteV("CV", 8),  // there is at least one test run at the current project version
	CompleteRV("CRV", 7), // there a test run but less than the current project version 
	CompleteNV("CNV", 6), // there is no test run >= current version 
	UpToDate("UTD", 5), // all children are less than the current version
	
	Planned("PL"),
	Updating("U"),

	NeedsDevelopment("NDEV"),
	UnderDevelopment("UDEV"),
	ReadyForBuild("RFB"),

	WaitingForDevelopment("WFD"),
	ReadyForTesting("RFT"),
	Testing("TEST"),
	VerifiedToDate("VER"),
	FailedTesting("FAIL"),
	
	Obsolete("Ob") // test run is for a past version of a requirement
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
