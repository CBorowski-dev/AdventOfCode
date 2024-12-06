package de.borowski.aoc;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Day06_GuardGallivant {
    int size = 130;
    // String filename = "/home/christoph/Projects/IdeaProjects/AdventOfCode/2024/input/input_day06_testset.txt";
    String filename = "/home/christoph/Projects/IdeaProjects/AdventOfCode/2024/input/input_day06.txt";
    private final char[][] inputMap = new char[size][size];
    private final char[][] visitedMap = new char[size][size];
    int startY = 0;
    int startX = 0;
    enum Direction {
        NORTH, WEST, SOUTH, EAST
    }

    public Day06_GuardGallivant() {
        readInput();
        initMap();
        moveGuard(startY, startX);
        System.out.println("--------------------------------");
        System.out.println(getMoveCount());
    }

    private void initMap() {
        for (int y = 0; y< visitedMap.length; y++) {
            for (int x = 0; x< visitedMap.length; x++) {
                visitedMap[y][x] = inputMap[y][x]=='^' ? 'X' : '.';
                if (inputMap[y][x]=='^') {
                    startY = y; startX = x;
                }
            }
        }
    }

    private int getMoveCount() {
        int count = 0;
        for (int y = 0; y< visitedMap.length; y++) {
            for (int x = 0; x< visitedMap.length; x++) {
                if (visitedMap[y][x]=='X') count += 1;
            }
        }
        return count;
    }

    private void moveGuard(int y, int x) {
        Day06_GuardGallivant_Part2.Direction dir = Day06_GuardGallivant_Part2.Direction.NORTH;
        while (true) {
            visitedMap[y][x] = 'X';
            switch (dir) {
                case WEST -> {
                    if (x - 1 < 0) return;
                    if (inputMap[y][x - 1] != '#') {
                        x--;
                    } else {
                        y--;
                        dir = Day06_GuardGallivant_Part2.Direction.NORTH;
                    }
                }
                case SOUTH -> {
                    if (y + 1 >= inputMap.length) return;
                    if (inputMap[y + 1][x] != '#') {
                        y++;
                    } else {
                        x--;
                        dir = Day06_GuardGallivant_Part2.Direction.WEST;
                    }
                }
                case EAST -> {
                    if (x + 1 >= inputMap.length) return;
                    if (inputMap[y][x + 1] != '#') {
                        x++;
                    } else {
                        y++;
                        dir = Day06_GuardGallivant_Part2.Direction.SOUTH;
                    }
                }
                case NORTH -> {
                    if (y - 1 < 0) return;
                    if (inputMap[y - 1][x] != '#') {
                        y--;
                    } else {
                        x++;
                        dir = Day06_GuardGallivant_Part2.Direction.EAST;
                    }
                }
            }
        }
    }

    private void readInput() {
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(filename));
            int row = 0;
            String line = reader.readLine();
            while (line != null) {
                inputMap[row++] = line.toCharArray();
                // read next line
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        new Day06_GuardGallivant();
    }
}
