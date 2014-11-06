package org.au.requisitor.services;

import org.au.requisitor.common.ChildNode;
import org.au.requisitor.common.Status;
import org.au.requisitor.common.Version;

public class VersionStateDeterminator {
	
	public static Status getVersionState(ChildNode node, Version projectVersion) {
		return node.getVersionState(projectVersion);
	}
}
