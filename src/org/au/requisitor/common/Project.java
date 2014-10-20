package org.au.requisitor.common;

import java.util.Collection;

import org.au.requisitor.common.requirements.Requirement;

/**
 * Represents a Project.
 * 
 * A project has requirements.
 * 
 * Project names are unique for each company, but not globally.
 * 
 * @author James Whelan, Neil Hoskins
 *
 */
public class Project implements ParentNode {

	private static final long serialVersionUID = -3949764880583964494L;

	private String name;
	
	private Version version;
	
	// TODO when we implement ORM, this is a reference to the owning Company
	private Company company;
	
	// TODO when we implement ORM, this will be a one-to-many on requirements
	private Collection<Requirement> requirements;
	
	
}
