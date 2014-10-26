package org.au.requisitor.services;

import java.util.Collection;

import org.au.requisitor.common.ChildNode;
import org.au.requisitor.common.ChildType;
import org.au.requisitor.common.ParentNode;
import org.au.requisitor.common.Status;
import org.au.requisitor.common.Version;
import org.au.requisitor.common.dependencies.TestRun;
import org.au.requisitor.common.requirements.Requirement;

public class VersionStateDeterminator {
	
	private static class VersionStates {
		boolean childOutOfDate;
		boolean hasValidTests;
		boolean testsRegressed;
		boolean zombieNode;
	}
	
	public static Status getVersionState(ChildNode node, Version projectVersion) {
		Status state = checkParentVersions(node);
		
		// test runs don't get updated, they do however become out of date
		if (node instanceof TestRun && state == Status.NeedsUpdating) {
			state = Status.Obsolete;

		// nodes that are updating are allowed to continue unless a parent is updating
		// the other option here is that the parent is waiting, which means we wait as well		
		} else if ((state == Status.UpToDate || state == Status.NeedsUpdating) && node.getStatus() == Status.Updating) {
			state = Status.Updating;
			
		// the parent state is ok, check children	
		} else if (node instanceof ParentNode && state == Status.UpToDate){
			// determine the state of children
			ParentNode pn = (ParentNode) node;
			VersionStates vStates = new VersionStates();
			checkChildVersions(pn.getChildNodes(), node.getVersion(), projectVersion, vStates);
			
			// out of date if there are children out of date
			if (vStates.childOutOfDate) {
				state = Status.InProgress;
				
			// requirements have the completed status
			} else if (node instanceof Requirement) {
				if (vStates.zombieNode) {
					state = Status.Planned;
				} else if (vStates.hasValidTests){	
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
	
	private static void checkChildVersions(Collection<ChildNode> children, Version nodeVersion, 
			Version projectVersion, VersionStates vStates) {
		for (ChildNode child : children) {			
			if (child instanceof TestRun) {
				// there are some valid test runs 
				// TODO check all tests have test cases
				if (child.getVersion().isGreaterOrEqualTo(nodeVersion)) {
					vStates.hasValidTests = true;
				}
				
				// the test runs are later than the current parent, but less than the project
				if (child.getVersion().isGreaterOrEqualTo(nodeVersion) && child.getVersion().isLessThan(projectVersion)) {
					vStates.testsRegressed = true;
				}
				
			// check if any other node is out of date
			} else if (nodeVersion.isGreaterThan(child.getVersion())) {
				vStates.childOutOfDate = true;
			}
			
			if (child instanceof ParentNode) {
				checkChildVersions(((ParentNode) child).getChildNodes(), nodeVersion, projectVersion, vStates);
			}
		}
	}

	private static Status checkParentVersions(ChildNode node) {
		Status state = Status.UpToDate;
		for (ParentNode p : node.getParentNodes()) {
			if (p.getStatus() == Status.Updating) {
				state = Status.WaitingForParent;
			}
			else if (p.getVersion().isGreaterThan(node.getVersion())) {
				state = Status.NeedsUpdating;
				break;
			}
			if (state == Status.UpToDate && p instanceof ChildNode) {
				state = state.getPrioritisedStatus(checkParentVersions((ChildNode) p));
			}
		}

		return state;
	}

}
