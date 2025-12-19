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
                // encodedSwitches.sort((o1, o2) -> o2.size() - o1.size());
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
            result += findFewestTotalPresses(joltageGoal.toArray(goals), SWITCHES.get(i), 1, maxButtonPressCount);
        }
        System.out.println("--------------------------------");
        System.out.println(result);
    }
    // 58280 to high

    private static int findFewestTotalPresses(Integer[] joltageGoal, List<List<Integer>> encodedSwitchIndexes, int recursion, int maxButtonPressCount) {
        List<List<Integer>> usableSwitchIndexes = findUsableSwitchIndexes(encodedSwitchIndexes, joltageGoal);
        if (usableSwitchIndexes.size()==0) return -128;
        for (int j = 0; j < usableSwitchIndexes.size(); j++) {
            // get next switch
            List<Integer> indexes = usableSwitchIndexes.get(j);
            // substract from joltageGoal at the indexes
            for (Integer idx : indexes) joltageGoal[idx]--;
            // check current joltageGoal
            boolean allowed = true;
            boolean allZero = true;
            for (Integer integer : joltageGoal) {
                if (integer < 0) allowed = false;
                if (integer != 0) allZero = false;
            }
            if (allZero) {
                printSolution(recursion);
                for (Integer idx : indexes) joltageGoal[idx]++;
                return recursion;
            } else if (allowed && (recursion + 1 < maxButtonPressCount) && !containsGoal(joltageGoal)) {
                int bpc = findFewestTotalPresses(joltageGoal, encodedSwitchIndexes, recursion + 1, maxButtonPressCount);
                if (bpc == -128 && !containsGoal(joltageGoal)) {
                    UNUSABLE_SOLUTIONS.add(Arrays.copyOf(joltageGoal, joltageGoal.length));
                }
                if (bpc != -128 && bpc < maxButtonPressCount) maxButtonPressCount = bpc;
            }
            for (Integer idx : indexes) joltageGoal[idx]++;
        }
        return maxButtonPressCount;
    }

    private static boolean containsGoal(Integer[] joltageGoal) {
        for (Integer[] x : UNUSABLE_SOLUTIONS) {
            if (Arrays.equals(x, joltageGoal)) return true;
        }
        return false;
    }

    private static List<List<Integer>> findUsableSwitchIndexes(List<List<Integer>> encodedSwitches, Integer[] joltageGoal) {
        List<List<Integer>> usableSwitchIndexes = new ArrayList<>();
        a:
        for (List<Integer> indexes : encodedSwitches) {
            for (Integer index : indexes) {
                if (joltageGoal[index] == 0) continue a;
            }
            usableSwitchIndexes.add(indexes);
        }
        if (usableSwitchIndexes.size()==1) {
            int old = -1;
            b: for (int g : joltageGoal) {
                if (old == -1 && g != 0) old = g;
                if (old != -1 && g != 0 && old != g) {
                    usableSwitchIndexes.remove(0);
                    break b;
                }
            }
        }
        return usableSwitchIndexes;
    }

    private static void printSolution(int recursion) {
        System.out.println("Solution : " + recursion + " " + UNUSABLE_SOLUTIONS.size());
        // for (Integer e : status) System.out.print(e);
        // System.out.println();
    }
}
