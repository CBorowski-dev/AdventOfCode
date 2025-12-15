package de.borowski.aoc;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Day11_Reactor {

	public static final Map<String, List<String>> CON = new HashMap<>();
	public static int counter = 0;


	public static void main(String[] args) {
		BufferedReader reader;

		try {
			// reader = new BufferedReader(new FileReader("/home/christoph/Projects/IdeaProjects/AdventOfCode/2025/input/input_day11_testset.txt"));
			reader = new BufferedReader(new FileReader("/home/christoph/Projects/IdeaProjects/AdventOfCode/2025/input/input_day11.txt"));
			String line = reader.readLine();

			while (line != null) {
				// System.out.println(line);
				String[] nodes = line.split(": ");
				String[] toNodes = nodes[1].split(" ");
				CON.put(nodes[0], new ArrayList<String>(Arrays.stream(toNodes).toList()));
				// read next line
				line = reader.readLine();
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Part 1
		// searchAllPaths(CON.get("you"));
		// Part 2
		searchAllSpecialPaths(CON.get("svr"));
		System.out.println("--------------------------------");
		System.out.println(counter);

	}

	private static void searchAllPaths(List<String> nodes) {
		for (String n: nodes) {
			if (n.equals("out")) counter++;
			else searchAllPaths(CON.get(n));
		}
	}

	private static void searchAllSpecialPaths(List<String> nodes) {
		for (String n: nodes) {
			if (n.equals("fft")) {
				counter++;
				System.out.print('*');
			} else {
				List<String> nodes2 = CON.get(n);
				if (nodes2 != null) searchAllSpecialPaths(nodes2);
			}
		}
	}

}
