package de.borowski.aoc;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day19_LinenLayout {
    // String filename = "/home/christoph/Projects/IdeaProjects/AdventOfCode/2024/input/input_day19_testset.txt";
    String filename = "/home/christoph/Projects/IdeaProjects/AdventOfCode/2024/input/input_day19.txt";

    char[][] towelPatterns;
    List<String> designs = new ArrayList<>();

    public Day19_LinenLayout() {
        readInput();
        reduceTowelPatterns();
        System.out.println("--------------------------------");
        System.out.println(getPossibleDesigns());
    }

    private void reduceTowelPatterns() {
        List<char[]> towelPatternsList = new ArrayList<>(Arrays.asList(towelPatterns));
        char[] design;
        for (int i=towelPatternsList.size()-1; i>=0; i--) {
            design = towelPatternsList.get(i);
            List<char[]> tmpTowelPatternsList = new ArrayList<>(towelPatternsList);
            tmpTowelPatternsList.remove(design);
            towelPatterns = tmpTowelPatternsList.toArray(new char[towelPatterns.length-1][]);
            if (isDesignPossible(design, 0)) towelPatternsList.remove(design);
        }
        towelPatterns = towelPatternsList.toArray(towelPatterns);
    }

    private int getPossibleDesigns() {
        int count = 0;
        int designCount = 0;
        for (String d : designs) {
            System.out.println(designCount++);
            if (isDesignPossible(d.toCharArray(), 0)) count++;
        }
        //if (isDesignPossible(designs.get(0).toCharArray(), 0)) count++;
        return count;
    }

    private boolean isDesignPossible(char[] design, int i) {
        for (int y=0; y<towelPatterns.length; y++) {
            if (design.length-i >= towelPatterns[y].length && doesPatterFits(design, towelPatterns[y], i)) {
                if (design.length-i == towelPatterns[y].length) return true;
                if (isDesignPossible(design, i+towelPatterns[y].length)) return true;
            }
        }
        return false;
    }

    private boolean doesPatterFits(char[] design, char[] towelPattern, int i) {
        for (int x=0; x<towelPattern.length; x++) {
            if (design[i+x] != towelPattern[x]) return false;
        }
        return true;
    }

    private void readInput() {
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(filename));
            boolean firstRow = true;
            String line = reader.readLine();
            while (line != null) {
                if (firstRow) {
                    String[] tp = line.split(", ");
                    towelPatterns = new char[tp.length][];
                    int i=0;
                    for (String x : tp) {
                        towelPatterns[i++] = x.toCharArray();
                    }
                    firstRow = false;
                } else if (!line.isBlank()) {
                    designs.add(line);
                }
                // read next line
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) { new Day19_LinenLayout(); }
}
