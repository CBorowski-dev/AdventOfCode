package de.borowski.aoc;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Day04_CampCleanup {

	public static void main(String[] args) {
		BufferedReader reader;
		int lineCounter = 0;
		int sum = 0;
		
		try {
			reader = new BufferedReader(new FileReader("C:\\Users\\cborowski\\Projects\\eclipse-workspace\\AdventOfCode\\Day01\\input\\input_day04.txt"));
			String line = reader.readLine();
			
			while (line != null) {
				lineCounter++;
				// Paret 1
				// sum += compute(line);
				
				// Part 2
				sum += compute(line);
				
				// read next line
				line = reader.readLine();
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("--------------------------------");
		// Part 1
		// System.out.println(sum);
		// Part 2
		System.out.println(lineCounter - sum);
	}

	private static int compute(String line) {
		final String[] inputs = line.split(",");
		return compute(inputs[0], inputs[1]);
	}

	private static int compute(String left, String right) {
		int leftFrom, leftTo, rightFrom, rightTo;
		String[] leftInputs = left.split("-");
		leftFrom = Integer.parseInt(leftInputs[0]);
		leftTo = Integer.parseInt(leftInputs[1]);
		String[] rightInputs = right.split("-");
		rightFrom = Integer.parseInt(rightInputs[0]);
		rightTo = Integer.parseInt(rightInputs[1]);
		
		// Part 1
//		boolean result = (leftFrom<=rightFrom && leftTo>=rightTo) || (leftFrom>=rightFrom && leftTo<=rightTo);
		
		// Part 2
		boolean result = leftTo<rightFrom || rightTo<leftFrom;

		return result ? 1 : 0;
	}

}
