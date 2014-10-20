package org.au.requisitor.common.requirements;

import java.util.ArrayList;
import java.util.Collection;

import org.au.requisitor.common.ChildNode;
import org.au.requisitor.common.ChildType;
import org.au.requisitor.common.ParentNode;
import org.au.requisitor.common.Version;
import org.au.requisitor.common.dependencies.Development;
import org.au.requisitor.common.dependencies.Test;


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
public class Requirement implements ParentNode, ChildNode {

	private static final long serialVersionUID = 1068855009582972250L;
	
	private String name;
	private Version version;	// TODO we should create a 'Version' class
	private String description;
	private RequirementStatus status;
	private ParentNode parent;
	private Collection<ChildNode> childNodes;
	
	public Requirement() {
		status = RequirementStatus.INVALID;
	}
	
	@Override
	public ChildType getType() {
		return ChildType.REQ;
	}

	public Collection<Requirement> getChildRequirements() {
		Collection<Requirement> reqs = new ArrayList<>();
		for (ChildNode r : childNodes) {
			if (ChildType.REQ.equals(r.getType())) {
				reqs.add((Requirement) r);
			}
		}
		return reqs;
	}
	
	public Collection<Development> getDevelopmentDependencies() {
		Collection<Development> dd = new ArrayList<>();
		for (ChildNode d : childNodes) {
			if (ChildType.DEV.equals(d.getType())) {
				dd.add((Development) d);
			}
		}
		return dd;
	}
	
	public Collection<Test> getTestDependencies() {
		Collection<Test> td = new ArrayList<>();
		for (ChildNode d : childNodes) {
			if (ChildType.TEST.equals(d.getType())) {
				td.add((Test) d);
			}
		}
		return td;
	}
}
