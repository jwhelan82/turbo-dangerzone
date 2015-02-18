package org.au.requisitor.common;

import java.io.Serializable;
import java.util.List;

import org.au.requisitor.common.action.ActionNotAvailableException;
import org.au.requisitor.common.action.ActionType;

public interface Node extends Serializable {
	Version getVersion();
	Status getStatus();
	List<ActionType> getAvailableActions() throws ActionNotAvailableException;
}
