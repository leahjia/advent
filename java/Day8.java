import java.io.*;
import java.util.*;

public class Day8 {
    public static void main(String[] args) throws FileNotFoundException {
        long startTime = System.currentTimeMillis();
        Scanner in = new Scanner(new FileReader("input/day8.txt"));
        processInput(in);
        //System.out.println("Part I  ??: " + part1());
        System.out.println("Part II ??: " + part2());
        System.out.println("Execution time: " + (System.currentTimeMillis() - startTime) + "ms");
    }
    
    private static long part1() {
        int step = 0;
        String curr = "AAA";
        int i = 0;
        while (!curr.equals("ZZZ")) {
            step++;
            char dr = direct[i++];
            curr = map.get(curr)[dr == 'R' ? 1 : 0];
            if (i == direct.length) {
                i = 0;
            }
        }
        return step;
    }
    
    static Queue<String> startingPoints = new LinkedList<>();
    
    private static long part2() {
        long step = 0;
        int idx = 0;
        while (!startingPoints.isEmpty()) {
            int iter = startingPoints.size();
            char dr = direct[idx++];
            int count = 0;
            for (int i = 0; i < iter; i++) {
                String curr = startingPoints.poll();
                // System.out.println("curr " + curr);
                if (curr.endsWith("Z")) {
                    count++;
                }
                String next = map.get(curr)[dr == 'R' ? 1 : 0];
                startingPoints.offer(next);
            }
            if (count == iter) {
                System.out.println("what? " + startingPoints.size());
                return step;
            }
            step++;
            System.out.println(step);
            if (idx == direct.length) {
                idx = 0;
            }
        }
        return step;
    }
    
    static Map<String, String[]> map = new HashMap<>();
    static char[] direct;
    
    private static void processInput(Scanner in) {
        direct = in.nextLine().toCharArray();
        in.nextLine();
        while (in.hasNextLine()) {
            String[] line = in.nextLine().split(" = ");
            String key = line[0];
            if (key.endsWith("A")) {
                startingPoints.offer(key);
            }
            String[] pair = line[1].substring(1, line[1].length() - 1).split(", ");
            map.put(key, pair);
        }
    }
    
}