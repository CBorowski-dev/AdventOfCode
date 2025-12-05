package de.borowski.aoc;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Day05_Cafereria {

	public static final Set<IDRange> idRanges = new HashSet<>();

    public static void main(String[] args) {
        BufferedReader reader;

		int sum = 0;
        try {
            reader = new BufferedReader(new FileReader("/home/christoph/Projects/IdeaProjects/AdventOfCode/2025/input/input_day05_testset.txt"));
            // reader = new BufferedReader(new FileReader("/home/christoph/Projects/IdeaProjects/AdventOfCode/2025/input/input_day05.txt"));
            String line = reader.readLine();

            while (line != null && !line.isEmpty()) {
				String[] ids = line.split("-");
				idRanges.add(new IDRange(Long.parseLong(ids[0]), Long.parseLong(ids[1])));
                // read next line
                line = reader.readLine();
            }

			// Part 1
			line = reader.readLine();
            while (line != null) {
				long id = Long.parseLong(line);
				for (IDRange idRange : idRanges) {
					if (idRange.contains(id)) {
						sum++;
						break;
					}
				}
                // read next line
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("--------------------------------");
		System.out.println(sum);
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
