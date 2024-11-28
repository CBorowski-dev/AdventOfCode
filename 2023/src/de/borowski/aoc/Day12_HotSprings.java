package de.borowski.aoc;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Day12_HotSprings {

	public static List<String> springs = new ArrayList<String>();

	public static void main(String[] args) {
		BufferedReader reader;
		long arrangementCountSum = 0;
		try {
			//reader = new BufferedReader(new FileReader("/home/christoph/Projects/eclipse-workspace/AdventOfCode/2023/input/input_day12_testset.txt"));
			reader = new BufferedReader(new FileReader("/home/christoph/Projects/eclipse-workspace/AdventOfCode/2023/input/input_day12.txt"));
			long start = System.currentTimeMillis();
			String line = reader.readLine();
			while (line != null) {
				springs.add(line);
				arrangementCountSum += getArrangementCount(line);
				// read next line
				line = reader.readLine();
			}
			System.out.println((System.currentTimeMillis()-start)/(float)1000);
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("--------------------------------");
		System.out.println(arrangementCountSum);
	}

	private static long getArrangementCount(String line) {
		List<Integer> values = new ArrayList<Integer>();
		int index = line.indexOf(' ');
		String springs = line.substring(0, index);
		for (String v : line.substring(index+1).split(","))	values.add(Integer.parseInt(v));

		long arrangementCount = 0;
		int countUnknown = getUnknownCount(springs);
		int pow = (int) Math.pow(2, countUnknown);
		String springsTmp;
		for (int i = 0; i<pow; i++) {
			springsTmp = getFinalSprings(springs, i);
			if (checkSprings(springsTmp, values))	arrangementCount++;
		}
		return arrangementCount;
	}

	private static boolean checkSprings(String s, List<Integer> values) {
		String[] xyz = s.split("\\.");
		List<String> springsFaultParts = new ArrayList<String>();
        for (String string : xyz) {
            if (!string.isEmpty()) springsFaultParts.add(string);
        }
		if (springsFaultParts.size() != values.size())	return false;
		for (int i = 0; i<springsFaultParts.size(); i++) {
			if (springsFaultParts.get(i).length() != values.get(i))	return false;
		}
		return true;
	}

	private static String getFinalSprings(String springs, int num) {
		char[] s = springs.toCharArray();
		int bit = 0;
		for (int x=0; x<s.length; x++) {
			if (s[x]=='?') {
				s[x] = (num & (int) Math.pow(2, bit)) > 0 ? '#' : '.';
				bit++;
			}
		}
		return new String(s);
	}

	private static int getUnknownCount(String springs) {
		int count = 0;
		for (int i=0; i<springs.length(); i++) {
			if (springs.charAt(i)=='?') count++;
		}
		return count;
	}
}