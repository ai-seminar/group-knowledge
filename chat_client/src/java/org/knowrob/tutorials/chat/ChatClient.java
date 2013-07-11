package org.knowrob.tutorials.chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.ArrayList;

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

	private static final Prolog PL = new Prolog();

	private static ArrayList<Pattern> getPatterns() {
		ArrayList<Pattern> patterns = new ArrayList<Pattern>();
		patterns.add(Pattern.compile("Where (?:is|are) (?:a|the) (.+?)\\?", Pattern.CASE_INSENSITIVE));
		patterns.add(Pattern.compile("Where (?:is|are) (?:a|the) (.+?)(:? located|located at|found|)\\?", Pattern.CASE_INSENSITIVE));
		patterns.add(Pattern.compile("Where did .* (?:put|place|hang up|lay down|store|throw|deposit|file|archive) [^ ]+ (.+?)\\?", Pattern.CASE_INSENSITIVE));
		return patterns;
	}

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
			boolean foundPattern = false;
			for (Pattern pattern : getPatterns()) {
				Matcher m = pattern.matcher(read);
				if (m.find() && !m.group(1).trim().isEmpty()) {
					System.out.println("Looking for " + m.group(1).trim());
					foundPattern = true;
				}
			}
			if (!foundPattern) {
				System.out.println("I'm sorry, I didn't understand your question.");
			}
		} while (!read.isEmpty());
	}
}
