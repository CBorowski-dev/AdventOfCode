package de.borowski.aoc;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Day10_PipeMaze {

	public static int dimX = 140;
	public static int dimY = 140;
	public static char[][] maze = new char[dimY][];
	public static char[][] maze2 = new char[dimY][dimX];
	public static Position cp;

	public static void main(String[] args) {
		BufferedReader reader;
		
		try {
			//reader = new BufferedReader(new FileReader("/home/christoph/Projects/eclipse-workspace/AdventOfCode/2023/input/input_day10_testset.txt"));
			reader = new BufferedReader(new FileReader("/home/christoph/Projects/eclipse-workspace/AdventOfCode/2023/input/input_day10.txt"));
			String line = reader.readLine();

			int y=0;
			while (line != null) {
				// System.out.println(line);
				if (line.indexOf('S')>-1) {
					cp = new Position(line.indexOf('S'), y);
				}
				maze[y++] = line.toCharArray();
				// read next line
				line = reader.readLine();
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		initilizeMaze(maze2);
		System.out.println("--------------------------------");
		//findNextPosition(maze, new Position(cp.x, cp.y-1), cp, 1);
		// findNextPosition(maze, new Position(cp.x-1, cp.y), cp, 1);
		maze2[cp.y+1][cp.x] = maze[cp.y+1][cp.x];
		findNextPosition(maze, new Position(cp.x, cp.y+1), cp, 1);
		//findNextPosition(maze, new Position(cp.x+1, cp.y), cp, 1);
	}

	private static boolean findNextPosition(char[][] maze, Position p, Position pp, int steps) {
		// System.out.println(p + " " + steps);
		int x = p.x;
		int y = p.y;
		if (x<0 || x>dimX || y<0 || y>dimY || maze[y][x]=='.') return false;
		if (maze[y][x]=='S') {
			System.out.println(steps);
			// S-Zeichen manuel ersetzen
			maze2[cp.y][cp.x] = '|';
			printMaze(maze2);
			System.out.println(computeInhalt(maze2));
			printMaze(maze2);
			return true;
		}
		// north
		if (y-1>=0 && pp.y != y-1 && (maze[y][x]=='|' || maze[y][x]=='J' || maze[y][x]=='L')) {
			char n = maze[y-1][x];
			if (n=='|' || n=='7' || n=='F' || n=='S') {
				// System.out.println("north");
				maze2[y-1][x] = maze[y-1][x];
				if (findNextPosition(maze, new Position(x, y-1), p, ++steps)) return true;
				maze2[y-1][x] = ' ';
			}
		}
		// west
		if (x-1>=0 && pp.x != x-1 && (maze[y][x]=='-' || maze[y][x]=='J' || maze[y][x]=='7')) {
			char n = maze[y][x-1];
			if (n=='-' || n=='L' || n=='F' || n=='S') {
				//System.out.println("west");
				maze2[y][x-1] = maze[y][x-1];
				if (findNextPosition(maze, new Position(x-1, y), p, ++steps)) return true;
				maze2[y][x-1] = ' ';
			}
		}
		// south
		if (y+1<dimY && pp.y != y+1 && (maze[y][x]=='|' || maze[y][x]=='F' || maze[y][x]=='7')) {
			char n = maze[y+1][x];
			if (n=='|' || n=='J' || n=='L' || n=='S') {
				//System.out.println("south");
				maze2[y+1][x] = maze[y+1][x];
				if (findNextPosition(maze, new Position(x, y+1), p, ++steps)) return true;
				maze2[y+1][x] = ' ';
			}
		}
		// east
		if (x+1<dimX && pp.x != x+1 && (maze[y][x]=='-' || maze[y][x]=='L' || maze[y][x]=='F')) {
			char n = maze[y][x+1];
			if (n=='-' || n=='7' || n=='J' || n=='S') {
				//System.out.println("east");
				maze2[y][x+1] = maze[y][x+1];
				if (findNextPosition(maze, new Position(x+1, y), p, ++steps)) return true;
				maze2[y][x+1] = ' ';
			}
		}
		return false;
	}

	private static int computeInhalt(char[][] m) {
		inside(m, 9, 4);
		int count = 0;
		for (int y=0; y<dimY; y++) {
			for (int x=0; x<dimX; x++) {
				if (m[y][x]==' ' && inside(m, x, y)) {
					m[y][x] = 'X';
					//System.out.println(x + " " + y);
					count++;
				}
			}
		}
		return count;
	}

	private static boolean inside(char[][] m, int x_, int y_) {
		// check North
		int borderCount = checkNorthSouth(m,0, y_, x_);
		if (borderCount % 2 == 0) return false;
		// check South
		borderCount = checkNorthSouth(m,y_+1, dimY, x_);
		if (borderCount % 2 == 0) return false;
		// check West
		borderCount = checkWestEasth(m,0, x_, y_);
		if (borderCount % 2 == 0) return false;
		// check East
		borderCount = checkWestEasth(m,x_+1, dimX, y_);
		if (borderCount % 2 == 0) return false;
		return true;
	}

	private static int checkNorthSouth(char[][] m, int yFrom, int yTo, int x) {
		int count = 0;
		StringBuffer sb = new StringBuffer();
		for (int y=yFrom; y<yTo; y++) {
			if (m[y][x] != ' ' && m[y][x] != '|' && m[y][x] != 'X') {
				sb.append(m[y][x]);
			}
		}
		char[] line = sb.toString().toCharArray();
		for (int i=0; i<line.length; ) {
			if (line[i]=='-') {
				count++;
				i++;
			} else {
				if ((line[i]=='F' && line[i+1]=='J')
						|| (line[i]=='7' && line[i+1]=='L')) {
					count++;
				}
				i+=2;
			}
		}
		return count;
	}

	private static int checkWestEasth(char[][] m, int xFrom, int xTo, int y) {
		int count = 0;
		StringBuffer sb = new StringBuffer();
		for (int x=xFrom; x<xTo; x++) {
			if (m[y][x] != ' ' && m[y][x] != '-' && m[y][x] != 'X') {
				sb.append(m[y][x]);
			}
		}
		char[] line = sb.toString().toCharArray();
		for (int i=0; i<line.length; ) {
			if (line[i]=='|') {
				count++;
				i++;
			} else {
				if ((line[i]=='L' && line[i+1]=='7')
						|| (line[i]=='F' && line[i+1]=='J')) {
					count++;
				}
				i+=2;
			}
		}
		return count;
	}

	private static boolean restIsEmpty(char[][] m, int y, int i) {
		for (int x=i; x<dimX; x++) {
			if (m[y][x]!=' ') return false;
		}
		return true;
	}

	private static void printMaze(char[][] maze2) {
		for (int y=0; y<dimY; y++) {
			for (int x=0; x<dimX; x++) {
				System.out.print(maze2[y][x]);
			}
			System.out.println();
		}
	}

	private static void initilizeMaze(char[][] maze2) {
		for (int y=0; y<dimY; y++) {
			for (int x=0; x<dimX; x++) {
				maze2[y][x] = ' ';
			}
		}
	}
	public static class Position {
		public int x, y;

		@Override
		public String toString() {
			return "Position{" +
					"x=" + x +
					", y=" + y +
					'}';
		}

		public Position(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}

}
