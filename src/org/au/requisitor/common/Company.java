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
	
	// TODO when we implement Hibernate, this will be a one-to-many on projects
	private Collection<Project> projects;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Collection<Project> getProjects() {
		return projects;
	}

	public void setProjects(Collection<Project> projects) {
		this.projects = projects;
	}
	
}
