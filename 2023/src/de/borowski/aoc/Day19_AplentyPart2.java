package de.borowski.aoc;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import java.util.*;

public class Day19_AplentyPart2 {

	public static Map<RuleName, Step> rules = new HashMap<>();
	public static RuleName ACCEPT = new RuleName("A");
	public static RuleName REJECT = new RuleName("R");
	public static RuleName IN = new RuleName("in");

	public static void main(String[] args) {
		BufferedReader reader;

		try {
			//reader = new BufferedReader(new FileReader("/home/christoph/Projects/eclipse-workspace/AdventOfCode/2023/input/input_day19_testset.txt"));
			reader = new BufferedReader(new FileReader("/home/christoph/Projects/eclipse-workspace/AdventOfCode/2023/input/input_day19.txt"));
			String line = reader.readLine();
			// read rules
			while (!line.isEmpty()) {
				// qzl{x>2819:gvl,s<3517:xz,s<3728:htx,fsq}
				int endIndex = line.indexOf('{');
				RuleName name = new RuleName(line.substring(0, endIndex));
				Step.createStep(name, line.substring(endIndex+1, line.length()-1));
				line = reader.readLine();
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("--------------------------------");
		int[][] borderValues = new int[][] {{1,1,1,1}, {4000,4000,4000,4000}};
		System.out.println(getDistinctCombinations(IN, borderValues));
	}

	private static long getDistinctCombinations(RuleName rn, int[][] bv) {
		long count = 0;

		Step s = rules.get(rn);
		// positive path
		System.out.print(rn.name + "[" + s.c + s.operator + s.value + "] -> ");
		if (s.posRN.equals(ACCEPT))	{
			int[][] bvCopy = deepCopy(bv);
			adjustBorderValues(s, bvCopy, true);
			count = computeCount(bvCopy);
			System.out.println("pos A " + count);
		} else if (!s.posRN.equals(REJECT)) {
			int[][] bvCopy = deepCopy(bv);
			adjustBorderValues(s, bvCopy, true);
			count = getDistinctCombinations(s.posRN, bvCopy);
		}
		// negative path
		if (rn.equals(IN)) System.out.println("------");
		if (s.negRN.equals(ACCEPT))	{
			int[][] bvCopy = deepCopy(bv);
			adjustBorderValues(s, bvCopy, false);
			count += computeCount(bvCopy);
			System.out.println("neg A " + count);
		} else if (!s.negRN.equals(REJECT)) {
			int[][] bvCopy = deepCopy(bv);
			adjustBorderValues(s, bvCopy, false);
			count += getDistinctCombinations(s.negRN, bvCopy);
		}

		return count;
	}

	private static long computeCount(int[][] bv) {
		long count = 1;
		for (int i=0; i<4; i++) {
			if (bv[1][i] < bv[0][i]) count = 0;
			else count *= bv[1][i]-bv[0][i]+1;
		}
		return count;
	}

	private static void adjustBorderValues(Step s, int[][] bvCopy, boolean isPosPath) {
		int index = s.c.getIndex();
		if (isPosPath) {
			if (s.operator == '<') {
				if (bvCopy[1][index] > s.value - 1) bvCopy[1][index] = s.value - 1;
			} else {
				if (bvCopy[0][index] < s.value + 1) bvCopy[0][index] = s.value + 1;
			}
		} else {
			if (s.operator == '<') {
				if (bvCopy[0][index] < s.value) bvCopy[0][index] = s.value;
			} else {
				if (bvCopy[1][index] > s.value) bvCopy[1][index] = s.value;
			}
		}
	}

	public static int[][] deepCopy(int[][] original) {
		final int[][] result = new int[original.length][];
		for (int i = 0; i < original.length; i++) {
			result[i] = Arrays.copyOf(original[i], original[i].length);
		}
		return result;
	}

	public static class RuleName {
		String name;

		@Override
		public String toString() {
			return "RuleName{" + "name='" + name + '\'' + '}';
		}

		public RuleName(String name) {
			this.name = name;
		}

		@Override
		public boolean equals(Object o) {
			if (this == o) return true;
			if (o == null || getClass() != o.getClass()) return false;
			RuleName ruleName = (RuleName) o;
			return Objects.equals(name, ruleName.name);
		}

		@Override
		public int hashCode() {
			return Objects.hash(name);
		}
	}
	public static class Step {
		RuleName posRN;
		RuleName negRN;
		Category c;
		char operator;
		int value;

		public static void createStep(RuleName ruleName, String initString) {
			// x>2819:gvl,
			// s<3517:xz,
			// s<3728:htx,fsq
			String[] parts =initString.split(",");
			RuleName negRN = new RuleName(parts[parts.length-1]); // fsq
			for (int i=parts.length-2; i>=0; i--) {
				Category c = Category.get(parts[i].charAt(0));
				char operator = parts[i].charAt(1);
				int endIndex = parts[i].indexOf(':');
				int value = Integer.parseInt(parts[i].substring(2, endIndex));
				RuleName posRN = new RuleName(parts[i].substring(endIndex+1));
				Step tmpStep = new Step(c, operator, value, posRN, negRN);
				RuleName tmpName = (i==0) ? ruleName : new RuleName(ruleName.name + i);
				rules.put(tmpName, tmpStep);
				negRN = tmpName;
			}
		}

		public Step(Category c, char operator, int value, RuleName posRN, RuleName negRN) {
			this.c = c;
			this.operator = operator;
			this.value = value;
			this.posRN = posRN;
			this.negRN = negRN;
		}
	}

	enum Category {
		XTREME('x'),
		MUSICAL('m'),
		AERODYNAMIC('a'),
		SHINY('s');
		public char value;

		public static Category get(char c) {
			switch (c) {
				case 'x': return XTREME;
				case 'm': return MUSICAL;
				case 'a': return AERODYNAMIC;
				case 's': return SHINY;
			}
			return null;
		};

		public int getIndex() {
			int index = 0;
			switch (this) {
				case XTREME -> index = 0;
				case MUSICAL -> index = 1;
				case AERODYNAMIC -> index = 2;
				case SHINY -> index = 3;
			}
			return index;
		}

		private Category(char value) {
			this.value = value;
		}
	}

}
