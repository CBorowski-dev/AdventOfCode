package de.borowski.aoc;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Day06_GuardGallivant_Part2 {
    int size = 130;
    // String filename = "/home/christoph/Projects/IdeaProjects/AdventOfCode/2024/input/input_day06_testset.txt";
    String filename = "/home/christoph/Projects/IdeaProjects/AdventOfCode/2024/input/input_day06.txt";
    private final char[][] inputMap = new char[size][size];
    private final char[][] visitedMap = new char[size][size];
    private final char[][] visitedMap2 = new char[size][size];
    int startY = 0;
    int startX = 0;
    enum Direction {
        NORTH, WEST, SOUTH, EAST
    }

    public Day06_GuardGallivant_Part2() {
        readInput();
        initMap(visitedMap);
        moveGuard(startY, startX);
        System.out.println("--------------------------------");
        System.out.println(getCellsVisitedCount());
        System.out.println(getObstructionCount());
    }

    private int getObstructionCount() {
        int obstructionCount = 0;
        for (int y = 0; y<visitedMap.length; y++) {
            for (int x = 0; x<visitedMap.length; x++) {
                if (visitedMap[y][x]=='X' && y!=89 && x!=74) {
                    // place obstruction temporarily
                    inputMap[y][x] = '#';
                    if (moveGuardWithTest(startY, startX)) obstructionCount++;
                    // remove obstruction
                    inputMap[y][x] = '.';
                }
            }
        }

        return obstructionCount;
    }

    private int getCellsVisitedCount() {
        int count = 0;
        for (int y = 0; y< visitedMap.length; y++) {
            for (int x = 0; x< visitedMap.length; x++) {
                if (visitedMap[y][x]=='X') count++;
            }
        }
        return count;
    }

    private boolean moveGuardWithTest(int y, int x) {
        initMap(visitedMap2);
        Direction dir = Direction.NORTH;
        while (true) {
            switch (dir) {
                case NORTH -> {
                    if (y - 1 < 0) return false;
                    if (visitedMap2[y][x]=='^') {
                        return true;
                    }
                    else visitedMap2[y][x]='^';
                    if (inputMap[y-1][x] != '#') {
                        y--;
                    } else {
                        x++;
                        dir = Direction.EAST;
                    }
                }
                case EAST -> {
                    if (x + 1 >= inputMap.length) return false;
                    if (visitedMap2[y][x]=='>') {
                        return true;
                    }
                    else visitedMap2[y][x]='>';
                    if (inputMap[y][x + 1] != '#') {
                        x++;
                    } else {
                        y++;
                        dir = Direction.SOUTH;
                    }
                }
                case SOUTH -> {
                    if (y + 1 >= inputMap.length) return false;
                    if (visitedMap2[y][x]=='v') {
                        return true;
                    }
                    else visitedMap2[y][x]='v';
                    if (inputMap[y+1][x] != '#') {
                        y++;
                    } else {
                        x--;
                        dir = Direction.WEST;
                    }
                }
                case WEST -> {
                    if (x - 1 < 0) return false;
                    if (visitedMap2[y][x]=='<') {
                        return true;
                    }
                    else visitedMap2[y][x]='<';
                    if (inputMap[y][x - 1] != '#') {
                        x--;
                    } else {
                        y--;
                        dir = Direction.NORTH;
                    }
                }
            }
        }
    }

    private void moveGuard(int y, int x) {
        Direction dir = Direction.NORTH;
        while (true) {
            visitedMap[y][x] = 'X';
            switch (dir) {
                case WEST -> {
                    if (x - 1 < 0) return;
                    if (inputMap[y][x - 1] != '#') {
                        x--;
                    } else {
                        y--;
                        dir = Direction.NORTH;
                    }
                }
                case SOUTH -> {
                    if (y + 1 >= inputMap.length) return;
                    if (inputMap[y + 1][x] != '#') {
                        y++;
                    } else {
                        x--;
                        dir = Direction.WEST;
                    }
                }
                case EAST -> {
                    if (x + 1 >= inputMap.length) return;
                    if (inputMap[y][x + 1] != '#') {
                        x++;
                    } else {
                        y++;
                        dir = Direction.SOUTH;
                    }
                }
                case NORTH -> {
                    if (y - 1 < 0) return;
                    if (inputMap[y - 1][x] != '#') {
                        y--;
                    } else {
                        x++;
                        dir = Direction.EAST;
                    }
                }
            }
        }
    }

    private void initMap(char[][] map) {
        for (int y = 0; y< map.length; y++) {
            for (int x = 0; x< map.length; x++) {
                map[y][x] = inputMap[y][x]=='^' ? 'X' : '.';
                if (inputMap[y][x]=='^') {
                    startY = y; startX = x;
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
        new Day06_GuardGallivant_Part2();
    }
}