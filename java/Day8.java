import java.io.*;
import java.util.*;

public class Day8 {
    static Map<String, String[]> paths = new HashMap<>();
    static char[] direct;
    
    public static void main(String[] args) throws FileNotFoundException {
        long startTime = System.currentTimeMillis();
        Scanner in = new Scanner(new FileReader("input/day8.txt"));
        Queue<String> startingPoints = processInput(in);
        System.out.println("Part I           17141:" + singleReach());
        System.out.println("Part II 10818234074807:" + bfs(startingPoints));
        System.out.println("Execution time: " + (System.currentTimeMillis() - startTime) + "ms");
    }
    
    private static long singleReach() {
        int step = 0;
        String curr = "AAA";
        int i = 0;
        while (!curr.equals("ZZZ")) {
            step++;
            curr = paths.get(curr)[direct[i++] == 'R' ? 1 : 0];
            if (i == direct.length) {
                i = 0;
            }
        }
        return step;
    }
    
    private static long bfs(Queue<String> startingPoints) {
        long steps = 1;
        long currStep = 0;
        int idx = 0;
        while (!startingPoints.isEmpty()) {
            int iter = startingPoints.size();
            char dr = direct[idx++];
            for (int i = 0; i < iter; i++) {
                String curr = startingPoints.poll();
                if (curr.endsWith("Z")) {
                    steps = lcm(steps, currStep);
                } else {
                    startingPoints.offer(paths.get(curr)[dr == 'R' ? 1 : 0]);
                }
            }
            currStep++;
            if (idx == direct.length) {
                idx = 0;
            }
        }
        return steps;
    }
    
    private static long lcm(long a, long b) {
        return a * b / gcd(a, b);
    }
    
    private static long gcd(long a, long b) {
        while (b != 0) {
            long temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }
    
    private static Queue<String> processInput(Scanner in) {
        Queue<String> startingPoints = new LinkedList<>();
        direct = in.nextLine().toCharArray();
        in.nextLine();
        while (in.hasNextLine()) {
            String[] line = in.nextLine().split(" = ");
            String key = line[0];
            if (key.endsWith("A")) {
                startingPoints.offer(key);
            }
            String[] pair = line[1].substring(1, line[1].length() - 1).split(", ");
            paths.put(key, pair);
        }
        return startingPoints;
    }
    
}