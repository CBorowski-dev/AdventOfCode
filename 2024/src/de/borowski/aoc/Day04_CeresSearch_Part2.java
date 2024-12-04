package de.borowski.aoc;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Day04_CeresSearch_Part2 {
    private final char[][] input = new char[140][140];

    public Day04_CeresSearch_Part2() {
        readInput();

        System.out.println("--------------------------------");
        System.out.println(getCombinationCount());
    }

    private int getCombinationCount() {
        int count = 0;
        for (int y=1; y<input.length-1; y++) {
            for (int x=1; x<input.length-1; x++) {
                if (input[y][x]=='A') count += getCombinationCount(y, x);
            }
        }
        return count;
    }
    private int getCombinationCount(int y, int x) {
        int count = 0;
        // original
        if (input[y-1][x-1]=='M' && input[y-1][x+1]=='S' && input[y+1][x+1]=='S' && input[y+1][x-1]=='M') count++;
        // 90° gedreht
        if (input[y-1][x-1]=='M' && input[y-1][x+1]=='M' && input[y+1][x+1]=='S' && input[y+1][x-1]=='S') count++;
        // 180° gedreht
        if (input[y-1][x-1]=='S' && input[y-1][x+1]=='M' && input[y+1][x+1]=='M' && input[y+1][x-1]=='S') count++;
        // 270° gedreht
        if (input[y-1][x-1]=='S' && input[y-1][x+1]=='S' && input[y+1][x+1]=='M' && input[y+1][x-1]=='M') count++;

        return count;
    }

    private void readInput() {
        BufferedReader reader;

        try {
            //reader = new BufferedReader(new FileReader("/home/christoph/Projects/IdeaProjects/AdventOfCode/2024/input/input_day04_testset.txt"));
            reader = new BufferedReader(new FileReader("/home/christoph/Projects/IdeaProjects/AdventOfCode/2024/input/input_day04.txt"));
            int row = 0;
            String line = reader.readLine();
            while (line != null) {
                // System.out.println(line);
                input[row++] = line.toCharArray();
                // read next line
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Day04_CeresSearch_Part2();
    }
}