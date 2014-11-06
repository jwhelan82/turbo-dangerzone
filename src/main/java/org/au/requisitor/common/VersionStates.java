package org.au.requisitor.common;

public class VersionStates {
	// there is a child that has a version less than its parent version
	public boolean childOutOfDate;
	
	// all tests are up to date for this parent
	public boolean hasValidTests;
	
	// there are tests that are complete but out of date
	public boolean testsRegressed;
	
	// one of the children is a parent that doesn't have any children, but needs to
	public boolean zombieNode;
	
	
	/**
	 * Helper method to return a 'version states' object based on how each child sees its state.
	 * 
	 * @param pn
	 * @param projectVersion
	 * @return
	 */
	public static VersionStates getChildVersionStates(ParentNode pn, Version projectVersion) {
		
		// determine the state of children
		VersionStates vStates = new VersionStates();
		
		for (ChildNode child : pn.getChildNodes()) {
			child.checkVersionState(pn.getVersion(), projectVersion, vStates);
		}
		
		return vStates;
	}
}