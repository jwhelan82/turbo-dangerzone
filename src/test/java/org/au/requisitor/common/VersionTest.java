package org.au.requisitor.common;

import java.text.ParseException;

import junit.framework.TestCase;

/**
 * 
 * @author Neil Hoskins
 *
 */
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
	
	public void testIncrementMajor() {
		Version v1 = new Version(1,0,0);
		Version v2 = v1.incrementMajor();
		
		assertEquals("2.0.0", v2.toString());
	}
	
	public void testIncrementMinor() {
		Version v1 = new Version(1,0,0);
		Version v2 = v1.incrementMinor();
		
		assertEquals("1.1.0", v2.toString());
		
	}
	
	public void testIncrementBuild() {
		Version v1 = new Version(1,0,0);
		Version v2 = v1.incrementBuild();
		
		assertEquals("1.0.1", v2.toString());
	}
	
	public void testLessThan() {
		
		Version v1 = new Version(1,0,0);
		Version v2 = v1.incrementMajor();
		assertEquals(true, v1.isLessThan(v2));
		
		Version v3 = new Version(1,0,0);
		Version v4 = v3.incrementMinor();
		assertEquals(true, v3.isLessThan(v4));
		
		Version v5 = new Version(1,0,0);
		Version v6 = v5.incrementBuild();
		assertEquals(true, v5.isLessThan(v6));
	}
	
	public void testGreaterThan() {
		
		Version v1 = new Version(1,0,0);
		Version v2 = v1.incrementMajor();
		assertEquals(true, v2.isGreaterThan(v1));
		
		Version v3 = new Version(1,0,0);
		Version v4 = v3.incrementMinor();
		assertEquals(true, v4.isGreaterThan(v3));
		
		Version v5 = new Version(1,0,0);
		Version v6 = v5.incrementBuild();
		assertEquals(true, v6.isGreaterThan(v5));
	}
	
	public void testEqualTo() {
		
		Version v1 = new Version(1,0,0);
		Version v2 = new Version(1,0,0);
		assertTrue(v1.isEqualTo(v2));
	}
}
