package de.borowski.aoc;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Day03_RucksackReorganization {
	
	/**
	 * 65	A --> 27
	 * 90	Z --> 52
	 * 97	a --> 1
	 * 122	z --> 26
	 */

	public static void main(String[] args) {
		BufferedReader reader;
		int sumOfPriorities = 0;
		
		try {
			reader = new BufferedReader(new FileReader("C:\\Users\\cborowski\\Projects\\eclipse-workspace\\AdventOfCode\\Day01\\input\\input_day03.txt"));
			String line = reader.readLine();
			
			while (line != null) {
//				System.out.println(line);
				sumOfPriorities += computeRoundScore(line);
				
				// read next line
				line = reader.readLine();
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("--------------------------------");
		System.out.println(sumOfPriorities);
	}

	public static int computeRoundScore(String line) {
		
		int l = line.length();
		CharSequence leftCompartment = line.subSequence(0, l/2);
		CharSequence rightCompartment = line.subSequence(l/2, l);
//		System.out.println(" --> " + leftCompartment);
//		System.out.println(" --> " + rightCompartment);
		char equalChar = 0;
		for (int x=0; x<l/2; x++) {
			char left = leftCompartment.charAt(x);
			for (int y=0; y<l/2; y++) {
				char right = rightCompartment.charAt(y);
				if (left == right) {
					equalChar = left;
//					System.out.println(" --> " + equalChar);
				}
			}			
		}

		return ((int) equalChar >= 97) ? ((int) equalChar) - 96 : ((int) equalChar) - 38;
	}

}
