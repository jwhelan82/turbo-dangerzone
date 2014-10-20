package org.au.requisitor.common.requirements;

import java.util.ArrayList;
import java.util.Collection;

import org.au.requisitor.common.ParentNode;
import org.au.requisitor.common.dependencies.Dependency;
import org.au.requisitor.common.dependencies.DependencyType;


/**
 * Represents a requirement.
 * 
 * A requirement has dependencies (e.g. development, testing etc).
 * 
 * Requirements must have a parent which is a project or a requirement.
 * 
 * Initially, all requirements have a status of invalid.
 * 
 * A Requirement is only valid when all children of a Requirement are Dependencies or other valid Requirements. 
 * 
 * @author James Whelan, Neil Hoskins
 *
 */
public class Requirement implements ParentNode {

	private static final long serialVersionUID = 1068855009582972250L;
	
	private String name;
	private String version;	// TODO we should create a 'Version' class
	private String description;
	private RequirementStatus status;
	private ParentNode parent;
	private Collection<Requirement> childRequirements;
	private Collection<Dependency> dependencies;
	
	public Requirement() {
		status = RequirementStatus.INVALID;
	}
	
	public Collection<Dependency> getDevelopmentDependencies() {
		Collection<Dependency> dd = new ArrayList<>();
		for (Dependency d : dependencies) {
			if (DependencyType.DEV.equals(d.getType())) {
				dd.add(d);
			}
		}
		return dd;
	}
	
	public Collection<Dependency> getTestDependencies() {
		Collection<Dependency> td = new ArrayList<>();
		for (Dependency d : dependencies) {
			if (DependencyType.TEST.equals(d.getType())) {
				td.add(d);
			}
		}
		return td;
	}
}
