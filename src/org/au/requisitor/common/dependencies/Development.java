package org.au.requisitor.common.dependencies;

import java.io.Serializable;

import org.au.requisitor.common.ChildType;

/**
 * 
 * @author James Whelan, Neil Hoskins
 *
 */
public class Development extends Dependency implements Serializable {

	private static final long serialVersionUID = 7296885544669722149L;

	@Override
	public ChildType getType() {
		return ChildType.DEV;
	}

}
