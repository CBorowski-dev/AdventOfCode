package de.borowski.aoc;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Day16_ReindeerMaze_Part2 {
    private final int size =15; // 15 17 141
    String filename = "/home/christoph/Projects/IdeaProjects/AdventOfCode/2024/input/input_day16_testset.txt";
    //String filename = "/home/christoph/Projects/IdeaProjects/AdventOfCode/2024/input/input_day16_testset2.txt";
    //String filename = "/home/christoph/Projects/IdeaProjects/AdventOfCode/2024/input/input_day16.txt";

    private final Map<Coordinate, Values> visitedNodes = new HashMap<>();
    private final Map<Coordinate, Values> unvisitedNodes = new HashMap<>();
    private final Map<Coordinate, Values> neighborNodes = new HashMap<>();
    private final char[][] maze = new char[size][size];
    private int startX = 0;
    private int startY = 0;

    enum Direction {
        NORTH, EAST, SOUTH, WEST;
    }

    public Day16_ReindeerMaze_Part2() {
        readInput();
        initializeUnvisitedNodes();
        searchShortestPath(new Coordinate(startY, startX), Direction.EAST);
        System.out.println("--------------------------------");
        printMaze();
        System.out.println(visitedNodes.get(new Coordinate(1, size - 2)).pathLength);
    }

    private void searchShortestPath(Coordinate c, Direction d) {
        Coordinate c_next;
        Values v = new Values(0, d);
        Values v_next;
        visitedNodes.put(c, v);
        do {
            addNeighborNodes(c, v);
            c_next = pickNeighborWithMinimumDistanceValue();
            v_next = neighborNodes.remove(c_next);
            c = c_next;
            v = v_next;
            visitedNodes.put(c, v);
        } while (!unvisitedNodes.isEmpty());
    }

    private Coordinate pickNeighborWithMinimumDistanceValue() {
        Coordinate tmpCoordinate = null;
        int tmpPathLength = 0;
        for (Coordinate c : neighborNodes.keySet()) {
            Values v = neighborNodes.get(c);
            if (tmpCoordinate==null || tmpPathLength>v.pathLength) {
                tmpCoordinate = c;
                tmpPathLength = v.pathLength;
            }
        }
        return tmpCoordinate;
    }

    private void addNeighborNodes(Coordinate c, Values v) {
        int y = c.y;
        int x = c.x;
        // NORTH
        Coordinate k = new Coordinate(y - 1, x);
        if (maze[y-1][x] != '#' && !visitedNodes.containsKey(k)) {
             neighborNodes.put(k, new Values(v.pathLength + (v.d.equals(Direction.NORTH) ? 1 : 1001), Direction.NORTH));
            unvisitedNodes.remove(k);
        }
        // EAST
        k = new Coordinate(y, x + 1);
        if (maze[y][x+1] != '#' && !visitedNodes.containsKey(k)) {
            neighborNodes.put(k, new Values(v.pathLength + (v.d.equals(Direction.EAST) ? 1 : 1001), Direction.EAST));
            unvisitedNodes.remove(k);
        }
        // WEST
        k = new Coordinate(y, x - 1);
        if (maze[y][x-1] != '#' && !visitedNodes.containsKey(k)) {
            neighborNodes.put(k, new Values(v.pathLength + (v.d.equals(Direction.WEST) ? 1 : 1001), Direction.WEST));
            unvisitedNodes.remove(k);
        }
        // SOUTH
        k = new Coordinate(y + 1, x);
        if (maze[y+1][x] != '#' && !visitedNodes.containsKey(k)) {
            neighborNodes.put(k, new Values(v.pathLength + (v.d.equals(Direction.SOUTH) ? 1 : 1001), Direction.SOUTH));
            unvisitedNodes.remove(k);
        }
    }

    private void initializeUnvisitedNodes() {
        for (int y = 1; y<size-1; y++) {
            for (int x = 1; x<size-1; x++) {
                if (maze[y][x] == '.' || maze[y][x] == 'E') {
                    unvisitedNodes.put(new Coordinate(y, x), new Values(-1, null));
                }
            }
        }
    }

    private void printMaze() {
        for (int y = 1; y<size-1; y++) {
            for (int x = 1; x<size-1; x++) {
                Values v = visitedNodes.get(new Coordinate(y, x));
                if ((maze[y][x] == '.' || maze[y][x] == 'E') && v!=null) {
                    System.out.print(v.pathLength + " |");
                } else {
                    System.out.print("     |");
                }
            }
            System.out.println();
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

    private static class Values {
        int pathLength;
        Direction d;

        public Values(int pathLength, Direction d) {
            this.pathLength = pathLength;
            this.d = d;
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

    public static void main(String[] args) { new Day16_ReindeerMaze_Part2(); }
    // 87380 too high
}