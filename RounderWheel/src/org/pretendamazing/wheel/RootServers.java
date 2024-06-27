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

import java.util.*;

public class RootServers {
	private ArrayList<RootServer> servers;
	
	/**
	 * Creates a new {@link RootServers}
	 */
	public RootServers() {
		this.servers = new ArrayList<RootServer>();
		
		servers.add(new RootServer("A", "198.41.0.4", "2001:503:ba3e::2:30", "VeriSign", "Dulles, Virginia, US"));
		servers.add(new RootServer("B", "192.228.79.201", "2001:478:65::53", "USC-ISI", "Marina Del Rey, California, US"));
		servers.add(new RootServer("C", "192.33.4.12", "", "Cogent Communications", "anycast"));
		servers.add(new RootServer("D", "128.8.10.90", "", "University of Maryland", "College Park, Maryland, US"));
		servers.add(new RootServer("E", "192.203.230.10", "", "NASA", "Mountain View, California, US"));
		servers.add(new RootServer("F", "192.5.5.241", "2001:500:2f::f", "ISC", "anycast"));
		servers.add(new RootServer("G", "192.112.36.4", "", "Defense Information Systems Agency", "Columbus, Ohio, US"));
		servers.add(new RootServer("H", "128.63.2.53", "2001:500:1::803f:235", "US Army Research Lab", "Aberdeen Proving Ground, Maryland, US", RootServer.DnsSoftware.NDS));
		servers.add(new RootServer("I", "192.36.148.17", "2001:7fe::53", "Autonomica", "anycast"));
		servers.add(new RootServer("J", "192.58.128.30", "2001:503:c27::2:30", "VeriSign", "anycast"));
		servers.add(new RootServer("K", "193.0.14.129", "2001:7fd::1", "RIPE NCC", "anycast", RootServer.DnsSoftware.NDS));
		servers.add(new RootServer("L", "199.7.83.42", "2001:500:3::42", "ICANN", "anycast", RootServer.DnsSoftware.NDS));
		servers.add(new RootServer("M", "202.12.27.33", "2001:dc3::35", "WIDE Project", "anycast"));
	}
	
	/**
	 * Gets a random {@link RootServer}
	 * 
	 * @return a random {@link RootServer}
	 */
	public RootServer getRandomServer() {
		return getRandomServer(false);
	}
	
	/**
	 * Gets a random {@link RootServer} with/without preference for IPv6-enabled servers
	 * 
	 * @param favorIpv6 {@link true} to favor ipv6-enabled servers, else {@link false}
	 * @return a random {@link RootServer}
	 */
	public RootServer getRandomServer(boolean favorIpv6) {
		// Pick a random server
		Random r = new Random();
		int index = r.nextInt(this.servers.size());
		RootServer ret = this.servers.get(index);
		
		// Do extra work if the caller wants IPv6
		if (favorIpv6) {
			int lookups = 1;
			
			while (ret.getIpv6Address() == "") {
				index = r.nextInt(this.servers.size());
				ret = this.servers.get(index);
				
				// Make sure we don't run into an infinite loop
				lookups++;
				
				if (lookups > 100 * this.servers.size())
					break;
			}
		}
		
		return ret;
	}
	
	/**
	 * Gets a {@link List} of all {@link RootServer}s
	 * 
	 * @return a {@link List} of all {@link RootServer}s
	 */
	public ArrayList<RootServer> getServers() {
		return this.servers;
	}
}
