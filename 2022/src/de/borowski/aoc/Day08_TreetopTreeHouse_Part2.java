package de.borowski.aoc;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Day08_TreetopTreeHouse_Part2 {

	
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
		System.out.println(computeMaxTreeScenicScore());
	}

	private static int computeMaxTreeScenicScore() {
		int maxTreeScenicScore = 0;
		for (byte x=0; x<edgeSize; x++) {
			for (byte y=0; y<edgeSize; y++) {
				boolean leftVisibility = true;
				boolean rightVisibility = true;
				boolean topVisibility = true;
				boolean downVisibility = true;
				
				int leftVisibilityCount = 0;
				int rightVisibilityCount = 0;
				int topVisibilityCount = 0;
				int downVisibilityCount = 0;

				for (byte i=1; i<edgeSize && (leftVisibility || rightVisibility || topVisibility || downVisibility); i++) {
					if (leftVisibility && (x-i)>=0) {
						leftVisibilityCount++;
						if (trees[x][y]<=trees[x-i][y]) {
							leftVisibility = false;
						}
					} else {
						leftVisibility = false;
					}
					if (rightVisibility && (x+i)<edgeSize) {
						rightVisibilityCount++;
						if (trees[x][y]<=trees[x+i][y]) {
							rightVisibility = false;
						}
					} else {
						rightVisibility = false;
					}
					if (topVisibility && (y-i)>=0) {
						topVisibilityCount++;
						if (trees[x][y]<=trees[x][y-i]) {
							topVisibility = false;
						}
					} else {
						topVisibility = false;
					}
					if (downVisibility && (y+i)<edgeSize) {
						downVisibilityCount++;
						if (trees[x][y]<=trees[x][y+i]) {
							downVisibility = false;
						}
					} else {
						downVisibility = false;
					}
				}
				if (!leftVisibility && !rightVisibility && !topVisibility && !downVisibility) {
					int score = leftVisibilityCount * rightVisibilityCount * topVisibilityCount * downVisibilityCount;
					if (score > maxTreeScenicScore) {
						maxTreeScenicScore = score;
						System.out.println("x, y : " + x + " " + y);
						System.out.println(leftVisibilityCount + " " + rightVisibilityCount + " " + topVisibilityCount + " " + downVisibilityCount);
					}
				}
			}
		}
		return maxTreeScenicScore;
	}

	private static void convertLine(String line, byte lineNumber) {
		for (byte i=0; i<line.length(); i++) {
			trees[i][lineNumber] = Byte.parseByte(Character.toString(line.charAt(i)));
		}
	}

}
