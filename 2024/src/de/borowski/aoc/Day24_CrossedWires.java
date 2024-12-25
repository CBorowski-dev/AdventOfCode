package de.borowski.aoc;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Day24_CrossedWires {

    //String filename = "/home/christoph/Projects/IdeaProjects/AdventOfCode/2024/input/input_day24_testset.txt";
    //String filename = "/home/christoph/Projects/IdeaProjects/AdventOfCode/2024/input/input_day24_testset2.txt";
    String filename = "/home/christoph/Projects/IdeaProjects/AdventOfCode/2024/input/input_day24.txt";

    private final List<Gate> gates = new ArrayList<>();
    private final List<Gate> gatesFinished = new ArrayList<>();
    private final Map<String, Byte> inputs= new HashMap<>();

    private enum GateType {
        AND, OR, XOR;
    }

    public Day24_CrossedWires() {
        readInput();
        System.out.println("--------------------------------");
        System.out.println("Result: " + computeResult());
    }

    private long computeResult() {
        int size;
        do {
            size = gates.size();
            for (int i = size - 1; i >= 0; i--) {
                if (gates.get(i).compute()) gatesFinished.add(gates.remove(i));
            }
        } while (!gates.isEmpty());
        return computeZValue();
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
        String i1, i2, o2;
        GateType type;

        public Gate(String i1, String i2, String o2, GateType type) {
            this.i1 = i1;
            this.i2 = i2;
            this.o2 = o2;
            this.type = type;
        }

        public boolean isZBit() {
            return o2.startsWith("z");
        }

        public byte getBitNumber() {
            return Byte.parseByte(o2.substring(1));
        }

        public boolean isSet() {
            return inputs.get(o2)==1 ? true : false;
        }

        public boolean compute() {
            Byte b1 = inputs.get(i1);
            Byte b2 = inputs.get(i2);
            if (b1 != null && b2!=null) {
                switch (type) {
                    case GateType.AND:
                        inputs.put(o2, Byte.valueOf((byte)(b1 & b2)));
                        break;
                    case GateType.OR:
                        inputs.put(o2, Byte.valueOf((byte)(b1 | b2)));
                        break;
                    case GateType.XOR:
                        inputs.put(o2, Byte.valueOf((byte)(b1 ^ b2)));
                }
                return true;
            }
            return false;
        }

        @Override
        public boolean equals(Object o) {
            if (o == null || getClass() != o.getClass()) return false;
            Gate gate = (Gate) o;
            return Objects.equals(i1, gate.i1) && Objects.equals(i2, gate.i2) && Objects.equals(o2, gate.o2) && type == gate.type;
        }

        @Override
        public int hashCode() {
            return Objects.hash(i1, i2, o2, type);
        }
    }

    public static void main(String[] args) { new Day24_CrossedWires(); }
}
