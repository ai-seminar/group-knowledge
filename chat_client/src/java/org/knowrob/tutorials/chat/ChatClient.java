package org.knowrob.tutorials.chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.tum.cs.ias.knowrob.json_prolog.Prolog;
import edu.tum.cs.ias.knowrob.json_prolog.PrologBindings;
import edu.tum.cs.ias.knowrob.json_prolog.PrologQueryProxy;
import edu.tum.cs.ias.knowrob.json_prolog.PrologValue;

/**
 * Native language client for interaction with livingroom_map
 * 
 * @author Moritz
 * 
 */
public class ChatClient {

	private static final Pattern SIMPLESENTENCE = Pattern.compile("Where (?:is|are) (?:a|the) (.+?)\\?", Pattern.CASE_INSENSITIVE);

	private static final Prolog PL = new Prolog();

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String... args) throws IOException {

		System.out.println("Chat client for livingroom_map");

		// Simple example of JSON prolog query service usage from JSONPrologTestClient
		PrologQueryProxy bdgs = PL.query("member(A, [1, 2, 3, 4]), B = ['x', A], C = foo(bar, A, B)");
		for (PrologBindings bdg : bdgs) {
			System.out.println("Found solution: ");
			for (Entry<String, PrologValue> bd : bdg.getBdgs_().entrySet()) {
				System.out.println(bd.getKey() + " = " + bd.getValue());
			}
		}
		System.out.println("Ask me something (eg. Where is the table?)");
		// Start listening for input.
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		String read;
		do {
			read = reader.readLine();
			Matcher m = SIMPLESENTENCE.matcher(read);
			if (m.find() && !m.group(1).trim().isEmpty()) {
				System.out.println("Looking for " + m.group(1).trim());
			}
		} while (!read.isEmpty());
	}
}
