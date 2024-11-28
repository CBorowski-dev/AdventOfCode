package de.borowski.aoc;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Day06_WaitForIt {

	public static int numberOfRaces = 1;
	public static long[][] timeDistanceValues = new long[2][numberOfRaces];
	public static void main(String[] args) {
		BufferedReader reader;

		try {
			reader = new BufferedReader(new FileReader("/home/christoph/Projects/eclipse-workspace/AdventOfCode/2023/input/input_day06.txt"));
			String line = reader.readLine();
			int i = 0;
			while (line != null) {
				getValuesIntoList(line, timeDistanceValues, i);
				// read next line
				line = reader.readLine();
				i++;
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		long result = 1;
		for (int i=0; i<numberOfRaces; i++) {
			result *= calculateNumberOfPossibilities(timeDistanceValues[0][i], timeDistanceValues[1][i]);
		}
		System.out.println("--------------------------------");
		System.out.println(result);
	}

	private static long calculateNumberOfPossibilities(long time, long distanceValue) {
		int count = 0;
		for (long t=0; t<=time; t++) {
			if (t*(time-t)>distanceValue)
				count++;
		}
		return count;
	}

	private static void getValuesIntoList(String line, long[][] timeDistanceValue, int i) {
		line = line.substring(line.indexOf(':')+1);
		String[] values = line.split(" ");
		int x = 0;
		String wholeNumber = "";
		for (int n=0; n<values.length; n++) {
			if (!values[n].isEmpty()) {
				// timeDistanceValue[i][x] = Integer.parseInt(values[n]);
				// x++;
				wholeNumber += values[n];
			}
		}
		timeDistanceValue[i][x] = Long.parseLong(wholeNumber);
	}

}
