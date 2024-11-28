package de.borowski.aoc;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Day01_Trebuchet {
	
	// one, two, three, four, five, six, seven, eight, and nine
	public static String[] writenDigits = new String[] {"one", "two", "three", "four", "five", "six", "seven", "eight", "nine"};

	public static void main(String[] args) {
		BufferedReader reader;
		int sumCalibrationValues = 0;

		try {
			reader = new BufferedReader(new FileReader("C:\\Users\\cborowski\\Projects\\eclipse-workspace\\AdventOfCode\\2023\\input\\input_day01.txt"));
			String line = reader.readLine();

			while (line != null) {
				sumCalibrationValues += getCalibrationValue(line);
				// read next line
				line = reader.readLine();
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println(sumCalibrationValues);
	}

	private static int getCalibrationValue(String line) {
		int[] digits = new int[2];
		int index = 0;
		int length = line.length();
		for (int i=0; i<length; i++) {
			if (Character.isDigit(line.charAt(i))) {
				digits[index] = Integer.parseInt(line.substring(i, i+1));
				if (index == 0) {
					digits[++index] = Integer.parseInt(line.substring(i, i+1));
				}
			} else {
				int result = checkForWritenDigit(line, i);
				if (result > 0) {
					digits[index] = result;
					if (index == 0) {
						digits[++index] = result;
					}
				}
			}
		}
		return digits[0]*10 + digits[1];
	}

	private static int checkForWritenDigit(String line, int i) {
		for (int x = 0; x<writenDigits.length; x++) {
			if (line.substring(i).startsWith(writenDigits[x])) {
				return x+1;
			}
		}
		return 0;
	}

}
