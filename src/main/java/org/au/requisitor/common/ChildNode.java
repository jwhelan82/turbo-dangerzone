package org.au.requisitor.common;

import java.util.Collection;

public interface ChildNode extends Node {

	Version getVersion();
	ChildType getType();
	
	void addParent(ParentNode parent);
	Collection<ParentNode> getParentNodes();

	// Get the status of the current node
	Status getVersionState(Version projectVersion);
	
	// Get the status of all child nodes, this fills out the vStates object
	void checkVersionState(Version version, Version pVersion, VersionStates vStates);

}
