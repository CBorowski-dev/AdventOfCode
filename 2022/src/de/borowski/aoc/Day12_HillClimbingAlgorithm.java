package de.borowski.aoc;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Day12_HillClimbingAlgorithm {
	
	static int[][] area = new int[64][41];
	static int startColumn;
	static int startRow;
	static int endColumn;
	static int endRow;

	public static void main(String[] args) {
		BufferedReader reader;
		
		try {
			reader = new BufferedReader(new FileReader("C:\\Users\\cborowski\\Projects\\eclipse-workspace\\AdventOfCode\\Day01\\input\\input_day12.txt"));
			String line = reader.readLine();
			
			int row = 0;
			while (line != null) {
//				System.out.println(line);
				for (int column=0; column<64; column++) {
					char charAt = line.charAt(column);
					if (charAt == 'S') {
						charAt = 'a';
						startColumn = column;
						startRow = row;
					}
					if (charAt == 'E') {
						charAt = 'z';
						endColumn = column;
						endRow = row;
					}
					area[column][row] = getByteRepresentation(charAt);
				}
				// read next line
				line = reader.readLine();
				row++;
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		searchPath(startColumn, startRow, 128, 0);
		System.out.println("--------------------------------");
	}

	private static int getByteRepresentation(char charAt) {
		return charAt - 97;
	}

	private static boolean searchPath(int column, int row, int previousStep, int length) {
		if (column < 0 || column >63 || row < 0 || row >40 || area[column][row] >=128) {
			return false;
		}
		if (area[column][row] > (previousStep -128) + 1) {
			return false;
		}
		if (column == endColumn && row == endRow) {
			System.out.println("End found in " + length + " steps.");
			return true;
		}
//		System.out.println(column + " | " + row);
		area[column][row] = area[column][row] + 128;
		boolean returnValue;
		returnValue = searchPath(column + 1, row, area[column][row], length + 1);
		if (returnValue) return returnValue;
		returnValue = searchPath(column, row + 1, area[column][row], length +1);
		if (returnValue) return returnValue;
		returnValue = searchPath(column - 1, row, area[column][row], length +1);
		if (returnValue) return returnValue;
		returnValue = searchPath(column, row - 1, area[column][row], length +1);
		if (returnValue) return returnValue;
		area[column][row] = area[column][row] - 128;
		return false;
	}

}
