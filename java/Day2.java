import java.io.*;
import java.util.*;

public class Day2 {
    public static void main(String[] args) throws FileNotFoundException {
        long startTime = System.currentTimeMillis();
        String file = "input/day2.txt";
        Scanner in = new Scanner(new FileReader(file));
        List<String> input = new ArrayList<>();
        while (in.hasNextLine()) {
            input.add(in.nextLine());
        }
        System.out.println("Part I : " + isPossible(input));
        System.out.println("Part II: " + minPower(input));
        System.out.println("Execution time: " + (System.currentTimeMillis() - startTime) + "ms");
    }
    
    static Map<String, Integer> cubes;
    
    private static int isPossible(List<String> in) {
        cubes = new HashMap<>() {{
            put("red", 12);
            put("green", 13);
            put("blue", 14);
        }};
        int id = 1;
        int res = 0;
        for (String line : in) {
            if (checkLine(line)) {
                res += id;
            }
            id++;
        }
        return res;
    }
    
    private static boolean checkLine(String line) {
        String[] draws = line.substring(line.indexOf(":") + 1).trim().split("; ");
        for (String draw : draws) {
            for (String token : draw.split(", ")) {
                String key = token.split(" ")[1];
                int ct = Integer.parseInt(token.split(" ")[0]);
                if (ct > cubes.get(key)) {
                    return false;
                }
            }
        }
        return true;
    }
    
    private static int minPower(List<String> in) {
        int res = 0;
        for (String line : in) {
            Map<String, Integer> map = new HashMap<>() {{
                put("red", 0);
                put("green", 0);
                put("blue", 0);
            }};
            String[] draws = line.substring(line.indexOf(":") + 1).trim().split("; ");
            for (String draw : draws) {
                for (String token : draw.split(", ")) {
                    String key = token.split(" ")[1];
                    int ct = Integer.parseInt(token.split(" ")[0]);
                    map.put(key, Math.max(ct, map.get(key)));
                }
            }
            int sum = 1;
            for (int i : map.values()) {
                sum *= i;
            }
            res += sum;
        }
        return res;
    }
}
