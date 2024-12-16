package de.borowski.aoc;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Day16_ReindeerMaze {
    int size =141; // 17 141
    // String filename = "/home/christoph/Projects/IdeaProjects/AdventOfCode/2024/input/input_day16_testset.txt";
    //String filename = "/home/christoph/Projects/IdeaProjects/AdventOfCode/2024/input/input_day16_testset2.txt";
    String filename = "/home/christoph/Projects/IdeaProjects/AdventOfCode/2024/input/input_day16.txt";

    private final char[][] maze = new char[size][size];
    private final char[][] visited = new char[size][size];
    private int rX = 0;
    private int rY = 0;
    private int bestScore = -1;

    enum Direction {
        NORTH, EAST, SOUTH, WEST;
    }

    public Day16_ReindeerMaze() {
        readInput();
        initVisited();
        moveReindeer(rY, rX, Direction.EAST, 0);
        System.out.println("--------------------------------");
        System.out.println(bestScore);
    }

    private void moveReindeer(int y, int x, Direction d, int score) {
        if (maze[y][x]=='E') {
            printMaze(score);
            if (bestScore == -1 || bestScore > score) bestScore = score;
        }
        //int currentScore = -1;
        char c = maze[y][x];
        maze[y][x] = 'x';
        // NORTH
        if (!d.equals(Direction.SOUTH) && maze[y-1][x] != '#' && maze[y-1][x] != 'x') {
            moveReindeer(y-1, x, Direction.NORTH, score + ((d.equals(Direction.NORTH)) ? 1 : 1001));
            //if (currentScore == -1 || currentScore > score) currentScore = score;
        }
        // EAST
        if (!d.equals(Direction.WEST) && maze[y][x+1] != '#' && maze[y][x+1] != 'x') {
            moveReindeer(y, x+1, Direction.EAST, score + ((d.equals(Direction.EAST)) ? 1 : 1001));
            //if (currentScore == -1 || currentScore > score) currentScore = score;
        }
        // SOUTH
        if (!d.equals(Direction.NORTH) && maze[y+1][x] != '#' && maze[y+1][x] != 'x') {
            moveReindeer(y+1, x, Direction.SOUTH, score + ((d.equals(Direction.SOUTH)) ? 1 : 1001));
            // if (currentScore == -1 || currentScore > score) currentScore = score;
        }
        // WEST
        if (!d.equals(Direction.EAST) && maze[y][x-1] != '#' && maze[y][x-1] != 'x') {
            moveReindeer(y, x-1, Direction.WEST, score + ((d.equals(Direction.WEST)) ? 1 : 1001));
            //if (currentScore == -1 || currentScore > score) currentScore = score;
        }
        maze[y][x] = c;
        // return currentScore;
    }

    private void initVisited() {
        for (int y = 0; y< maze.length; y++) {
            for (int x = 0; x< maze.length; x++) {
                visited[y][x] = maze[y][x];
                if (visited[y][x]=='E' || visited[y][x]=='S') visited[y][x]='.';
            }
        }
    }

    private void printMaze(int score) {
        System.out.println("---------------" + score + "---------------------");
        for (int y = 0; y< maze.length; y++) {
            for (int x = 0; x< maze.length; x++) {
                System.out.print(maze[y][x]);
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
                        rX = line.indexOf('S');
                        rY = row;
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

    public static void main(String[] args) { new Day16_ReindeerMaze(); }
}
