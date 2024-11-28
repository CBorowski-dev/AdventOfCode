package de.borowski.aoc;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Day11_CosmicExpansion {
	public static Map<Integer, Coordinate> stars = new HashMap<Integer, Coordinate>();

	public static int dimX = 140;
	public static int expansion = 1000000;

	public static void main(String[] args) {
		BufferedReader reader;
		
		try {
			reader = new BufferedReader(new FileReader("/home/christoph/Projects/eclipse-workspace/AdventOfCode/2023/input/input_day11.txt"));
			String line = reader.readLine();

			int starNr = 0;
			int row = 0;
			while (line != null) {
				if (line.contains("#")) {
					Map<Integer, Coordinate> starsTmp = getCoordinates(line, row, starNr);
					stars.putAll(starsTmp);
					starNr += starsTmp.size();
					row++;
				} else {
					row += expansion;
				}
				// read next line
				line = reader.readLine();
			}
			insertNecessarySpace(stars);
			// listStars(stars);
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("--------------------------------");
		System.out.println(calculateAllDistances(stars));
	}

	private static long calculateAllDistances(Map<Integer, Coordinate> stars) {
		long d = 0;
		int starCount = stars.size();
		for (int i=0; i<starCount; i++) {
			for (int x=i+1; x<starCount; x++) {
				d += calculateDistance(stars.get(i), stars.get(x));
			}
		}
		return d;
	}

	private static int calculateDistance(Coordinate c0, Coordinate c1) {
		int dx = Math.abs(c1.x - c0.x);
		int dy = Math.abs(c1.y - c0.y);

		return dx + dy;
	}
	private static void listStars(Map<Integer, Coordinate> stars) {
		for (int starNr : stars.keySet()) {
			System.out.println(stars.get(starNr));
		}
	}

	private static void insertNecessarySpace(Map<Integer, Coordinate> stars) {
		for (int col=dimX-1; col>=0; col--) {
			if (!hasColumnStars(stars, col)) {
				moveStars(stars, col);
			}
		}
	}

	private static void moveStars(Map<Integer, Coordinate> stars, int col) {
		for (Coordinate c : stars.values()) {
			if (c.x > col) c.x+= (expansion - 1);
		}
	}

	private static boolean hasColumnStars(Map<Integer, Coordinate> stars, int col) {
		for (Coordinate c : stars.values()) {
			if (c.x == col) return true;
		}
		return false;
	}

	private static Map<Integer, Coordinate> getCoordinates(String line, int row, int starNr) {
		Map<Integer, Coordinate> starsTmp = new HashMap<Integer, Coordinate>();
		int column = 0;
		while((column = line.indexOf('#', column)) >= 0) {
			Coordinate star = new Coordinate(column, row);
			starsTmp.put(starNr, star);
			starNr++;
			column++;
		}
		return starsTmp;
	}

	public static class Coordinate {
		public int x, y;

		@Override
		public String toString() {
			return "Coordinate{" +
					"x=" + x +
					", y=" + y +
					'}';
		}

		public Coordinate(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}

}
