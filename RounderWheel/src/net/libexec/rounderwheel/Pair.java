/*
 * RounderWheel DNS Resolver
 * Licensed under the MIT license.
 *
 * (C) 2008, 2024 Ross Nelson
 * Originally written for CSCE 855 (Distributed Operating Systems), University of Nebraska-Lincoln
 */

package net.libexec.rounderwheel;

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
