package org.au.requisitor.common;

import java.text.ParseException;

import junit.framework.TestCase;

public class VersionTest extends TestCase {

	public void testCompare() {
		Version v1 = new Version();
		Version v2 = new Version(1,0,0);
		compareVs(v1, v2);

		v2 = new Version(0,1,0);
		compareVs(v1, v2);
		
		v2 = new Version(0,0,1);
		compareVs(v1, v2);
		
		v1 = new Version(1,3,2);
		v2 = new Version(3,2,1);
		compareVs(v1, v2);		
	}
	
	private void compareVs(Version v1, Version v2) {
		assertEquals(v1.compareTo(v2), -1);
		assertEquals(v2.compareTo(v1), 1);
		assertEquals(v2.compareTo(v2), 0);
		
	}
	
	public void testFromString() {
		try {
			Version v1 = new Version("0.0.0");
			assertEquals("0.0.0", v1.toString());
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
	}
	
}
