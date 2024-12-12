package de.borowski.aoc;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Day12_GardenGroups_Part2 {
    int size =5;
    int previousUnvisitedArea = size * size;
    //String filename = "/home/christoph/Projects/IdeaProjects/AdventOfCode/2024/input/input_day12_testset.txt";
    String filename = "/home/christoph/Projects/IdeaProjects/AdventOfCode/2024/input/input_day12_testset1.txt";
    //String filename = "/home/christoph/Projects/IdeaProjects/AdventOfCode/2024/input/input_day12_testset2.txt";
    //String filename = "/home/christoph/Projects/IdeaProjects/AdventOfCode/2024/input/input_day12.txt";

    private final char[][] map = new char[size][size];
    private final boolean[][] visited = new boolean[size][size];
    private boolean[][] visited2;

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
                    visited2  = new boolean[size][size];
                    int borderCount = countBordersForRegion(y, x, map[y][x]);
                    int sideCount = countSidesForRegion();
                    int unvisitedArea = getUnvisitedArea();
                    int regionArea = previousUnvisitedArea - unvisitedArea;
                    // fencePrice += borderCount * regionArea; // Part 1
                    fencePrice += sideCount * regionArea; // Part 2
                    previousUnvisitedArea = unvisitedArea;
                    // System.out.println(fencePrice);
                }
            }
        }
        return fencePrice;
    }

    private int countSidesForRegion() {
        int sides = 0;
        int startIndexPrev = -1;
        int gardenPlotsPrev = 0;
        int startIndex, gardenPlots;

        // rows
        for (int y=0; y<size; y++) {
            startIndex = -1;
            gardenPlots = 0;
            for (int x = 0; x < size; x++) {
                if (visited2[y][x]) {
                    gardenPlots += 1;
                    if (startIndex == -1) startIndex = x;
                }
            }
            if (startIndexPrev >= 0 && startIndexPrev != startIndex) sides++;
            if (gardenPlots > 0 && startIndexPrev+gardenPlotsPrev != startIndex+gardenPlots) sides++;
            startIndexPrev = startIndex;
            gardenPlotsPrev = gardenPlots;
        }
        if (gardenPlotsPrev > 0 ) sides++;

        // columns
        startIndexPrev = -1;
        gardenPlotsPrev = 0;
        for (int x=0; x<size; x++) {
            startIndex = -1;
            gardenPlots = 0;
            for (int y = 0; y < size; y++) {
                if (visited2[y][x]) {
                    gardenPlots += 1;
                    if (startIndex == -1) startIndex = y;
                }
            }
            if (startIndexPrev >= 0 && startIndexPrev != startIndex) sides++;
            if (gardenPlots > 0 && startIndexPrev+gardenPlotsPrev != startIndex+gardenPlots) sides++;
            startIndexPrev = startIndex;
            gardenPlotsPrev = gardenPlots;
        }
        if (gardenPlotsPrev > 0 ) sides++;

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
        visited2[y][x] = true;
        // compute border count
        int borderCount = computeBorderCount(y, x);
        // visit neighbors
        if (y-1>=0 && map[y-1][x]==plantType && !visited[y-1][x]) borderCount += countBordersForRegion(y-1, x, plantType);
        if (x+1<size && map[y][x+1]==plantType && !visited[y][x+1]) borderCount += countBordersForRegion(y, x+1, plantType);
        if (y+1<size && map[y+1][x]==plantType && !visited[y+1][x]) borderCount += countBordersForRegion(y+1, x, plantType);
        if (x-1>=0 && map[y][x-1]==plantType && !visited[y][x-1]) borderCount += countBordersForRegion(y, x-1, plantType);

        return borderCount;
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

    private int[] convertToIntegerArray(String line) {
        int length = line.length();
        int[] ba = new int[length];
        for (int i=0; i<length; i++) ba[i] = Integer.parseInt(line.substring(i, i+1));
        return ba;
    }

    public static void main(String[] args) {
        new Day12_GardenGroups_Part2();
    }
}
