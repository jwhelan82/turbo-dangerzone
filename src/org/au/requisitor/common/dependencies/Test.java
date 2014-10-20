package org.au.requisitor.common.dependencies;

import java.io.Serializable;

import org.au.requisitor.common.ChildType;

/**
 * 
 * @author James Whelan, Neil Hoskins
 *
 */
public class Test extends Dependency implements Serializable {

	private static final long serialVersionUID = -1205725343697613533L;

	@Override
	public ChildType getType() {
		return ChildType.TEST;
	}

}
