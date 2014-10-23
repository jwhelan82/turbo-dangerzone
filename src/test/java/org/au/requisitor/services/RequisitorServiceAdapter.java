package org.au.requisitor.services;

import java.util.Collection;
import java.util.List;

import org.au.requisitor.common.ChildNode;
import org.au.requisitor.common.Company;
import org.au.requisitor.common.Node;
import org.au.requisitor.common.NodeId;
import org.au.requisitor.common.ParentNode;
import org.au.requisitor.common.Project;
import org.au.requisitor.common.Version;
import org.au.requisitor.common.VersionLevel;
import org.au.requisitor.common.action.Action;
import org.au.requisitor.common.action.ActionType;
import org.au.requisitor.common.requirements.Requirement;
import org.au.requisitor.common.search.SeachCritera;

public class RequisitorServiceAdapter implements RequisitorService {

	@Override
	public void createCompany(Company c) {
		// TODO Auto-generated method stub

	}

	@Override
	public void createProject(Project p) {
		// TODO Auto-generated method stub

	}

	@Override
	public void createRequirement(Requirement req) {
		// TODO Auto-generated method stub

	}

	@Override
	public void createDependency(ParentNode parent, ChildNode dep) {
		// TODO Auto-generated method stub

	}

	@Override
	public Company getCompanyById(NodeId id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Project> getProjectsForCompany(Company c) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Node> search(SeachCritera s) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ActionType> getValidActions(Node nodeId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void doAction(Node actee, Action action) {
		// TODO Auto-generated method stub

	}

	@Override
	public Version incrementVersion(VersionLevel versionIncrement) {
		// TODO Auto-generated method stub
		return null;
	}

}
