package de.borowski.aoc;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Day18_RAMRun {
    private final int size =71; // 7 71
    private final int byteCount = 2882; // 12 1024
    //String filename = "/home/christoph/Projects/IdeaProjects/AdventOfCode/2024/input/input_day18_testset.txt";
    String filename = "/home/christoph/Projects/IdeaProjects/AdventOfCode/2024/input/input_day18.txt";

    private final Map<Coordinate, Integer> visitedNodes = new HashMap<>();
    private final Map<Coordinate, Integer> neighborNodes = new HashMap<>();
    private final char[][] maze = new char[size][size];
    private int startX = 0;
    private int startY = 0;

    public Day18_RAMRun() {
        initializeMaze();
        readInput();
        searchShortestPath(new Coordinate(startY, startX));
        System.out.println("--------------------------------");
        Integer pathLength = visitedNodes.get(new Coordinate(size - 1, size - 1));
        System.out.println((pathLength!=null) ? pathLength : "Path not found");
    }

    private void searchShortestPath(Coordinate c) {
        int pathLength = 0;
        visitedNodes.put(c, pathLength);
        addNeighborNodes(c, pathLength);
        do {
            c = pickNeighborWithMinimumDistanceValue();
            if (c == null) return;
            pathLength = neighborNodes.remove(c);
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
        if (y-1>=0 && maze[y-1][x] != '#' && !visitedNodes.containsKey(k)) {
            neighborNodes.put(k, pathLength + 1);
        }
        // EAST
        k = new Coordinate(y, x + 1);
        if (x+1<size && maze[y][x+1] != '#' && !visitedNodes.containsKey(k)) {
            neighborNodes.put(k, pathLength + 1);
        }
        // WEST
        k = new Coordinate(y, x - 1);
        if (x-1>=0 && maze[y][x-1] != '#' && !visitedNodes.containsKey(k)) {
            neighborNodes.put(k, pathLength + 1);
        }
        // SOUTH
        k = new Coordinate(y + 1, x);
        if (y+1<size && maze[y+1][x] != '#' && !visitedNodes.containsKey(k)) {
            neighborNodes.put(k, pathLength + 1);
        }
    }

    private void initializeMaze() {
        for (int y = 0; y<size; y++) {
            for (int x = 0; x<size; x++) {
                maze[y][x] = '.';
            }
        }
    }

    private void readInput() {
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(filename));
            int count = 0;
            String line = reader.readLine();
            while (line != null && count<byteCount) {
                String[] values = line.split(",");
                int y = Integer.parseInt(values[1]);
                int x = Integer.parseInt(values[0]);
                maze[y][x] = '#';
                count++;
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

    public static void main(String[] args) { new Day18_RAMRun(); }
}
