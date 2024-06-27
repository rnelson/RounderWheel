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
import java.util.*;

public class DnsMessageHeader {
	public enum OpcodeType { QUERY, IQUERY, STATUS };
	public enum RcodeType { NoError, FormatError, ServerFailure, NameError, NotImplemented, Refused };
	
	// Top part of header, describing the rest of the data in the header
	public int ID;
	public short QR;
	public short OPCODE;
	public short AA;
	public short TC;
	public short RD;
	public short RA;
	public short Z;
	public short RCODE;
	public int QDCOUNT;
	public int ANCOUNT;
	public int NSCOUNT;
	public int ARCOUNT;
	
	/**
	 * Parses the DNS message header
	 *
	 * @param buf the data to parse from
	 */
	public void parse(ByteBuffer buf) {		
		// Row 1
		this.ID = buf.getShort();
		
		// Row 2
		short rowTwo = buf.getShort();
		this.QR =		(short) (rowTwo & 0x08000);
		this.OPCODE =	(short) (rowTwo & 0x07800);
		this.AA =		(short) (rowTwo & 0x00400);
		this.TC =		(short) (rowTwo & 0x00200);
		this.RD =		(short) (rowTwo & 0x00100);
		this.RA =		(short) (rowTwo & 0x00080);
		this.Z =		(short) (rowTwo & 0x00070);
		this.RCODE =	(short) (rowTwo & 0x0000F);
		
		// Row 3
		this.QDCOUNT = buf.getShort();
		
		// Row 4
		this.ANCOUNT = buf.getShort();
		
		// Row 5
		this.NSCOUNT = buf.getShort();
		
		// Row 6
		this.ARCOUNT = buf.getShort();
		
		if (InetAddress.Debug == true) {
			String output = "% Response code: ";
			
			switch (this.RCODE) {
				case 0:
					output += "No error condition";
					break;
				case 1:
					output += "Format error";
					break;
				case 2:
					output += "Server failure";
					break;
				case 3:
					output += "Name error";
					break;
				case 4:
					output += "Not implemented";
					break;
				case 5:
					output += "Query refused";
					break;
				default:
					output += "!!! We really broke it. :/ !!!";
					break;
			}
			
			System.out.println(output);
			
			output  = "  Header\n  ------\n";
			output += "    > ID =         " + this.ID + "\n";
			output += "    > Q/R =        " + this.QR + "\n";
			output += "    > Opcode =     " + this.OPCODE + "\n";
			output += "    > AA =         " + this.AA + "\n";
			output += "    > TC =         " + this.TC + "\n";
			output += "    > Recursive =  " + this.RD + "\n";
			output += "    > RA =         " + this.RA + "\n";
			output += "    > Z =          " + this.Z + "\n";
			output += "    > Rcode =      " + this.RCODE + "\n";
			output += "    > Q. Count =   " + this.QDCOUNT + "\n";
			output += "    > An. Count =  " + this.ANCOUNT + "\n";
			output += "    > NS Count =   " + this.NSCOUNT + "\n";
			output += "    > Add. Count = " + this.ARCOUNT;
			System.out.println(output);
		}
	}
	
	/**
	 * Creates a query header with the specified sequence ID
	 * 
	 * @param buf the {@link ByteBuffer} to write to
	 * @param sequenceId the sequence ID for this message
	 */
	public void constructQueryHeader(ByteBuffer buf, short sequenceId) {
		// Row 1: sequence ID
		buf.putShort(sequenceId);
		
		// Row 2: flags, all 0 for non-recursive queries
		buf.put((byte) 1);
		buf.put((byte) 0);
		
		// Row 3 (number of question sections = 1)
		buf.put((byte) 0);
		buf.put((byte) 1);
		
		// Row 4-6: no other sections
		for (int i = 0; i < 6; i++) {
			buf.put((byte) 0);
		}
	}
}
