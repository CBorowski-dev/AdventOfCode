package de.borowski.aoc;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Day02_CubeConundrum_Part2 {

	public static void main(String[] args) {
		BufferedReader reader;
		
		try {
			int sumOfPowers = 0;
			reader = new BufferedReader(new FileReader("/home/christoph/Projects/eclipse-workspace/AdventOfCode/2023/input/input_day02.txt"));
			String line = reader.readLine();
			
			while (line != null) {
				sumOfPowers += getPowerForGame(removePrefix(line));
				// read next line
				line = reader.readLine();
			}
			reader.close();
			System.out.println("--------------------------------");
			System.out.println(sumOfPowers);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static int getPowerForGame(String line) {
		int redCount = 0;
		int greenCount = 0;
		int blueCount = 0;
		String[] subsetOfCubes = line.split(";");
		for (String subset: subsetOfCubes) {
			subset = subset.trim();
			// System.out.println(subset);
			String[] colors = subset.split(",");
			for (String c: colors) {
				c = c.trim();
				int i;
				if ((i = c.indexOf("red")) > 0) {
					int count = Integer.parseInt(c.substring(0, i - 1));
					if (count > redCount) {
						redCount = count;
					}
				} else if ((i = c.indexOf("green")) > 0) {
					int count = Integer.parseInt(c.substring(0, i - 1));
					if (count > greenCount) {
						greenCount = count;
					}
				} else if ((i = c.indexOf("blue")) > 0) {
					int count = Integer.parseInt(c.substring(0, i - 1));
					if (count > blueCount) {
						blueCount = count;
					}
				}
			}
		}
		return redCount * greenCount * blueCount;
	}

	private static String removePrefix(String line) {
		return line.substring(line.indexOf(':')+2);
	}

}
