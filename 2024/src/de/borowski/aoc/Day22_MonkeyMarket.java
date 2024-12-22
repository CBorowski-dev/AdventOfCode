package de.borowski.aoc;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Day22_MonkeyMarket {

    // String filename = "/home/christoph/Projects/IdeaProjects/AdventOfCode/2024/input/input_day22_testset.txt";
    String filename = "/home/christoph/Projects/IdeaProjects/AdventOfCode/2024/input/input_day22.txt";

    private final List<Long> secretNumbers = new ArrayList<>();

    public Day22_MonkeyMarket() {
        readInput();
        System.out.println("--------------------------------");
        System.out.println("Result: " + computeResult(2000));
    }

    private long computeResult(int rounds) {
        long result = 0;

        for (long sn : secretNumbers) {
            for (int i=0; i<rounds; i++) {
                sn = (sn ^ (sn * 64)) % 16777216; // first step
                sn = (sn ^ (long) Math.abs((double) sn / 32)) % 16777216; // second step
                sn = (sn ^ (sn * 2048)) % 16777216; // third step
            }
            result += sn;
        }

        return result;
    }

    private void readInput() {
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(filename));
            int row = 0;
            String line = reader.readLine();
            while (line != null) {
                secretNumbers.add(Long.parseLong(line));
                // read next line
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) { new Day22_MonkeyMarket(); }
}
