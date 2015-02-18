package org.au.requisitor.common.action.actions;

import org.au.requisitor.common.ChildNode;
import org.au.requisitor.common.Dependency;
import org.au.requisitor.common.Node;
import org.au.requisitor.common.ParentNode;
import org.au.requisitor.common.Status;
import org.au.requisitor.common.action.Action;
import org.au.requisitor.common.action.ActionNotAvailableException;
import org.au.requisitor.common.action.ActionType;

public abstract class AbstractAction implements Action {

	public ActionType actionType;
	
	protected AbstractAction(ActionType actionType) {
		this.actionType = actionType;
	}
	
	@Override
	public ActionType getActionType() {
		return actionType;
	}

	protected void verifyActionAvailable(Node node) throws ActionNotAvailableException {
		if (!node.getAvailableActions().contains(getActionType())) {
			throw new ActionNotAvailableException(node, getActionType());
		}
	}
	
	protected void setRecursiveStatus(ParentNode parent, Status status) {
		for (ChildNode child : parent.getChildNodes()) {			
			((Dependency) child).setStatus(status);
			if (child instanceof ParentNode) {
				setRecursiveStatus((ParentNode)child, status);
			}
		}
	}
}
