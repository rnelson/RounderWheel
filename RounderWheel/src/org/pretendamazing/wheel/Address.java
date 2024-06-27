/*
 * RounderWheel DNS Resolver
 * Licensed under the MIT license.
 *
 * (C) 2008, 2024 Ross Nelson
 * Originally written for CSCE 855 (Distributed Operating Systems), University of Nebraska-Lincoln
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