package org.au.requisitor.common.action;

import org.au.requisitor.common.Node;

public interface Action {
    ActionType getActionType();
    void act(Node node) throws ActionNotAvailableException;
}