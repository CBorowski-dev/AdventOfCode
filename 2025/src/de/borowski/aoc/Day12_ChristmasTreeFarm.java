package de.borowski.aoc;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Day12_ChristmasTreeFarm {

    public static final List<Shape> SHAPES = new ArrayList<>();
    public static final List<Region> REGIONS = new ArrayList<>();

    public static void main(String[] args) {
        BufferedReader reader;

        try {
            // reader = new BufferedReader(new FileReader("/home/christoph/Projects/IdeaProjects/AdventOfCode/2025/input/input_day12_testset.txt"));
            reader = new BufferedReader(new FileReader("/home/christoph/Projects/IdeaProjects/AdventOfCode/2025/input/input_day12.txt"));

            String line;
            for (int n = 0; n < 6; n++) {
                Shape s = new Shape();
                for (int r = 0; r < 3; r++) {
                    char[] cLine = reader.readLine().toCharArray();
                    for (int i = 0; i < cLine.length; i++) s.shape[0][r][i] = (cLine[i] == '#') ? 1 : 0;
                }
                s.createRotationShapes();
                SHAPES.add(s);
                reader.readLine();
            }
            line = reader.readLine();
            while (line != null) {
                // System.out.println(line);
                // 12x5: 1 0 1 0 2 2
                String[] parts = line.split(": ");
                String[] size = parts[0].split("x");
                int xSize = Integer.parseInt(size[0]);
                int ySize = Integer.parseInt(size[1]);
                String[] quantitiesStr = parts[1].split(" ");
                List<Integer> quantities = new ArrayList<>();
                for (int i = 0; i < 6; i++) {
                    int count = Integer.parseInt(quantitiesStr[i]);
                    if (count > 0) {
                        for (int j = 0; j < count; j++) {
                            quantities.add(i);
                        }
                    }
                }
                REGIONS.add(new Region(xSize, ySize, quantities));
                // read next line
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // check Feasibility
        for (int i = REGIONS.size() - 1; i >= 0; i--) {
            if (!REGIONS.get(i).checkFeasibility(SHAPES)) REGIONS.remove(i);
        }

        System.out.println("--------------------------------");
        System.out.println(part1());
    }

    private static int part1() {
        int counter = 0;
        for (Region r : REGIONS) counter += r.checkPresentFit(SHAPES, 0);
        return counter;
    }

    public static class Shape {
        private int area;
        public int[][][] shape = new int[4][3][3];

        public void createRotationShapes() {
            for (int r = 0; r < 3; r++) {
                for (int c = 0; c < 3; c++) {
                    shape[1][2 - c][r] = shape[0][r][c];
                    shape[2][2 - r][2 - c] = shape[0][r][c];
                    shape[3][c][2 - r] = shape[0][r][c];
                }
            }
        }

        public int getArea() {
            if (area > 0) return area;
            for (int r = 0; r < 3; r++) {
                for (int c = 0; c < 3; c++) {
                    area += shape[0][r][c];
                }
            }
            return area;
        }
    }

    public static class Region {
        public int[][] region;
        public List<Integer> shapeQuantities;

        public Region(int xSize, int ySize, List<Integer> quantities) {
            region = new int[ySize][xSize];
            shapeQuantities = quantities;
        }

        public boolean checkFeasibility(List<Shape> shapes) {
            int counter = 0;
            for (int i : shapeQuantities) {
                counter += shapes.get(i).getArea();
            }
            return counter <= region.length * region[0].length;
        }

        public int checkPresentFit(List<Shape> shapes, int shapeId) {
            for (int rotation = 0; rotation < 4; rotation++) {
                for (int x = 0; x <= region.length - 3; x++) {
                    for (int y = 0; y <= region[0].length - 3; y++) {
                        // place shape in region
                        placeOrRemoveShapeInRegion(shapes.get(shapeQuantities.get(shapeId)).shape[rotation], x, y, true);
                        if (checkInegrity()) {
                            if (shapeId == shapeQuantities.size() - 1) return 1;
                            else {
                                if (checkPresentFit(shapes, shapeId + 1) == 1) return 1;
                                placeOrRemoveShapeInRegion(shapes.get(shapeQuantities.get(shapeId)).shape[rotation], x, y, false);
                            }
                        } else {
                            placeOrRemoveShapeInRegion(shapes.get(shapeQuantities.get(shapeId)).shape[rotation], x, y, false);
                        }
                    }
                }
            }
            return 0;
        }

        private boolean checkInegrity() {
            for (int x = 0; x < region.length; x++) {
                for (int y = 0; y < region[0].length; y++) {
                    if (region[x][y] > 1) return false;
                }
            }
            return true;
        }

        private void placeOrRemoveShapeInRegion(int[][] shape, int x, int y, boolean place) {
            for (int xt = x; xt < x + 3; xt++) {
                for (int yt = y; yt < y + 3; yt++) {
                    if (place)
                        region[xt][yt] += shape[xt - x][yt - y];
                    else
                        region[xt][yt] -= shape[xt - x][yt - y];
                }
            }
        }
    }

}
