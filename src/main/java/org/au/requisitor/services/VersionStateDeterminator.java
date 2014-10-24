package org.au.requisitor.services;

import org.au.requisitor.common.ChildNode;
import org.au.requisitor.common.ChildType;
import org.au.requisitor.common.ParentNode;
import org.au.requisitor.common.Status;
import org.au.requisitor.common.Version;

public class VersionStateDeterminator {
	
	public static Status getVersionState(ChildNode node, Version projectVersion) {
		Status state = checkParentVersions(node);
		
		if (node instanceof ParentNode && state == Status.UpToDate) {
			ParentNode pn = (ParentNode) node;
			if (!pn.getChildNodes().isEmpty()) {
				state = checkChildVersions((ParentNode) node, node.getVersion(), projectVersion);
			}
		}
		
		return state;
	}
	
	private static Status checkParentVersions(ChildNode node) {
		Status state = Status.UpToDate;
		for (ParentNode p : node.getParentNodes()) {
			if (p.getVersion().isGreaterThan(node.getVersion())) {
				state = Status.NeedsUpdating;
				break;
			}
			if (state == Status.UpToDate && p instanceof ChildNode) {
				state = state.getPrioritisedStatus(checkParentVersions((ChildNode) p));
			}
		}

		return state;
	}
	
	private static Status checkChildVersions(ParentNode node, Version version, Version projectVersion) {
		Status state = Status.UpToDate;
		for (ChildNode c : node.getChildNodes()) {
			
			// test runs determine if the parent is verified or not
			if (c.getType() == ChildType.TESTRUN) {
				if (version.isGreaterOrEqualTo(c.getVersion())) {
					state = state.getPrioritisedStatus(Status.CompleteNV);
				} else if (projectVersion.isGreaterOrEqualTo(c.getVersion())) {
					state = state.getPrioritisedStatus(Status.CompleteRV);
				}
				
			// if a child has a lower version than a parent, then that parent is not completed	
			} else if (version.isGreaterThan(c.getVersion())) {
				state = state.getPrioritisedStatus(Status.InProgress);
				
			// finally if one of the child nodes is in progress, we can skip the rest of the checks
			// because of the priority
			} else if (c instanceof ParentNode){
				state = checkChildVersions((ParentNode) c, version, projectVersion);
				if (state == Status.InProgress) {
					break;
				}
			}
		}
		
		return state;
	}

}
