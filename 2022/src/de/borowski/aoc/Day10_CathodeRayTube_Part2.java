package de.borowski.aoc;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Day10_CathodeRayTube_Part2 {

	static int[] relevantCycles = new int[] {40, 80, 120, 160, 200, 240};
	static int relevantCyclesPointer = 0;
	static int registerX = 1;
	static int cycle = 0;

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
	}

	private static void processInstruction(String line) {
		printToScreen();
		cycle++;
		if (!line.equals("noop")) {
			printToScreen();
			final String[] x = line.split(" ");
			registerX += Integer.parseInt(x[1]);
			cycle++;
		}
	}

	private static void printToScreen() {
		if (cycle == relevantCycles[relevantCyclesPointer]) {
			relevantCyclesPointer++;
			System.out.println();
		}
		if ((cycle%40)>= (registerX-1) && (cycle%40)<= (registerX+1)) {
			System.out.print('#');
		} else {
			System.out.print('.');
		}
	}

}