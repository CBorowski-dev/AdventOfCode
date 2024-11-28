package de.borowski.aoc;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Day14_ParabolicReflectorDish {

	public static int dim = 100;
	public static long cycles = 1000000000;
	public static char[][] field = new char[dim][];

	public static void main(String[] args) {
		BufferedReader reader;
		
		try {
			//reader = new BufferedReader(new FileReader("/home/christoph/Projects/eclipse-workspace/AdventOfCode/2023/input/input_day14_testset.txt"));
			reader = new BufferedReader(new FileReader("/home/christoph/Projects/eclipse-workspace/AdventOfCode/2023/input/input_day14.txt"));
			String line = reader.readLine();
			int y=0;
			while (line != null) {
				field[y++] = line.toCharArray();
				// read next line
				line = reader.readLine();
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("mod : " + cycles % 22);
		long start = System.currentTimeMillis();
		long oldSum = 0;
		boolean stop = false;
		for (int i=0; i<100000 && !stop; i++) {
			cantFieldNorthOrSouth(field, true);
			cantFieldWestOrEast(field, true);
			cantFieldNorthOrSouth(field, false);
			cantFieldWestOrEast(field, false);
			long newSum = countRockValues(field);
			//if ((i+1) % 1000 == 0) System.out.println(i+1 + ";" + newSum);
			System.out.println(i+1 + ";" + newSum);
			if (newSum != oldSum) oldSum=newSum;
			else break;
		}
		long end = System.currentTimeMillis();
		printField(field);
		System.out.println(((end - start) / (float)1000));
		System.out.println("--------------------------------");
		System.out.println(countRockValues(field));
	}

	private static void cantFieldWestOrEast(char[][] field, boolean west) {
		for (int y=0; y<dim; y++) {
			if (west) {
				for (int x = 0; x < dim; x++) {
					if (field[y][x] == '.') {
						for (int xl = x + 1; xl < dim; xl++) {
							if (field[y][xl] == 'O') {
								field[y][x++] = 'O';
								field[y][xl] = '.';
							} else if (field[y][xl] == '#') {
								x = xl;
								break;
							}
						}
					}
				}
			} else {
				// east
				for (int x = dim - 1; x >= 0; x--) {
					if (field[y][x] == '.') {
						for (int xl = x - 1; xl >= 0; xl--) {
							if (field[y][xl] == 'O') {
								field[y][x--] = 'O';
								field[y][xl] = '.';
							} else if (field[y][xl] == '#') {
								x = xl;
								break;
							}
						}
					}
				}
			}
		}
	}

	private static long countRockValues(char[][] field) {
		long sum = 0;
		for (int y=0; y<dim; y++) {
			for (int x=0; x<dim; x++) {
				if (field[y][x]=='O') sum += (dim-y);
			}
		}
		return sum;
	}

	private static void cantFieldNorthOrSouth(char[][] field, boolean north) {
		for (int x=0; x<dim; x++) {
			if (north) {
				for (int y = 0; y < dim; y++) {
					if (field[y][x] == '.') {
						for (int yl = y + 1; yl < dim; yl++) {
							if (field[yl][x] == 'O') {
								field[y++][x] = 'O';
								field[yl][x] = '.';
							} else if (field[yl][x] == '#') {
								y = yl;
								break;
							}
						}
					}
				}
			} else {
				// south
				for (int y = dim-1; y >= 0; y--) {
					if (field[y][x] == '.') {
						label_yl:
						for (int yl = y - 1; yl >= 0; yl--) {
							if (field[yl][x] == 'O') {
								field[y--][x] = 'O';
								field[yl][x] = '.';
							} else if (field[yl][x] == '#') {
								y = yl;
								break;
							}
						}
					}
				}
			}
		}
	}

	private static void printField(char[][] field) {
		for (int r=0; r<dim; r++) {
			for (int c=0; c<dim; c++) {
				System.out.print(field[r][c]);
			}
			System.out.println();
		}
		System.out.println();
	}
}
