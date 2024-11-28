package de.borowski.aoc;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Day01_CalorieCounting {

	public static void main(String[] args) {
		BufferedReader reader;
		int tmpCalories = 0;
		List<Integer> caloriesList = new ArrayList<Integer>();

		try {
			reader = new BufferedReader(new FileReader("C:\\Users\\cborowski\\Projects\\eclipse-workspace\\AdventOfCode\\Day01\\input\\input_day0.txt"));
			String line = reader.readLine();

			while (line != null) {
				if (line.equals("")) {
					System.out.println(tmpCalories);
					caloriesList.add(tmpCalories);
					tmpCalories = 0;
				} else {
					tmpCalories += Integer.parseInt(line);
				}
				// read next line
				line = reader.readLine();
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		caloriesList.sort((o1, o2) -> {
			return -(o1.compareTo(o2));
		});

		System.out.println("-------- Top calories ----------");
		int cal = 0;
		for (int i=0; i<3; i++) {
			int c = (int) caloriesList.get(i);
			System.out.println(c);
			cal += c;
		}
		System.out.println("--------------------------------");
		System.out.println(cal);
	}

}
