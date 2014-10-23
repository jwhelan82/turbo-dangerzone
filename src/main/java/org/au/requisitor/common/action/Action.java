package org.au.requisitor.common.action;

import org.au.requisitor.common.Node;

public interface Action {
    ActionType getActionType();
    boolean isActionAvailable(Node node);
    void act(Node node);
}