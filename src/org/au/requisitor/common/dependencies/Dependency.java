package org.au.requisitor.common.dependencies;

import java.io.Serializable;

import org.au.requisitor.common.requirements.Requirement;

/**
 * Represents a dependency.
 * 
 * A dependency is a development item, a testing item, or whatever else.
 * 
 * @author James Whelan, Neil Hoskins
 *
 */
public abstract class Dependency implements Serializable {

	private static final long serialVersionUID = 3774784501638109805L;

	private Requirement parent;
	private DependencyType type;
	
	public DependencyType getType() {
		return type;
	}
}
