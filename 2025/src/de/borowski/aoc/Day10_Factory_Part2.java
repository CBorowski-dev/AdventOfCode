package de.borowski.aoc;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Day10_Factory_Part2 {

    public static final List<Integer> LIGHTS_GOAL = new ArrayList<>();
    public static final List<List<Integer>> SWITCHES = new ArrayList<>();
    public static final List<List<Integer>> JOLTAGES_GOAL = new ArrayList<>();

    public static void main(String[] args) {
        BufferedReader reader;

        try {
            reader = new BufferedReader(new FileReader("/home/christoph/Projects/IdeaProjects/AdventOfCode/2025/input/input_day10_testset.txt"));
            // reader = new BufferedReader(new FileReader("/home/christoph/Projects/IdeaProjects/AdventOfCode/2025/input/input_day10.txt"));
            // reader = new BufferedReader(new FileReader("/home/christoph/Projects/IdeaProjects/AdventOfCode/2025/input/input_day10_test.txt"));

            String line = reader.readLine();

            while (line != null) {
                // lights
                String[] parts = line.split("] ");
                LIGHTS_GOAL.add(createBitMask(parts[0].substring(1)));

                // switches
                parts = parts[1].split(" \\{");
                String[] switches = parts[0].split(" ");
                List<Integer> encodedSwitches = new ArrayList<>();
                for (String s: switches) {
                    int encodedSwitch = 0;
                    s = s.substring(1, s.length()-1);
                    String[] values = s.split(",");
                    for (String s2: values) {
                        encodedSwitch = encodedSwitch ^ (int) Math.pow(2, Integer.parseInt(s2));
                    }
                    encodedSwitches.add(encodedSwitch);
                }
                SWITCHES.add(encodedSwitches);

                // joltages
                String joltagesStr = parts[1].substring(0, parts[1].length()-1);
                String[] j = joltagesStr.split(",");
                List<Integer> joltages = new ArrayList<>();
                for (String s: j) {
                    int e = Integer.parseInt(s);
                    if (e==0) System.out.println(joltagesStr);
                    joltages.add(e);
                }
                JOLTAGES_GOAL.add(joltages);
                // read next line
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        long result = 0;

        // Part 1
        /* for (int i=0; i<LIGHTS_GOAL.size(); i++) {
            Integer lightGoal = LIGHTS_GOAL.get(i);
            result += findFewestTotalPresses(lightGoal, SWITCHES.get(i));
        }
        System.out.println("--------------------------------");
        System.out.println(result);
         */

        // Part 2
        long start = System.currentTimeMillis();
        for (int i = 0; i < JOLTAGES_GOAL.size(); i++) {
            result += part2(JOLTAGES_GOAL.get(i), SWITCHES.get(i));
        }
        long end = System.currentTimeMillis();
        System.out.println("--------------------------------");
        System.out.println((end - start)/ 1000.0);
        System.out.println("--------------------------------");
        System.out.println(result);
    }

    private static long part2(List<Integer> joltageGoal, List<Integer> switches) {
        // finished ermitteln
        boolean finished = true;
        for (Integer j : joltageGoal) if (j > 0) finished = false;
        if (finished) return 0;

        long result = Long.MAX_VALUE;

        // Überprüfen, ob noch weiter gesucht werden muss
        // Integer[] goals = new Integer[joltageGoal.size()];
        StringBuffer sb = new StringBuffer();
        boolean foundLight = false;
        for (Integer j : joltageGoal) {
            if ((j & 1) == 1) {
                sb.append('#');
                foundLight = true;
            } else {
                sb.append('.');
            }
        }

        // Es gibt noch Lichter, die abzubauen sind
        if (foundLight) {
            Integer lightGoal = createBitMask(sb.toString());
            List<Combo> combos = findAllPossiblePresses(lightGoal, switches); // * faktor

            for (Combo c: combos) {
                // joltageGoalNextRecursion gemäß combos erstellen
                List<Integer> joltageGoalNextRecursion = new ArrayList<>();
                for (int i = 0; i< joltageGoal.size(); i++) {
                    joltageGoalNextRecursion.add(joltageGoal.get(i));
                }
                // joltageGoalNextRecursion gemäß Combo c erniedrigen

                // Rekursiver Aufruf
                long tmpResult = part2(joltageGoalNextRecursion, switches);
                if ((tmpResult + c.buttonPressCount) < result) result = tmpResult + c.buttonPressCount;
            }
        }
        return result;
    }

    private static int findFewestTotalPresses(Integer lightGoal, List<Integer> encodedSwitches) {
        int minFewestTotalPresses = Integer.MAX_VALUE;
        for (int comb = 1; comb < Math.pow(2, encodedSwitches.size()); comb++) {
            int buttonPressCount = 0;
            int mask = 0;
            for (int i=0; i<encodedSwitches.size(); i++) {
                if ((comb & (1 << i)) > 0) {
                    mask = mask ^ encodedSwitches.get(i);
                    buttonPressCount++;
                }
            }
            if (lightGoal == mask && minFewestTotalPresses > buttonPressCount) minFewestTotalPresses = buttonPressCount;
        }
        return minFewestTotalPresses;
    }

    private static List<Combo> findAllPossiblePresses(Integer lightGoal, List<Integer> encodedSwitches) {
        List<Combo> allPossiblePresses = new ArrayList<>();
        for (int comb = 1; comb < Math.pow(2, encodedSwitches.size()); comb++) {
            int buttonPressCount = 0;
            int mask = 0;
            for (int i=0; i<encodedSwitches.size(); i++) {
                if ((comb & (1 << i)) > 0) {
                    mask = mask ^ encodedSwitches.get(i);
                    buttonPressCount++;
                }
            }
            if (lightGoal == mask) {
                allPossiblePresses.add(new Combo(comb, buttonPressCount));
            }
        }
        return allPossiblePresses;
    }

    private static Integer createBitMask(String lights) {
        int bitMask = 0;
        char[] l = lights.toCharArray();
        for (int i=0; i<l.length; i++) {
            if (l[i] == '#') {
                bitMask =  bitMask ^ (int) Math.pow(2, i);
            }
        }
        return bitMask;
    }

    private record Combo(int combo, int buttonPressCount) {};
}
