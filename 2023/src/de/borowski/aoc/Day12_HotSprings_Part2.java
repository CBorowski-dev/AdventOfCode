package de.borowski.aoc;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Day12_HotSprings_Part2 {

	public List<String> lines = new ArrayList<>(1000);
	public long arrangementCountSum = 0;

	public static void main(String[] args) {
		Day12_HotSprings_Part2 day = new Day12_HotSprings_Part2();
		day.readInput();
		day.compute();
	}

	public Day12_HotSprings_Part2() {
		readInput();
	}

	private void readInput() {
		BufferedReader reader;
		try {
			// reader = new BufferedReader(new FileReader("/home/christoph/Projects/eclipse-workspace/AdventOfCode/2023/input/input_day12_testset.txt"));
			reader = new BufferedReader(new FileReader("/home/christoph/Projects/eclipse-workspace/AdventOfCode/2023/input/input_day12.txt"));
			String line = reader.readLine();
			while (line != null) {
				lines.add(line);
				// read next line
				line = reader.readLine();
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void compute() {
		System.out.println("--------------------------------");
		for (int i=0; i<8; i++) {
			List<String> threadLines = lines.subList(i*125, i*125 + 125);
			Worker w = new Worker(this, threadLines);
			Thread t = new Thread(w);
			t.start();
		}
	}

	public synchronized void callback(long arrangementCount) {
		arrangementCountSum += arrangementCount;
		System.out.println(arrangementCountSum);
	}
}

 class Worker implements Runnable {
	Day12_HotSprings_Part2 day;
	Map<Tupel, Long> hm = new HashMap<>();
	List<String> lines;
	long arrangementCount = 0;

	public Worker(Day12_HotSprings_Part2 day, List<String> lines) {
		this.day = day;
		this.lines = lines;
	}

	@Override
	public void run() {
		List<Integer> values = new ArrayList<Integer>();
		for (String line: lines) {
			int index = line.indexOf(' ');
			int times = 5;
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
		int maxLength = springs.length();
		char[] pattern = new char[maxLength];
		Arrays.fill(pattern, '.');

		// values summieren mit je einem Abstand
		int sumValues = 0;
		for (int v: values)	sumValues += v;
		sumValues += values.size()-1;

		return getCount(springs.toCharArray(), values, 0, pattern, 0, springs.length()-sumValues, maxLength);
	}

	private long getCount(char[] springs, List<Integer> values, int vIdx, char[] pattern, int pIdx, int maxIdx, int maxLength) {
		Long c = hm.get(new Tupel(vIdx, pIdx));
		if (c != null) return c;

		long count = 0;
		boolean bomb;
		int cIdx;
		int valSize = values.size();
		int val = values.get(vIdx);
		for (int x=0; x<=maxIdx; x++) {
			bomb = pIdx+x > 0 && springs[pIdx+x-1] == '#';
			if (pIdx+val+x-1 < maxLength-1 && springs[pIdx+val+x] == '#') bomb = true;
			for (int i = x; i < (val+x) && !bomb; i++) {
				cIdx = pIdx + i;
				if (springs[cIdx]!='.') pattern[cIdx] = '#';
				else bomb = true;
			}
			if (!bomb && checkSprings(springs, pattern, pIdx + x + val)) {
				int pIdx2 = pIdx + x + val;
				if (vIdx < valSize - 1) {
					int sumValues = 0;
					for (int i = vIdx + 1; i < valSize; i++) sumValues += values.get(i);
					sumValues += (valSize - (1 + vIdx));
					int maxIdxNext = maxLength - pIdx2 - sumValues;
					count += getCount(springs, values, vIdx + 1, pattern, pIdx2 + 1, maxIdxNext, maxLength);
				} else if (checkSprings(springs, pattern, pattern.length)) count++;
			}
			for (int i = 0; i < val; i++) {
				pattern[pIdx + x + i] = '.';
			}
		}
		if (count > 0) hm.put(new Tupel(vIdx, pIdx), count);
		return count;
	}

	private boolean checkSprings(char[] springs, char[] pattern, int idx) {
		for (int i=idx-1; i>=0; i--) {
			if ((pattern[i]=='#' && springs[i]=='.') || (pattern[i]=='.' && springs[i]=='#')) return false;
		}
		return true;
	}
}
 class Tupel {
	int vIdx;
	int pIdx;

	public Tupel(int vIdx, int pIdx) {
		this.vIdx = vIdx;
		this.pIdx = pIdx;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Tupel tupel = (Tupel) o;
		return vIdx == tupel.vIdx && pIdx == tupel.pIdx;
	}

	@Override
	public int hashCode() {
		return Objects.hash(vIdx, pIdx);
	}
}