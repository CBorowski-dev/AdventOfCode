package de.borowski.aoc;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import java.util.List;
import java.util.ArrayList;

public class Day01_SecretEntrance {

	private static final List<String> input = new ArrayList<>();
	private static int idx = 50;

    public static void main(String[] args) {
		BufferedReader reader;
		
		try {
			// reader = new BufferedReader(new FileReader("/home/christoph/Projects/IdeaProjects/AdventOfCode/2025/input/input_day01_testset.txt"));
			reader = new BufferedReader(new FileReader("/home/christoph/Projects/IdeaProjects/AdventOfCode/2025/input/input_day01.txt"));
			String line = reader.readLine();
			
			while (line != null) {
				// System.out.println(line);
				input.add(line);
				// read next line
				line = reader.readLine();
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		int zero_counter = 0;
		int max_steps = 100;

		// Part 1
        for (String step: input) {
			int clicks = Integer.parseInt(step.substring(1));
			if (step.charAt(0) == 'R') {
				idx = (idx + clicks) % max_steps;
			} else {
				idx = (idx - clicks) % max_steps;
			}
			if (idx == 0) zero_counter++;
		}
		System.out.println("--------------------------------");
		System.out.println("Part 1 : " + zero_counter);

		// Part 2
		zero_counter = 0;
		int clicks, new_idx;

		for (String step: input) {
			clicks = Integer.parseInt(step.substring(1));
			if (step.charAt(0) == 'R') {
				new_idx = idx + clicks;
				if (new_idx >= max_steps) {
					zero_counter += new_idx / max_steps;
					idx = new_idx % max_steps;
				} else {
					idx = new_idx;
				}
			} else {
				new_idx = idx - clicks;
				if (new_idx <= 0) {
					zero_counter -= (new_idx / max_steps) - (idx==0 ? 0 : 1);
					idx = (max_steps + new_idx % max_steps) % max_steps;
				} else {
					idx = new_idx;
				}
			}
		}

		System.out.println("--------------------------------");
		System.out.println("Part 2 : " + zero_counter);
	}

}
