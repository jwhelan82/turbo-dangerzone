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
	
	/**
	 * This method will check the relative status of this dependency with its parents.
	 * For example if a parent is a higher version, then this dependency needs to be updated. 
	 * @return the status relative to the parents.
	 */
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
	
	/**
	 * Method to check if the state is updating and that is valid based off parent states. 
	 * @param state The state of the parents based off the checkParentVersions() method
	 * @return true if updating is a valid state for this dependency
	 */
	protected boolean isValidUpdatingState(Status state) {
		return (state == Status.UpToDate || state == Status.NeedsUpdating)
				&& getStatus() == Status.Updating;
	}
}
