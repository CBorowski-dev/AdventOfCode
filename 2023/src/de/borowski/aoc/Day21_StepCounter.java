package de.borowski.aoc;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

public class Day21_StepCounter {

	public static int dim = 131;
	public static char[][][] fields = new char[2][dim][dim];

	public static void main(String[] args) {
		BufferedReader reader;
		
		try {
			//reader = new BufferedReader(new FileReader("/home/christoph/Projects/eclipse-workspace/AdventOfCode/2023/input/input_day21_testset.txt"));
			reader = new BufferedReader(new FileReader("/home/christoph/Projects/eclipse-workspace/AdventOfCode/2023/input/input_day21.txt"));
			String line = reader.readLine();

			int y=0;
			while (line != null) {
				fields[0][y++] = line.toCharArray();
				line = reader.readLine();
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// printField(fields[0]);
		int currentFieldIdx=0;
		for (int i=0; i<64; i++) {
			currentFieldIdx = computeNextFieldIteration(currentFieldIdx);
			System.out.println(" --> " + currentFieldIdx);
			printField(fields[currentFieldIdx]);
		}
		System.out.println("--------------------------------");
		System.out.println(countReachedPlots(fields[currentFieldIdx]));
	}

	private static int countReachedPlots(char[][] field) {
		int count = 0;
		for (int y=0; y<field.length; y++) {
			for (int x=0; x<field[0].length; x++) {
				if (field[y][x]=='O')	count++;
			}
		}
		return count;
	}

	private static int computeNextFieldIteration(int currentFieldIdx) {
		int nextFieldIdx = (currentFieldIdx+1) % 2;
		for (int y=0; y<fields[currentFieldIdx].length; y++) {
			for (int x=0; x<fields[currentFieldIdx][y].length; x++) {
				if (fields[currentFieldIdx][y][x]=='O' || fields[currentFieldIdx][y][x]=='S') {
					fields[nextFieldIdx][y][x] = '.';
					if (x-1 >= 0 && fields[currentFieldIdx][y][x-1] != '#') {
						fields[nextFieldIdx][y][x-1] = 'O';
					}
					if (x+1 < dim && fields[currentFieldIdx][y][x+1] != '#') {
						fields[nextFieldIdx][y][x+1] = 'O';
					}
					if (y-1 >= 0 && fields[currentFieldIdx][y-1][x] != '#') {
						fields[nextFieldIdx][y-1][x] = 'O';
					}
					if (y+1 < dim && fields[currentFieldIdx][y+1][x] != '#') {
						fields[nextFieldIdx][y+1][x] = 'O';
					}
				} else if (fields[currentFieldIdx][y][x]=='#' || (fields[currentFieldIdx][y][x]=='.' && fields[nextFieldIdx][y][x]!='O')) {
					fields[nextFieldIdx][y][x] = fields[currentFieldIdx][y][x];
				}
			}
		}
		return nextFieldIdx;
	}

	private static void printField(char[][] field) {
		for (int r=0; r<field.length; r++) {
			for (int c=0; c<field[0].length; c++) {
				System.out.print(field[r][c]);
			}
			System.out.println();
		}
		System.out.println();
	}
}
