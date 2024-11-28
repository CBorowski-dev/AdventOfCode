package de.borowski.aoc;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import java.util.List;
import java.util.ArrayList;

public class Day13_PointOfIncidence {

	public static char[][] field = new char[3][];
	public static List<char[]> rows = new ArrayList<>();
	public static int sum = 0;

	public static void main(String[] args) {
		BufferedReader reader;
		try {
			//reader = new BufferedReader(new FileReader("/home/christoph/Projects/eclipse-workspace/AdventOfCode/2023/input/input_day13_testset.txt"));
			reader = new BufferedReader(new FileReader("/home/christoph/Projects/eclipse-workspace/AdventOfCode/2023/input/input_day13.txt"));
			String line = reader.readLine();
			while (line != null) {
				if (!line.isEmpty()) {
					rows.add(line.toCharArray());
				} else {
					int i=0;
					field = new char[rows.size()][];
					for (char[] r : rows)	field[i++] = r;

					// Wert für Original-Feld
					List<Integer> vHOriginal = determineMirroring(field, true, -1);
					List<Integer> vVOriginal = determineMirroring(field, false, -1);

					List<Integer> vHNew = new ArrayList<>();
					List<Integer> vVNew = new ArrayList<>();
					for (int c=0; c<field[0].length; c++) {
						for (int r=0; r<field.length; r++) {
							// flip
							field[r][c] = (field[r][c] == '.') ? '#' : '.';

							List<Integer> vHTmp = determineMirroring(field, true, !vHOriginal.isEmpty() ?vHOriginal.get(0): -1);
							if (!vHTmp.isEmpty()) {
								vHTmp.removeAll(vHOriginal);
								if (!vHTmp.isEmpty())	vHNew = vHTmp;
							}
							List<Integer> vVTmp = determineMirroring(field, false, !vVOriginal.isEmpty() ?vVOriginal.get(0): -1);
							if (!vVTmp.isEmpty()) {
								vVTmp.removeAll(vVOriginal);
								if (!vVTmp.isEmpty())	vVNew = vVTmp;
							}
							// flip back
							field[r][c] = (field[r][c] == '.') ? '#' : '.';
						}
					}
					sum += !vHNew.isEmpty() ? (100 * vHNew.get(0)) : vVNew.get(0);
					rows.clear();
				}
				// read next line
				line = reader.readLine();
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("--------------------------------");
		System.out.println(sum);
	}

	private static List<Integer> determineMirroring(char[][] field, boolean horizontal, int previousValue) {
		int columnSize = field[0].length;
		int rowSize = field.length;

		boolean found = false;
		boolean breakLoop;
		List<Integer> result = new ArrayList<>();
		if (horizontal) {
			// horizontal
			for (int r = 1; r < rowSize && !found; r++) {
				breakLoop = false;
				loop_c:
				for (int c = 0; c < columnSize; c++) {
					for (int y = 0; y < r; y++) {
						int secondRow = r + (r - y) - 1;
						if (secondRow < rowSize && field[y][c] != field[secondRow][c]) {
							breakLoop = true;
							break loop_c;
						}
					}
				}
				if (!breakLoop && previousValue!=r) {
					found = true;
					result.add(r);
				}
			}
		} else {
			// vertical
			for (int c = 1; c < columnSize && !found; c++) {
				breakLoop = false;
				loop_r:
				for (int r = 0; r < rowSize; r++) {
					for (int x = 0; x < c; x++) {
						int secondColumn = c + (c - x) - 1;
						if (secondColumn < columnSize && field[r][x] != field[r][secondColumn]) {
							breakLoop = true;
							break loop_r;
						}
					}
				}
				if (!breakLoop && previousValue!=c) {
					found = true;
					result.add(c);
				}
			}
		}
		return result;
	}

	private static int determineMirroringPart1(char[][] field, boolean horizontal) {
		int columnSize = field[0].length;
		int rowSize = field.length;

		boolean found = false;
		boolean breakLoop;
		int result = -1;
		if (horizontal) {
			// horizontal
			for (int r = 1; r < rowSize && !found; r++) {
				breakLoop = false;
				loop_c:
				for (int c = 0; c < columnSize; c++) {
					for (int y = 0; y < r; y++) {
						int secondRow = r + (r - y) - 1;
						if (secondRow < rowSize && field[y][c] != field[secondRow][c]) {
							breakLoop = true;
							break loop_c;
						}
					}
				}
				if (!breakLoop) {
					found = true;
					result = r;
				}
			}
		} else {
			// vertical
			for (int c = 1; c < columnSize && !found; c++) {
				breakLoop = false;
				loop_r:
				for (int r = 0; r < rowSize; r++) {
					for (int x = 0; x < c; x++) {
						int secondColumn = c + (c - x) - 1;
						if (secondColumn < columnSize && field[r][x] != field[r][secondColumn]) {
							breakLoop = true;
							break loop_r;
						}
					}
				}
				if (!breakLoop) {
					found = true;
					result = c;
				}
			}
		}
		return result;
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
