package de.borowski.aoc;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import java.util.*;

public class Day24_NeverTellMeTheOdds {

	public static List<Vector> p_vec = new ArrayList<>();
	public static List<Vector> d_vec = new ArrayList<>();

	public static double MIN = 200000000000000.0;
	public static double MAX = 400000000000000.0;

	public static void main(String[] args) {
		BufferedReader reader;
		
		try {
			//reader = new BufferedReader(new FileReader("/home/christoph/Projects/eclipse-workspace/AdventOfCode/2023/input/input_day24_testset.txt"));
			reader = new BufferedReader(new FileReader("/home/christoph/Projects/eclipse-workspace/AdventOfCode/2023/input/input_day24.txt"));
			String line = reader.readLine();


			
			while (line != null) {
				line = line.replace('@', ',');
				//line.replace(' ', null);
				String[] x = line.split(",");
				p_vec.add(new Vector(Long.parseLong(x[0].trim()), Long.parseLong(x[1].trim()), Long.parseLong(x[2].trim())));
				d_vec.add(new Vector(Long.parseLong(x[3].trim()), Long.parseLong(x[4].trim()), Long.parseLong(x[5].trim())));
				line = reader.readLine();
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		int count = 0;
		for (int x=0; x<p_vec.size()-1; x++) {
			for (int y=x+1; y<p_vec.size(); y++) {
				if (interact(p_vec.get(x), d_vec.get(x), p_vec.get(y), d_vec.get(y))) count++;
			}
		}
		System.out.println("--------------------------------");
		System.out.println(count);
	}

	private static boolean interact(Vector p1, Vector d1, Vector p2, Vector d2) {
		System.out.println("--------------------------------");
		double s1 = ((double)(p2.x-p1.x)/d1.x + ((double)(p1.y-p2.y)*d2.x)/(d2.y*d1.x)) / (1 - (double)(d1.y*d2.x)/(d2.y*d1.x));
		double s2 = ((double)(p1.y-p2.y)/d2.y + ((double)(p2.x-p1.x)*d1.y)/(d1.x*d2.y)) / (1 - (double)(d2.x*d1.y)/(d1.x*d2.y));
		System.out.println(s1 + " ~ " + s2);

		if (s1 <= 0 || s2 <= 0 || s1 == Double.POSITIVE_INFINITY) return false;

		double x = p1.x + s1 * d1.x;
		double y = p1.y + s1 * d1.y;

		if (x<MIN || x>MAX || y<MIN || y>MAX) return false;
		System.out.println(x + " " + y);

		return true;
	}

	public static class Vector {
		long x, y, z;

		public Vector(long x, long y, long z) {
			this.x = x;
			this.y = y;
			this.z = z;
		}

		@Override
		public boolean equals(Object o) {
			if (this == o) return true;
			if (o == null || getClass() != o.getClass()) return false;
			Vector vector = (Vector) o;
			return x == vector.x && y == vector.y && z == vector.z;
		}

		@Override
		public int hashCode() {
			return Objects.hash(x, y, z);
		}

		@Override
		public String toString() {
			return "Vector{" +
					"x=" + x +
					", y=" + y +
					", z=" + z +
					'}';
		}
	}

}
