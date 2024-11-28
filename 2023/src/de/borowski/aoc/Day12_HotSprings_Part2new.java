package de.borowski.aoc;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Day12_HotSprings_Part2new {

	public static void main(String[] args) {
		BufferedReader reader;
		long arrangementCountSum = 0;
		try {
			//reader = new BufferedReader(new FileReader("/home/christoph/Projects/eclipse-workspace/AdventOfCode/2023/input/input_day12.txt"));
			reader = new BufferedReader(new FileReader("/home/christoph/Projects/eclipse-workspace/AdventOfCode/2023/input/input_day12_testset.txt"));
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
		StringBuffer sb = new StringBuffer(substring);
		for (int i = 0; i<5; i++) {
			sb.append(c);
			if (i<4) sb.append(substring);
		}
		return sb.toString();
	}

	private static long getArrangementCount(String springs, List<Integer> values) {
		// zerlege springs in separate Container
		String[] springContainers = springs.split("\\.");
		List<String> list = new ArrayList<>();
        for (String springContainer : springContainers) {
            if (!springContainer.isEmpty()) list.add(springContainer);
        }
		springContainers = list.toArray(new String[0]);

		return getCount(springContainers, values, 0, 0);
	}

	private static long getCount(String[] springContainers, List<Integer> values, int scIdx, int valueIdx) {
		long count = 0;
		String container = springContainers[scIdx];
		int containerLength = container.length();
		int cIdx = 0;
		for (int v=valueIdx; v<values.size(); v++) {
			//if (values.get(v)>containerLength) return 0;
			if (cIdx + values.get(v) > containerLength) {
				// nächster Value passt nicht mehr in Container
				// überprüfen, ob im restlichen Container ein # ist
				for (int i=cIdx; i<containerLength; i++)	if (container.charAt(i)=='#') return 0;
				// nächsten Container aufrufen
				count += getCount(springContainers, values, scIdx+1, v);
			} else if (cIdx + values.get(v)==containerLength) {
				// nächster Value geht genau bis Ende des Containers
				count += getCount(springContainers, values, scIdx+1, v+1);
			} else if (container.charAt(cIdx + values.get(v)) == '?')
				cIdx += values.get(v)+1;
			else return 0; // nächster Value passt zwar rein, aber danach ist direkt ein #
		}

		return count;
	}

	private static boolean checkSprings(char[] springs, char[] pattern) {
		for (int i=0; i<pattern.length; i++) {
			if ((pattern[i]=='#' && springs[i]=='.') || (pattern[i]=='.' && springs[i]=='#')) return false;
		}
		// System.out.println("---> Treffer");
		// System.out.println(springs);
		// System.out.println(String.valueOf(pattern));
		return true;
	}

}
