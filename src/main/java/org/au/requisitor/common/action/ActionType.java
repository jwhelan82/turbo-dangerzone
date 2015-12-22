package org.au.requisitor.common.action;

public enum ActionType {
    // Versioned dependency actions
    Incorporate ("Incorporate"),
    Reject("Reject"),
    MarkNotRequired("Mark Not Required"),
    
    // instanced dependency actions
    Pass ("Pass"),
    Fail ("Fail"),
    
    // singularity actions
    Cancel("Cancel"),
    
    
    // Obsolete actions
    RequestUpdate ("Request Update"),
    Update ("Update"),
    CancelUpdate ("Cancel Update"),
    NoUpdateRequired ("No Update Required"),
    CompleteUpdate ("Complete Update"),
    
    StartDevelopment ("Begin Development"),
    CompleteDevelopment ("Complete Development"),
    CancelDevelopment ("Cancel Development"),
    
    StartTesting ("Begin Testing"),
    CancelTesting ("Cancel Testing");
    
    String defaultDisplayTest;
    
    private ActionType(String displayText) {
    	this.defaultDisplayTest = displayText;
    }
}
