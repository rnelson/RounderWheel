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

public class RootServer {
	public enum DnsSoftware { BIND, NDS, DJBDNS }
	
	private String letter;
	private String ipv4Address;
	private String ipv6Address;
	private String operator;
	private String location;
	private DnsSoftware software;
	
	/**
	 * Creates a new {@link RootServer}
	 * 
	 * @param letter the root server's letter
	 * @param ipv4 the root server's IP
	 */
	public RootServer(String letter, String ipv4) {
		setLetter(letter);
		setIpv4Address(ipv4);
		setIpv6Address("");
		setOperator("");
		setLocation("");
		setSoftware(DnsSoftware.BIND);
	}
	
	/**
	 * Creates a new {@link RootServer}
	 * 
	 * @param letter the root server's letter
	 * @param ipv4 the root server's IPv4 address
	 * @param ipv6 the root server's IPv6 address
	 * @param operator the operator of the root server
	 * @param location the location of the root server
	 */
	public RootServer(String letter, String ipv4, String ipv6, String operator, String location) {
		setLetter(letter);
		setIpv4Address(ipv4);
		setIpv6Address(ipv6);
		setOperator(operator);
		setLocation(location);
		setSoftware(DnsSoftware.BIND);
	}
	
	/**
	 * Creates a new {@link RootServer}
	 * 
	 * @param letter the root server's letter
	 * @param ipv4 the root server's IPv4 address
	 * @param ipv6 the root server's IPv6 address
	 * @param operator the operator of the root server
	 * @param location the location of the root server
	 * @param software the software the server runs
	 */
	public RootServer(String letter, String ipv4, String ipv6, String operator, String location, DnsSoftware software) {
		setLetter(letter);
		setIpv4Address(ipv4);
		setIpv6Address(ipv6);
		setOperator(operator);
		setLocation(location);
		setSoftware(software);
	}
	
	/**
	 * Gets a string representation of this server
	 * 
	 * @return a string representation of this server
	 */
	public String toString() {
		String hostname = getHostname();
		String v4 = getIpv4Address();
		String v6 = getIpv6Address();
		String operator = getOperator();
		String location = getLocation();
		
		String ret = hostname + " -> ";
		
		if (v6 != "") {
			ret += "{" + v4 + ", [" + v6 + "]}";
		}
		else {
			ret += v4;
		}
		
		if (operator != "") {
			ret += "\n" + operator;
			
			if (location != "") {
				ret += " (" + location + ")";
			}
		}
		
		return ret;
	}
	
	/**
	 * Gets the hostname for this server
	 * 
	 * @return the server's hostname
	 */
	public String getHostname() {
		return getLetter().toLowerCase() + ".root-servers.net";
	}
	
	/**
	 * Gets the hostname for this letter
	 * 
	 * @return the server's letter
	 */
	public String getLetter() {
		return this.letter;
	}
	
	/**
	 * Sets the letter for this server
	 * 
	 * @param letter the server's letter
	 */
	public void setLetter(String letter) {
		this.letter = letter;
	}
	
	/**
	 * Gets the ipv4 address for this server
	 * 
	 * @return the server's ipv4 address
	 */
	public String getIpv4Address() {
		return this.ipv4Address;
	}
	
	/**
	 * Sets the ipv4 address for this server
	 * 
	 * @param ipv4Address the server's ipv4 address
	 */
	public void setIpv4Address(String ipv4Address) {
		this.ipv4Address = ipv4Address;
	}
	
	/**
	 * Gets the ipv6 address for this server
	 * 
	 * @return the server's ipv6 address
	 */
	public String getIpv6Address() {
		return this.ipv6Address;
	}
	
	/**
	 * Sets the ipv6 address for this server
	 * 
	 * @param ipv6Address the server's ipv6 address
	 */
	public void setIpv6Address(String ipv6Address) {
		this.ipv6Address = ipv6Address;
	}
	
	/**
	 * Gets the operator for this server
	 * 
	 * @return the server's operator
	 */
	public String getOperator() {
		return this.operator;
	}
	
	/**
	 * Sets the operator for this server
	 * 
	 * @param operator the server's operator
	 */
	public void setOperator(String operator) {
		this.operator = operator;
	}
	
	/**
	 * Gets the location for this server
	 * 
	 * @return the server's location
	 */
	public String getLocation() {
		return this.location;
	}
	
	/**
	 * Sets the location for this server
	 * 
	 * @param location the server's location
	 */
	public void setLocation(String location) {
		this.location = location;
	}
	
	/**
	 * Gets the software for this server
	 * 
	 * @return the server's software
	 */
	public DnsSoftware getSoftware() {
		return this.software;
	}
	
	/**
	 * Sets the software for this server
	 * 
	 * @param software the software the server runs
	 */
	public void setSoftware(DnsSoftware software) {
		this.software = software;
	}
}