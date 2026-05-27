package de.borowski.aoc;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Day10_Factory_Part2new {

    public static final List<List<List<Integer>>> SWITCHES = new ArrayList<>();
    public static final List<List<Integer>> JOLTAGES_GOAL = new ArrayList<>();
    public static final Set<Integer[]> UNUSABLE_SOLUTIONS = new HashSet<>();
    public static final Map<Integer[], Integer> USABLE_SOLUTIONS = new HashMap<>();

    public static int MAX_BUTTONPRESS_COUNT;

    public static void main(String[] args) {
        BufferedReader reader;

        try {
            // reader = new BufferedReader(new FileReader("/home/christoph/Projects/IdeaProjects/AdventOfCode/2025/input/input_day10_testset.txt"));
            // reader = new BufferedReader(new FileReader("/home/christoph/Projects/IdeaProjects/AdventOfCode/2025/input/input_day10.txt"));
            reader = new BufferedReader(new FileReader("/home/christoph/Projects/IdeaProjects/AdventOfCode/2025/input/input_day10_test.txt"));
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
        long start = System.currentTimeMillis();
        for (int i = 0; i < JOLTAGES_GOAL.size(); i++) {
            List<Integer> joltageGoal = JOLTAGES_GOAL.get(i);
            Integer[] goals = new Integer[joltageGoal.size()];
            UNUSABLE_SOLUTIONS.clear();
            USABLE_SOLUTIONS.clear();
            MAX_BUTTONPRESS_COUNT = Integer.MAX_VALUE;
            int[] returnValues = new int[]{0, 0};
            System.out.print("* " + i + " ");
            if (findFewestTotalPresses(returnValues, joltageGoal.toArray(goals), SWITCHES.get(i), 1)) {
                result += MAX_BUTTONPRESS_COUNT;
            }
        }
        long end = System.currentTimeMillis();
        System.out.println("--------------------------------");
        System.out.println((end - start)/ 1000.0);
        System.out.println("--------------------------------");
        System.out.println(result);
    }
    // 58280 to high

    private static boolean findFewestTotalPresses(int[] returnValues, Integer[] joltageGoal, List<List<Integer>> encodedSwitchIndexes, int recursion) {
        List<List<Integer>> usableSwitchIndexes = findUsableSwitchIndexes(encodedSwitchIndexes, joltageGoal);
        if (usableSwitchIndexes.isEmpty()) return false;
        boolean solutionFound = false;
        int solutionDepth = 0;
        a:
        for (List<Integer> indexes : usableSwitchIndexes) {
            // substract from joltageGoal at the indexes
            for (Integer idx : indexes) joltageGoal[idx]--;
            // check current joltageGoal
            boolean allZero = true;
            b: for (Integer integer : joltageGoal) {
                if (integer != 0) {
                    allZero = false;
                    break b;
                }
            }
            if (allZero) {
                printSolution(recursion);
                MAX_BUTTONPRESS_COUNT = recursion;
                solutionFound = true;
                for (Integer idx : indexes) joltageGoal[idx]++;
                break a;
            }
            if ((recursion + 1 < MAX_BUTTONPRESS_COUNT) && !unusabelContainsGoal(joltageGoal)) {
                Integer[] goal = usabelContainsGoal(joltageGoal);
                if (goal != null) {
                    int depth = USABLE_SOLUTIONS.get(goal);
                    if (recursion + depth < MAX_BUTTONPRESS_COUNT) {
                        printSolution(recursion + depth);
                        MAX_BUTTONPRESS_COUNT = recursion + depth;
                        solutionFound = true; // we found a solution
                        solutionDepth = depth; // depth
                    }
                    continue a;
                }
                if (findFewestTotalPresses(returnValues, joltageGoal, usableSwitchIndexes, recursion + 1)) {
                    // found solution
                    goal = usabelContainsGoal(joltageGoal);
                    if (goal == null || USABLE_SOLUTIONS.get(goal) > returnValues[1]) {
                        USABLE_SOLUTIONS.remove(goal);
                        USABLE_SOLUTIONS.put(Arrays.copyOf(joltageGoal, joltageGoal.length), returnValues[1]);
                        solutionDepth = returnValues[1] + 1;
                        solutionFound = true;
                        for (Integer idx : indexes) joltageGoal[idx]++;
                        break a;
                    }
                } else {
                    // found no solution
                    if (!UNUSABLE_SOLUTIONS.contains(joltageGoal))
                        UNUSABLE_SOLUTIONS.add(Arrays.copyOf(joltageGoal, joltageGoal.length));
                }
            }
            for (Integer idx : indexes) joltageGoal[idx]++;
        }
        returnValues[0] = solutionFound ? 1 : 0; // we found a solution
        returnValues[1] = solutionFound ? solutionDepth + 1 : 0; // depth
        return solutionFound;
    }

    private static boolean unusabelContainsGoal(Integer[] joltageGoal) {
        for (Integer[] x : UNUSABLE_SOLUTIONS) {
            if (Arrays.equals(x, joltageGoal)) {
                // System.out.println("- " + UNUSABLE_SOLUTIONS.size());
                return true;
            }
        }
        return false;
    }

    private static Integer[] usabelContainsGoal(Integer[] joltageGoal) {
        for (Integer[] x : USABLE_SOLUTIONS.keySet()) {
            if (Arrays.equals(x, joltageGoal)) {
                // System.out.println("+ " + USABLE_SOLUTIONS.size());
                return x;
            }
        }
        return null;
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
        if (usableSwitchIndexes.size() == 1) {
            int old = -1;
            b:
            for (int g : joltageGoal) {
                if (old == -1 && g != 0) old = g;
                if (old != -1 && g != 0 && old != g) {
                    usableSwitchIndexes.removeFirst();
                    break b;
                }
            }
        }
        return usableSwitchIndexes;
    }

    private static void printSolution(int recursion) {
        System.out.println("Solution : " + recursion + " " + UNUSABLE_SOLUTIONS.size() + " " + USABLE_SOLUTIONS.size());
    }
}
