package de.borowski.aoc;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Stack;

public class Day05_SupplyStacks {

	static final ArrayList<Stack<Character>> stacks = new ArrayList<Stack<Character>>();
	static final int stackCount = 9;

	public static void main(String[] args) {
		BufferedReader reader;
		int lineCounter = 0;
		String endResult = "ToDo";
		
		init();
		
		try {
			reader = new BufferedReader(new FileReader("C:\\Users\\cborowski\\Projects\\eclipse-workspace\\AdventOfCode\\Day01\\input\\input_day05.txt"));

			// Read Stacks
			String line = reader.readLine();
			while (line != null && !line.startsWith(" 1")) {
				lineCounter++;
				// System.out.println(line);
				fillStacks(line);
				// read next line
				line = reader.readLine();
			}
			invertStacks();
			
			while (!line.startsWith("move")) {
				line = reader.readLine();
			}
			// Read move commands
			while (line != null) {
				// perform move command
				performMoveCommad(line);
				line = reader.readLine();
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("--------------------------------");
		// Part 1
		System.out.println(computeResult());
	}

	private static String computeResult() {
		StringBuffer sb = new StringBuffer();
		for (int i=0; i<stackCount; i++) {
			sb.append((Character) ((Stack) stacks.get(i)).peek());
		}
		return sb.toString();
	}

	private static void invertStacks() {
		for (int i=0; i<stackCount; i++) {
			Stack stack = (Stack) stacks.get(i);
			Stack newStack = new Stack();
			int size = stack.size();
			for (int s=0; s<size; s++) {
				newStack.push(stack.pop());
			}
			stacks.set(i, newStack);
		}
	}

	private static void performMoveCommad(String line) {
		int count, from, to;
		// move 11 from 1 to 7
		
		String[] lineParts = line.split(" ");
		count = Integer.parseInt(lineParts[1]);
		from = Integer.parseInt(lineParts[3]);
		to = Integer.parseInt(lineParts[5]);
		
		for (int c=0; c<count; c++) {
			char item = (Character) ((Stack) stacks.get(from -1)).pop();
			((Stack) stacks.get(to -1)).push(item);
		}
	}

	private static void init() {
		for (int i=0; i<stackCount; i++) {
			stacks.add(new Stack<Character>());
		}
	}

	private static void fillStacks(String line) {
		for (int i=0; i<stackCount; i++) {
			char c = line.substring((i*4)+1, (i*4)+2).charAt(0);
			if (c!=' ') {
				((Stack) stacks.get(i)).push(c);
			}
		}
	}

}
