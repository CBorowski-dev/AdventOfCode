package de.borowski.aoc;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Day03_RucksackReorganization_Part2 {
	
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
			char equalChar;
			
			while (line != null) {
				// read second line
				line = findCommonItems(line, reader.readLine());
				// read third line
				line = findCommonItems(line, reader.readLine());
				equalChar = line.charAt(0);
				sumOfPriorities += ((int) equalChar >= 97) ? ((int) equalChar) - 96 : ((int) equalChar) - 38;;

				// read first line of the next three lines
				line = reader.readLine();
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("--------------------------------");
		System.out.println(sumOfPriorities);
	}

	public static String findCommonItems(String line1, String line2) {
		StringBuffer equalChars = new StringBuffer();
		for (int x=0; x<line1.length(); x++) {
			char left = line1.charAt(x);
			for (int y=0; y<line2.length(); y++) {
				char right = line2.charAt(y);
				if (left == right && equalChars.indexOf((new Character(left)).toString())==-1) {
					equalChars.append(left);
//					System.out.println(" --> " + equalChars.toString());
				}
			}			
		}
		return equalChars.toString();
	}

}
