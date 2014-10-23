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

public interface RequisitorService {

    // create stuff
    void createCompany(Company c);
    void createProject(Project p);
    void createRequirement(Requirement req);
    void createDependency(ParentNode parent, ChildNode dep);    

    // get stuff
    Company getCompanyById(NodeId id);
    Collection<Project> getProjectsForCompany(Company c);
    List<Node> search(SeachCritera s);
        
    // do stuff    
    List<ActionType> getValidActions(Node nodeId);
    void doAction(Node actee, Action action);
    
    // versions
    Version incrementVersion(VersionLevel versionIncrement);    
}