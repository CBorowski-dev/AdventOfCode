package de.borowski.aoc;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Day06_TrashCompactor {

    public static final List<Values> valuesList = new ArrayList<>();

    public static void main(String[] args) {
        BufferedReader reader;
        long sum = 0;

        try {
            // reader = new BufferedReader(new FileReader("/home/christoph/Projects/IdeaProjects/AdventOfCode/2025/input/input_day06_testset.txt"));
            reader = new BufferedReader(new FileReader("/home/christoph/Projects/IdeaProjects/AdventOfCode/2025/input/input_day06.txt"));
            String line = reader.readLine();
            while (line != null) {
                String[] values = line.trim().split("\\s+");
                int i = 0;
                for (String v : values) {
                    if (!v.equals("+") && !v.equals("*")) {
                        if (valuesList.size() == i) valuesList.add(new Values());
                        Values val = valuesList.get(i);
                        val.v_add += Long.parseLong(v);
                        val.v_mul *= Long.parseLong(v);
                    } else {
                        Values val = valuesList.get(i);
                        sum += v.equals("+") ? val.v_add : val.v_mul;
                    }
                    i++;
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

    public static class Values {
        public long v_add = 0;
        public long v_mul = 1;
    }

}
