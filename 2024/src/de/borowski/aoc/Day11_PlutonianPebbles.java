package de.borowski.aoc;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Day11_PlutonianPebbles {
    //String filename = "/home/christoph/Projects/IdeaProjects/AdventOfCode/2024/input/input_day11_testset.txt";
    String filename = "/home/christoph/Projects/IdeaProjects/AdventOfCode/2024/input/input_day11.txt";

    private List<Long> stones = new ArrayList<>();
    private final Map<Long, List<Long>> map = new HashMap<>();

    public Day11_PlutonianPebbles() {
        readInput();
        System.out.println("--------------------------------");
    }

    public void partOne() {
        System.out.println(computeStonesCount(stones).size());
    }

    public void partTwo() {
        System.out.println(computeStoneCount(75, 25, compressStones(computeStonesCount(stones))));
    }

    private long computeStoneCount(int blinkCount, int start, Map<Long, Integer> compressedStones) {
        long count = 0;
        Set<Long> stones = compressedStones.keySet();
        for (Long s : stones) {
            int countPerStone = compressedStones.get(s);
            List<Long> stoneList = map.get(s);
            if (start == blinkCount-25) {
                // 50er Lauf
                if (stoneList != null) {
                    count += ((long) countPerStone * stoneList.size());
                } else {
                    List<Long> x = new ArrayList<>(Arrays.asList(s));
                    stoneList = computeStonesCount(x);
                    // map.put(s, stoneList);
                    count += ((long) countPerStone * stoneList.size());
                }
            } else {
                // 25er Lauf
                if (stoneList == null) {
                    List<Long> x = new ArrayList<>(Arrays.asList(s));
                    stoneList = computeStonesCount(x);
                    map.put(s, stoneList);
                }
                Map<Long, Integer> compressedStonesTmp = compressStones(stoneList);
                count += (countPerStone * computeStoneCount(blinkCount, start+25, compressedStonesTmp));
            }
        }
        return count;
    }

    private Map<Long, Integer> compressStones(List<Long> stones) {
        Map<Long, Integer> compressedStones = new HashMap<>();
        Set<Long> stonesSet = new HashSet<>(stones);
        for (long s : stonesSet) {
            int startIdx = stones.indexOf(s);
            int endIdx = stones.lastIndexOf(s);
            compressedStones.put(s, endIdx-startIdx+1);
        }
        return compressedStones;
    }

    private List<Long> computeStonesCount(List<Long> stones) {
        for (int x=0; x<25; x++) {
            for (int i=stones.size()-1; i>=0; i--) {
                long l = stones.get(i);
                if (l == 0) {
                    // replace by 1
                    stones.remove(i);
                    stones.add(i, 1L);
                } else {
                    String s = Long.toString(l);
                    if (s.length() % 2 == 0) {
                        // split
                        long first = Long.parseLong(s.substring(0, s.length() / 2));
                        long second = Long.parseLong(s.substring(s.length() / 2));
                        stones.remove(i);
                        stones.add(i, second);
                        stones.add(i, first);
                    } else {
                        // multiply by 2024
                        stones.remove(i);
                        stones.add(i, l * 2024);
                    }
                }
            }
        }
        return stones.stream().sorted().toList();
    }

    private void readInput() {
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(filename));
            int row = 0;
            String line = reader.readLine();
            while (line != null) {
                String[] stonesArray = line.split(" ");
                for (String x: stonesArray) stones.add(Long.valueOf(x));
                // read next line
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        // new Day11_PlutonianPebbles().partOne();  // Solution for part 1 is: 202019
        new Day11_PlutonianPebbles().partTwo();     // Solution for part 2 is: 239321955280205
    }
}
