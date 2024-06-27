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

public class DnsMessage {
	public DnsMessageHeader Header;
	public List<QuestionSection> Questions;
	public List<ResourceRecord> Answers;
	public List<ResourceRecord> Authorities;
	public List<ResourceRecord> Additionals;
	
	/**
	 * Creates a new {@link DnsMessage} object
	 */
	public DnsMessage() {
		this.Questions = new ArrayList<QuestionSection>();
		this.Answers = new ArrayList<ResourceRecord>();
		this.Authorities = new ArrayList<ResourceRecord>();
		this.Additionals = new ArrayList<ResourceRecord>();
	}
	
	/**
	 * Parses a {@link DnsMessage}
	 * 
	 * @param data the {@link ByteBuffer} to read from
	 * @throws java.lang.Exception an unsuccessful RCODE was returned
	 */
	public void parse(ByteBuffer data) throws Exception {
		// Parse the header
		Header = new DnsMessageHeader();
		Header.parse(data);
		
		if (Header.RCODE != 0 && Header.RCODE != 3 && InetAddress.Debug == true) {
			String message = "Query response code: ";
			
			switch (Header.RCODE) {
				case 0:
					message += "No error condition";
					break;
				case 1:
					message += "Format error";
					break;
				case 2:
					message += "Server failure";
					break;
				case 3:
					message += "Name error";
					break;
				case 4:
					message += "Not implemented";
					break;
				case 5:
					message += "Query refused";
					break;
				default:
					message += "!!! We really broke it. :/ !!!";
					break;
			}
			
			throw new Exception(message);
		}
		
		// Parse the rest of the message
		QuestionSection.parse(data, Questions,   Header.QDCOUNT);
		ResourceRecord.parse (data, Answers,     Header.ANCOUNT);
		ResourceRecord.parse (data, Authorities, Header.NSCOUNT);
		ResourceRecord.parse (data, Additionals, Header.ARCOUNT);
		
		if (InetAddress.Debug == true) {
			String output = "";
			output += "% Parsed Message\n";
			output += "%   ID:           " + Header.ID + "\n";
			output += "%   QR:           " + (int) (0x0000F000 & Header.QR) + "\n";
			output += "%   Questions:    " + Header.QDCOUNT + "\n";
			output += "%   Answers:      " + Header.ANCOUNT + "\n";
			output += "%   Authorities:  " + Header.NSCOUNT + "\n";
			output += "\n";
			
			if (Header.QDCOUNT > 0) {
				int number = 0;
				
				for (QuestionSection qs : this.Questions) {
					number++;
					
					output += "%   Question " + number + "\n";
					output += "%     Name:         " + qs.QNAME + "\n";
					output += "%     Class:        " + qs.QCLASS + "\n";
					output += "%     Type:         " + qs.QTYPE + "\n";
				}
				
				output += "\n";
			}
			
			if (Header.NSCOUNT > 0) {
				for (ResourceRecord rr : this.Authorities) {
					if (rr.RDATA instanceof NsRecord) {
						NsRecord r = (NsRecord) rr.RDATA;
						output += "%   Authority:    " + r.NSDNAME + "\n";
					}
					
					if (rr.RDATA instanceof SoaRecord) {
						SoaRecord r = (SoaRecord) rr.RDATA;
						
						output += "%        SOA:    " + r + "\n";
					}
				}
			}
			
			if (Header.ANCOUNT > 0) {
				for (ResourceRecord rr : this.Answers) {
					if (rr.RDATA instanceof ARecord) {
						ARecord r = (ARecord) rr.RDATA;
						
						for (Address a : r.Addresses) {
							output += "%           A:    " + a + "\n";
						}
					}
					
					if (rr.RDATA instanceof CnameRecord) {
						CnameRecord r = (CnameRecord) rr.RDATA;
						
						output += "%       CNAME:    " + r + "\n";
					}
					
					if (rr.RDATA instanceof SoaRecord) {
						SoaRecord r = (SoaRecord) rr.RDATA;
						
						output += "%        SOA:    " + r + "\n";
					}
				}
			}
			
			System.out.println(output);
		}
	}
	
	/**
	 * Choses an authority at random from the list
	 * 
	 * @return a {@link ResourceRecord} from the authority section
	 */
	public ResourceRecord getRandomAuthority() {
		if (this.Authorities.size() < 1) {
			return null;
		}
		
		// Pick a random server
		Random r = new Random();
		int index = r.nextInt(this.Authorities.size());
		ResourceRecord ret = this.Authorities.get(index);
		
		return ret;
	}
	
	/**
	 * Constructs a query for a given hostname
	 * 
	 * @param hostname the hostname to query for
	 * @return a {@link ByteBuffer} containing the query
	 */
	public ByteBuffer getQuery(String hostname) {
		ByteBuffer h = ByteBuffer.allocate(1024000);
		
		// Add the header
		DnsMessageHeader header = new DnsMessageHeader();
		Random r = new Random();
		short id = (short) r.nextInt(65535);
		header.constructQueryHeader(h, id);
		
		if (InetAddress.Debug == true)
			System.out.println("% Created query with ID " + id);
		
		// Add a question section
		QuestionSection q = new QuestionSection();		
		q.QNAME = hostname;		// QNAME  = `hostname`
		q.QTYPE = 1;			// QTYPE  = A (1), * (255)
		q.QCLASS = 1;			// QCLASS = IN (1)
		q.getQuestionSection(h);
		
		if (InetAddress.Debug == true) {
			System.err.println("% Question Section");
			System.err.println("%   QNAME:  " + q.QNAME);
			System.err.println("%   QTYPE:  " + q.QTYPE);
			System.err.println("%   QCLASS: " + q.QCLASS);
		}
		
		return h;
	}
}
