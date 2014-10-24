package org.au.requisitor.common.dependencies;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedList;

import org.au.requisitor.common.ChildNode;
import org.au.requisitor.common.ChildType;
import org.au.requisitor.common.ParentNode;

/**
 * 
 * @author James Whelan, Neil Hoskins
 *
 */
public class Test extends Dependency implements Serializable, ParentNode {

	private static final long serialVersionUID = -1205725343697613533L;

	private Collection<ChildNode> children = new LinkedList<>();
	
	@Override
	public ChildType getType() {
		return ChildType.TEST;
	}

	@Override
	public Collection<ChildNode> getChildNodes() {
		return children;
	}

	@Override
	public void addDependency(ChildNode node) {
		children.add(node);
		node.addParent(this);
	}
}
