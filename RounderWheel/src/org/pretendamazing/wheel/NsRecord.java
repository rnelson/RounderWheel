/*
 * RounderWheel DNS Resolver
 * Licensed under the MIT license.
 *
 * (C) 2008, 2024 Ross Nelson
 * Originally written for CSCE 855 (Distributed Operating Systems), University of Nebraska-Lincoln
 */

package org.pretendamazing.wheel;

import java.nio.*;

public class NsRecord extends DnsRecord {
	public String NSDNAME;
	
	/**
	 * Parses an NS record's RDATA
	 * 
	 * @param buf the {@link ByteBuffer} to read from
	 * @param length the length of the data section
	 */
	public void parse(ByteBuffer buf, int length) {		
		this.NSDNAME = Helper.getName(buf);
	}
	
	/**
	 * Gets a string representation of this record
	 * 
	 * @return the nameserver hostname
	 */
	public String toString() {
		return this.NSDNAME;
	}
}