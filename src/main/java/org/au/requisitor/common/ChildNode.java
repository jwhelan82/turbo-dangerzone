package org.au.requisitor.common;

import java.util.Collection;

public interface ChildNode extends Node {

	Version getVersion();
	ChildType getType();
	
	void addParent(ParentNode parent);
	Collection<ParentNode> getParentNodes();

}
