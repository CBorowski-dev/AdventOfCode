package de.borowski.aoc;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day03_MullItOver_Part2 {

	private String processedInput;

	public Day03_MullItOver_Part2() {
		readInput();

		System.out.println("--------------------------------");
		System.out.println(computeResult());
	}

	private int computeResult() {
		int result = 0;
		Pattern pattern = Pattern.compile("mul[(][0-9]+,[0-9]+[)]");
		Matcher matcher = pattern.matcher(processedInput);
		while (matcher.find()) {
			String group = matcher.group();
			group = group.substring(4, group.length()-1);
			int index = group.indexOf(',');
			int v1 = Integer.parseInt(group.substring(0, index));
			int v2 = Integer.parseInt(group.substring(index + 1));
			result += (v1 * v2);
		}
		return result;
	}

	private void readInput() {
		StringBuffer sb = new StringBuffer();
		BufferedReader reader;
		try {
			// reader = new BufferedReader(new FileReader("/home/christoph/Projects/IdeaProjects/AdventOfCode/2024/input/input_day03_testset.txt"));
			reader = new BufferedReader(new FileReader("/home/christoph/Projects/IdeaProjects/AdventOfCode/2024/input/input_day03.txt"));
			String line = reader.readLine();
			while (line != null) {
				sb.append(line);
				// read next line
				line = reader.readLine();
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		processedInput = process(sb.toString());
	}

	private String process(String line) {
		String[] parts = line.split("do[(][)]");
		StringBuffer sb = new StringBuffer();
		for (int x=0; x < parts.length; x++) {
			String tmpPart = parts[x];
			int i = tmpPart.indexOf("don't()");
			if (i > 0) {
				tmpPart = tmpPart.substring(0, i);
			}
			sb.append(tmpPart);
		}
		return sb.toString();
	}

	public static void main(String[] args) {
		new Day03_MullItOver_Part2();
	}
}