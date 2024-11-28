package de.borowski.aoc;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Day16_TheFloorWillBeLava {

	public static int dim = 110;
	public static char[][] field = new char[dim][];
	public static char[][][] directionFrom = new char[dim][dim][];
	private static final char EAST = 'E';
	private static final char WEST = 'W';
	private static final char NORTH = 'N';
	private static final char SOUTH = 'S';

	public static void main(String[] args) {
		BufferedReader reader;
		
		try {
			//reader = new BufferedReader(new FileReader("/home/christoph/Projects/eclipse-workspace/AdventOfCode/2023/input/input_day16_testset.txt"));
			reader = new BufferedReader(new FileReader("/home/christoph/Projects/eclipse-workspace/AdventOfCode/2023/input/input_day16.txt"));
			String line = reader.readLine();
			int y=0;
			while (line != null) {
				field[y++] = line.toCharArray();
				line = reader.readLine();
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// printField(field);
		int maxCount=0;
		int tmpCount;
		for (int x=0; x<dim; x++) {
			initilizeDirectionFrom();
			followBeamNorthOrSouth(field, directionFrom, x, 0, false); // obere Kante nach vvv
			tmpCount = countTiles();
			if (tmpCount>maxCount) maxCount=tmpCount;
			initilizeDirectionFrom();
			followBeamNorthOrSouth(field, directionFrom, x, dim-1, true); // untere Kante nach ^^^
			tmpCount = countTiles();
			if (tmpCount>maxCount) maxCount=tmpCount;
		}
		for (int y=0; y<dim; y++) {
			initilizeDirectionFrom();
			followBeamWestOrEast(field, directionFrom, 0, y, false); // linke Kante nach >>>
			tmpCount = countTiles();
			if (tmpCount>maxCount) maxCount=tmpCount;
			initilizeDirectionFrom();
			followBeamWestOrEast(field, directionFrom, dim-1, y, true); // rechte Kante nach <<<
			tmpCount = countTiles();
			if (tmpCount>maxCount) maxCount=tmpCount;
		}
		followBeamWestOrEast(field, directionFrom, 0, 0, false);
		System.out.println("--------------------------------");
		System.out.println(maxCount);
	}

	private static int countTiles() {
		int count=0;
		for (int y=0; y<dim; y++) {
			for (int x=0; x<dim; x++) {
				if (directionFrom[y][x][0]!=' ') {
					count++;
					// System.out.print('#');
				} //else System.out.print('.');
			}
			//System.out.println();
		}
		return count;
	}

	private static void initilizeDirectionFrom() {
		for (int y=0; y<dim; y++) {
			for (int x=0; x<dim; x++) {
				directionFrom[y][x] = new char[] {' ', ' ', ' ', ' '};
			}
		}
	}

	private static void followBeamWestOrEast(char[][] field, char[][][] directionFrom, int x, int y, boolean west) {
		if (west) {
			// direction west <<<
			for (int x1=x; x1>=0; x1--) {
				if (!containsDirection(directionFrom[y][x1], EAST)) {
					insertDirection(directionFrom[y][x1], EAST);
					// prüfen, on |, / oder \ vorliegt
					char tile = field[y][x1];
					if (tile =='|') {
						followBeamNorthOrSouth(field, directionFrom, x1, y-1, true);
						followBeamNorthOrSouth(field, directionFrom, x1, y+1, false);
						return;
					} else if (tile =='\\' || tile =='/') {
						followBeamNorthOrSouth(field, directionFrom, x1, tile=='/'?y+1:y-1, tile=='\\');
						return;
					}
				} else return;
			}
		} else {
			// direction east >>>
			for (int x1=x; x1<dim; x1++) {
				if (!containsDirection(directionFrom[y][x1], WEST)) {
					insertDirection(directionFrom[y][x1], WEST);
					// prüfen, on |, / oder \ vorliegt
					char tile = field[y][x1];
					if (tile =='|') {
						followBeamNorthOrSouth(field, directionFrom, x1, y-1, true);
						followBeamNorthOrSouth(field, directionFrom, x1, y+1, false);
						return;
					} else if (tile =='\\' || tile =='/') {
						followBeamNorthOrSouth(field, directionFrom, x1, tile=='/'?y-1:y+1, tile=='/');
						return;
					}
				} else return;
			}
		}
	}

	private static void followBeamNorthOrSouth(char[][] field, char[][][] directionFrom, int x, int y, boolean north) {
		if (north) {
			// direction north ^^
			for (int y1=y; y1>=0; y1--) {
				if (!containsDirection(directionFrom[y1][x], SOUTH)) {
					insertDirection(directionFrom[y1][x], SOUTH);
					// prüfen, on -, / oder \ vorliegt
					char tile = field[y1][x];
					if (tile =='-') {
						followBeamWestOrEast(field, directionFrom, x-1, y1, true);
						followBeamWestOrEast(field, directionFrom, x+1, y1, false);
						return;
					} else if (tile =='\\' || tile =='/') {
						followBeamWestOrEast(field, directionFrom, tile=='/'?x+1:x-1, y1, tile=='\\');
						return;
					}
				} else return;
			}
		} else {
			// direction south vvv
			for (int y1=y; y1<dim; y1++) {
				if (!containsDirection(directionFrom[y1][x], NORTH)) {
					insertDirection(directionFrom[y1][x], NORTH);
					// prüfen, on -, / oder \ vorliegt
					char tile = field[y1][x];
					if (tile =='-') {
						followBeamWestOrEast(field, directionFrom, x-1, y1, true);
						followBeamWestOrEast(field, directionFrom, x+1, y1, false);
						return;
					} else if (tile =='\\' || tile =='/') {
						followBeamWestOrEast(field, directionFrom, tile=='/'?x-1:x+1, y1, tile=='/');
						return;
					}
				} else return;
			}
		}
	}

	private static void insertDirection(char[] directions, char d) {
		for (int i=0; i<4; i++) {
			if (directions[i] == ' ') {
				directions[i] = d;
				return;
			}
		}
	}

	private static boolean containsDirection(char[] directions, char d) {
		for (int i=0; i<4; i++) {
			if (directions[i] == d)	return true;
		}
		return false;
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
