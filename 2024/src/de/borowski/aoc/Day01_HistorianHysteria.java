package de.borowski.aoc;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Day01_HistorianHysteria {

	private static List<Integer> l1 = new ArrayList<>();
	private static List<Integer> l2 = new ArrayList<>();

	public static void main(String[] args) {
		BufferedReader reader;
		
		try {
			// reader = new BufferedReader(new FileReader("/home/christoph/Projects/IdeaProjects/AdventOfCode/2024/input/input_day01_testset.txt"));
			reader = new BufferedReader(new FileReader("/home/christoph/Projects/IdeaProjects/AdventOfCode/2024/input/input_day01.txt"));
			String line = reader.readLine();
			
			while (line != null) {
				// System.out.println(line);
				String[] values = line.split("   ");
				l1.add(Integer.parseInt(values[0]));
				l2.add(Integer.parseInt(values[1]));
				// read next line
				line = reader.readLine();
			}
			reader.close();

			l1.sort((Integer v1, Integer v2) -> v1 - v2);
			l2.sort((Integer v1, Integer v2) -> v1 - v2);

			int sum=0;
			for (int i=0; i<l1.size(); i++) {
				sum += Math.abs(l1.get(i) - l2.get(i));
			}
			System.out.println(sum);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("--------------------------------");
	}
}
