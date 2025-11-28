package de.borowski.aoc;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Day17_ChronospatialComputer {

    //String filename = "/home/christoph/Projects/IdeaProjects/AdventOfCode/2024/input/input_day17_testset.txt";
    String filename = "/home/christoph/Projects/IdeaProjects/AdventOfCode/2024/input/input_day17.txt";

    long regA = 0;
    long regB = 0;
    long regC = 0;
    int[] program;

    public Day17_ChronospatialComputer() {
        readInput();
        System.out.println("--------------------------------");
        runProgramm();
    }

    private void runProgramm() {
        for (int i=0; i<program.length; i += 2) {
            int opcode = program[i];
            int operand = program[i+1];

            switch (opcode) {
                case 0: // adv
                    regA /= (long) Math.pow(2, getComboOperandValue(operand));
                    break;
                case 1: // bxl
                    regB ^= operand;
                    break;
                case 2: // bst
                    regB = getComboOperandValue(operand) % 8;
                    break;
                case 3: // jnz
                    if (regA != 0) i = operand - 2;
                    break;
                case 4: // bxc
                    regB ^= regC;
                    break;
                case 5: // out
                    System.out.print(getComboOperandValue(operand) % 8 + ",");
                    break;
                case 6: // bdv
                    long denominator = (long) Math.pow(2, getComboOperandValue(operand));
                    regB = regA / denominator;
                    break;
                case 7: // cdv
                    regC = regA / (long) Math.pow(2, getComboOperandValue(operand));
                    break;
                }
            }
        }

    private long getComboOperandValue(int operand) {
        if (operand <= 3) return operand;
        switch (operand) {
            case 4: return regA;
            case 5: return regB;
            case 6: return regC;
        }
        return -1;
    }

    private void readInput() {
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(filename));
            int row = 0;
            String line = reader.readLine();
            while (line != null) {
                if (line.startsWith("Register")) {
                    String[] values = line.split(" ");
                    if (values[1].equals("A:")) regA = Long.parseLong(values[2]);
                    else if (values[1].equals("B:")) regB = Long.parseLong(values[2]);
                    else regC = Long.parseLong(values[2]);
                } else if (line.startsWith("Program:")) {
                    line = line.substring(9);
                    String[] values = line.split(",");
                    program = new int[values.length];
                    int i=0;
                    for (String v: values) {
                        program[i] = Integer.parseInt(values[i]);
                        i++;
                    }
                }
                // read next line
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) { new Day17_ChronospatialComputer(); }
}
