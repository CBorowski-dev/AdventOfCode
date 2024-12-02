package de.borowski.aoc;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Day02_RedNosedReports_Part2 {

	private final List<List<Integer>> reports = new ArrayList<>();

	public Day02_RedNosedReports_Part2() {
		readInput();

		int sumCorrectReports=0;
        for (List<Integer> report : reports) {
            boolean reportCorrect = isReportCorrect(report);
            if (reportCorrect) {
                sumCorrectReports += 1;
            } else {
                boolean correctReportFound = false;
                for (int x = 0; !correctReportFound && x < report.size(); x++) {
                    List<Integer> tmpReport = new ArrayList<>(report);
                    tmpReport.remove(x);
                    if (isReportCorrect(tmpReport)) {
                        sumCorrectReports += 1;
                        correctReportFound = true;
                    }
                }
            }
        }
		System.out.println("--------------------------------");
		System.out.println(sumCorrectReports);
	}

	private boolean isReportCorrect(List<Integer> repData) {
		int size = repData.size();
		boolean increasingValues = true;
		for (int i=0; i<size-1; i++) {
			int diff = Math.abs(repData.get(i+1) - repData.get(i));
			if (diff<1 || diff>3) return false;
			if (i == 0) {
				if (repData.get(i+1) < repData.get(i)) increasingValues = false;
			} else {
				if (increasingValues) {
					if (repData.get(i+1) < repData.get(i)) return false;
				} else
					if (repData.get(i+1) > repData.get(i)) return false;
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

	private List<Integer> convertReportData(String[] values) {
		int length = values.length;
		List<Integer> repData = new ArrayList<Integer>(length);
        for (String value : values) {
            repData.add(Integer.parseInt(value));
        }
		return repData;
	}

	public static void main(String[] args) {
		new Day02_RedNosedReports_Part2();
	}
}
