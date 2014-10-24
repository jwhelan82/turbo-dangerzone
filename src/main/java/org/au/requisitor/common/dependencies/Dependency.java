package org.au.requisitor.common.dependencies;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedList;

import org.au.requisitor.common.ChildNode;
import org.au.requisitor.common.ChildType;
import org.au.requisitor.common.ParentNode;
import org.au.requisitor.common.Version;

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

	Version version;
	private Collection<ParentNode> parents = new LinkedList<>();
	
	public abstract ChildType getType();

	@Override
	public void addParent(ParentNode parent) {
		parents.add(parent);
	}

	@Override
	public Collection<ParentNode> getParentNodes() {
		return parents;
	}

	public Version getVersion() {
		return version;
	}

	public void setVersion(Version version) {
		this.version = version;
	}
}
