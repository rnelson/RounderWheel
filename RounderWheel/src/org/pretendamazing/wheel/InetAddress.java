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

import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.*;
import java.nio.channels.*;
import java.util.*;

public class InetAddress {
	public static final boolean Debug = false;
	
	/**
	 * The port that DNS runs on
	 */
	public static final int DnsPort = 53;
	
	/**
	 * Queries a random root server for a hostname and prints out
	 * the result. Only understands A, CNAME, and SOA. The returned
	 * object is all but useless.
	 * 
	 * @param host the host to query for
	 * @return ignore it.
	 */
	public static InetAddress getByName(String host) {		
		try {
			// Create the variables we'll need; while I could write a
			// recursive function to deal with all of this crap, I'm
			// much too tired to do anything but the simplest of solutions.
			ByteBuffer query = null;
			DatagramChannel channel = null;
			DatagramSocket socket = null;
			DnsMessage message = null;
			InetAddress ret = new InetAddress();
			RootServers am = new RootServers();
			SocketAddress address = null;
			SocketAddress server = null;
			boolean done = false;
			
			String serverName = am.getRandomServer(true).getHostname();
			
			while (!done) {
			    // Start up a local socket to receive on
			    channel = DatagramChannel.open();
			    address = new InetSocketAddress(0);
			    socket = channel.socket();
			    socket.bind(address);

			    // Open a socket for the remote end
			    server = new InetSocketAddress(serverName, InetAddress.DnsPort);
			    channel.connect(server);

			    // Construct a query to send to the server
			    message = new DnsMessage();
			    query = message.getQuery(host);
			    query.order(ByteOrder.BIG_ENDIAN);

			    // Send the query!
			    query.flip();
			    channel.write(query);

			    // Receive the reply
			    query.clear();
			    channel.read(query);
			    query.flip();

			    // Do some work!
			    message.parse(query);

			    // Disconnect from the server
			    channel.disconnect();
			    
			    // Check the answer section for the data we want
			    if (message.Header.ANCOUNT > 0) {
				    done = true;
				    
				    // Separate CNAME and A records
				    ArrayList<ResourceRecord> cnameRecords = new ArrayList<ResourceRecord>();
				    ArrayList<ResourceRecord> aRecords = new ArrayList<ResourceRecord>();
				    
				    for (ResourceRecord rr : message.Answers) {
					    switch (rr.TYPE) {
						    case 5: // CNAME
							    cnameRecords.add(rr);
							    break;
						    case 1: // A
							    aRecords.add(rr);
							    break;
					    }
				    }
				    
				    // Print out the CNAME if it exists
				    if (cnameRecords.size() > 0) {
					    ResourceRecord record = cnameRecords.get(0);
					    CnameRecord c = (CnameRecord) record.RDATA;
					    
					    String key = record.NAME;
					    String value = c.CNAME;
					    
					    System.out.println(key + " A = CNAME to " + value);
				    }
				    
				    // Print out all A records
				    for (ResourceRecord record : aRecords) {
					    ARecord a = (ARecord) record.RDATA;
					    
					    String key = host;
					    
					    for (Address addr : a.Addresses) {
						    String value = addr.toString();
						    
						    System.out.println(key + " A = " + value);
					    }
				    }
				    
			    }
			    else {
				    // Do we have authoritative nameservers or other authorities to ask?
				    // Maybe we have a reply code telling us that the domain doesn't exist!
				    if (message.Header.RCODE == (short) 3) {
					    System.out.println(host + " A = does not exist");
					    done = true;
				    }
				    else if (message.Header.NSCOUNT > 0) {
					    boolean valid = false;
					    int count = 0;
					    
					    while (!valid && count < 8 * message.Header.NSCOUNT) {
						    // Get a random authority to ask
						    ResourceRecord auth = message.getRandomAuthority();
						    
						    if (auth.RDATA instanceof NsRecord) {
							    // We have a nameserver to query! Update our
							    // server name
							    NsRecord ns = (NsRecord) auth.RDATA;
							    serverName = ns.NSDNAME;
							    
							    // Get the IP address
							    String ip = Helper.getIpFromAdditionalSection(serverName, message.Additionals);
							    
							    if (ip.length() > 0) {
								    serverName = ip;
							    }
							    
							    if (serverName.length() > 0) {
								    valid = true;
							    }
						    }
						    
						    count++;
					    }
				    }
				    else if (message.Header.ARCOUNT > 0) {
					    boolean valid = false;
					    int count = 0;
					    
					    while (!valid && count < 8 * message.Header.ARCOUNT) {
						    // Get a random authority to ask
						    ResourceRecord auth = message.getRandomAuthority();
						    
						    if (auth.RDATA instanceof NsRecord) {
							    // We have a nameserver to query! Update our
							    // server name
							    NsRecord ns = (NsRecord) auth.RDATA;
							    serverName = ns.NSDNAME;
							    
							    valid = true;
						    }
						    
						    count++;
					    }
				    }
				    else {
					    throw new Exception();
				    }
			    }
			}
			
			return ret;
		}
		catch (Exception e) {
			System.err.println("An unexpected error has occurred.");
		}
		
		return null;
	}
}