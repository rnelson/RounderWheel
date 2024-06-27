/*
 * RounderWheel DNS Resolver
 * 
 * Class:	    CSCE 855 - Distributed Operating Systems
 * Semester:	    Fall 2008
 * Student:	    Ross Nelson <rnelson@cse.unl.edu>
 * Due Date:	    14 November 2008, 10:30
 * Description:	    This assignment reinvents the wheel (makes it rounder,
 *		    perhaps?) by resolving DNS queries by hand.
 * 
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