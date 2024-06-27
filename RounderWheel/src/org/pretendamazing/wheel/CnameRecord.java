/*
 * RounderWheel DNS Resolver
 * 
 * Class:         CSCE 855 - Distributed Operating Systems
 * Semester:      Fall 2008
 * Student:       Ross Nelson <rnelson@cse.unl.edu>
 * Due Date:      14 November 2008, 10:30
 * Description:   This assignment reinvents the wheel (makes it rounder,
 *                perhaps?) by resolving DNS queries by hand.
 * 
 */

package org.pretendamazing.wheel;

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