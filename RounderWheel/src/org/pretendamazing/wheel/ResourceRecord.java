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

public class ResourceRecord {	
	public String NAME;
	public short TYPE;
	public short CLASS;
	public int TTL;
	public short RDLENGTH;
	public DnsRecord RDATA;
	
	/**
	 * Parses a resource record
	 * 
	 * @param buf the {@link ByteBuffer} to read from
	 * @param records the {@link List} of {@link ResourceRecord}s to add to
	 * @param count the count of this type of record
	 * @throws java.lang.Exception
	 */
	public static void parse(ByteBuffer buf, List<ResourceRecord> records, int count) throws Exception {
		// Get each question section
		for (int i = 0; i < count; i++) {
			ResourceRecord rr = new ResourceRecord();
			
			// Get the name
			rr.NAME = Helper.getName(buf);
			
			// Get the simple number values
			rr.TYPE = buf.getShort();
			rr.CLASS = buf.getShort();
			rr.TTL = buf.getInt();
			rr.RDLENGTH = buf.getShort();
			
			// Create the correct object type based on rr.TYPE			
			switch (rr.TYPE) {
				case 1: // A
					rr.RDATA = new ARecord();
					((ARecord) rr.RDATA).parse(buf, rr.RDLENGTH);
					break;
				case 2: // NS
					rr.RDATA = new NsRecord();
					((NsRecord) rr.RDATA).parse(buf, rr.RDLENGTH);
					break;
				case 5: // CNAME
					rr.RDATA = new CnameRecord();
					((CnameRecord) rr.RDATA).parse(buf, rr.RDLENGTH);
					break;
				case 6: // SOA					
					rr.RDATA = new SoaRecord();
					((SoaRecord) rr.RDATA).parse(buf, rr.RDLENGTH);
					break;
				default:
					/*
					System.err.println("Incoming exception from ResourceRecord.parse()");
					//System.err.println("  Name length: " + nameLength);
					
					System.err.println("  Name:        " + rr.NAME);
					System.err.println("  Type:        " + rr.TYPE);
					System.err.println("  Class:       " + rr.CLASS);
					System.err.println("  TTL:         " + rr.TTL);
					System.err.println("  Data length: " + rr.RDLENGTH);
					throw new Exception("resource record contains type not implemented (" + rr.TYPE + ")");
					*/
			}
			
			records.add(rr);
		}
	}
}