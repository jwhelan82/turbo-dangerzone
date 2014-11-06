package org.au.requisitor.common;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedList;

import org.au.requisitor.common.dependencies.TestRun;
import org.au.requisitor.common.requirements.Requirement;

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

	Status status;
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

	@Override
	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	@Override
	public Status getVersionState(Version projectVersion) {
		Status state = checkParentVersions();

		// test runs don't get updated, they do however become out of date
		if (this instanceof TestRun && state == Status.NeedsUpdating) {
			state = Status.Obsolete;

			// nodes that are updating are allowed to continue unless a parent
			// is updating
			// the other option here is that the parent is waiting, which means
			// we wait as well
		} else if ((state == Status.UpToDate || state == Status.NeedsUpdating)
				&& getStatus() == Status.Updating) {
			state = Status.Updating;

			// the parent state is ok, check children
		} else if (this instanceof ParentNode && state == Status.UpToDate) {
			// determine the state of children
			ParentNode pn = (ParentNode) this;
			VersionStates vStates = new VersionStates();
			
			for (ChildNode child : pn.getChildNodes()) {
				child.checkVersionState(getVersion(), projectVersion, vStates);
			}
			
			// out of date if there are children out of date
			if (vStates.childOutOfDate) {
				state = Status.InProgress;

				// requirements have the completed status
			} else if (this instanceof Requirement) {
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

	protected Status checkParentVersions() {
		Status state = Status.UpToDate;
		for (ParentNode p : getParentNodes()) {
			if (p.getStatus() == Status.Updating) {
				state = Status.WaitingForParent;
			} else if (p.getVersion().isGreaterThan(getVersion())) {
				state = Status.NeedsUpdating;
				break;
			}
			if (state == Status.UpToDate && p instanceof Dependency) {
				state = state.getPrioritisedStatus(((Dependency) p).checkParentVersions());
			}
		}

		return state;
	}
}
