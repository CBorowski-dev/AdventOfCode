package de.borowski.aoc;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Day10_Factory_Part2 {

	public static final List<Integer> LIGHTS_GOAL = new ArrayList<>();
	public static final List<List<List<Integer>>> SWITCHES = new ArrayList<>();
	public static final List<List<Integer>> JOLTAGES_GOAL = new ArrayList<>();

	public static void main(String[] args) {
		BufferedReader reader;
		
		try {
			// reader = new BufferedReader(new FileReader("/home/christoph/Projects/IdeaProjects/AdventOfCode/2025/input/input_day10_testset.txt"));
			reader = new BufferedReader(new FileReader("/home/christoph/Projects/IdeaProjects/AdventOfCode/2025/input/input_day10.txt"));
			String line = reader.readLine();
			
			while (line != null) {
				// lights
				String[] parts = line.split("] ");
				LIGHTS_GOAL.add(createBitMask(parts[0].substring(1)));

				// switches
				parts = parts[1].split(" \\{");
				String[] switches = parts[0].split(" ");
				List<List<Integer>> encodedSwitches = new ArrayList<>();
				for (String s: switches) {
					List<Integer> indexes = new ArrayList<>();
					s = s.substring(1, s.length()-1);
					String[] values = s.split(",");
					for (String s2: values) {
						indexes.add(Integer.parseInt(s2));
					}
					encodedSwitches.add(indexes);
				}
				SWITCHES.add(encodedSwitches);

				// joltages
				String joltagesStr = parts[1].substring(0, parts[1].length()-1);
				String[] j = joltagesStr.split(",");
				List<Integer> joltages = new ArrayList<>();
				for (String s: j) {
					int e = Integer.parseInt(s);
					if (e==0) System.out.println(joltagesStr);
					joltages.add(e);
				}
				JOLTAGES_GOAL.add(joltages);
				// read next line
				line = reader.readLine();
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		long result = 0;

		// Part 2
		for (int i=0; i<JOLTAGES_GOAL.size(); i++) {
			List<Integer> joltageGoal = JOLTAGES_GOAL.get(i);
			result += findFewestTotalPresses(joltageGoal, SWITCHES.get(i));
		}
		System.out.println("--------------------------------");
		System.out.println(result);
	}

	private static int findFewestTotalPresses(List<Integer> joltageGoal, List<List<Integer>> encodedSwitches) {
		//Generate bit mask
		int bitCount = getNecessaryBitCount(joltageGoal, encodedSwitches);
		if (bitCount > 64) System.out.println(bitCount);
		return 0;
	}

	private static int getNecessaryBitCount(List<Integer> joltageGoal, List<List<Integer>> encodedSwitches) {
		int bitCount = 0;
		for (List<Integer> encSwitch : encodedSwitches) {
			int n = Integer.MAX_VALUE;
			for (Integer idx : encSwitch) {
				if (joltageGoal.get(idx) < n) n = joltageGoal.get(idx);
			}
			int c = getNecessaryBitCount(n);
			bitCount += c;
			// System.out.println(n + " --> " + c);
		}
		return bitCount;
	}

	private static int getNecessaryBitCount(int n) {
		if (n == 0) return 1;
		int bitCount = 0;
		while (n > 0) {
			bitCount++;
			n = n >> 1;
		}
		return bitCount;
	}

	private static Integer createBitMask(String lights) {
		int bitMask = 0;
		char[] l = lights.toCharArray();
		for (int i=0; i<l.length; i++) {
			if (l[i] == '#') {
				bitMask =  bitMask ^ (int) Math.pow(2, i);
			}
		}
		return bitMask;
	}
}
