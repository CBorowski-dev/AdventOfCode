package de.borowski.aoc;

import java.util.ArrayList;
import java.util.List;

public class Day02_GiftShop {

    // private static String idString = "11-22,95-115,998-1012,1188511880-1188511890,222220-222224,1698522-1698528,446443-446449,38593856-38593862,565653-565659,824824821-824824827,2121212118-2121212124";
    private static String idString = "9595822750-9596086139,1957-2424,88663-137581,48152-65638,12354817-12385558,435647-489419,518494-609540,2459-3699,646671-688518,195-245,295420-352048,346-514,8686839668-8686892985,51798991-51835611,8766267-8977105,2-17,967351-995831,6184891-6331321,6161577722-6161678622,912862710-913019953,6550936-6625232,4767634976-4767662856,2122995-2257010,1194-1754,779-1160,22-38,4961-6948,39-53,102-120,169741-245433,92902394-92956787,531-721,64-101,15596-20965,774184-943987,8395-11781,30178-47948,94338815-94398813";

    private static final List<ID_Range> ids = new ArrayList<>();

    public static void main(String[] args) {

        // split input
        String[] idRanges = idString.split(",");
        for (String idRange : idRanges) {
            String[] idsStrings = idRange.split("-");
            ids.add(new ID_Range(Long.parseLong(idsStrings[0]), Long.parseLong(idsStrings[1])));
        }

        // Search invalid IDs
        long sum = 0;
        for (ID_Range idr : ids) {
            for (long x = idr.startID; x <= idr.endID; x++) {
                // sum += getInvalidID(x);
                sum += getInvalidID2(x);
            }
        }
        System.out.println("--------------------------------");
        System.out.println(sum);
    }

    // for Part 1
    private static long getInvalidID(long x) {
        String x_String = Long.toString(x);
        int l = x_String.length();
        if (l % 2 == 0) {
            String left = x_String.substring(0, l / 2);
            String right = x_String.substring(l / 2);
            return (left.equals(right) ? x : 0);
        } else return 0;
    }

    // for Part 2
    private static long getInvalidID2(long x) {
        String x_String = Long.toString(x);
        int l = x_String.length();
        for (int i = l / 2; i >= 1; i--) {
            if (l % i == 0) {
                int partCount = l / i;
                String part = x_String.substring(0, i);
                boolean found = true;
                for (int y = 1; y < partCount; y++) {
                    if (!x_String.substring(i * y, (i * y) + i).equals(part)) found = false;
                }
                if (found) return x;
            }
        }
        return 0;
    }

    private record ID_Range(long startID, long endID) {
    }

}
