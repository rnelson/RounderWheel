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

public class ARecord extends DnsRecord {
	public List<Address> Addresses;
	
	/**
	 * Parses the RDATA section for A names
	 * 
	 * @param buf the {@link ByteBuffer} to read from
	 * @param length the size of the data section, dictates the number of records
	 */
	public void parse(ByteBuffer buf, int length) {
		this.Addresses = new ArrayList<Address>();
		int count = length / 4;
		
		for (int number = 0; number < count; number++) {
			Address a = new Address();
			
			// Get the IP
			a.ADDRESS[0] = (short) (buf.get() & 0x00FF);
			a.ADDRESS[1] = (short) (buf.get() & 0x00FF);
			a.ADDRESS[2] = (short) (buf.get() & 0x00FF);
			a.ADDRESS[3] = (short) (buf.get() & 0x00FF);
			
			// Save the address
			this.Addresses.add(a);
		}
	}
	
	
	
	/**
	 * Choses an address at random from the list
	 * 
	 * @return an {@link Address} from for this {@link ARecord}
	 */
	public Address getRandomAddress() {
		if (this.Addresses.size() < 1) {
			return null;
		}
		
		// Pick a random server
		Random r = new Random();
		int index = r.nextInt(this.Addresses.size());
		Address addr = this.Addresses.get(index);
		
		return addr;
	}
}