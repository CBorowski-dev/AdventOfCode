package de.borowski.aoc;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

public class Day15_LensLibrary {

	public static void main(String[] args) {
		BufferedReader reader;

		long sum=0;
		try {
			reader = new BufferedReader(new FileReader("/home/christoph/Projects/eclipse-workspace/AdventOfCode/2023/input/input_day15.txt"));
			//reader = new BufferedReader(new FileReader("/home/christoph/Projects/eclipse-workspace/AdventOfCode/2023/input/input_day15.txt"));
			String line = reader.readLine();
			
			/*while (line != null) {
				line = reader.readLine();
			}*/
			String[] steps = line.split(",");
			for (String s : steps) {
				//System.out.println(s);
				sum += getSumOf(s);
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("--------------------------------");
		System.out.println(sum);
	}

	private static long getSumOf(String s) {
		long cv=0;
		char[] sa = s.toCharArray();
		for (int i=0; i<sa.length; i++) {
			//System.out.println(sa[i] + " -> " + String.valueOf((int)sa[i]));
			cv += (int)sa[i];
			cv *= 17;
			cv = cv%256;
		}
		//System.out.println("-> " + cv);

		return cv;
	}

	public static class lens {
		String name;
		int focalLength;

		@Override
		public String toString() {
			return "[" + name + ' ' + focalLength + ']';
		}

		public lens(String name, int focalLength) {
			this.name = name;
			this.focalLength = focalLength;
		}

	}

}
