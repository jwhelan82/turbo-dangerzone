package org.au.requisitor.common.action;

import org.au.requisitor.common.Node;

public class ActionNotAvailableException extends Exception {

	ActionType type;
	Node node;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ActionNotAvailableException(Node node, ActionType type) {
		this.node = node;
		this.type = type;
	}

	public ActionType getType() {
		return type;
	}

	public void setType(ActionType type) {
		this.type = type;
	}

	public Node getNode() {
		return node;
	}

	public void setNode(Node node) {
		this.node = node;
	}
	
}
