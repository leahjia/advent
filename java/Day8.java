import java.io.*;
import java.math.BigInteger;
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
    static Map<String, BigInteger> steps = new HashMap<>();
    
    private static BigInteger part2() {
        BigInteger step = BigInteger.ZERO;
        int idx = 0;
        while (!startingPoints.isEmpty()) {
            int iter = startingPoints.size();
            char dr = direct[idx++];
            int count = 0;
            for (int i = 0; i < iter; i++) {
                String curr = startingPoints.poll();
                // System.out.println("curr " + curr);
                if (curr.endsWith("Z")) {
                    steps.put(curr, step);
                } else {
                    String next = map.get(curr)[dr == 'R' ? 1 : 0];
                    startingPoints.offer(next);
                }
            }
            step = step.add(BigInteger.ONE);
            // System.out.println(step);
            if (idx == direct.length) {
                idx = 0;
            }
        }
        System.out.println(steps);
        //        BigInteger res = BigInteger.ONE;
        //        for (BigInteger val : steps.values()) {
        //            res = res.multiply(val);
        //        }
        return findLCM(); // [, 18953436893326990687332607]
    }
    
    private static BigInteger gcd(BigInteger a, BigInteger b) {
        while (!b.equals(BigInteger.ZERO)) {
            BigInteger temp = new BigInteger(b.toString());
            b = a.mod(b);
            a = temp;
        }
        return a;
    }
    
    private static BigInteger lcm(BigInteger a, BigInteger b) {
        return a.multiply(b.divide(gcd(a, b)));
    }
    
    private static BigInteger findLCM() {
        BigInteger res = BigInteger.ONE;
        for (BigInteger val : steps.values()) {
            res = lcm(res, val);
        }
        return res;
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