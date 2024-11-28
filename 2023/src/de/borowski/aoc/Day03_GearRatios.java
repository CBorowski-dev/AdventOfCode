package de.borowski.aoc;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day03_GearRatios {

	public static Map<Integer, List<Integer>> mapOfYCoordinatesByX = new HashMap<Integer, List<Integer>>();
	public static List<NumberData> listOfNumberData = new ArrayList<NumberData>();

	public static void main(String[] args) {
		BufferedReader reader;
		
		try {
			int lineNumber = 0;
			reader = new BufferedReader(new FileReader("/home/christoph/Projects/eclipse-workspace/AdventOfCode/2023/input/input_day03.txt"));
			String line = reader.readLine();
			
			while (line != null) {
				extractData(line, lineNumber, mapOfYCoordinatesByX);
				// read next line
				line = reader.readLine();
				lineNumber++;
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		int sum = 0;
		for (NumberData nd : listOfNumberData) {
			sum += checkRelevanceOfNumberData(nd);
		}
		System.out.println("--------------------------------");
		System.out.println(sum);
	}

	private static int checkRelevanceOfNumberData(NumberData nd) {
		for (int x=nd.x - 1; x<=nd.x+nd.length; x++) {
			for (int y=nd.y-1; y<=nd.y+1; y++) {
				List<Integer> l = mapOfYCoordinatesByX.get(y);
				if (l != null && l.contains(x)) {
					return nd.number;
				}
			}
		}
		return 0;
	}

	private static void extractData(String line, int lineNumber, Map<Integer, List<Integer>> mapOfYCoordinatesByX) {
		for (int x=0; x<line.length(); x++) {
			char c = line.charAt(x);
			if (c != '.') {
				if (!Character.isDigit(c)) {
					// Symbol found
					System.out.println(c);
					List<Integer> l = mapOfYCoordinatesByX.computeIfAbsent(lineNumber, k -> new ArrayList<Integer>());
					l.add(x);
				} else {
					// Number found
					String lineShortened = line.substring(x);
					int x1=0;
					while (x1<lineShortened.length() && Character.isDigit(lineShortened.charAt(x1))) {
						x1++;
					}
					lineShortened = lineShortened.substring(0, x1);
					NumberData numberData = new NumberData(Integer.parseInt(lineShortened), lineShortened.length(), x, lineNumber);
					listOfNumberData.add(numberData);
					System.out.println(numberData);
					x += (x1 - 1);
				}
			}
		}
	}

	public static class NumberData {
		int x, y;
		int number;
		int length;

		public NumberData(int n, int l, int x, int y) {
			number = n;
			length = l;
			this.x = x;
			this.y = y;
		}

		@Override
		public String toString() {
			return number + " " + length + " " + x + " " +y;
		}
	}

}
