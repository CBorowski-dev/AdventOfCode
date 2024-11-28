package de.borowski.aoc;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Day12_HotSprings_Part2_DP {

	public List<String> lines = new ArrayList<>(1000);
	public long arrangementCountSum = 0;

	public static void main(String[] args) {
		Day12_HotSprings_Part2_DP day = new Day12_HotSprings_Part2_DP();
		day.readInput();
		day.compute();
	}

	public Day12_HotSprings_Part2_DP() {
		readInput();
	}

	private void readInput() {
		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader("/home/christoph/Projects/eclipse-workspace/AdventOfCode/2023/input/input_day12_testset.txt"));
			// reader = new BufferedReader(new FileReader("/home/christoph/Projects/eclipse-workspace/AdventOfCode/2023/input/input_day12.txt"));
			String line = reader.readLine();
			while (line != null) {
				lines.add(line);
				line = reader.readLine();
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void compute() {
		System.out.println("--------------------------------");
		for (int i=0; i<6; i++) {
			List<String> threadLines = lines.subList(i, i+1);
			WorkerDP w = new WorkerDP(this, threadLines);
			Thread t = new Thread(w);
			t.start();
		}
	}

	public synchronized void callback(long arrangementCount) {
		arrangementCountSum += arrangementCount;
		System.out.println(arrangementCountSum);
	}
}

 class WorkerDP implements Runnable {
	 Day12_HotSprings_Part2_DP day;
	Map<Tupel, Long> hm = new HashMap<>();
	List<String> lines;
	long arrangementCount = 0;

	public WorkerDP(Day12_HotSprings_Part2_DP day, List<String> lines) {
		this.day = day;
		this.lines = lines;
	}

	@Override
	public void run() {
		List<Integer> values = new ArrayList<Integer>();
		for (String line: lines) {
			int index = line.indexOf(' ');
			int times = 1;
			String springs = replicateInput(line.substring(0, index), '?', times);
			String valuesTmp = replicateInput(line.substring(index + 1), ',', times);
			for (String v : valuesTmp.split(",")) values.add(Integer.parseInt(v));
			arrangementCount += getArrangementCount(springs, values);
			//System.out.println("--> " + arrangementCount);
			values.clear();
			hm.clear();
		}
		day.callback(arrangementCount);
	}
	private String replicateInput(String substring, char c, int times) {
		StringBuilder sb = new StringBuilder();
		for (int i=0; i<times-1; i++) {
			sb.append(substring);
			sb.append(c);
		}
		sb.append(substring);
		return sb.toString();
	}
	private long getArrangementCount(String springs, List<Integer> values) {
		int lengthPlusOne = springs.length() + 1;
		char[] pattern = Arrays.copyOf(springs.toCharArray(), lengthPlusOne);
		pattern[springs.length()] = '.';

		// values summieren mit je einem Abstand
		int sumValues = 0;
		for (int v: values)	sumValues += v;

		int n = values.size();
		int w = lengthPlusOne - sumValues - n + 1;

		int pos = 0;
		int[] previousCounts = new int[lengthPlusOne];
		Arrays.fill(previousCounts, 1);
		int[] currentCounts = new int[lengthPlusOne];
		for (Integer v: values) {
			Arrays.fill(currentCounts, 0);
			int newPos = findFirstPosition(pattern, (pos==0)?pos:pos+1, v);
			if (pos==0) {
				for (int i = newPos; i < (newPos + w - 1); i++) currentCounts[i] = 1;
			} else {
				for (int i = 0; i < w-1 && pattern[i+newPos]!='#'; i++) currentCounts[i+newPos] = currentCounts[i+newPos-1] + previousCounts[i+pos];
			}
			pos = newPos;
			for (int x=0; x<previousCounts.length; x++) previousCounts[x] = currentCounts[x];
		}
		return currentCounts[lengthPlusOne-1];
	}

	 private int findFirstPosition(char[] pattern, int pos, Integer v) {
		i_label: for (int i=pos; i<pattern.length-v; i++) {
			for (int x=i; x<i+v; x++) {
				if (pattern[x] == '.') continue i_label;
			}
			if (i>0 && pattern[i-1] == '#') continue i_label;
			if (pattern[i+v] == '#') continue i_label;
			pos =  i + v;
			break;
		}
		return pos;
	 }
 }