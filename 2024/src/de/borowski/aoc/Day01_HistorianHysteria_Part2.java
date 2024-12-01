package de.borowski.aoc;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Day01_HistorianHysteria_Part2 {

	private List<Integer> l1 = new ArrayList<>();
	private List<Integer> l2 = new ArrayList<>();

	public Day01_HistorianHysteria_Part2() {
		readInput();
		sortLists();

		int sum=0;
		for (int i=0; i<l1.size(); i++) {
			sum += l1.get(i) * getValueCountFromList2(l1.get(i));
		}
		System.out.println("--------------------------------");
		System.out.println(sum);
	}

	private Integer getValueCountFromList2(Integer v) {
		int i1 = l2.indexOf(v);
		return (i1 >=0) ? l2.lastIndexOf(v) - l2.indexOf(v) + 1 : 0;
	}

	private void sortLists() {
		l1.sort((Integer v1, Integer v2) -> v1 - v2);
		l2.sort((Integer v1, Integer v2) -> v1 - v2);
	}

	private void readInput() {
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
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new Day01_HistorianHysteria_Part2();
	}
}
