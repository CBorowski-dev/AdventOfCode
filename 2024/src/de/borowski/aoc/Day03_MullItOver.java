package de.borowski.aoc;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day03_MullItOver {

    private final List<String> line = new ArrayList<>();

    public Day03_MullItOver() {
        readInput();

        System.out.println("--------------------------------");
        System.out.println(computeResult());
    }

    private int computeResult() {
        int result = 0;
        for (String l : line) {
            Pattern pattern = Pattern.compile("mul[(][0-9]+,[0-9]+[)]", Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(l);
            while (matcher.find()) {
                String group = matcher.group();
                group = group.substring(4, group.length() - 1);
                int index = group.indexOf(',');
                int v1 = Integer.parseInt(group.substring(0, index));
                int v2 = Integer.parseInt(group.substring(index + 1));
                result += (v1 * v2);
            }
        }
        return result;
    }

    private void readInput() {
        BufferedReader reader;

        try {
            // reader = new BufferedReader(new FileReader("/home/christoph/Projects/IdeaProjects/AdventOfCode/2024/input/input_day03_testset.txt"));
            reader = new BufferedReader(new FileReader("/home/christoph/Projects/IdeaProjects/AdventOfCode/2024/input/input_day03.txt"));
            String line = reader.readLine();

            while (line != null) {
                // System.out.println(line);
                this.line.add(line);
                // read next line
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Day03_MullItOver();
    }
}