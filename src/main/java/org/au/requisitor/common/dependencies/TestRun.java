package org.au.requisitor.common.dependencies;

import java.util.Collections;
import java.util.List;

import org.au.requisitor.common.ChildNode;
import org.au.requisitor.common.ChildType;
import org.au.requisitor.common.Dependency;
import org.au.requisitor.common.ParentNode;
import org.au.requisitor.common.Status;
import org.au.requisitor.common.Version;
import org.au.requisitor.common.VersionStates;
import org.au.requisitor.common.action.ActionType;
import org.au.requisitor.common.requirements.Requirement;

public class TestRun extends Dependency {

	private static final long serialVersionUID = 4142549912416997367L;

	@Override
	public ChildType getType() {
		return ChildType.TESTRUN;
	}
	
	@Override
	public void checkVersionState(Version version, Version pVersion, VersionStates vStates) {
		
		// there are some valid test runs
		// TODO check all tests have test cases
		if (getVersion().isGreaterOrEqualTo(version)) {
			vStates.hasValidTests = true;
		}

		// the test runs are later than the current parent, but less
		// than the project
		if (getVersion().isGreaterOrEqualTo(version)
				&& getVersion().isLessThan(pVersion)) {
			vStates.testsRegressed = true;
		}			
	}
	
	@Override
	public Status getVersionState(Version projectVersion) {
		Status state = checkParentVersions();

		// test runs don't get updated with different versions, they do however become out of date
		if (state == Status.NeedsUpdating) {
			state = Status.Obsolete;
		} else if (isValidUpdatingState(state)) {
			state = Status.Updating;
		} 
		
		return state;
	}

	@Override
	public List<ActionType> getAvailableActions() {
		return Collections.emptyList();
	}

}
