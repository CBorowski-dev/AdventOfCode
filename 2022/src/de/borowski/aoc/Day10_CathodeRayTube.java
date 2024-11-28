package de.borowski.aoc;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Day10_CathodeRayTube {
	
	static int[] relevantCycles = new int[] {20, 60, 100, 140, 180, 220};
	static int relevantCyclesPointer = 0;
	static int registerX = 1;
	static int cycle = 1;
	static int sum = 0;

	public static void main(String[] args) {
		BufferedReader reader;
		
		try {
			reader = new BufferedReader(new FileReader("C:\\Users\\cborowski\\Projects\\eclipse-workspace\\AdventOfCode\\Day01\\input\\input_day10.txt"));
			String line = reader.readLine();
			
			while (line != null) {
				processInstruction(line);
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

	private static void processInstruction(String line) {
		checkIfRelevantCycle();
		cycle++;
		if (!line.equals("noop")) {
			checkIfRelevantCycle();
			String[] x = line.split(" ");
			registerX += Integer.parseInt(x[1]);
//			System.out.println(x[1]);
			cycle++;
		}
	}

	private static void checkIfRelevantCycle() {
		if (relevantCyclesPointer <=5 && cycle == relevantCycles[relevantCyclesPointer]) {
			sum += relevantCycles[relevantCyclesPointer] * registerX;
			System.out.println(registerX + " " + sum + " " + relevantCycles[relevantCyclesPointer] + " " + relevantCycles[relevantCyclesPointer] * registerX);
			relevantCyclesPointer++;
		}
	}

}