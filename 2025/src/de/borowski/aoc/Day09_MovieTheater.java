package de.borowski.aoc;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Day09_MovieTheater {

    public static final int TILES_COUNT = 496; // 8 or 496
    public static final int[][] tiles = new int[TILES_COUNT][2];

    public static void main(String[] args) {
        BufferedReader reader;

        try {
            // reader = new BufferedReader(new FileReader("/home/christoph/Projects/IdeaProjects/AdventOfCode/2025/input/input_day09_testset.txt"));
            reader = new BufferedReader(new FileReader("/home/christoph/Projects/IdeaProjects/AdventOfCode/2025/input/input_day09.txt"));
            String line = reader.readLine();
            int i = 0;
            while (line != null) {
                String[] values = line.split(",");
                tiles[i][0] = Integer.parseInt(values[0]);
                tiles[i][1] = Integer.parseInt(values[1]);
                i++;
                // read next line
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Part 1
        int t1Index = 0, t2Index = 0;
        long maxArea = 0;
        for (int i = 0; i < TILES_COUNT - 1; i++) {
            for (int j = i + 1; j < TILES_COUNT; j++) {
                long area = (long) (Math.abs(tiles[j][0] - tiles[i][0]) + 1) * (Math.abs(tiles[j][1] - tiles[i][1]) + 1);
                if (maxArea < area) {
                    maxArea = area;
                    t1Index = i;
                    t2Index = j;
                }
            }
        }
        System.out.println("--------------------------------");
        System.out.println("Max area: " + maxArea);
    }

}
