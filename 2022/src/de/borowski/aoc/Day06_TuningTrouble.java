package de.borowski.aoc;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Day06_TuningTrouble {
	
	static final int markerLength = 14;	// 4 for Part 1 and 14 for Part 2

	public static void main(String[] args) {
		BufferedReader reader;
		int markerPosition = 0;
		
		try {
			reader = new BufferedReader(new FileReader("C:\\Users\\cborowski\\Projects\\eclipse-workspace\\AdventOfCode\\Day01\\input\\input_day06.txt"));
			String line = reader.readLine();
			
			while (line != null) {
				// Part 1
				markerPosition = findMarkerPosition(line);
				
				// read next line
				line = reader.readLine();
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("--------------------------------");
		// Part 1
		System.out.println(markerPosition);
	}

	private static int findMarkerPosition(String line) {
		boolean foundMarker = false;
		int i=markerLength-1;
		while (!foundMarker) {
			foundMarker = checkString(line.subSequence(i-(markerLength-1), i+1));
			i++;
		}
		return i;
	}

	private static boolean checkString(CharSequence subSequence) {
		System.out.println(subSequence);
		for (int x=0; x<(markerLength-1); x++) {
			for (int y=x+1; y<=(markerLength-1); y++) {
				if (subSequence.charAt(x) == subSequence.charAt(y)) {
					return false;
				}
			}
		}
		return true;
	}

}
