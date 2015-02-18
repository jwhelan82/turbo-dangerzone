package org.au.requisitor.common.action;

public enum ActionType {
    RequestUpdate ("Request Update"),
    Update ("Update"),
    CancelUpdate ("Cancel Update"),
    NoUpdateRequired ("No Update Required"),
    CompleteUpdate ("Complete Update"),
    
    StartDevelopment ("Begin Development"),
    CompleteDevelopment ("Complete Development"),
    CancelDevelopment ("Cancel Development"),
    
    StartTesting ("Begin Testing"),
    PassTesting ("Pass"),
    FailTesting ("Fail"),
    CancelTesting ("Cancel Testing");
    
    String defaultDisplayTest;
    
    private ActionType(String displayText) {
    	this.defaultDisplayTest = displayText;
    }
}
