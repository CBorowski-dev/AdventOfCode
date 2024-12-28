package de.borowski.aoc;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Day19_LinenLayout_Part2 {
    // String filename = "/home/christoph/Projects/IdeaProjects/AdventOfCode/2024/input/input_day19_testset.txt";
    String filename = "/home/christoph/Projects/IdeaProjects/AdventOfCode/2024/input/input_day19.txt";

    List<String> designs = new ArrayList<>();
    List<String> towelPatterns = new ArrayList<>();
    Map<Integer, Long> interimResults = new HashMap<>();

    public Day19_LinenLayout_Part2() {
        readInput();
        System.out.println("--------------------------------");
        System.out.println(computePossibleDesignsCount());
    }

    private long computePossibleDesignsCount() {
        long variations = 0;
        for (String d : designs) {
            interimResults.clear();
            variations += computeVariations(d.toCharArray(), 0);
        }
        return variations;
    }

    private long computeVariations(char[] design, int i) {
        long variations = 0;
        char c = design[i];
        int length = design.length - i;
        for (int y = 0; y < towelPatterns.size(); y++) {
            String s = towelPatterns.get(y);
            if (s.charAt(0)!=c) continue;
            if (length >= s.length() && doesPatterFits(design, s.toCharArray(), i)) {
                if (length == s.length()) variations++;
                else {
                    Long varCount = interimResults.get(i + s.length());
                    if (varCount == null) {
                        varCount = computeVariations(design, i + s.length());
                        interimResults.put(i + s.length(), varCount);
                    }
                    variations += varCount;
                }
            }
        }
        return variations;
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
                    for (String x : tp) towelPatterns.add(x);
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

    public static void main(String[] args) { new Day19_LinenLayout_Part2(); }
}
