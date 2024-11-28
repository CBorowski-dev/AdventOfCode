package de.borowski.aoc;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import java.util.*;

public class Day19_Aplenty {

	public static Map<RuleName, Step> rules = new HashMap<>();
	public static RuleName accept = new RuleName("A");
	public static RuleName reject = new RuleName("R");

	public static void main(String[] args) {
		BufferedReader reader;

		int sum = 0;
		try {
			reader = new BufferedReader(new FileReader("/home/christoph/Projects/eclipse-workspace/AdventOfCode/2023/input/input_day19.txt"));
			String line = reader.readLine();
			// read rules
			while (!line.isEmpty()) {
				// qzl{x>2819:gvl,s<3517:xz,s<3728:htx,fsq}
				int endIndex = line.indexOf('{');
				RuleName name = new RuleName(line.substring(0, endIndex));
				rules.put(name, Step.createStep(line.substring(endIndex+1, line.length()-1)));
				line = reader.readLine();
			}
			line = reader.readLine();
			// read rules
			while (line!=null) {
				// {x=787,m=2655,a=1222,s=2876}
				line = line.substring(1, line.length()-1);
				// System.out.println(line);
				String[] x = line.split(",");
				Part p = new Part(Integer.parseInt(x[0].substring(2)),
						Integer.parseInt(x[1].substring(2)),
						Integer.parseInt(x[2].substring(2)),
						Integer.parseInt(x[3].substring(2)));
				// verarbeite Part
				Step inStep = rules.get(new RuleName("in"));
				boolean goon = true;
				while (goon) {
					RuleName rn = inStep.processPart(p);
					if (rn.equals(accept) || rn.equals(reject)) {
						goon = false;
						System.out.println(rn);
						if (rn.equals(accept)) sum += p.getSum();
					} else {
						inStep = rules.get(rn);
					}
				}
				line = reader.readLine();
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("--------------------------------");
		System.out.println(sum);
	}

	public static class RuleName implements Action {
		String name;

		@Override
		public String toString() {
			return "RuleName{" +
					"name='" + name + '\'' +
					'}';
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

		@Override
		public boolean isStep() {
			return false;
		}
	}
	public static class Step implements Action {
		Action posAction;
		Action negAction;
		Category c;
		char operator;
		int value;

		public static Step createStep(String initString) {
			// x>2819:gvl,
			// s<3517:xz,
			// s<3728:htx,
			// fsq
			String[] parts =initString.split(",");
			Action negAction = new RuleName(parts[parts.length-1]); // fsq
			for (int i=parts.length-2; i>=0; i--) {
				Category c = Category.get(parts[i].charAt(0));
				char operator = parts[i].charAt(1);
				int endIndex = parts[i].indexOf(':');
				int value = Integer.parseInt(parts[i].substring(2, endIndex));
				Action posAction = new RuleName(parts[i].substring(endIndex+1, parts[i].length()));
				Step tmpStep = new Step(c, operator, value, posAction, negAction);
				negAction = tmpStep;
			}
			return (Step) negAction;
		}

		public Step(Category c, char operator, int value, Action posAction, Action negAction) {
			this.c = c;
			this.operator = operator;
			this.value = value;
			this.posAction = posAction;
			this.negAction = negAction;
		}

		public RuleName processPart(Part p) {
			int val = 0;
			switch (c) {
				case XTREME:
					val = p.values[0];
					break;
				case MUSICAL:
					val = p.values[1];
					break;
				case AERODYNAMIC:
					val = p.values[2];
					break;
				case SHINY:
					val = p.values[3];
			}
			boolean result = false;
			if (operator=='<') {
				if (val < value) result = true;
			} else if (val > value) result = true;
			if (result) {
				return ((RuleName) posAction);
			} else {
				return (negAction instanceof Step) ? ((Step) negAction).processPart(p) : ((RuleName) negAction);
			}
		}

		@Override
		public boolean isStep() {
			return true;
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

		private Category(char value) {
			this.value = value;
		}
	}

	public static class Part {
		int[] values;

		public Part(int x, int m, int a, int s) {
			values = new int[] {x, m, a, s};
		}

		public int getSum() {
			return values[0]+values[1]+values[2]+values[3];
		}
	}

	interface Action {
		public boolean isStep();
	}

}
