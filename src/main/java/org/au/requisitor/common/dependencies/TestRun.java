package org.au.requisitor.common.dependencies;

import org.au.requisitor.common.ChildType;

public class TestRun extends Dependency {

	private static final long serialVersionUID = 4142549912416997367L;

	@Override
	public ChildType getType() {
		return ChildType.TESTRUN;
	}

}
