package de.borowski.aoc;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

public class Day09_MirageMaintenance {

	public List<Integer> nextValues = new ArrayList<Integer>();

	public static void main(String[] args) {
        BufferedReader reader;

        long sumNextValues = 0;
        try {
            reader = new BufferedReader(new FileReader("/home/christoph/Projects/eclipse-workspace/AdventOfCode/2023/input/input_day09_testset.txt"));
            String line = reader.readLine();

            sumNextValues = 0;
            while (line != null) {
				// sumNextValues += getNextValue2(line); // part 1
				sumNextValues += getNextValue2(line); // part 2
                // read next line
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("--------------------------------");
        System.out.println(sumNextValues);
    }

	private static long getNextValue2(String line) {
		List<Long> edgeValues = new ArrayList<Long>();
		String[] valueStrings = line.split(" ");
		Long[] values = new Long[valueStrings.length];
		for (int i=0; i<valueStrings.length; i++) {
			values[i] = Long.parseLong(valueStrings[i]);
		}
		boolean noNextGeneration = true;
		int generation = 1;
		while (noNextGeneration) {
			for (int i=values.length-1; i>=generation; i--) {
				values[i] = values[i] - values[i-1];
			}

			if (checkValues2(values, generation)) {
				generation++;
			} else {
				noNextGeneration = false;
			}
		}
		long sum = values[generation];
		for (int i=generation-1; i>=0; i--) {
			sum = values[i]-sum;
		}

		return sum;
	}

	private static boolean checkValues2(Long[] values, int generation) {
		long v = values[generation];
		for (int i=generation+1; i<values.length-1; i++) {
			if (v != values[i]) {
				return true;
			}
		}
		return false;
	}
	private static long getNextValue(String line) {
		List<Long> edgeValues = new ArrayList<Long>();
		String[] valueStrings = line.split(" ");
		Long[] values = new Long[valueStrings.length];
		for (int i=0; i<valueStrings.length; i++) {
			values[i] = Long.parseLong(valueStrings[i]);
		}
		boolean noNextGeneration = true;
		int generation = 1;
		while (noNextGeneration) {
			for (int i=0; i<values.length-generation; i++) {
				values[i] = values[i+1] - values[i];
			}

			if (checkValues(values, generation)) {
				generation++;
			} else {
				noNextGeneration = false;
			}
		}
		long sum = 0;
		for (int i=values.length-1-generation; i<values.length; i++) {
			sum += values[i];
		}
		return sum;
	}

	private static boolean checkValues(Long[] values, int generation) {
		long v = values[0];
		for (int i=1; i<values.length-generation; i++) {
			if (v != values[i]) {
				return true;
			}
		}
		return false;
	}
}
