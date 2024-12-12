package de.borowski.aoc;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Day12_GardenGroups_Part2 {
    int size =140;
    int previousUnvisitedArea = size * size;
    //String filename = "/home/christoph/Projects/IdeaProjects/AdventOfCode/2024/input/input_day12_testset.txt";
    //String filename = "/home/christoph/Projects/IdeaProjects/AdventOfCode/2024/input/input_day12_testset1.txt";
    //String filename = "/home/christoph/Projects/IdeaProjects/AdventOfCode/2024/input/input_day12_testset2.txt";
    //String filename = "/home/christoph/Projects/IdeaProjects/AdventOfCode/2024/input/input_day12_testset3.txt";
    String filename = "/home/christoph/Projects/IdeaProjects/AdventOfCode/2024/input/input_day12.txt";

    private final char[][] map = new char[size][size];
    private final boolean[][] visited = new boolean[size][size];
    private final Map<Integer, List<Integer>> hSides = new HashMap<>();
    private final Map<Integer, List<Integer>> vSides = new HashMap<>();

    public Day12_GardenGroups_Part2() {
        readInput();
        System.out.println("--------------------------------");
        System.out.println(computeFencePrice());
    }

    private int computeFencePrice() {
        int fencePrice = 0;
        for (int y = 0; y< map.length; y++) {
            for (int x = 0; x< map.length; x++) {
                if (!visited[y][x]) {
                    hSides.clear();
                    vSides.clear();
                    countBordersForRegion(y, x, map[y][x]);
                    int sideCount = countSidesForRegion();
                    int unvisitedArea = getUnvisitedArea();
                    int regionArea = previousUnvisitedArea - unvisitedArea;
                    fencePrice += sideCount * regionArea; // Part 2
                    previousUnvisitedArea = unvisitedArea;
                }
            }
        }
        return fencePrice;
    }

    private int countSidesForRegion() {
        int sides = 0;
        int previousIdx;
        for (int rowID : hSides.keySet()) {
            List<Integer> row = hSides.get(rowID);
            row.sort(Comparator.comparingInt(x -> x));
            previousIdx = -2;
            for (int id: row) {
                if (id-1 > previousIdx) sides++;
                previousIdx = id;
            }
        }
        for (int colID : vSides.keySet()) {
            List<Integer> col = vSides.get(colID);
            col.sort(Comparator.comparingInt(x -> x));
            previousIdx = -2;
            for (int id: col) {
                if (id-1 > previousIdx) sides++;
                previousIdx = id;
            }
        }
        return sides;
    }

    private int getUnvisitedArea() {
        int unvisitedArea = 0;
        for (int y = 0; y< map.length; y++) {
            for (int x = 0; x< map.length; x++) {
                if (!visited[y][x]) unvisitedArea++;
            }
        }
        return unvisitedArea;
    }

    private int countBordersForRegion(int y, int x, char plantType) {
        visited[y][x] = true;
        // compute border count
        int borderCount = computeBorderCount(y, x);
        updateSideData(y, x);
        // visit neighbors
        if (y-1>=0 && map[y-1][x]==plantType && !visited[y-1][x]) borderCount += countBordersForRegion(y-1, x, plantType);
        if (x+1<size && map[y][x+1]==plantType && !visited[y][x+1]) borderCount += countBordersForRegion(y, x+1, plantType);
        if (y+1<size && map[y+1][x]==plantType && !visited[y+1][x]) borderCount += countBordersForRegion(y+1, x, plantType);
        if (x-1>=0 && map[y][x-1]==plantType && !visited[y][x-1]) borderCount += countBordersForRegion(y, x-1, plantType);

        return borderCount;
    }

    private void updateSideData(int y, int x) {
        // Norden
        if (y-1<0 || map[y-1][x] != map[y][x]) {
            List<Integer> row = hSides.computeIfAbsent(y*2, k -> new ArrayList<>());
            row.add(x);
        }
        // Osten
        if (x+1>=size || map[y][x+1] != map[y][x]) {
            List<Integer> col = vSides.computeIfAbsent(x*2+1, k -> new ArrayList<>());
            col.add(y);
        }
        // Süden
        if (y+1>=size || map[y+1][x] != map[y][x]) {
            List<Integer> row = hSides.computeIfAbsent(y*2+1, k -> new ArrayList<>());
            row.add(x);
        }
        // Westen
        if (x-1<0 || map[y][x-1] != map[y][x]) {
            List<Integer> col = vSides.computeIfAbsent(x*2, k -> new ArrayList<>());
            col.add(y);
        }
    }

    private int computeBorderCount(int y, int x) {
        return ((y-1<0 || map[y-1][x] != map[y][x]) ? 1 : 0) + // nach Norden
                ((x+1>=size || map[y][x+1] != map[y][x]) ? 1 : 0) + // nach Osten
                ((y+1>=size || map[y+1][x] != map[y][x]) ? 1 : 0) + // nach Süden
                ((x-1<0 || map[y][x-1] != map[y][x]) ? 1 : 0); // nach Westen
    }

    private void readInput() {
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(filename));
            int row = 0;
            String line = reader.readLine();
            while (line != null) {
                map[row++] = line.toCharArray();
                // read next line
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Day12_GardenGroups_Part2();
    }
}
