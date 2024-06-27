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

import java.nio.ByteBuffer;

public abstract class DnsRecord {
	/**
	 * Parses a record
	 * 
	 * @param buf the {@link ByteBuffer} to read from
	 * @param length the length of the record
	 */
	public abstract void parse(ByteBuffer buf, int length);
}
