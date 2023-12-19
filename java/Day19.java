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
        System.out.println("Part II ?? : " + part2());
        System.out.println("Execution time: " + (System.currentTimeMillis() - startTime) + "ms");
    }
    
    private static long part2() {
        long res = 0;
        return res;
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
            String[] spl = rule.split(":");
            if (spl.length == 1) {
                String next = rule.split(":")[0];
                return getVal(p, next);
            }
            String equa = spl[0];
            String next = spl[1];
            if (equa.contains("<")) {
                String symbol = equa.split("<")[0];
                int val = Integer.parseInt(equa.split("<")[1]);
                switch (symbol) {
                    case "x" -> {
                        if (p.x < val) {
                            return getVal(p, next);
                        }
                    }
                    case "m" -> {
                        if (p.m < val) {
                            return getVal(p, next);
                        }
                    }
                    case "a" -> {
                        if (p.a < val) {
                            return getVal(p, next);
                        }
                    }
                    default -> {
                        if (p.s < val) {
                            return getVal(p, next);
                        }
                    }
                }
            } else {
                String symbol = equa.split(">")[0];
                int val = Integer.parseInt(equa.split(">")[1]);
                switch (symbol) {
                    case "x" -> {
                        if (p.x > val) {
                            return getVal(p, next);
                        }
                    }
                    case "m" -> {
                        if (p.m > val) {
                            return getVal(p, next);
                        }
                    }
                    case "a" -> {
                        if (p.a > val) {
                            return getVal(p, next);
                        }
                    }
                    default -> {
                        if (p.s > val) {
                            return getVal(p, next);
                        }
                    }
                }
            }
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