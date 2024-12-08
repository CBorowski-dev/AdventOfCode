package de.borowski.aoc;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Day08_ResonantCollinearity {
    int size = 50;
    // String filename = "/home/christoph/Projects/IdeaProjects/AdventOfCode/2024/input/input_day08_testset.txt";
    String filename = "/home/christoph/Projects/IdeaProjects/AdventOfCode/2024/input/input_day08.txt";

    private final char[][] input = new char[size][size];
    private final int[][] antinodes = new int[size][size];
    private final List<String> antenaTypes = new ArrayList<>();

    public Day08_ResonantCollinearity() {
        readInput();
        initAntinodes();
        System.out.println("--------------------------------");
        System.out.println(countAntinodes());
    }

    private int countAntinodes() {
        char antenaType;
        List<Coordinate> coordinates = new ArrayList<>();
        int result = 0;
        for (String at: antenaTypes) {
            antenaType = at.toCharArray()[0];
            for (int y=0; y<input.length; y++) {
                for (int x=0; x<input.length; x++) {
                    if (input[y][x] == antenaType) coordinates.add(new Coordinate(y, x));
                }
            }
            result += countAntinodes(coordinates);
            coordinates.clear();
        }
        return result;
    }

    private int countAntinodes(List<Coordinate> coordinates) {
        int result = 0;
        for (Coordinate c1 : coordinates) {
            for (Coordinate c2 : coordinates) {
                if (!c1.equals(c2)) {
                    int y = c1.y - (c2.y-c1.y);
                    int x = c1.x - (c2.x-c1.x);
                    if (x>=0 && x<size && y>=0 && y<size && antinodes[y][x]==0) {
                        result++;
                        antinodes[y][x] += 1;
                    }
                }
            }
        }
        return result;
    }

    private void initAntinodes() {
        for (int y=0; y<input.length; y++) {
            for (int x=0; x<input.length; x++) {
                antinodes[y][x] = 0;
            }
        }
    }

    private void readInput() {
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(filename));
            int row = 0;
            String line = reader.readLine();
            while (line != null) {
                // System.out.println(line);
                String[] at = line.split("\\.");
                for (String t: at) if (!t.isEmpty() && !antenaTypes.contains(t)) antenaTypes.add(t);
                input[row++] = line.toCharArray();
                // read next line
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static class Coordinate {
        public int y, x;

        public Coordinate(int y, int x) {
            this.y = y;
            this.x = x;
        }

        @Override
        public boolean equals(Object o) {
            if (o == null || getClass() != o.getClass()) return false;
            Coordinate that = (Coordinate) o;
            return y == that.y && x == that.x;
        }

        @Override
        public int hashCode() {
            return Objects.hash(y, x);
        }
    }

    public static void main(String[] args) {
        new Day08_ResonantCollinearity();
    }
}
