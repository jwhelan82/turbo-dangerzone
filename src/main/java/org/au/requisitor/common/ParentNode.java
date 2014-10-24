package org.au.requisitor.common;

import java.util.Collection;


/**
 * Interface that can be used to define a parent node  
 * 
 * @author James Whelan, Neil Hoskins
 *
 */
public interface ParentNode extends Node {
	public Collection<ChildNode> getChildNodes();
	public void addDependency(ChildNode node);
}
