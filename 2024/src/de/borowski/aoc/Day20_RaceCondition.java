package de.borowski.aoc;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Day20_RaceCondition {
    private final int size =141; // 15 141
    private final int savings = 100;

    //String filename = "/home/christoph/Projects/IdeaProjects/AdventOfCode/2024/input/input_day20_testset.txt";
    String filename = "/home/christoph/Projects/IdeaProjects/AdventOfCode/2024/input/input_day20.txt";

    private final Map<Coordinate, Integer> visitedNodes = new HashMap<>();
    private final Map<Coordinate, Integer> unvisitedNodes = new HashMap<>();
    private final Map<Coordinate, Integer> neighborNodes = new HashMap<>();
    private final char[][] maze = new char[size][size];
    private int startX = 0;
    private int startY = 0;
    private int endX = 0;
    private int endY = 0;

    public Day20_RaceCondition() {
        readInput();
        initializeUnvisitedNodes();
        searchShortestPath(new Coordinate(startY, startX));
        System.out.println("--------------------------------");
        int shortestPath = visitedNodes.get(new Coordinate(endY, endX));
        System.out.println("Original shortest path: " + shortestPath);
        int count = 0;
        int currentShortestPath;
        for (int y=1; y<size-1; y++) {
            for (int x=1; x<size-1; x++) {
                if (maze[y][x]=='#' && !(maze[y-1][x]=='#' && maze[y+1][x]=='#' && maze[y][x-1]=='#' && maze[y-1][x+1]=='#')) {
                    maze[y][x] = '.';
                    initializeUnvisitedNodes();
                    visitedNodes.clear();
                    neighborNodes.clear();
                    try {
                        searchShortestPath(new Coordinate(startY, startX));
                        currentShortestPath = visitedNodes.get(new Coordinate(endY, endX));
                        if (shortestPath - currentShortestPath >= savings) count++;
                    } catch (NullPointerException e) {
                        System.out.println(y + " - " + x);
                    }
                    maze[y][x] = '#';
                }
            }
        }
        System.out.println("Savings: " + count);
    }

    private void searchShortestPath(Coordinate c) {
        Coordinate c_next;
        int pathLength = 0;
        int pathLengthNext;
        visitedNodes.put(c, pathLength);
        unvisitedNodes.remove(c);
        addNeighborNodes(c, pathLength);
        do {
            c_next = pickNeighborWithMinimumDistanceValue();
            pathLengthNext = neighborNodes.remove(c_next);
            c = c_next;
            pathLength = pathLengthNext;
            visitedNodes.put(c, pathLength);
            addNeighborNodes(c, pathLength);
        } while (!neighborNodes.isEmpty());
    }

    private Coordinate pickNeighborWithMinimumDistanceValue() {
        Coordinate tmpCoordinate = null;
        int tmpPathLength = 0;
        for (Coordinate c : neighborNodes.keySet()) {
            int pathLength = neighborNodes.get(c);
            if (tmpCoordinate==null || tmpPathLength>pathLength) {
                tmpCoordinate = c;
                tmpPathLength = pathLength;
            }
        }
        return tmpCoordinate;
    }

    private void addNeighborNodes(Coordinate c, int pathLength) {
        int y = c.y;
        int x = c.x;
        // NORTH
        Coordinate k = new Coordinate(y - 1, x);
        if (maze[y-1][x] != '#' && !visitedNodes.containsKey(k)) {
            neighborNodes.put(k, pathLength + 1);
            unvisitedNodes.remove(k);
        }
        // EAST
        k = new Coordinate(y, x + 1);
        if (maze[y][x+1] != '#' && !visitedNodes.containsKey(k)) {
            neighborNodes.put(k, pathLength + 1);
            unvisitedNodes.remove(k);
        }
        // WEST
        k = new Coordinate(y, x - 1);
        if (maze[y][x-1] != '#' && !visitedNodes.containsKey(k)) {
            neighborNodes.put(k, pathLength + 1);
            unvisitedNodes.remove(k);
        }
        // SOUTH
        k = new Coordinate(y + 1, x);
        if (maze[y+1][x] != '#' && !visitedNodes.containsKey(k)) {
            neighborNodes.put(k, pathLength + 1);
            unvisitedNodes.remove(k);
        }
    }

    private void initializeUnvisitedNodes() {
        unvisitedNodes.clear();
        for (int y = 1; y<size-1; y++) {
            for (int x = 1; x<size-1; x++) {
                if (maze[y][x] != '#') {
                    unvisitedNodes.put(new Coordinate(y, x), -1);
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
                if (row < size) {
                    maze[row] = line.toCharArray();
                    if (line.indexOf('S')>0) {
                        startX = line.indexOf('S');
                        startY = row;
                    } else if (line.indexOf('E')>0) {
                        endX = line.indexOf('E');
                        endY = row;
                    }
                    row++;
                }
                // read next line
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class Coordinate {
        int x, y;

        public Coordinate(int y, int x) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object o) {
            if (o == null || getClass() != o.getClass()) return false;
            Coordinate that = (Coordinate) o;
            return x == that.x && y == that.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }

        public Coordinate minus(Coordinate c) {
            return new Coordinate(y-c.y, x-c.x);
        }
    }

    public static void main(String[] args) { new Day20_RaceCondition(); }
}
