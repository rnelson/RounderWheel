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

public class Pair<K, V> {
	private K key;
	private V value;
	
	/**
	 * Creates a new {@link Pair}
	 * 
	 * @param key the key
	 * @param value the value
	 */
	public Pair(K key, V value) {
		this.key = key;
		this.value = value;
	}
	
	/**
	 * Gets the key
	 * 
	 * @return the key
	 */
	public K getKey() {
		return this.key;
	}
	
	/**
	 * Sets the key
	 * 
	 * @param key the key
	 */
	public void setKey(K key) {
		this.key = key;
	}
	
	/**
	 * Gets the value
	 * 
	 * @return the value
	 */
	public V getValue() {
		return this.value;
	}
	
	/**
	 * Sets the value
	 * 
	 * @param value the value
	 */
	public void setValue(V value) {
		this.value = value;
	}
}
