package de.borowski.aoc;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Day10_HoofIt {
    int size =55;
    //String filename = "/home/christoph/Projects/IdeaProjects/AdventOfCode/2024/input/input_day10_testset.txt";
    String filename = "/home/christoph/Projects/IdeaProjects/AdventOfCode/2024/input/input_day10.txt";

    private final int[][] map = new int[size][size];
    private List<Coordinate> visited = new ArrayList<>();

    enum Direction {
        NORTH(-1, 0),
        WEST(0, -1),
        SOUTH(1, 0),
        EAST(0, 1);

        public int x, y;

        Direction(int y, int x) {
            this.y = y;
            this.x = x;
        }
    }

    public Day10_HoofIt() {
        readInput();
        System.out.println("--------------------------------");
        System.out.println(computeSumOfScores());
    }

    private int computeSumOfScores() {
        int sum = 0;
        for (int y = 0; y< map.length; y++) {
            for (int x = 0; x< map.length; x++) {
                if (map[y][x]==0) {
                    visited.clear();
                    sum += computeScore(new Coordinate(y, x));
                }
            }
        }
        return sum;
    }

    private int computeScore(Coordinate c) {
        int hight = map[c.y][c.x];
        if (hight == 9 && !visited.contains(c)) { // comment out "&& !visited.contains(c)" for part 2
            visited.add(c);                       // comment out "visited.add(c);" for part 2
            return 1;
        }
        int score = 0;
        Coordinate cn = c.add(Direction.NORTH);
        if (cn.y >= 0 && map[cn.y][cn.x] == hight+1) score += computeScore(cn);
        cn = c.add(Direction.EAST);
        if (cn.x < size && map[cn.y][cn.x] == hight+1) score += computeScore(cn);
        cn = c.add(Direction.SOUTH);
        if (cn.y < size && map[cn.y][cn.x] == hight+1) score += computeScore(cn);
        cn = c.add(Direction.WEST);
        if (cn.x >= 0 && map[cn.y][cn.x] == hight+1) score += computeScore(cn);

        return score;
    }

    private void readInput() {
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(filename));
            int row = 0;
            String line = reader.readLine();
            while (line != null) {
                map[row++] = convertToIntegerArray(line);
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

    public static class Coordinate {
        public int y, x;

        public Coordinate(int y, int x) {
            this.y = y;
            this.x = x;
        }

        @Override
        public boolean equals(Object o) {
            if (o == null || getClass() != o.getClass()) return false;
            Day10_HoofIt.Coordinate that = (Day10_HoofIt.Coordinate) o;
            return y == that.y && x == that.x;
        }

        @Override
        public int hashCode() {
            return Objects.hash(y, x);
        }

        public Coordinate add(Direction d) {
            return new Coordinate(y+d.y, x+d.x);
        }
    }

    public static void main(String[] args) {
        new Day10_HoofIt();
    }
}
