import java.io.*;
import java.util.*;

public class Day19 {
    static Map<String, String[]> workflows;
    static List<Part> parts;
    
    public static void main(String[] args) throws FileNotFoundException {
        long startTime = System.currentTimeMillis();
        Scanner in = new Scanner(new FileReader("input/day19.txt"));
        processInput(in);
        System.out.println("Part I  368523 : " + part1());
        System.out.println("Part II 124167549767307 : " + part2());
        System.out.println("Execution time: " + (System.currentTimeMillis() - startTime) + "ms");
    }
    
    private static long part2() {
        Map<Character, int[]> ranges = new HashMap<>();
        for (char ch : "xmas".toCharArray()) {
            ranges.put(ch, new int[]{1, 4000});
        }
        return getVal(ranges, "in");
    }
    
    private static long getVal(Map<Character, int[]> ranges, String key) {
        if (key.equals("R")) {
            return 0;
        }
        if (key.equals("A")) {
            long combinations = 1;
            for (int[] range : ranges.values()) {
                combinations *= (range[1] - range[0] + 1);
            }
            return combinations;
        }
        
        long res = 0;
        for (String rule : workflows.get(key)) {
            String[] tokens = rule.split(":");
            if (tokens.length == 1) {
                res += getVal(ranges, tokens[0]);
                break;
            }
            
            char letter = tokens[0].charAt(0);
            int val = Integer.parseInt(tokens[0].substring(2));
            boolean lessThan = tokens[0].charAt(1) == '<';
            
            int[] match;
            int[] nomatch;
            int[] range = ranges.get(letter);
            if (lessThan) {
                match = new int[]{range[0], val - 1};
                nomatch = new int[]{val, range[1]};
            } else {
                match = new int[]{val + 1, range[1]};
                nomatch = new int[]{range[0], val};
            }
            
            if (isValidRange(match)) {
                Map<Character, int[]> newMap = cloneMap(ranges); // must clone
                newMap.put(letter, match);
                res += getVal(newMap, tokens[1]);
            }
            if (isValidRange(nomatch)) {
                ranges.put(letter, nomatch);
            }
        }
        return res;
    }
    
    private static boolean isValidRange(int[] range) {
        return range[0] <= range[1];
    }
    
    private static Map<Character, int[]> cloneMap(Map<Character, int[]> map) {
        Map<Character, int[]> clone = new HashMap<>();
        for (char key : map.keySet()) {
            clone.put(key, map.get(key).clone());
        }
        return clone;
    }
    
    private static long part1() {
        long res = 0;
        for (Part p : parts) {
            res += getVal(p, "in");
        }
        return res;
    }
    
    private static long getVal(Part p, String key) {
        if (key.equals("A")) {
            return p.x + p.m + p.a + p.s;
        }
        if (key.equals("R")) {
            return 0;
        }
        for (String rule : workflows.get(key)) {
            String[] tokens = rule.split(":");
            if (tokens.length == 1) {
                return getVal(p, tokens[0]);
            }
            
            char letter = tokens[0].charAt(0);
            int val = Integer.parseInt(tokens[0].substring(2));
            boolean lessThan = tokens[0].charAt(1) == '<';
            
            if (lessThan) {
                if (getValByLetter(p, letter) < val) {
                    return getVal(p, tokens[1]);
                }
            } else {
                if (getValByLetter(p, letter) > val) {
                    return getVal(p, tokens[1]);
                }
            }
        }
        return 0;
    }
    
    private static int getValByLetter(Part p, char letter) {
        if (letter == 'x') {
            return p.x;
        } else if (letter == 'm') {
            return p.m;
        } else if (letter == 'a') {
            return p.a;
        } else if (letter == 's') {
            return p.s;
        }
        return 0;
    }
    
    private static void processInput(Scanner in) {
        workflows = new HashMap<>();
        parts = new ArrayList<>();
        while (in.hasNextLine()) {
            String line = in.nextLine();
            if (line.isEmpty()) {
                break;
            }
            String label = line.split("\\{")[0];
            String flow = line.split("\\{")[1];
            flow = flow.substring(0, flow.length() - 1);
            workflows.put(label, flow.split(","));
        }
        while (in.hasNextLine()) {
            String line = in.nextLine();
            String[] vals = line.substring(1, line.length() - 1).split(",");
            parts.add(new Part(Integer.parseInt(vals[0].substring(2)), Integer.parseInt(vals[1].substring(2)), Integer.parseInt(vals[2].substring(2)), Integer.parseInt(vals[3].substring(2))));
        }
    }
}

class Part {
    int x, m, a, s;
    
    Part(int x, int m, int a, int s) {
        this.x = x;
        this.m = m;
        this.a = a;
        this.s = s;
    }
}