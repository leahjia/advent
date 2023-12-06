import java.io.*;
import java.util.*;

public class Day6 {
    static Map<Integer, Integer> record;
    static String[] timeSplit;
    static String[] distSplit;
    
    public static void main(String[] args) throws FileNotFoundException {
        long startTime = System.currentTimeMillis();
        Scanner in = new Scanner(new FileReader("input/day6.txt"));
        processInput(in);
        System.out.println("Part I  633080  : " + part1());
        System.out.println("Part II 20048741: " + part2());
        System.out.println("Execution time: " + (System.currentTimeMillis() - startTime) + "ms");
    }
    
    private static long part1() {
        int prodOfWays = 1;
        for (int time : record.keySet()) {
            int currWays = 0;
            for (int t = 0; t <= time; t++) {
                if ((time - t) * t > record.get(time)) {
                    currWays++;
                }
            }
            prodOfWays *= currWays;
        }
        return prodOfWays;
    }
    
    private static long part2() {
        StringBuilder timeStr = new StringBuilder();
        StringBuilder distStr = new StringBuilder();
        for (int i = 1; i < timeSplit.length; i++) {
            timeStr.append(timeSplit[i]);
            distStr.append(distSplit[i]);
        }
        long time = Long.parseLong(timeStr.toString());
        long dist = Long.parseLong(distStr.toString());
        long ways = 0;
        for (long t = 0; t <= time; t++) {
            if ((time - t) * t > dist) {
                ways++;
            }
        }
        return ways;
    }
    
    private static void processInput(Scanner in) {
        timeSplit = in.nextLine().trim().split("\\s+");
        distSplit = in.nextLine().trim().split("\\s+");
        record = new HashMap<>();
        for (int i = 1; i < timeSplit.length; i++) {
            record.put(Integer.parseInt(timeSplit[i]), Integer.parseInt(distSplit[i]));
        }
    }
    
}