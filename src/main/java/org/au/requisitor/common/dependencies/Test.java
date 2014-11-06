package org.au.requisitor.common.dependencies;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedList;

import org.au.requisitor.common.ChildNode;
import org.au.requisitor.common.ChildType;
import org.au.requisitor.common.Dependency;
import org.au.requisitor.common.ParentNode;
import org.au.requisitor.common.Status;
import org.au.requisitor.common.Version;
import org.au.requisitor.common.VersionStates;
import org.au.requisitor.common.requirements.Requirement;

/**
 * 
 * @author James Whelan, Neil Hoskins
 *
 */
public class Test extends Dependency implements Serializable, ParentNode {

	private static final long serialVersionUID = -1205725343697613533L;

	private Collection<ChildNode> children = new LinkedList<>();
	
	@Override
	public ChildType getType() {
		return ChildType.TEST;
	}

	@Override
	public Collection<ChildNode> getChildNodes() {
		return children;
	}

	@Override
	public void addDependency(ChildNode node) {
		children.add(node);
		node.addParent(this);
	}
	
	@Override
	public void checkVersionState(Version version, Version pVersion, VersionStates vStates) {
		
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

		if (isValidUpdatingState(state)) {
			state = Status.Updating;
			
		} else if (state == Status.UpToDate) {

			// determine the state of children
			VersionStates vStates = VersionStates.getChildVersionStates(this, projectVersion);
			
			// out of date if there are children out of date
			if (vStates.childOutOfDate) {
				state = Status.InProgress;
			}
		}

		return state;
	}

}
