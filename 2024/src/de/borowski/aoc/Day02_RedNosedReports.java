package de.borowski.aoc;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Day02_RedNosedReports {

	private List<int[]> reports = new ArrayList<>();

	public Day02_RedNosedReports() {
		readInput();

		int sumCorrectReports=0;
		for (int i = 0; i< reports.size(); i++) {
			sumCorrectReports += isReportCorrect(reports.get(i)) ? 1 : 0;
		}
		System.out.println("--------------------------------");
		System.out.println(sumCorrectReports);
	}

	private boolean isReportCorrect(int[] repData) {
		int length = repData.length;
		boolean increasingValues = true;
		for (int i=0; i<length-1; i++) {
			int diff = Math.abs(repData[i+1] - repData[i]);
			if (diff<1 || diff>3) return false;
			if (i == 0) {
				if (repData[i+1] < repData[i]) increasingValues = false;
			} else {
				if (increasingValues) {
					if (repData[i+1] < repData[i]) return false;
				} else
					if (repData[i+1] > repData[i]) return false;
			}
		}
		return true;
	}

	private void readInput() {
		BufferedReader reader;

		try {
			// reader = new BufferedReader(new FileReader("/home/christoph/Projects/IdeaProjects/AdventOfCode/2024/input/input_day02_testset.txt"));
			reader = new BufferedReader(new FileReader("/home/christoph/Projects/IdeaProjects/AdventOfCode/2024/input/input_day02.txt"));
			String line = reader.readLine();

			while (line != null) {
				// System.out.println(line);
				String[] values = line.split(" ");
				reports.add(convertReportData(values));
				// read next line
				line = reader.readLine();
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private int[] convertReportData(String[] values) {
		int length = values.length;
		int[] repData = new int[length];
		for (int i=0; i<length; i++) {
			repData[i] = Integer.parseInt(values[i]);
		}
		return repData;
	}

	public static void main(String[] args) {
		new Day02_RedNosedReports();
	}
}
