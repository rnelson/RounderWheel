/*
 * RounderWheel DNS Resolver
 * 
 * Class:	    CSCE 855 - Distributed Operating Systems
 * Semester:	    Fall 2008
 * Student:	    Ross Nelson <rnelson@cse.unl.edu>
 * Due Date:	    14 November 2008, 10:30
 * Description:	    This assignment reinvents the wheel (makes it rounder,
 *		    perhaps?) by resolving DNS queries by hand.
 * File ID:	    $Id$
 * 
 */

package org.pretendamazing.wheel;

import java.nio.*;
import java.util.*;

public class QuestionSection {
	public String QNAME;
	public short QTYPE;
	public short QCLASS;
	
	/**
	 * Parses a question section
	 * 
	 * @param buf the {@link ByteBuffer} to read from
	 * @param questions a {@link List} of {@link QuestionSection}s to add to
	 * @param count the number of {@link QuestionSection}s
	 */
	public static void parse(ByteBuffer buf, List<QuestionSection> questions, int count) {
		// Get each question section
		for (int i = 0; i < count; i++) {
			QuestionSection qs = new QuestionSection();
			
			// Get the name
			qs.QNAME = Helper.getName(buf);
			
			// Get the type and class values
			qs.QTYPE = buf.getShort();
			qs.QCLASS = buf.getShort();
			
			// Save the question
			questions.add(qs);
		}
	}
	
	/**
	 * Adds a {@link QuestionSection} to the given buffer
	 * 
	 * @param buf the {@link ByteBuffer} to write to
	 */
	public void getQuestionSection(ByteBuffer buf) {		
		// Hostname
		String strHostname = (this.QNAME.trim().toLowerCase());
		
		// Reverse the string; we will use this to get counts
		StringBuffer reverseHostnameBuffer = new StringBuffer(strHostname.length());
		
		for (int i = strHostname.length() - 1; i >= 0; i--) {
			reverseHostnameBuffer.append(strHostname.charAt(i));
		}
		
		String reverseHostname = reverseHostnameBuffer.toString();
		
		// Count the lengths of each section
		Stack segmentLengths = new Stack();
		int segmentLength = 0;
		for (int i = 0; i < reverseHostname.length(); i++) {
			char current = reverseHostname.charAt(i);
			
			if (current == '.') {
				// If we found a period, save and reset the length
				segmentLengths.push(new Integer(segmentLength));
				segmentLength = 0;
			}
			else if (i == (reverseHostname.length() - 1)) {
				segmentLength++;
				segmentLengths.push(new Integer(segmentLength));
			}
			else {
				segmentLength++;
			}
		}
		
		// Put the hostname into the buffer
		byte len = ((Integer) segmentLengths.pop()).byteValue();
		buf.put(len);
		int loc = 0, length = strHostname.length();
		
		while (loc < length) {
			char current = strHostname.charAt(loc);
			
			if (current == '.') {
				len = ((Integer) segmentLengths.pop()).byteValue();
				buf.put(len);
			}
			else {
				buf.put((byte) current);
			}
			
			loc++;
		}
		
		// Null terminate the string
		buf.put((byte) 0);
		
		// Type and class
		buf.putShort(this.QTYPE);
		buf.putShort(this.QCLASS);
	}
}