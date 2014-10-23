package org.au.requisitor.services;

import java.util.Collections;
import java.util.List;

import junit.framework.TestCase;

import org.au.requisitor.common.Node;
import org.au.requisitor.common.Status;
import org.au.requisitor.common.action.ActionType;
import org.au.requisitor.common.requirements.Requirement;

public class TestGetValidActions extends TestCase {

	RequisitorService testReqService = new RequisitorServiceAdapter() {
		@Override
		public List<ActionType> getValidActions(Node nodeId) {
			return Collections.emptyList();
		}
	};

	// TODO this is a work in progress
	public void testPlannedState() {
		Requirement req = new Requirement();
		req.setStatus(Status.Planned);
		
		// first of all, check that we have no actions for the planned type
		List<ActionType> types = testReqService.getValidActions(req);
		assertTrue(types.isEmpty());		
	}
	
}
