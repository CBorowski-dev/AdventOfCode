package de.borowski.aoc;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Day07_Laboratories {

    public static final int LENGTH = 141; // 15 or 141
    public static final boolean[] splitter = new boolean[LENGTH];
    public static final long[] counter = new long[LENGTH];

    public static void main(String[] args) {
        BufferedReader reader;

        int splitCount = 0;
        long timelines = 0;
        try {
            // reader = new BufferedReader(new FileReader("/home/christoph/Projects/IdeaProjects/AdventOfCode/2025/input/input_day07_testset.txt"));
            reader = new BufferedReader(new FileReader("/home/christoph/Projects/IdeaProjects/AdventOfCode/2025/input/input_day07.txt"));
            String line = reader.readLine();

            while (line != null) {
                int i = line.indexOf('^');
                if (i >= 0) {
                    do {
                        if (splitter[i]) {
                            splitter[i - 1] = true;
                            splitter[i + 1] = true;
                            splitter[i] = false;
                            splitCount++;
                            counter[i - 1] = counter[i - 1] + counter[i];
                            counter[i + 1] = counter[i + 1] + counter[i];
                            counter[i] = 0;
                        }
                        i = line.indexOf('^', i + 1);
                    } while (i >= 0);
                } else {
                    int s = line.indexOf('S');
                    if (s >= 0) {
                        splitter[s] = true;
                        counter[s] = 1;
                    }
                }
                // read next line
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (long x : counter) timelines += x;
        System.out.println("--------------------------------");
        System.out.println("Split count : " + splitCount);
        System.out.println("Timelines   : " + timelines);
    }

}
