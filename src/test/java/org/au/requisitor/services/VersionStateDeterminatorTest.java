package org.au.requisitor.services;

import junit.framework.TestCase;

import org.au.requisitor.common.Status;
import org.au.requisitor.common.Version;
import org.au.requisitor.common.dependencies.Development;
import org.au.requisitor.common.dependencies.Test;
import org.au.requisitor.common.dependencies.TestRun;
import org.au.requisitor.common.requirements.Requirement;

public class VersionStateDeterminatorTest extends TestCase {
	
	public void testRequirementTree() {
		Version projectVersion = new Version(1,0,0);
		Requirement r = new Requirement();
		r.setVersion(new Version(1,0,0));
		
//		assertEquals(VersionStateDeterminator.getVersionState(r, projectVersion), Status.UpToDate);
		
		// add a child requirement
		Requirement r2 = new Requirement();
		r2.setVersion(new Version(1,0,0));
		r.addDependency(r2);
		assertEquals(VersionStateDeterminator.getVersionState(r, projectVersion), Status.CompleteNV);		
		assertEquals(VersionStateDeterminator.getVersionState(r2, projectVersion), Status.CompleteNV);		

		// add another child requirement
		Requirement r3 = new Requirement();
		r3.setVersion(new Version(1,0,0));
		r.addDependency(r3);
		assertEquals(VersionStateDeterminator.getVersionState(r, projectVersion), Status.CompleteNV);		
		assertEquals(VersionStateDeterminator.getVersionState(r2, projectVersion), Status.CompleteNV);		
		assertEquals(VersionStateDeterminator.getVersionState(r3, projectVersion), Status.CompleteNV);		
		
		// now change the versions, first R, children should be out of date now
		r.setVersion(new Version(2,0,0));
		assertEquals(VersionStateDeterminator.getVersionState(r, projectVersion), Status.InProgress);		
		assertEquals(VersionStateDeterminator.getVersionState(r2, projectVersion), Status.NeedsUpdating);		
		assertEquals(VersionStateDeterminator.getVersionState(r3, projectVersion), Status.NeedsUpdating);		
		
		// now add dev dependencies for the children.
		Development d2 = new Development();
		d2.setVersion(new Version(1,0,0));
		r2.addDependency(d2);
		assertEquals(VersionStateDeterminator.getVersionState(r, projectVersion), Status.InProgress);		
		assertEquals(VersionStateDeterminator.getVersionState(r2, projectVersion), Status.NeedsUpdating);	
		assertEquals(VersionStateDeterminator.getVersionState(d2, projectVersion), Status.NeedsUpdating);
		assertEquals(VersionStateDeterminator.getVersionState(r3, projectVersion), Status.NeedsUpdating);						
		
		// now complete the tree, R is higher version so all need updating except for him
		Development d3 = new Development();
		d3.setVersion(new Version(1,0,0));
		r3.addDependency(d3);		
		assertEquals(VersionStateDeterminator.getVersionState(r, projectVersion), Status.InProgress);		
		assertEquals(VersionStateDeterminator.getVersionState(r2, projectVersion), Status.NeedsUpdating);	
		assertEquals(VersionStateDeterminator.getVersionState(d2, projectVersion), Status.NeedsUpdating);
		assertEquals(VersionStateDeterminator.getVersionState(r3, projectVersion), Status.NeedsUpdating);
		assertEquals(VersionStateDeterminator.getVersionState(d3, projectVersion), Status.NeedsUpdating);
	}
	
	
	
	public void testDevelopmentOnlyDependencies() {
		Version projectVersion = new Version(1,0,0);
		Requirement r = new Requirement();
		r.setVersion(new Version(0,1,0));
		
		// add one development item
		Development d1 = new Development();
		d1.setVersion(new Version(0,1,0));
		r.addDependency(d1);
		
		assertEquals(VersionStateDeterminator.getVersionState(r, projectVersion), Status.CompleteNV);
		assertEquals(VersionStateDeterminator.getVersionState(d1, projectVersion), Status.UpToDate);
		
		// change the version of the parent
		r.setVersion(new Version(0,2,0));
		assertEquals(VersionStateDeterminator.getVersionState(r, projectVersion), Status.InProgress);
		assertEquals(VersionStateDeterminator.getVersionState(d1, projectVersion), Status.NeedsUpdating);
		
		// add another dev dependency at the same version as the requirement
		// D1 is still behind, D2 is up to date with the req
		Development d2 = new Development();
		d2.setVersion(new Version(0,2,0));
		r.addDependency(d2);
		assertEquals(VersionStateDeterminator.getVersionState(r, projectVersion), Status.InProgress);
		assertEquals(VersionStateDeterminator.getVersionState(d1, projectVersion), Status.NeedsUpdating);
		assertEquals(VersionStateDeterminator.getVersionState(d2, projectVersion), Status.UpToDate);

		
		// now update D1 to a higher version
		d1.setVersion(new Version(0,3,0));
		assertEquals(VersionStateDeterminator.getVersionState(r, projectVersion), Status.CompleteNV);
		assertEquals(VersionStateDeterminator.getVersionState(d1, projectVersion), Status.UpToDate);
		assertEquals(VersionStateDeterminator.getVersionState(d2, projectVersion), Status.UpToDate);		
	}
	
	public void testTestRunVersions() {
		Requirement r = new Requirement();
		Development d = new Development();
		Test t = new Test();
		TestRun tr = new TestRun();
		
		// set up dependencies
		r.addDependency(d);
		r.addDependency(t);
		t.addDependency(tr);
		
		// set versions, all the same
		Version v = new Version(1,0,0);
		r.setVersion(v);
		d.setVersion(v);
		t.setVersion(v);
		tr.setVersion(v);

		assertEquals(VersionStateDeterminator.getVersionState(r, v), Status.CompleteV);		
		assertEquals(VersionStateDeterminator.getVersionState(d, v), Status.UpToDate);
		assertEquals(VersionStateDeterminator.getVersionState(t, v), Status.UpToDate);
		assertEquals(VersionStateDeterminator.getVersionState(tr, v), Status.UpToDate);

		// now update the requirement
		r.setStatus(Status.Updating);
		assertEquals(VersionStateDeterminator.getVersionState(r, v), Status.Updating);		
		assertEquals(VersionStateDeterminator.getVersionState(d, v), Status.WaitingForParent);
		assertEquals(VersionStateDeterminator.getVersionState(t, v), Status.WaitingForParent);
		assertEquals(VersionStateDeterminator.getVersionState(tr, v), Status.WaitingForParent);

		// finished updating, now the version of the req is higher
		v = v.incrementBuild();
		r.setVersion(v);
		r.setStatus(Status.UpToDate);
		assertEquals(VersionStateDeterminator.getVersionState(r, v), Status.InProgress);		
		assertEquals(VersionStateDeterminator.getVersionState(d, v), Status.NeedsUpdating);
		assertEquals(VersionStateDeterminator.getVersionState(t, v), Status.NeedsUpdating);
		assertEquals(VersionStateDeterminator.getVersionState(tr, v), Status.Obsolete);

		// now bring the test and development up to a higher version
		v = v.incrementBuild();
		d.setVersion(v);
		t.setVersion(v);
		
		assertEquals(VersionStateDeterminator.getVersionState(r, v), Status.CompleteNV);		
		assertEquals(VersionStateDeterminator.getVersionState(d, v), Status.UpToDate);
		assertEquals(VersionStateDeterminator.getVersionState(t, v), Status.UpToDate);
		assertEquals(VersionStateDeterminator.getVersionState(tr, v), Status.Obsolete); // actually obsolete
		
		// add another test
		v = v.incrementBuild();
		TestRun tr2 = new TestRun();
		tr2.setVersion(v);
		t.addDependency(tr2);

		assertEquals(VersionStateDeterminator.getVersionState(r, v), Status.CompleteV);		
		assertEquals(VersionStateDeterminator.getVersionState(d, v), Status.UpToDate);
		assertEquals(VersionStateDeterminator.getVersionState(t, v), Status.UpToDate);
		assertEquals(VersionStateDeterminator.getVersionState(tr, v), Status.Obsolete); // actually obsolete
		assertEquals(VersionStateDeterminator.getVersionState(tr2, v), Status.UpToDate);
		
		// now increment the build, the Test run will push the requirment out of date
		v = v.incrementBuild();
		assertEquals(VersionStateDeterminator.getVersionState(r, v), Status.CompleteRV);		
		assertEquals(VersionStateDeterminator.getVersionState(d, v), Status.UpToDate);
		assertEquals(VersionStateDeterminator.getVersionState(t, v), Status.UpToDate);
		assertEquals(VersionStateDeterminator.getVersionState(tr, v), Status.Obsolete); // actually obsolete
		assertEquals(VersionStateDeterminator.getVersionState(tr2, v), Status.UpToDate);		
	}
}
