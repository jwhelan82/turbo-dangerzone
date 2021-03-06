package org.au.requisitor.common.requirements;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.au.requisitor.common.ChildNode;
import org.au.requisitor.common.ChildType;
import org.au.requisitor.common.Dependency;
import org.au.requisitor.common.ParentNode;
import org.au.requisitor.common.Project;
import org.au.requisitor.common.Status;
import org.au.requisitor.common.Version;
import org.au.requisitor.common.VersionStates;
import org.au.requisitor.common.action.ActionMapBuilder;
import org.au.requisitor.common.action.ActionType;
import org.au.requisitor.common.dependencies.Development;
import org.au.requisitor.common.dependencies.Test;


/**
 * Represents a requirement.
 * 
 * A requirement has dependencies (e.g. development, testing etc).
 * 
 * Requirements must have a parent which is a project or a requirement.
 * 
 * Initially, all requirements have a status of Planned.
 * 
 * A Requirement is only valid when all children of a Requirement are Dependencies or other valid Requirements. 
 * 
 * @author James Whelan, Neil Hoskins
 *
 */
public class Requirement extends Dependency implements ParentNode {

	private static final long serialVersionUID = 1068855009582972250L;
	
	private String name;
	private Version version;	
	private String description;
	private Status status;
	private Project project;
	private ParentNode parent;
	private Collection<ChildNode> childNodes;
	
	public Requirement() {
		status = Status.Planned;
		childNodes = new LinkedList<>();
	}
	
	@Override
	public ChildType getType() {
		return ChildType.REQ;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Version getVersion() {
		return version;
	}

	public void setVersion(Version version) {
		this.version = version;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public ParentNode getParent() {
		return parent;
	}

	public void setParent(ParentNode parent) {
		this.parent = parent;
	}

	public void addDependency(ChildNode node) {
		this.childNodes.add(node);
		node.addParent(this);
	}
	
	public Collection<ChildNode> getChildNodes() {
		return childNodes;
	}

	public void setChildNodes(Collection<ChildNode> childNodes) {
		this.childNodes = childNodes;
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

	@Override
	public Collection<ParentNode> getParentNodes() {
		if (parent == null) {
			return Collections.EMPTY_LIST;
		}
		return Collections.singletonList(parent);
	}

	@Override
	public void addParent(ParentNode parent) {
		this.parent = parent;
	}

	@Override
	public void checkVersionState(Version version, Version pVersion,
			VersionStates vStates) {
		
		if (version.isGreaterThan(getVersion())) {
			vStates.childOutOfDate = true;
		}

		for (ChildNode child : getChildNodes()) {
			child.checkVersionState(version, pVersion, vStates);
		}
	}
	
	@Override
	public Status getVersionState(Version projectVersion) {
		Status state = checkParentVersions();

			// nodes that are updating are allowed to continue unless a parent
			// is updating
			// the other option here is that the parent is waiting, which means
			// we wait as well
		if (isValidUpdatingState(state)) {
			state = Status.Updating;

			// the parent state is ok, check children
		} else if (state == Status.UpToDate) {

			// determine the state of children
			VersionStates vStates = VersionStates.getChildVersionStates(this, projectVersion);
			
			// out of date if there are children out of date
			if (vStates.childOutOfDate) {
				state = Status.InProgress;

				// requirements have the completed status
			} else {
				if (vStates.zombieNode) {
					state = Status.Planned;
				} else if (vStates.hasValidTests) {
					if (vStates.testsRegressed) {
						state = Status.CompleteRV;
					} else {
						state = Status.CompleteV;
					}
				} else {
					state = Status.CompleteNV;
				}
			}
		}

		return state;
	}

	@Override
	public List<ActionType> getAvailableActions() {
		return availableActions.getActions(getStatus());
	}

	static ActionMapBuilder availableActions;
	static {
		availableActions = new ActionMapBuilder();
		availableActions
			.addStatus(Status.Planned)
				.addActions(ActionType.Update)
			.addStatus(Status.NeedsUpdating)
				.addActions(ActionType.Update, ActionType.NoUpdateRequired, ActionType.CancelUpdate)
			.addStatus(Status.Updating)
				.addActions(ActionType.CompleteUpdate, ActionType.CancelUpdate)
			.addStatus(Status.InProgress)
				.addActions(ActionType.RequestUpdate)
			.addStatus(Status.CompleteNV)
				.addActions(ActionType.RequestUpdate)
			.addStatus(Status.CompleteV)
				.addActions(ActionType.RequestUpdate)
			.addStatus(Status.CompleteRV)
				.addActions(ActionType.RequestUpdate)
			;
	}
}
