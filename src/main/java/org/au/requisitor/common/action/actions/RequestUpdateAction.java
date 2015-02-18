package org.au.requisitor.common.action.actions;

import org.au.requisitor.common.Node;
import org.au.requisitor.common.Status;
import org.au.requisitor.common.action.ActionNotAvailableException;
import org.au.requisitor.common.action.ActionType;
import org.au.requisitor.common.dependencies.Development;
import org.au.requisitor.common.dependencies.Test;
import org.au.requisitor.common.requirements.Requirement;

public class RequestUpdateAction extends AbstractAction {

	public RequestUpdateAction() {
		super(ActionType.RequestUpdate);
	}

	@Override
	public void act(Node node) throws ActionNotAvailableException {
		node.getAvailableActions();
		
		if (node instanceof Requirement) {
			Requirement r = (Requirement) node;
			r.setStatus(Status.NeedsUpdating);
			super.setRecursiveStatus(r, Status.WaitingForParent);
		} else if (node instanceof Development) {
			((Development) node).setStatus(Status.NeedsUpdating);
		} else if (node instanceof Test) {
			Test t = (Test) node;
			t.setStatus(Status.NeedsUpdating);
			super.setRecursiveStatus(t, Status.WaitingForParent);
		} 
		// Test runs can't be updated.
	}

}
