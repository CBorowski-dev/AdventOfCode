package de.borowski.aoc;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day12_HotSprings_Part2S {

	public static void main(String[] args) {
		BufferedReader reader;
		long arrangementCountSum = 0;
		try {
			reader = new BufferedReader(new FileReader("/home/christoph/Projects/eclipse-workspace/AdventOfCode/2023/input/input_day12_testset.txt"));
			//reader = new BufferedReader(new FileReader("/home/christoph/Projects/eclipse-workspace/AdventOfCode/2023/input/input_day12.txt"));
			String line = reader.readLine();
			List<Integer> values = new ArrayList<Integer>();
			long start = System.currentTimeMillis();
			while (line != null) {
				values.clear();
				int index = line.indexOf(' ');
				String springs = replicateInput(line.substring(0, index), '?');
				String valuesTmp = replicateInput(line.substring(index + 1), ',');
				for (String v : valuesTmp.split(","))	values.add(Integer.parseInt(v));
				long arrangementCount = getArrangementCount(springs, values);
				System.out.println(arrangementCount);
				arrangementCountSum += arrangementCount;
				// read next line
				line = reader.readLine();
			}
			System.out.println((System.currentTimeMillis()-start)/(float)1000);
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("--------------------------------");
		System.out.println(arrangementCountSum);
	}

	private static String replicateInput(String substring, char c) {
		StringBuilder sb = new StringBuilder(substring);
		for (int i=0; i<4; i++) {
			sb.append(c);
			sb.append(substring);
		}
		sb.append(c);
		return sb.toString();
	}

	private static long getArrangementCount(String springs, List<Integer> values) {
		int maxLength = springs.length();
		char[] pattern = new char[maxLength];
        Arrays.fill(pattern, '.');

		// values summieren mit je einem Abstand
		int sumValues = 0;
		for (int v: values)	sumValues += v;
		sumValues += values.size()-1;

		return getCount(springs.toCharArray(), values, 0, pattern, 0, springs.length()-sumValues, maxLength);
	}

	private static long getCount(char[] springs, List<Integer> values, int vIdx, char[] pattern, int pIdx, int maxIdx, int maxLength) {
		long count = 0;
		boolean bomb;
		int cIdx;
		int valSize = values.size();
		int val = values.get(vIdx);
		for (int x=0; x<=maxIdx; x++) {
			bomb = false;
			for (int i = x; i < (val+x) && !bomb; i++) {
				cIdx = pIdx + i;
				if (springs[cIdx] == '#' && lengthFieldError(springs, cIdx+1, maxLength)>val) return count;
				else if (springs[cIdx]!='.') pattern[cIdx] = '#';
				else bomb = true;
			}
			//printSprings(springs, pattern);
			if (!bomb) {
				int pIdx2 = pIdx + x + val;
				// if (pIdx2 < maxLength && springs[pIdx2] == '#') bomb = true;
				if (pIdx2 >= maxLength || springs[pIdx2] != '#') {
					if (vIdx < valSize - 1) {
						int sumValues = 0;
						for (int i = vIdx + 1; i < valSize; i++) sumValues += values.get(i);
						sumValues += (valSize - (1 + vIdx));
						int maxIdxNext = maxLength - pIdx2 - sumValues;
						count += getCount(springs, values, vIdx + 1, pattern, pIdx2 + 1, maxIdxNext, maxLength);
					} else if (checkSprings(springs, pattern)) count++;
				}
			}

			for (int i = 0; i < val; i++) {
				pattern[pIdx + x + i] = '.';
			}
		}
		return count;
	}

	private static int lengthFieldError(char[] springs, int idx, int maxLength) {
		int count=1;
		while (idx<maxLength && springs[idx++]=='#') {
			count++;
		}
		return count;
	}

	private static boolean checkSprings(char[] springs, char[] pattern) {
		for (int i=0; i<pattern.length; i++) {
			if ((pattern[i]=='#' && springs[i]=='.') || (pattern[i]=='.' && springs[i]=='#')) {
				/*System.out.println("---------------------");
				System.out.println(String.valueOf(springs));
				System.out.println(String.valueOf(pattern));*/
				return false;
			}
		}
		//System.out.println("---> Treffer");
		return true;
	}

	private static void printSprings(char[] springs, char[] pattern) {
		System.out.println("---------------------");
		System.out.println(String.valueOf(springs));
		System.out.println(String.valueOf(pattern));
	}

}