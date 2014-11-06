package org.au.requisitor.common.dependencies;

import java.io.Serializable;

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
public class Development extends Dependency implements Serializable {

	private static final long serialVersionUID = 7296885544669722149L;

	@Override
	public ChildType getType() {
		return ChildType.DEV;
	}

	@Override
	public void checkVersionState(Version version, Version pVersion, VersionStates vStates) {
		
		if (version.isGreaterThan(getVersion())) {
			vStates.childOutOfDate = true;
		}
	}

	@Override
	public Status getVersionState(Version projectVersion) {
		Status state = checkParentVersions();

		if (isValidUpdatingState(state)) {
			state = Status.Updating;
		}

		return state;
	}

}
