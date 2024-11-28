package de.borowski.aoc;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Day04_Scratchcards {

	public static List<List<Integer>> winningNumbers = new ArrayList<List<Integer>>();
	public static List<List<Integer>> ownNumbers = new ArrayList<List<Integer>>();

	public static void main(String[] args) {
		BufferedReader reader;
		
		try {
			reader = new BufferedReader(new FileReader("/home/christoph/Projects/eclipse-workspace/AdventOfCode/2023/input/input_day04_testset.txt"));
			String line = reader.readLine();
			
			while (line != null) {
				fillLists(line.substring(line.indexOf(':') + 2));
				// read next line
				line = reader.readLine();
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("--------------------------------");
		System.out.println(determineWinningAmount());
	}

	private static int determineWinningAmount() {
		int amount = 0;

		for (int i=0; i<ownNumbers.size(); i++) {
			List<Integer> on = ownNumbers.get(i);
			List<Integer> wn = winningNumbers.get(i);
			int winCount = -1;
			for (Integer n : on) {
				if (wn.contains(n)) {
					winCount++;
				}
			}
			if (winCount > -1) {
				amount += Math.pow(2, winCount);
			}
		}
		return amount;
	}

	private static void fillLists(String line) {
		winningNumbers.add(createList(line.substring(0, line.indexOf('|') - 1)));
		ownNumbers.add(createList(line.substring(line.indexOf('|') + 2)));
	}

	private static List<Integer> createList(String line) {
		String[] numbers = line.split(" ");
		List<Integer> numberList = new ArrayList<Integer>();
		for (String n : numbers) {
			if (!n.isEmpty()) {
				numberList.add(Integer.parseInt(n));
			}
		}
		return numberList;
	}

}
