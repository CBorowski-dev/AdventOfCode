package de.borowski.aoc;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Day05_Seed {

	public static List<Long> seeds = new ArrayList<Long>();
	public static Map<Integer, List<MapValue>> mapping = new HashMap<Integer, List<MapValue>>();

	public static void main(String[] args) {
		BufferedReader reader;
		
		try {
			int mapKey = -1;
			reader = new BufferedReader(new FileReader("/home/christoph/Projects/eclipse-workspace/AdventOfCode/2023/input/input_day05.txt"));
			String line = reader.readLine();
			
			while (line != null) {
				if (line.startsWith("seeds: ")) {
					fillSeeds(line);
				} else if (line.contains("map:")) {
					mapKey++;
				} else if (!line.isBlank()) {
					// create mappings
					createMapping(line, mapKey);
				}
				// read next line
				line = reader.readLine();
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// printMap();
		long minLocation = -1;
		/*for (Long seedNumber : seeds) {
			long location = findLocation(seedNumber, 0);
			if (minLocation == -1 || minLocation > location) {
				minLocation = location;
			}
		}*/
		for (int i=0; i<(seeds.size()/2); i++) {
			long startNumber = seeds.get(i*2);
			long range = seeds.get((i*2)+1);
			System.out.println(System.currentTimeMillis() + " index : " + i);
			for (long seedNumber=startNumber; seedNumber<startNumber+range; seedNumber++) {
				long location = findLocation(seedNumber, 0);
				if (minLocation == -1 || minLocation > location) {
					minLocation = location;
				}
			}
		}
		System.out.println(System.currentTimeMillis());
		System.out.println("--------------------------------");
		System.out.println(minLocation);
	}

	private static long findLocation(Long source, int mapKey) {
		List<MapValue> mapValues = mapping.get(mapKey);
		for (MapValue mv : mapValues) {
			if (mv.s <= source && source < (mv.s + mv.l)) {
				if (mapKey == 6) {
					return mv.d + (source - mv.s);
				} else {
					return findLocation(mv.d + (source - mv.s), ++mapKey);
				}
			}
		}
		return mapKey == 6 ? source : findLocation(source, ++mapKey);
	}

	private static void printMap() {
		for (int x=0; x<mapping.size(); x++) {
			System.out.println(mapping.get(x));
		}
	}

	private static void createMapping(String line, int mapKey) {
		String[] values = line.split(" ");

        List<MapValue> mapValues = mapping.computeIfAbsent(mapKey, k -> new ArrayList<MapValue>());
        mapValues.add(new MapValue(Long.parseUnsignedLong(values[1]), Long.parseUnsignedLong(values[0]), Long.parseUnsignedLong(values[2])));
	}

	private static void fillSeeds(String line) {
		line = line.substring(line.indexOf(':')+2);
		String[] values = line.split(" ");
		for (String sv : values) {
			seeds.add(Long.parseUnsignedLong(sv));
		}
		// System.out.println(seeds);
	}

	public static class MapValue {
		long s, d, l;

		public MapValue(long s, long d, long l) {
			this.s = s;
			this.d = d;
			this.l = l;
		}

		@Override
		public String toString() {
			return "MapValue{" +
					"s=" + s +
					", d=" + d +
					", l=" + l +
					'}';
		}
	}

}
