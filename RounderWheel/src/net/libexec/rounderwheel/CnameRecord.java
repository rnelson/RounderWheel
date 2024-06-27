/*
 * RounderWheel DNS Resolver
 * Licensed under the MIT license.
 *
 * (C) 2008, 2024 Ross Nelson
 * Originally written for CSCE 855 (Distributed Operating Systems), University of Nebraska-Lincoln
 */

package net.libexec.rounderwheel;

import java.nio.*;

public class CnameRecord extends DnsRecord {
	public String CNAME;
	
	/**
	 * Parses the RDATA section for a CNAME
	 * 
	 * @param buf the {@link ByteBuffer} to read from
	 * @param length unused
	 */
	public void parse(ByteBuffer buf, int length) {
		this.CNAME = Helper.getName(buf);
	}
	
	/**
	 * Returns a string representation of the CNAME record
	 * 
	 * @return the hostname
	 */
	public String toString() {
		return this.CNAME;
	}
}
