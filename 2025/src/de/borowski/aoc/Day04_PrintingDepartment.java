package de.borowski.aoc;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Day04_PrintingDepartment {

    public static final int SIZE = 135;
    public static final int[][] warehouse = new int[SIZE + 2][SIZE + 2];
    public static final List<Coordinate> coord = new ArrayList<>();

    public static void main(String[] args) {
        BufferedReader reader;

        try {
            // reader = new BufferedReader(new FileReader("/home/christoph/Projects/IdeaProjects/AdventOfCode/2025/input/input_day04_testset.txt"));
            reader = new BufferedReader(new FileReader("/home/christoph/Projects/IdeaProjects/AdventOfCode/2025/input/input_day04.txt"));
            String line = reader.readLine();

            int y = 1, x = 1;
            while (line != null) {
                // System.out.println(line);
                char[] chars = line.toCharArray();
                for (char c : chars) {
                    warehouse[x][y] = (c == '@') ? 1 : 0;
                    y++;
                }
                x++;
                y = 1;
                // read next line
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        int sum = 0;

        // Part 1
		/*for (int x=1; x<SIZE+1; x++) {
			for (int y=1; y<SIZE+1; y++) {
				if (lager[x][y]==1) {
					if (getRollsCount(x, y) < 4) sum++;
				}
			}
		}*/

        // Part 2
        do {
            coord.clear();
            for (int x = 1; x < SIZE + 1; x++) {
                for (int y = 1; y < SIZE + 1; y++) {
                    if (warehouse[x][y] == 1) {
                        if (getRollsCount(x, y) < 4) {
                            sum++;
                            coord.add(new Coordinate(x, y));
                        }
                    }
                }
            }
            removeIdentifiedRolls();
        } while (!coord.isEmpty());

        System.out.println("--------------------------------");
        System.out.println(sum);
    }

    private static void removeIdentifiedRolls() {
        for (Coordinate c : coord) {
            warehouse[c.x][c.y] = 0;
        }
    }

    private static int getRollsCount(int x, int y) {
        return warehouse[x - 1][y] + warehouse[x - 1][y - 1] + warehouse[x][y - 1] + warehouse[x + 1][y - 1]
                + warehouse[x + 1][y] + warehouse[x + 1][y + 1] + warehouse[x][y + 1] + warehouse[x - 1][y + 1];
    }

    public record Coordinate(int x, int y) {
    }

}
