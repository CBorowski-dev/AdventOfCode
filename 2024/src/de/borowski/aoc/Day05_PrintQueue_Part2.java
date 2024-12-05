package de.borowski.aoc;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day05_PrintQueue_Part2 {

    private final Map<Integer, List<Integer>> rules = new HashMap<>();
    private final List<List<Integer>> updates = new ArrayList<>();

    public Day05_PrintQueue_Part2() {
        readInput();
        System.out.println("--------------------------------");
        System.out.println(computeResult());
    }

    private int computeResult() {
        int result = 0;
        for (int i=0; i<updates.size(); i++) {
            List<Integer> u = updates.get(i);
            if (!checkUpdate(u)) {
                correctUpdate(u);
                result += u.get((int) Math.floor(u.size()/2));
            }
        }
        return result;
    }

    private void correctUpdate(List<Integer> u) {
        // This more or less implements the Bubblesort algorithm
        // based on the given rules and NOT natural ordering.
        do {
            int i=0;
            while (i < u.size() - 1) {
                int p1 = u.get(i);
                int p2 = u.get(i + 1);
                List followUpPages = rules.get(p1);
                if (followUpPages == null || !followUpPages.contains(p2)) {
                    // change order
                    u.set(i, u.get(i + 1));
                    u.set(i + 1, p1);
                }
                i++;
            }
        } while (!checkUpdate(u));
    }

    private boolean checkUpdate(List<Integer> u) {
        for (int i=0; i<u.size()-1; i++) {
            int p1 = u.get(i);
            List followUpPages = rules.get(p1);
            if (followUpPages==null) {
                return false;
            }
            for (int j=i+1; j<u.size(); j++) {
                int p2 =u.get(j);
                if (!followUpPages.contains(p2)) return false;
            }
        }
        return true;
    }

    private void readInput() {
        BufferedReader reader;
        try {
            // reader = new BufferedReader(new FileReader("/home/christoph/Projects/IdeaProjects/AdventOfCode/2024/input/input_day05_testset.txt"));
            reader = new BufferedReader(new FileReader("/home/christoph/Projects/IdeaProjects/AdventOfCode/2024/input/input_day05.txt"));
            String line = reader.readLine();

            // Read rules
            while (!line.equals("")) {
                // System.out.println(line);
                String[] values = line.split("\\|");
                List pages = rules.get(Integer.parseInt(values[0]));
                if (pages != null) {
                    pages.add(Integer.parseInt(values[1]));
                } else {
                    pages = new ArrayList<Integer>();
                    pages.add(Integer.parseInt(values[1]));
                    rules.put(Integer.parseInt(values[0]), pages);
                }
                // read next line
                line = reader.readLine();
            }
            line = reader.readLine();
            // Read updates
            while (line != null) {
                // System.out.println(line);
                String[] values = line.split(",");
                List<Integer> update = new ArrayList<>();
                for (String page : values) update.add(Integer.parseInt(page));
                updates.add(update);
                // read next line
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Day05_PrintQueue_Part2();
    }
}