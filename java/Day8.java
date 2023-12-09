import java.io.*;
import java.util.*;

public class Day8 {
    static Map<String, String[]> paths;
    static char[] direct;
    
    public static void main(String[] args) throws FileNotFoundException {
        long startTime = System.currentTimeMillis();
        Scanner in = new Scanner(new FileReader("input/day8.txt"));
        Queue<String> startingPoints = processInput(in);
        System.out.println("Part I           17141:" + singleReach());
        System.out.println("Part II 10818234074807:" + bfs(startingPoints));
        System.out.println("Execution time: " + (System.currentTimeMillis() - startTime) + "ms");
    }
    
    private static int singleReach() {
        String curr = "AAA";
        int step = 0;
        int i = 0;
        while (!curr.equals("ZZZ")) {
            step++;
            int next = direct[i++ % direct.length] == 'R' ? 1 : 0;
            curr = paths.get(curr)[next];
        }
        return step;
    }
    
    private static long bfs(Queue<String> startingPoints) {
        long steps = 1;
        long currStep = 0;
        int idx = 0;
        while (!startingPoints.isEmpty()) {
            int iter = startingPoints.size();
            int next = direct[idx++ % direct.length] == 'R' ? 1 : 0;
            while (iter-- > 0) {
                String curr = startingPoints.poll();
                if (curr.endsWith("Z")) {
                    steps = lcm(steps, currStep);
                } else {
                    startingPoints.offer(paths.get(curr)[next]);
                }
            }
            currStep++;
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
        direct = in.nextLine().toCharArray();
        paths = new HashMap<>();
        Queue<String> startingPoints = new LinkedList<>();
        in.nextLine();
        while (in.hasNextLine()) {
            String[] line = in.nextLine().split(" = ");
            String key = line[0];
            String[] pair = line[1].substring(1, line[1].length() - 1).split(", ");
            if (key.endsWith("A")) {
                startingPoints.offer(key);
            }
            paths.put(key, pair);
        }
        return startingPoints;
    }
    
}