package de.borowski.aoc;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Day10_Factory_Part2 {

    public static final List<List<List<Integer>>> SWITCHES = new ArrayList<>();
    public static final List<List<Integer>> JOLTAGES_GOAL = new ArrayList<>();
    public static final Set<Integer[]> UNUSABLE_SOLUTIONS = new HashSet<>();

    public static void main(String[] args) {
        BufferedReader reader;

        try {
            // reader = new BufferedReader(new FileReader("/home/christoph/Projects/IdeaProjects/AdventOfCode/2025/input/input_day10_testset.txt"));
            reader = new BufferedReader(new FileReader("/home/christoph/Projects/IdeaProjects/AdventOfCode/2025/input/input_day10.txt"));
            String line = reader.readLine();

            while (line != null) {
                // lights
                String[] parts = line.split("] ");
                // switches
                parts = parts[1].split(" \\{");
                String[] switches = parts[0].split(" ");
                List<List<Integer>> encodedSwitches = new ArrayList<>();
                for (String s : switches) {
                    List<Integer> indexes = new ArrayList<>();
                    s = s.substring(1, s.length() - 1);
                    String[] values = s.split(",");
                    for (String s2 : values) {
                        indexes.add(Integer.parseInt(s2));
                    }
                    encodedSwitches.add(indexes);
                }
                // sort
                encodedSwitches.sort((o1, o2) -> o2.size() - o1.size());
                SWITCHES.add(encodedSwitches);

                // joltages
                String joltagesStr = parts[1].substring(0, parts[1].length() - 1);
                String[] j = joltagesStr.split(",");
                List<Integer> joltages = new ArrayList<>();
                for (String s : j) {
                    int e = Integer.parseInt(s);
                    if (e == 0) System.out.println(joltagesStr);
                    joltages.add(e);
                }
                // sort by size
                JOLTAGES_GOAL.add(joltages);
                // read next line
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        long result = 0;

        // Part 2
        for (int i = 0; i < JOLTAGES_GOAL.size(); i++) {
            List<Integer> joltageGoal = JOLTAGES_GOAL.get(i);
            Integer[] goals = new Integer[joltageGoal.size()];
            UNUSABLE_SOLUTIONS.clear();
            int maxButtonPressCount = 0;
            for (int bp : joltageGoal) maxButtonPressCount += bp;
            System.out.print("* ");
            result += findFewestTotalPresses(joltageGoal.toArray(goals), SWITCHES.get(i),1, maxButtonPressCount);
        }
        System.out.println("--------------------------------");
        System.out.println(result);
    }
    // 58280 to high

    private static int findFewestTotalPresses(Integer[] joltageGoal, List<List<Integer>> encodedSwitches, int recursion, int bestResult) {
        int bestResultOriginal = bestResult;
        // List<List<Integer>> usableSwitches = findUsableSwitches(encodedSwitches, joltageGoal);
        for (int j = 0; j < encodedSwitches.size(); j++) {
            List<Integer> indexes = encodedSwitches.get(j);
            for (Integer i : indexes) joltageGoal[i]--;
            boolean allowed = true;
            boolean allZero = true;
            for (Integer integer : joltageGoal) {
                if (integer < 0) allowed = false;
                if (integer != 0) allZero = false;
            }
            if (allZero) {
                printSolution(recursion);
                for (Integer i : indexes) joltageGoal[i]++;
                return recursion;
            } else if (allowed && ((recursion + 1) < bestResult) && !containsGoal(joltageGoal)) {
                bestResult= findFewestTotalPresses(joltageGoal, encodedSwitches,recursion + 1, bestResult);
                // if (tmpResult < bestResult) return bestResult = ;
            }
            for (Integer i : indexes) joltageGoal[i]++;
        }
        if (bestResultOriginal == bestResult && !containsGoal(joltageGoal)) {
            UNUSABLE_SOLUTIONS.add(Arrays.copyOf(joltageGoal, joltageGoal.length));
        }
        return bestResult;
    }

    private static boolean containsGoal(Integer[] joltageGoal) {
        for (Integer[] x : UNUSABLE_SOLUTIONS) {
            if (Arrays.equals(x, joltageGoal)) return true;
        }
        return false;
    }

    private static List<List<Integer>> findUsableSwitches(List<List<Integer>> encodedSwitches, Integer[] joltageGoal) {
        List<List<Integer>> usableSwitches = new ArrayList<>();
        a:
        for (List<Integer> indexes : encodedSwitches) {
            for (Integer index : indexes) {
                if (joltageGoal[index] == 0) continue a;
            }
            usableSwitches.add(indexes);
        }
        return usableSwitches;
    }

    private static void printSolution(int recursion) {
        System.out.println("Solution : " + recursion + " " + UNUSABLE_SOLUTIONS.size());
        // for (Integer e : status) System.out.print(e);
        // System.out.println();
    }
}
