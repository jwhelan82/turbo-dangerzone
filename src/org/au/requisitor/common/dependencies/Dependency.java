package org.au.requisitor.common.dependencies;

import java.io.Serializable;

import org.au.requisitor.common.ChildNode;
import org.au.requisitor.common.ChildType;
import org.au.requisitor.common.ParentNode;

/**
 * Represents a dependency.
 * 
 * A dependency is a development item, a testing item, or whatever else.
 * 
 * @author James Whelan, Neil Hoskins
 *
 */
public abstract class Dependency implements Serializable, ChildNode {

	private static final long serialVersionUID = 3774784501638109805L;

	private ParentNode parent;
	
	public abstract ChildType getType();

	public ParentNode getParent() {
		return parent;
	}
	
	
}
