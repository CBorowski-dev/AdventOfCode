package de.borowski.aoc;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Day09_MovieTheater_Part2 {

    public static final int TILES_COUNT = 496; // 8 or 496
    public static final int[][] tiles = new int[TILES_COUNT][2];
    public static final double SCALE = Math.pow(10, 3);

    public static void main(String[] args) {
        BufferedReader reader;

        try {
            // reader = new BufferedReader(new FileReader("/home/christoph/Projects/IdeaProjects/AdventOfCode/2025/input/input_day09_testset.txt"));
            reader = new BufferedReader(new FileReader("/home/christoph/Projects/IdeaProjects/AdventOfCode/2025/input/input_day09.txt"));
            String line = reader.readLine();
            int i = 0;
            while (line != null) {
                String[] values = line.split(",");
                tiles[i][0] = Integer.parseInt(values[0]);
                tiles[i][1] = Integer.parseInt(values[1]);
                i++;
                // read next line
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("--------------------------------");
        System.out.println("Max area : " + getMaxArea());
    }

    private static long getMaxArea() {
        // Part 2
        long maxArea = 0;
        for (int i = 0; i < TILES_COUNT - 1; i++) {
            for (int j = i + 1; j < TILES_COUNT; j++) {
                long area = ((long) Math.abs(tiles[j][0] - tiles[i][0]) + 1) * ((long) Math.abs(tiles[j][1] - tiles[i][1]) + 1);
                if (maxArea < area) {
                    if (isRectangleInside(tiles[i][0], tiles[i][1], tiles[j][0], tiles[j][1])) {
                        maxArea = area;
                        System.out.println("==> " + maxArea);
                    }
                }
            }
        }
        return maxArea;
    }

    private static boolean isRectangleInside(int x1, int y1, int x2, int y2) {
        int xStart = x1, xEnd = x2, yStart = y1, yEnd = y2;
        // Sort corners, so start is at the top left corner and end is at the bottom right corner.
        if (x1 > x2) {
            xStart = x2;
            xEnd = x1;
        }
        if (y1 > y2) {
            yStart = y2;
            yEnd = y1;
        }
        // Special treatment because of the shape
        if (yStart < 50218 && yEnd > 48563 && xStart < 94880) return false;

        // Check corners first
        if (isCoordinateOutside(xStart, yStart) || isCoordinateOutside(xStart, yEnd)
                || isCoordinateOutside(xEnd, yStart) || isCoordinateOutside(xEnd, yEnd)) return false;

        // Check remaining coordinates on the edges
        for (int x = xStart + 1; x < xEnd; x++) {
            if (isCoordinateOutside(x, yStart) || (yStart != yEnd && isCoordinateOutside(x, yEnd))) return false;
        }
        for (int y = yStart + 1; y < yEnd; y++) {
            if (isCoordinateOutside(xStart, y) || (xStart != xEnd && isCoordinateOutside(xEnd, y))) return false;
        }
        return true;
    }

    private static boolean isCoordinateOutside(int x, int y) {
        double gesamtwinkel = 0.0d;
        double alpha;
        for (int i = 0; i < TILES_COUNT; i++) {
            // compute two vectors
            int v1_x = tiles[i][0] - x;
            int v1_y = tiles[i][1] - y;
            if (v1_x == 0 && v1_y == 0) return false;
            int i_next = (i + 1) == TILES_COUNT ? 0 : i + 1;
            int v2_x = tiles[i_next][0] - x;
            int v2_y = tiles[i_next][1] - y;
            if (v2_x == 0 && v2_y == 0) return false;

            // compute "gerichteter Winkel"
            alpha = Math.atan2((long) v1_x * v2_y - (long) v1_y * v2_x, (long) v1_x * v2_x + (long) v1_y * v2_y);
            gesamtwinkel += alpha;
        }
        return gesamtwinkel > -0.00001 && gesamtwinkel < 0.00001;
    }

}
