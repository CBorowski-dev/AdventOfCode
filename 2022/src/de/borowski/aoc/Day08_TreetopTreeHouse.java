package de.borowski.aoc;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Day08_TreetopTreeHouse {
	
	static final int edgeSize = 99;
	static final byte[][] trees = new byte[edgeSize][edgeSize];

	public static void main(String[] args) {
		BufferedReader reader;
		
		try {
			byte lineNumber = 0;
			reader = new BufferedReader(new FileReader("C:\\Users\\cborowski\\Projects\\eclipse-workspace\\AdventOfCode\\Day01\\input\\input_day08.txt"));
			String line = reader.readLine();
			
			while (line != null) {
				// System.out.println(line);
				convertLine(line, lineNumber);
				lineNumber++;
				// read next line
				line = reader.readLine();
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("--------------------------------");
		System.out.println(computeCountOfVisibleTrees());
	}

	private static int computeCountOfVisibleTrees() {
		int numberOfVisibleTrees = (edgeSize + (edgeSize-2)) * 2;
		for (byte x=1; x<=edgeSize-2; x++) {
			for (byte y=1; y<=edgeSize-2; y++) {
				boolean leftVisibility = true;
				boolean rightVisibility = true;
				boolean topVisibility = true;
				boolean downVisibility = true;
				for (byte i=1; i<edgeSize && (leftVisibility || rightVisibility || topVisibility || downVisibility); i++) {
					if (leftVisibility && (x-i)>=0 && trees[x][y]<=trees[x-i][y]) {
						leftVisibility = false;
					}
					if (rightVisibility && (x+i)<edgeSize && trees[x][y]<=trees[x+i][y]) {
						rightVisibility = false;
					}
					if (topVisibility && (y-i)>=0 && trees[x][y]<=trees[x][y-i]) {
						topVisibility = false;
					}
					if (downVisibility && (y+i)<edgeSize && trees[x][y]<=trees[x][y+i]) {
						downVisibility = false;
					}
				}
				if (leftVisibility || rightVisibility || topVisibility || downVisibility) {
					numberOfVisibleTrees++;
				}
			}
		}
		return numberOfVisibleTrees;
	}

	private static void convertLine(String line, byte lineNumber) {
		for (byte i=0; i<line.length(); i++) {
			trees[i][lineNumber] = Byte.parseByte(Character.toString(line.charAt(i)));
		}
	}

}
