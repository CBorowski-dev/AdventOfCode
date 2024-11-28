package de.borowski.aoc;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Day02_CubeConundrum {

	public static int RED_CUBES = 12;
	public static int GREEN_CUBES = 13;
	public static int BLUE_CUBES = 14;

	public static void main(String[] args) {
		BufferedReader reader;
		
		try {
			int index = 1;
			int sum = 0;
			reader = new BufferedReader(new FileReader("/home/christoph/Projects/eclipse-workspace/AdventOfCode/2023/input/input_day02.txt"));
			String line = reader.readLine();
			
			while (line != null) {
				line = removePrefix(line);
				if (isGamePossible(line)) {
					sum += index;
				}
				// read next line
				line = reader.readLine();
				index++;
			}
			reader.close();
			System.out.println("--------------------------------");
			System.out.println(sum);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static boolean isGamePossible(String line) {
		String[] subsetOfCubes = line.split(";");
		for (String subset: subsetOfCubes) {
			subset = subset.trim();
			// System.out.println(subset);
			String[] colors = subset.split(",");
			for (String c: colors) {
				c = c.trim();
				int i;
				if ((i = c.indexOf("red")) > 0) {
					if (Integer.parseInt(c.substring(0, i-1)) > RED_CUBES) {
						return false;
					}
				} else if ((i = c.indexOf("green")) > 0) {
					if (Integer.parseInt(c.substring(0, i-1)) > GREEN_CUBES) {
						return false;
					}
				} else if ((i = c.indexOf("blue")) > 0) {
					if (Integer.parseInt(c.substring(0, i-1)) > BLUE_CUBES) {
						return false;
					}
				}
			}
		}
		return true;
	}

	private static String removePrefix(String line) {
		return line.substring(line.indexOf(':')+2);
	}

}
