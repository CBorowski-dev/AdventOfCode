package de.borowski.aoc;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Day10_Factory_Part2 {

    public static final List<Integer> LIGHTS_GOAL = new ArrayList<>();
    public static final List<List<Integer>> SWITCHES = new ArrayList<>();
    public static final List<List<Integer>> JOLTAGES_GOAL = new ArrayList<>();
    // public static final Map<Integer, Integer> SOLUTIONS = new HashMap<>();

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
                    // if (e==0) System.out.println(joltagesStr);
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

        // Part 2
        long start = System.currentTimeMillis();
        for (int i = 0; i < JOLTAGES_GOAL.size(); i++) {
            // SOLUTIONS.clear();
            long resultTmp = part2(JOLTAGES_GOAL.get(i), SWITCHES.get(i), 1);
            System.out.println("--> " + resultTmp);
            result += resultTmp;
        }
        long end = System.currentTimeMillis();
        System.out.println("--------------------------------");
        System.out.println((end - start)/ 1000.0);
        System.out.println("--------------------------------");
        System.out.println(result);
    }

    private static int part2(List<Integer> joltageGoal, List<Integer> switches, int recursion) {
        // https://www.reddit.com/r/adventofcode/comments/1pk87hl/2025_day_10_part_2_bifurcate_your_way_to_victory/
        int result = Integer.MAX_VALUE;
        int faktor = 1;

        List<Combo> combos;
        StringBuilder sb;
        boolean foundOdd;

        sb = new StringBuilder();
        foundOdd = false;
        for (Integer j : joltageGoal) {
            if ((j & 1) == 1) {
                sb.append('#');
                foundOdd = true;
            } else {
                sb.append('.');
            }
        }
        if (!foundOdd) {
            // keine ungeraden Counter gefunden --> für alle gerade # setzen
            sb = new StringBuilder();
            for (Integer g : joltageGoal) sb.append((g > 0) ? '#' : '.');
        }



        Integer lightGoal = createBitMask(sb.toString());
        combos = findAllPossiblePresses(lightGoal, switches);
        System.out.println("-------------------------------------\nRekursion " + recursion + "\nPattern " + sb.toString() + " = " + printJoltageGoal(joltageGoal));
        System.out.println("# Combos " + combos.size() + " : " + printCombos(combos));
        // System.out.print(recursion +  " " + sb.toString() + " " + lightGoal + " " + printJoltageGoal(joltageGoal) + printCombos(combos));

        if (combos.isEmpty()) {
            if (foundOdd) {
                System.out.println(" -> max exit");
                return Integer.MAX_VALUE;
            } else {
                // keine Lösung gefunden und alle Counter waren vorher gerade: alle gerade --> halbieren
                // System.out.println(" -> halbieren");
                joltageGoal.replaceAll(g -> g / 2);
                faktor *= 2;
            }
        }

        if (faktor == 1) {
            // System.out.println(" -> verarbeiten");
            nextc:
            for (Combo c : combos) {
                System.out.println("Rekursion " + recursion + " Processing combo (" + c.combo + "|" + c.buttonPressCount + ")");
                // joltageGoalNextRecursion aus joltageGoal erstellen
                List<Integer> joltageGoalNextRecursion = new ArrayList<>(joltageGoal);
                // joltageGoalNextRecursion gemäß Combo c erniedrigen
                for (int i = 0; i < switches.size(); i++) {
                    if ((c.combo & (1 << i)) > 0) {
                        // applay i'th switch
                        int s = switches.get(i);
                        for (int j = 0; j < joltageGoalNextRecursion.size(); j++) {
                            if ((s & (1 << j)) > 0) {
                                int ng = joltageGoalNextRecursion.get(j);
                                if (ng == 0) {
                                    System.out.println(" => 0 Wert = Abbruch");
                                    continue nextc;
                                }
                                joltageGoalNextRecursion.set(j, ng - 1);
                            }
                        }
                    }
                }
                // finished ermitteln
                boolean finished = true;
                for (Integer j : joltageGoalNextRecursion)
                    if (j > 0) {
                        finished = false;
                        break;
                    }
                if (finished) {
                    if (c.buttonPressCount < result)
                        result = c.buttonPressCount;
                } else {
                    System.out.println("--> Aufruf 1");
                    // Rekursiver Aufruf
                    int tmpResult = part2(joltageGoalNextRecursion, switches, recursion+1);
                    if (tmpResult < Integer.MAX_VALUE && (tmpResult + c.buttonPressCount) < result)
                        result = faktor * tmpResult + c.buttonPressCount; // faktor ist hier 1
                }
            }
        } else {
            // joltageGoalNextRecursion aus joltageGoal erstellen
            List<Integer> joltageGoalNextRecursion = new ArrayList<>(joltageGoal);
            System.out.println("--> Aufruf 2");
            // Rekursiver Aufruf
            int tmpResult = part2(joltageGoalNextRecursion, switches, recursion+1);
            if (tmpResult < Integer.MAX_VALUE)
                result = faktor * tmpResult;
        }
        return result;
    }

    private static String printJoltageGoal(List<Integer> joltageGoal) {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int g : joltageGoal) {
            sb.append(g);
            sb.append(" ,");
        }
        sb.append("] ");
        return sb.toString();
    }

    private static String printCombos(List<Combo> combos) {
        StringBuilder sb = new StringBuilder();
        for (Combo c :combos) {
            sb.append("(");
            sb.append(c.combo);
            sb.append("|");
            sb.append(c.buttonPressCount);
            sb.append(") ");
        }
        return sb.toString();
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
        double pow = Math.pow(2, encodedSwitches.size());
        for (int comb = 1; comb <= pow; comb++) {
            int buttonPressCount = 0;
            int mask = 0;
            for (int i=0; i<encodedSwitches.size(); i++) {
                if ((comb & (1 << i)) > 0) {
                    mask = mask ^ encodedSwitches.get(i);
                    buttonPressCount++;
                }
            }
            // 1.
            if (lightGoal == mask) {
                allPossiblePresses.add(new Combo(comb, buttonPressCount));
            }
            // 2.
            /* boolean useMask = true;
            for (int i = 0; i < encodedSwitches.size(); i++) {
                if ((lightGoal & (1 << i)) > 0 && (mask & (1 << i)) == 0) {
                    useMask = false;
                }
            }
            if (useMask) {
                allPossiblePresses.add(new Combo(comb, buttonPressCount));
            }*/
        }
        // Sort
        allPossiblePresses = allPossiblePresses.stream().sorted(Comparator.comparingInt(Combo::buttonPressCount)).toList();
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
