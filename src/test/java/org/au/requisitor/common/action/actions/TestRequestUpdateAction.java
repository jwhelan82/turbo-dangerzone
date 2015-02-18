package org.au.requisitor.common.action.actions;

import junit.framework.TestCase;

import org.au.requisitor.common.Status;
import org.au.requisitor.common.action.ActionNotAvailableException;
import org.au.requisitor.common.dependencies.Development;
import org.au.requisitor.common.dependencies.Test;
import org.au.requisitor.common.dependencies.TestRun;
import org.au.requisitor.common.requirements.Requirement;

public class TestRequestUpdateAction extends TestCase {

	public void testAction() throws ActionNotAvailableException {
		
		Requirement r = new Requirement();
		Development d = new Development();
		Test t = new Test();
		TestRun tr = new TestRun();
		r.addDependency(d);
		r.addDependency(t);
		t.addDependency(tr);
		
		r.setStatus(Status.InProgress);
		d.setStatus(Status.UnderDevelopment);
		t.setStatus(Status.WaitingForDevelopment);
		tr.setStatus(Status.Obsolete);
		
		RequestUpdateAction action = new RequestUpdateAction();
		action.act(r);
		
		assertEquals(r.getStatus(), Status.NeedsUpdating);
		assertEquals(d.getStatus(), Status.WaitingForParent);
		assertEquals(t.getStatus(), Status.WaitingForParent);
		assertEquals(tr.getStatus(), Status.WaitingForParent);
	}
	
	public void testAction2() {
		
	}
	}
	
}
