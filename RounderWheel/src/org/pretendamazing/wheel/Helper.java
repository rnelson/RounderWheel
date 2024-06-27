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
import java.util.*;

public class Helper {
	/**
	 * Reads a name from a DNS message; handles compression
	 * and multiple segments separated by segment lengths. Note
	 * that it assumes the name starts at the current position of
	 * the {@link ByteBuffer}.
	 *
	 * @param buf the {@link ByteBuffer} to read from
	 * @return the name
	 * 
	 * Okay, it's 12:04 AM. I have been working on this method since
	 * about noon today. Why I'm having so much trouble with it I don't
	 * know. I eventually decided to find something other than the RFC
	 * and Wireshark output to help me.
	 * 
	 * I ended up looking through the code for dnsjava to find the
	 * section where they deal with compression (which is the part that
	 * has been tripping me up for somewhere in the neighborhood of 12
	 * hours). I've read the section on compression in the RFC numerous
	 * times and had many, many implemented attempts at reading names.
	 * I have a handful of backups that include various versions of
	 * Helper.java that I can provide upon request.
	 * 
	 * The code that I adapted for my use is the constructor for dnsjava's
	 * Name class that takes a DNSInput object. It can be found at:
	 * 
	 * http://www.dnsjava.org/dnsjava-current/org/xbill/DNS/Name.java
	 */
	 public static String getName(ByteBuffer buf) {
		StringBuffer name = new StringBuffer();
		boolean done = false;
		boolean moved = false;
		
		// Save the current position in case we change it
		int position = buf.position();
		
		while (!done) {
			byte size = buf.get(buf.position());
			
			switch (size & 0xC0) {
				case 0xC0:	// compression
					int offset = buf.getShort() & 0x3FFF;
					
					// Make sure we have a valid offset (something in the past)
					if (offset > buf.position()) {
						done = true;
					}
					
					// If we haven't kept track of where we're at, do that now
					if (!moved) {
						position = buf.position();
						moved = true;
					}
					
					// Move to the specified offset
					buf.position(offset);
					
					continue;
				default:	// no compression
					if (size == 0x00) {
						// Advance past that 0x00
						buf.get();
						
						// We're done!
						done = true;
					}
					else {
						// Get past the size
						size = buf.get();
						
						// Get the segment
						for (int i = 0; i < (int) size; i++) {
							name.append((char) buf.get());
						}
						
						// Check out the next byte; if it's not
						// 0x00, we need to add a '.'
						byte peek = buf.get(buf.position());
						if (peek != 0x00) {
							name.append(".");
						}
					}
					break;
			}
		}
		
		if (moved) {
		    buf.position(position);
		}
		
		return name.toString().toLowerCase();
	}
	
	/**
	 * Looks up the IP address for the specified server name
	 * 
	 * @param serverName the name to look up in the additional section
	 * @param additionalSection a {@link List} of {@link ResourceRecord}s from the additional section
	 * @return ip address (as a string) if found, else ""
	 */
	public static String getIpFromAdditionalSection(String serverName, List<ResourceRecord> additionalSection) {
		String ip = "";
		
		for (ResourceRecord rr : additionalSection) {
			if (rr.RDATA instanceof ARecord) {
				// Get the A record for this resource record
				ARecord a = (ARecord) rr.RDATA;
				
				// Check the name; if it's a match, update the
				// ip and quit
				if (rr.NAME.equalsIgnoreCase(serverName)) {
					ip = a.getRandomAddress().toString();
					break;
				}
			}
		}
		
		return ip;
	}
}