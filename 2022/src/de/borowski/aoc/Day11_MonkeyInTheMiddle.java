package de.borowski.aoc;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Day11_MonkeyInTheMiddle {
	
	static List<Monkey> monkeys = new ArrayList<Monkey>();

	public static void main(String[] args) {
		BufferedReader reader;
		
		try {
			StringBuffer monkeyData = new StringBuffer();
			int monkeyNumber = 0;
			reader = new BufferedReader(new FileReader("C:\\Users\\cborowski\\Projects\\eclipse-workspace\\AdventOfCode\\Day01\\input\\input_day11.txt"));
			String line = reader.readLine();
			
			while (line != null) {
				if (!line.startsWith("Monkey") && line.trim().length()!=0) {
					monkeyData.append(line.trim());
					monkeyData.append('|');
				}
				// read next line
				line = reader.readLine();
				if ( line == null || line.startsWith("Monkey")) {
					monkeys.add(createMonkey(monkeyNumber, monkeyData.toString()));
					monkeyNumber++;
					monkeyData = new StringBuffer();
				}
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		playRounds(10000);
		System.out.println("--------------------------------");
		printprintTotalNumberOfInspections();
	}

	private static void playRounds(int round_max) {
		for (int round=1; round<=round_max; round++) {
			for (int monkeyNr = 0; monkeyNr<monkeys.size(); monkeyNr++) {
				monkeys.get(monkeyNr).inspectItem();
			}
			printMonkes(round);
		}
	}

	private static void printMonkes(int round ) {
		System.out.println("After round " + round + ", the monkeys are holding items with these worry levels:");
		for (Monkey m : monkeys) {
			m.printItems();
		}
	}
	
	private static void printprintTotalNumberOfInspections() {
		for (Monkey m : monkeys) {
			m.printTotalNumberOfInspections();
		}
	}

	private static Monkey createMonkey(int monkeyNumber, String monkeyData) {
		String[] lines = monkeyData.split("\\|");
		
		Monkey m = new Monkey(monkeyNumber);
		
		// get items
		lines[0] = lines[0].substring(16);
		m.addItems(lines[0].split(", "));
		
		// get operator and operand
		lines[1] = lines[1].substring(21);
		m.setOperatorAndOperand(lines[1].split(" "));
		
		// get divisible by
		m.setDivisibleBy(Integer.parseInt(lines[2].substring(19)));
		
		// get monkeyIfTrue
		m.setMonkeyIfTrue(Integer.parseInt(lines[3].substring(25)));

		// get monkeyIfTrue
		m.setMonkeyIfFalse(Integer.parseInt(lines[4].substring(26)));
		
		m.printData();

		return m;
	}
	
	private static class Monkey {
		static long productOfAllDivisibleBy = 1;
		int totalNumberOfInspections = 0;
		int monkeyNumber = 0;
		List<Long> worryLevels = new ArrayList<Long>();
		int divisibleBy = 1;
		int[] monkeyIfTrueFalse = {0, 0};
		char operator = '+';
		long operand = 0;	// 0 stands for 'old'

		public Monkey(int monkeyNumber) {
			this.monkeyNumber = monkeyNumber;
		}

		public void inspectItem() {
			while (worryLevels.size()>0) {
				// remove first item
				long wl = worryLevels.remove(0);
				long operand = this.operand;
				if (this.operand == 0) {
					System.out.print(wl);
					System.out.println(" -> " + wl);
					operand = wl;
				}
				wl = (operator == '+') ? wl + operand : wl * operand;
				// in part 2 worry levels are no longer divided by three after each item is inspected
				// item = Math.floorDiv(item, 3);
				wl = wl % productOfAllDivisibleBy;
				if (wl % divisibleBy == 0) {
					monkeys.get(monkeyIfTrueFalse[0]).addItems(wl);
				} else {
					monkeys.get(monkeyIfTrueFalse[1]).addItems(wl);
				}
				totalNumberOfInspections++;
			}
		}

		public void setOperatorAndOperand(String[] operatorAndOperand) {
			this.operator = operatorAndOperand[0].charAt(0);
			if (!operatorAndOperand[1].equals("old")) {
				this.operand = Integer.parseInt(operatorAndOperand[1]);
			}	
		}

		public void setMonkeyIfTrue(int monkeyIfTrue) {
			this.monkeyIfTrueFalse[0] = monkeyIfTrue;
		}

		public void setMonkeyIfFalse(int monkeyIfFalse) {
			this.monkeyIfTrueFalse[1] = monkeyIfFalse;
		}

		public void setDivisibleBy(int divisibleBy) {
			this.divisibleBy = divisibleBy;
			productOfAllDivisibleBy = productOfAllDivisibleBy * divisibleBy;
		}

		public void addItems(String[] items) {
			for (String item : items) {
				this.worryLevels.add(Long.parseLong(item));
			}
		}
		
		private void addItems(long item) {
			this.worryLevels.add(item);
		}

		public void printData() {
			printItems();
			System.out.println(operator + " " + operand);
			System.out.println(divisibleBy);
			System.out.println(monkeyIfTrueFalse[0]);
			System.out.println(monkeyIfTrueFalse[1]);
		}
		
		public void printItems() {
			System.out.print("Monkey " + monkeyNumber + " : ");
			for (Long item : worryLevels) {
				System.out.print(item);
				System.out.print(' ');
			}
			System.out.println();
		}

		public void printTotalNumberOfInspections() {
			System.out.println("Monkey " + monkeyNumber + " inspected items " + totalNumberOfInspections + " times.");
		}
	}

}
