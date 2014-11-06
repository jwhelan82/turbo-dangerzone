package org.au.requisitor.common.dependencies;

import org.au.requisitor.common.ChildType;
import org.au.requisitor.common.Dependency;
import org.au.requisitor.common.Version;
import org.au.requisitor.common.VersionStates;

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
}
