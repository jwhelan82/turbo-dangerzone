package org.au.requisitor.common;

import java.io.Serializable;
import java.text.ParseException;

public final class Version implements Serializable, Comparable<Version> {

	private static final long serialVersionUID = -421809733995321827L;

	int majorNumber;
	int minorNumber;
	int buildNumber;

	public Version() {
		majorNumber = 0;
		minorNumber = 0;
		buildNumber = 0;
	}

	public Version(int maj, int min, int build) {
		this.majorNumber = maj;
		this.minorNumber = min;
		this.buildNumber = build;
	}
	
	public Version(String vString) throws ParseException {
		fromString(vString);
	}

	private void fromString(String vString) throws ParseException {
		if (vString != null) {
			String[] parts = vString.split("\\.");
			if (parts.length != 3) {
				throw new ParseException("Must have 3 version numbers", 0);
			}
			majorNumber = Integer.valueOf(parts[0]);
			minorNumber = Integer.valueOf(parts[1]);
			buildNumber = Integer.valueOf(parts[2]);
		}
	}

	@Override
	public final String toString() {
		return majorNumber + "." + minorNumber + "." + buildNumber;
	}

	public final int compareTo(Version v) {
		int comp = majorNumber - v.majorNumber;
		if (comp == 0) {
			comp = minorNumber - v.minorNumber;
			if (comp == 0) {
				comp = buildNumber - v.buildNumber;
			}
		}
		return comp == 0? 0 : comp > 0? 1 : -1;
	}
}
