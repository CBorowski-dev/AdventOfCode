package de.borowski.aoc;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import java.util.*;

public class Day18_LavaductLagoonPart2 {

	public static List<Point> pointList = new LinkedList<>();

	public static void main(String[] args) {
		BufferedReader reader;

		long x=0, y=0, stepCount = 0;

		pointList.add(new Point(0, 0));
		try {
			reader = new BufferedReader(new FileReader("/home/christoph/Projects/eclipse-workspace/AdventOfCode/2023/input/input_day18_testset.txt"));
			//reader = new BufferedReader(new FileReader("/home/christoph/Projects/eclipse-workspace/AdventOfCode/2023/input/input_day18.txt"));
			String line = reader.readLine();

			while (line != null) {
				line = line.substring(line.indexOf('#')+1, line.length()-1);
				char direction = line.charAt(line.length()-1);
				int steps = Integer.parseInt(line.substring(0, line.length()-1), 16);
				stepCount += steps;
				switch (direction) {
					case '3':
						y -= steps;
						break;
					case '0':
						x += steps;
						break;
					case '2':
						x -= steps;
						break;
					case '1':
						y += steps;
				}
				pointList.add(new Point(x , y));
				line = reader.readLine();
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("--------------------------------");
		System.out.println(countCubics() + (stepCount+2)/2);
	}

	private static long countCubics() {
		long count = 0;

		for (int i=0; i<pointList.size()-1; i+=2) {
			Point p1 = pointList.get(i);
			Point p2 = pointList.get(i+1);
			count += (p1.x*p2.y - p1.y*p2.x);
		}
		Point p1 = pointList.get(pointList.size()-2);
		Point p2 = pointList.get(pointList.size()-1);
		count += (p1.x*p2.y - p1.y*p2.x);
		return count;
	}

	public static class Point {
		long x, y;

		@Override
		public String toString() {
			return "Point{" +
					"x=" + x +
					", y=" + y +
					'}';
		}

		public Point(long x, long y) {
			this.x = x;
			this.y = y;
		}
	}
}
