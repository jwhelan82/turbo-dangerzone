package org.au.requisitor.common;

import java.io.Serializable;
import java.util.Collection;

/**
 * Represents a company.
 * 
 * Company name is unique.
 * 
 * A company can have many projects.
 * 
 * @author James Whelan, Neil Hoskins
 *
 */
public class Company implements Serializable {

	private static final long serialVersionUID = -6621529588349066059L;

	private String name;
	private String email;
	
	// TODO when we implement ORM, this will be a one-to-many on projects
	private Collection<Project> projects;
	
}
