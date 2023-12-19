import java.io.*;
import java.util.*;

public class Day12 {
    static List<char[]> puzzle;
    static List<int[]> condition;
    static int len;
    
    public static void main(String[] args) throws FileNotFoundException {
        long startTime = System.currentTimeMillis();
        Scanner in = new Scanner(new FileReader("input/day12.txt"));
        processInput(in);
        System.out.println("Part I  6958 : " + part1());
        //System.out.println("Part II ?? : " + part2());
        System.out.println("Execution time: " + (System.currentTimeMillis() - startTime) + "ms");
    }
    
    static long count = 0;
    
    private static long part1() {
        long res = 0;
        for (int l = 0; l < len; l++) {
            count = 0;
            int need = 0;
            for (int n : condition.get(l)) {
                need += n;
            }
            helper(puzzle.get(l), condition.get(l), 0, "", need);
            res += count;
        }
        return res;
    }
    
    private static long part2() {
        long res = 0;
        for (int l = 0; l < len; l++) {
            count = 0;
            
            char[] puzz = puzzle.get(l);
            List<Character> newPuzz = new ArrayList<>();
            for (int c = 0; c < 5; c++) {
                for (int i = 0; i < puzz.length; i++) {
                    newPuzz.add(puzz[i]);
                }
                newPuzz.add('?');
            }
            puzz = new char[newPuzz.size()];
            for (int i = 0; i < puzz.length; i++) {
                puzz[i] = newPuzz.get(i);
            }
            
            int[] cond = condition.get(l);
            List<Integer> newCond = new ArrayList<>();
            int need = 0;
            for (int i = 0; i < cond.length * 5; i++) {
                newCond.add(cond[i % cond.length]);
                need += cond[i % cond.length];
            }
            cond = new int[newCond.size()];
            for (int i = 0; i < cond.length; i++) {
                cond[i] = newCond.get(i);
            }
            
            helper(puzz, cond, 0, "", need);
            System.out.println(Arrays.toString(puzz) + " " + Arrays.toString(cond) + " - " + count + " arrangement(s)");
            res += count;
        }
        return res;
    }
    
    private static void helper(char[] puzz, int[] cond, int i, String str, int need) {
        // System.out.println("   WHAT " + str + " and " + Arrays.toString(cond));
        if (need < 0) {
            return;
        }
        if (i == puzz.length) {
            if (isPossible(str, cond)) {
                // System.out.println("       Checked " + str + " and " + Arrays.toString(cond));
                count++;
            }
        } else if (puzz[i] == '?') {
            helper(puzz, cond, i + 1, str + "#", need - 1);
            helper(puzz, cond, i + 1, str + ".", need);
        } else if (puzz[i] == '.') {
            helper(puzz, cond, i + 1, str + ".", need);
        } else {
            helper(puzz, cond, i + 1, str + "#", need - 1);
        }
    }
    
    private static boolean isPossible(String str, int[] cond) {
        char[] chars = str.toCharArray();
        int i = 0;
        int r = 0;
        int curr = 0;
        while (i < str.length()) {
            char ch = chars[i];
            if (ch == '#') {
                curr++;
                i++;
            } else if (ch == '.') {
                if (curr > 0) {
                    if (r < cond.length && cond[r] == curr) {
                        r++;
                    } else {
                        return false;
                    }
                }
                curr = 0;
                while (i < str.length() && chars[i] == '.') {
                    i++;
                }
            }
        }
        if (curr > 0) {
            return r == cond.length - 1 && cond[r] == curr;
        }
        return r == cond.length;
    }
    
    private static void processInput(Scanner in) {
        puzzle = new ArrayList<>();
        condition = new ArrayList<>();
        while (in.hasNextLine()) {
            String line = in.nextLine();
            puzzle.add(line.split(" ")[0].toCharArray());
            String[] cond = line.split(" ")[1].split(",");
            int[] digits = new int[cond.length];
            for (int i = 0; i < cond.length; i++) {
                digits[i] = Integer.parseInt(cond[i]);
            }
            condition.add(digits);
        }
        len = puzzle.size();
    }
}