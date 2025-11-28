package de.borowski.aoc;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Day17_ChronospatialComputer_Part2 {

    // String filename = "/home/christoph/Projects/IdeaProjects/AdventOfCode/2024/input/input_day17_testset.txt";
    String filename = "/home/christoph/Projects/IdeaProjects/AdventOfCode/2024/input/input_day17.txt";

    long regA = 0;
    long regB = 0;
    long regC = 0;
    int[] program;

    // 2105355
    // 32101347331053713310136320105315201013072110533121101323
    // 1611535516111347171153711711136304115315041113070511533105111323720253557202134773025371730213636002531560021307610253316102132356035355560313475703537157031363440353154403130745035331450313233234535532341347333453713334136320345315203413072134533121341323163553551635134717355371173513630435531504351307053553310535132372265355722613477326537173261363602653156026130761265331612613235627535556271347572753715727136344275315442713074527533145271323
    // 32505355325

    StringBuffer output = new StringBuffer();

    public Day17_ChronospatialComputer_Part2() {
        readInput();
        System.out.println("--------------------------------");
        // for (long a=117440-10; a<117440+10; a++) {
        for (long a=900000000l; a<9000000000l; a++) {
            // if (a % 100000000 == 0) System.out.println("--> " + a);
            //System.out.print(a + " : ");
            regA = a;
            //if (runProgramm()) System.out.println(a);
            runProgramm();
            if (output.length() >= 10 && output.substring(0, 10).equals("24157516034")) {
                System.out.println(a + ": " + output);
            }
            /*if (a>=0 && a<8) {
                System.out.print(output);
            }*/
            output = new StringBuffer();
        }
    }

    private boolean runProgramm() {
        int idx=0;
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
                    long outValue = getComboOperandValue(operand) % 8;
                    //if (program[idx] != outValue) return false;
                    //idx++;
                    //System.out.print(outValue + ",");
                    output.append(outValue);
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
        return true;
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

    public static void main(String[] args) { new Day17_ChronospatialComputer_Part2(); }
    // Part 2: 35184372088832 - 281474976710655
}
