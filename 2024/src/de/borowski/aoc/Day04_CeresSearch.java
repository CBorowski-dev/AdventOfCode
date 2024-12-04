package de.borowski.aoc;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Day04_CeresSearch {
    private final char[][] input = new char[140][140];

    public Day04_CeresSearch() {
        readInput();

        System.out.println("--------------------------------");
        System.out.println(getCombinationCount());
    }

    private int getCombinationCount() {
        int count = 0;
        for (int y=0; y<input.length; y++) {
            for (int x=0; x<input.length; x++) {
                if (input[y][x]=='X') count += getCombinationCount(y, x);
            }
        }
        return count;
    }

    private int getCombinationCount(int y, int x) {
        int count = 0;
        // nach rechts suchen
        if (x<=input.length-4 && input[y][x+1]=='M' && input[y][x+2]=='A' && input[y][x+3]=='S') count++;
        // nach links suchen
        if (x>=3 && input[y][x-1]=='M' && input[y][x-2]=='A' && input[y][x-3]=='S') count++;
        // nach unten suchen
        if (y<=input.length-4 && input[y+1][x]=='M' && input[y+2][x]=='A' && input[y+3][x]=='S') count++;
        // nach oben suchen
        if (y>=3 && input[y-1][x]=='M' && input[y-2][x]=='A' && input[y-3][x]=='S') count++;

        // nach unten/rechts suchen
        if (x<=input.length-4 && y<=input.length-4 && input[y+1][x+1]=='M' && input[y+2][x+2]=='A' && input[y+3][x+3]=='S') count++;
        // nach oben/links suchen
        if (x>=3 && y>=3 && input[y-1][x-1]=='M' && input[y-2][x-2]=='A' && input[y-3][x-3]=='S') count++;
        // nach unten/links suchen
        if (x>=3 && y<=input.length-4 && input[y+1][x-1]=='M' && input[y+2][x-2]=='A' && input[y+3][x-3]=='S') count++;
        // nach oben/rechts suchen
        if (x<=input.length-4 && y>=3 && input[y-1][x+1]=='M' && input[y-2][x+2]=='A' && input[y-3][x+3]=='S') count++;

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
        new Day04_CeresSearch();
    }
}