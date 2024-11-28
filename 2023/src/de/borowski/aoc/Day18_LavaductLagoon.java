package de.borowski.aoc;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

public class Day18_LavaductLagoon {

	// (0,0) -> (6,9): xDim=7, yDim=10
	// (-10,-157) -> (270, 85): xDim=281, yDim=243
	//public static int xDim = 7, yDim = 10;
	//public static int xOffset = 0, yOffset = 0;
	public static int xDim = 281, yDim = 243;
	public static int xOffset = 10, yOffset = 157;

	// Startpoints for the flood fill alg.: (1, 1) & (30, 30)
	//public static int xStart = 1, yStart = 1;
	public static int xStart = 30, yStart = 30;

	public static boolean[][] field = new boolean[yDim][xDim];

	public static void main(String[] args) {
		BufferedReader reader;

		int x_max=0, y_max=0, x_min=0, y_min=0, x=0, y=0;

		try {
			//reader = new BufferedReader(new FileReader("/home/christoph/Projects/eclipse-workspace/AdventOfCode/2023/input/input_day18_testset.txt"));
			reader = new BufferedReader(new FileReader("/home/christoph/Projects/eclipse-workspace/AdventOfCode/2023/input/input_day18.txt"));
			String line = reader.readLine();
			
			while (line != null) {
				String[] values = line.split(" ");
				int steps = Integer.parseInt(values[1]);
				/*switch (values[0]) {
					case "U":
						y -= steps;
						if (y_min>y) y_min=y;
						break;
					case "R":
						x += steps;
						if (x_max<x) x_max=x;
						break;
					case "L":
						x -= steps;
						if (x_min>x) x_min=x;
						break;
					case "D":
						y += steps;
						if (y_max<y) y_max=y;
				}*/
				switch (values[0]) {
					case "U":
						for (int i=1; i<=steps; i++) field[y+yOffset-i][x+xOffset]=true;
						y -= steps;
						break;
					case "R":
						for (int i=1; i<=steps; i++) field[y+yOffset][x+xOffset+i]=true;
						x += steps;
						break;
					case "L":
						for (int i=1; i<=steps; i++) field[y+yOffset][x+xOffset-i]=true;
						x -= steps;
						break;
					case "D":
						for (int i=1; i<=steps; i++) field[y+yOffset+i][x+xOffset]=true;
						y += steps;
				}
				line = reader.readLine();
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		doFloodFill(xStart, yStart);
		//System.out.println(x_min + " " + y_min + " | " + x_max + " " + y_max);
		System.out.println("--------------------------------");
		System.out.println(countCubics(field));
	}

	private static int countCubics(boolean[][] field) {
		int count = 0;
		for (int y=0; y<yDim; y++) {
			for (int x=0; x<xDim; x++) {
				count += field[y][x]?1:0;
			}
		}
		return count;
	}

	private static void doFloodFill(int y, int x) {
		if (x>=0 && x<xDim && y>=0 && y<yDim && !field[y][x]) {
			field[y][x]=true;
			doFloodFill(y-1, x);
			doFloodFill(y, x-1);
			doFloodFill(y+1, x);
			doFloodFill(y, x+1);
		}
	}

	private static void initilizeField() {
		for (int y=0; y<yDim; y++) {
			for (int x=0; x<xDim; x++) {
				field[y][x] = false;
			}
		}
	}

	private static void printField(boolean[][] field) {
		for (int y=0; y<yDim; y++) {
			for (int x=0; x<xDim; x++) {
				System.out.print(field[y][x]?'x':'_');
			}
			System.out.println();
		}
		System.out.println();
	}

}
