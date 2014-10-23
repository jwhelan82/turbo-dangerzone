package org.au.requisitor.common.action;

public enum ActionType {
    RequestUpdate,
    Update,
    CancelUpdate,
    CompleteUpdate,
    
    StartDevelopment,
    CompleteDevelopment,
    CancelDevelopment,
    
    StartTesting,
    PassTesting,
    FailTesting,
    CancelTesting,
}
