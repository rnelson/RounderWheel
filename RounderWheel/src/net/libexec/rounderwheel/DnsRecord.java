/*
 * RounderWheel DNS Resolver
 * Licensed under the MIT license.
 *
 * (C) 2008, 2024 Ross Nelson
 * Originally written for CSCE 855 (Distributed Operating Systems), University of Nebraska-Lincoln
 */

package net.libexec.rounderwheel;

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
