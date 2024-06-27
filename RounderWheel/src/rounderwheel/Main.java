/*
 * RounderWheel DNS Resolver
 * Licensed under the MIT license.
 *
 * (C) 2008, 2024 Ross Nelson
 * Originally written for CSCE 855 (Distributed Operating Systems), University of Nebraska-Lincoln
 */

package rounderwheel;

import org.pretendamazing.wheel.*;
import java.io.*;

public class Main {
	private static String prompt = "C:\\>";
	
	/**
	 * Runs the RounderWheel Shell
	 * 
	 * @param args command-line arguments
	 */
	public static void main(String[] args) {
		try {
		    String input = "";

		    // Set the prompt if the user specified a new one
		    if (args.length > 0) {
			    Main.prompt = args[0];
		    }

		    // Print the header
		    printHeader();

		    // Set up a BufferedReader to get input from the user
		    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		    
		    while (true) {
			// Print the prompt and wait for user input
			System.out.print(prompt);
			input = reader.readLine();

			// Trim the input so it's easier to work with
			input = input.trim();
			
			// Your wish is my command...
			if (input.startsWith("quit")) {
				// Does the user want to quit?
				return;
			}
			else if (input.startsWith("myresolver")) {
				// Did the user type in 'myresolver <name>'? We don't need 'myresolver '
				input = input.substring(11);
			}
			
			// Since I don't have time to add commands in, let's just assume
			// that any other input is request.

			// Are there spaces? Invalid input!
			if (input.indexOf(32) != -1) {
				System.out.println("Invalid query");
			}
			else {
				InetAddress a = InetAddress.getByName(input);
			}
		    }
		}
		catch (Exception e) {
			System.err.println("You broke it. Good job.");
			return;
		}
	}
	
	/**
	 * Prints the header! :-)
	 */
	public static void printHeader() {
		System.out.println("RounderWheel DNS Resolver (v20240624)");
		System.out.println("");
	}
}