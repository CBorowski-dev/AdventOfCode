package de.borowski.aoc;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day08_HauntedWasteland {

	public static char[] directions;
	public static Map<String, Node> nodes = new HashMap<String, Node>();

	public static void main(String[] args) {
		BufferedReader reader;
		
		try {
			reader = new BufferedReader(new FileReader("/home/christoph/Projects/eclipse-workspace/AdventOfCode/2023/input/input_day08.txt"));
			String line = reader.readLine();

			directions = line.toCharArray();

			line = reader.readLine();
			while (line != null) {
				if (!line.isEmpty()) {
					// HQV = (LSD, NCQ)
					int index = line.indexOf('=') - 1;
					String nodeIndex = line.substring(0, index);
					line = line.substring(index+4, line.length()-1);
					String left = line.substring(0, 3);
					String right = line.substring(5);
					nodes.put(nodeIndex, new Node(left, right));
				}
				// read next line
				line = reader.readLine();
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("--------------------------------");
		// part 1
		//System.out.println(goToTheEnd(nodes.get("AAA")));

		// part 2
		List<Node> nl = searchAllStartingNodes();
		for (Node n : nl) {
			// output every count and that calculate the common minimum multiplier
			System.out.println(goToTheEnd(n));
		}
	}

	private static int goToTheEnd(Node currentNode) {
		boolean endReached = false;
		int stepCounter = 0;
		int directionIndex = 0;
		Node nextNode;
		while (!endReached) {
			String key = directions[directionIndex] == 'L' ? currentNode.left : currentNode.right;
			stepCounter++;
			directionIndex++;
			if (directionIndex == directions.length) {
				directionIndex = 0;
			}
			// part 1
			// if (key.equals("ZZZ")) return stepCounter;
			// part 2
			if (key.endsWith("Z")) return stepCounter;
			currentNode = nodes.get(key);
		}
		return 0;
	}

	private static List searchAllStartingNodes() {
		List<Node> nl = new ArrayList<Node>();
		for (String n : nodes.keySet()) {
			if (n.endsWith("A")) {
				nl.add(nodes.get(n));
			}
		}
		return nl;
	}

	public static class Node {
		String left, right;

		@Override
		public String toString() {
			return "Node{" +
					"left='" + left + '\'' +
					", right='" + right + '\'' +
					'}';
		}

		public Node(String left, String right) {
			this.left = left;
			this.right = right;
		}
	}

}
