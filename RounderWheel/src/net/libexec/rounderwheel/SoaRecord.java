/*
 * RounderWheel DNS Resolver
 * Licensed under the MIT license.
 *
 * (C) 2008, 2024 Ross Nelson
 * Originally written for CSCE 855 (Distributed Operating Systems), University of Nebraska-Lincoln
 */

package net.libexec.rounderwheel;

import java.nio.*;

public class SoaRecord extends DnsRecord {
	public String MNAME;
	public String RNAME;
	public int SERIAL;
	public int REFRESH;
	public int RETRY;
	public int EXPIRE;
	public int MINIMUM;
	
	/**
	 * Parses the RDATA section for a SOA record
	 * 
	 * @param buf the {@link ByteBuffer} to read from
	 * @param length the length of the record
	 */
	public void parse(ByteBuffer buf, int length) {
		// Get the names; they're simple now too thanks to
		// my Helper class
		this.MNAME = Helper.getName(buf);		
		this.RNAME = Helper.getName(buf);
		
		// Get the other fields; they're all simple
		this.SERIAL = buf.getInt();
		this.REFRESH = buf.getInt();
		this.RETRY = buf.getInt();
		this.EXPIRE = buf.getInt();
		this.MINIMUM = buf.getInt();
	}
	
	/**
	 * Gets a string representation of this SOA record
	 * 
	 * @return the string reprsentation of this SOA record
	 */
	public String toString() {
		return "MNAME(" + this.MNAME + "), RNAME(" + this.RNAME + "), TTL(" + this.EXPIRE + ")";
	}
}
