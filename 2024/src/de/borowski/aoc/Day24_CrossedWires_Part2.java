package de.borowski.aoc;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Day24_CrossedWires_Part2 {

    //String filename = "/home/christoph/Projects/IdeaProjects/AdventOfCode/2024/input/input_day24_testset.txt";
    //String filename = "/home/christoph/Projects/IdeaProjects/AdventOfCode/2024/input/input_day24_testset2.txt";
    String filename = "/home/christoph/Projects/IdeaProjects/AdventOfCode/2024/input/input_day24.txt";

    private final int zCount = 46;
    private final List<Gate> gates = new ArrayList<>();
    private final List<Gate> gatesFinished = new ArrayList<>();
    private final Map<String, Byte> inputs= new HashMap<>();

    //  100110011011100110101010011010111111011001101 = 21127838400205 = X
    //  110011100110110111000110001001100100000011001 = 28371358894105 = Y
    // 1011010000010011101110000100100100011011100110 = 49499197294310 = Z (soll)
    // 1011001111010011101101100100100011111011100110 = 49430469426918 = Z (ist)
    //          1000000000000100000000000100000000000 Fehler

    /*
    z11 --> (x11 AND y11) --> (x11 xor y11)
    z16 --> x16 AND y16 --> x16 XOR y16
    z23
    z36
     */



    private enum GateType {
        AND, OR, XOR;
    }

    public Day24_CrossedWires_Part2() {
        readInput();
        printZEquations();
        System.out.println("--------------------------------");
        //System.out.println("Result: " + computeResult());
    }

    private void printZEquations() {
        for (int z=0; z<zCount; z++) {
            String out = "z" + ((z<10) ? "0"+z : z);
            for (Gate g: gates) {
                if (g.out.equals(out)) {
                    System.out.println(g.out + " = " + printInput(g.in1, g.out) + " " + g.type + " " + printInput(g.in2, g.out));
                }
            }
        }
    }

    private String printInput(String in, String out) {
        if (in.startsWith("x") || in.startsWith("y")) {
            /*int intNumber = Integer.parseInt(in.substring(1));
            int outNumber = Integer.parseInt(out.substring(1));
            if (intNumber>outNumber) System.out.println(in + " --> " + out);*/
            return in;
        }
        for (Gate g: gates) {
            if (g.out.equals(in)) {
                return "(" + printInput(g.in1, out) + " " + g.type + " " + printInput(g.in2, out) + ")";
            }
        }
        return "";
    }

    private long computeResult() {
        int size;
        do {
            size = gates.size();
            for (int i = size - 1; i >= 0; i--) {
                if (gates.get(i).compute()) gatesFinished.add(gates.remove(i));
            }
        } while (!gates.isEmpty());
        System.out.println("X: " + computeXValue());
        System.out.println("Y: " + computeYValue());
        return computeZValue();
    }

    private long computeXValue() {
        long value = 0;
        for (String k: inputs.keySet()) {
            if (k.startsWith("x") && inputs.get(k)==1) {
                byte bitNr = Byte.parseByte(k.substring(1));
                value += Math.pow(2, bitNr);
            }
        }
        return value;
    }

    private long computeYValue() {
        long value = 0;
        for (String k: inputs.keySet()) {
            if (k.startsWith("y") && inputs.get(k)==1) {
                byte bitNr = Byte.parseByte(k.substring(1));
                value += Math.pow(2, bitNr);
            }
        }
        return value;
    }

    private long computeZValue() {
        long value = 0;
        for (Gate g: gatesFinished) {
            if (g.isZBit() && g.isSet()) {
                byte bitNr = g.getBitNumber();
                value += Math.pow(2, bitNr);
            }
        }
        return value;
    }

    private void readInput() {
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(filename));
            int row = 0;
            String line = reader.readLine();
            while (line != null) {
                if (line.length()==6) {
                    inputs.put(line.substring(0, 3), Byte.parseByte(line.substring(line.length()-1)));
                } else if (line.length()>6) {
                    String[] values = line.split(" ");
                    gates.add(new Gate(values[0], values[2], values[4],
                            (values[1].equals("AND")) ? GateType.AND : (values[1].equals("OR")) ? GateType.OR : GateType.XOR)
                    );
                }
                // read next line
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class Gate {
        String in1, in2, out;
        GateType type;

        public Gate(String in1, String in2, String out, GateType type) {
            this.in1 = in1;
            this.in2 = in2;
            this.out = out;
            this.type = type;
        }

        public boolean isZBit() {
            return out.startsWith("z");
        }

        public byte getBitNumber() {
            return Byte.parseByte(out.substring(1));
        }

        public boolean isSet() {
            return inputs.get(out)==1 ? true : false;
        }

        public boolean compute() {
            Byte b1 = inputs.get(in1);
            Byte b2 = inputs.get(in2);
            if (b1 != null && b2!=null) {
                switch (type) {
                    case GateType.AND:
                        inputs.put(out, Byte.valueOf((byte)(b1 & b2)));
                        break;
                    case GateType.OR:
                        inputs.put(out, Byte.valueOf((byte)(b1 | b2)));
                        break;
                    case GateType.XOR:
                        inputs.put(out, Byte.valueOf((byte)(b1 ^ b2)));
                }
                return true;
            }
            return false;
        }

        @Override
        public boolean equals(Object o) {
            if (o == null || getClass() != o.getClass()) return false;
            Gate gate = (Gate) o;
            return Objects.equals(in1, gate.in1) && Objects.equals(in2, gate.in2) && Objects.equals(out, gate.out) && type == gate.type;
        }

        @Override
        public int hashCode() {
            return Objects.hash(in1, in2, out, type);
        }
    }

    public static void main(String[] args) { new Day24_CrossedWires_Part2(); }
}
