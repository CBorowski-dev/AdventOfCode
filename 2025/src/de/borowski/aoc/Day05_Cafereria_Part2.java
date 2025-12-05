package de.borowski.aoc;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Day05_Cafereria_Part2 {

	public static final List<IDRange> idRanges = new ArrayList<>();

    public static void main(String[] args) {
        BufferedReader reader;

        try {
            // reader = new BufferedReader(new FileReader("/home/christoph/Projects/IdeaProjects/AdventOfCode/2025/input/input_day05_testset.txt"));
            reader = new BufferedReader(new FileReader("/home/christoph/Projects/IdeaProjects/AdventOfCode/2025/input/input_day05.txt"));
            String line = reader.readLine();

            while (line != null && !line.isEmpty()) {
				String[] ids = line.split("-");
				IDRange x = new IDRange(Long.parseLong(ids[0]), Long.parseLong(ids[1]));
				mergeWithCurrentIDRanges(x);
                // read next line
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

		long sum = 0;
		for (IDRange idr : idRanges) {
			sum += (idr.bis - idr.von + 1);
		}
        System.out.println("--------------------------------");
		System.out.println(sum);
    }

	private static void mergeWithCurrentIDRanges(IDRange x) {
		int size = idRanges.size();

		if (size==0) idRanges.add(x);
		else {
			for (int i=size-1; i>=0; i--) {
				IDRange y = checkOverlap(x, idRanges.get(i));
				if (y != null) {
					idRanges.remove(i);
					x = y;
				}
			}
			idRanges.add(x);
		}
	}

	private static IDRange checkOverlap(IDRange x, IDRange y) {
		if (x.von == y.von && x.bis == y.bis) return x; // beide identisch
		if (x.von <= y.von && x.bis >= y.bis) return x; // x beinhaltet y
		if (x.von >= y.von && x.bis <= y.bis) return y; // y beinhaltet x
		if (x.von < y.von && x.bis >= y.von) return new IDRange(x.von, y.bis);
		if (x.von >= y.von && x.von <= y.bis) return new IDRange(y.von, x.bis);
		return null;
	}

	public record IDRange(long von, long bis) {
        public boolean contains(long x) {
            return x >= von && x <= bis;
        }

		@Override
		public boolean equals(Object o) {
			if (o == null || getClass() != o.getClass()) return false;
			IDRange idRange = (IDRange) o;
			return von == idRange.von && bis == idRange.bis;
		}

		@Override
		public int hashCode() {
			return Objects.hash(von, bis);
		}
	}

}
