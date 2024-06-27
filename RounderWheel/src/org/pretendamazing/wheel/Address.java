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

public class Address {
	public short[] ADDRESS;
	
	/**
	 * Creates a new {@link Address} object
	 */
	public Address() {
		this.ADDRESS = new short[4];
	}
	
	/**
	 * Gets a string representation of the address
	 * 
	 * @return a string representation of the address
	 */
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append(Short.toString(ADDRESS[0]));
		sb.append(".");
		sb.append(Short.toString(ADDRESS[1]));
		sb.append(".");
		sb.append(Short.toString(ADDRESS[2]));
		sb.append(".");
		sb.append(Short.toString(ADDRESS[3]));
		
		return sb.toString();
	}
}