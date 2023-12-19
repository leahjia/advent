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
        // System.out.println("Part I  6958 : " + countArrange(1));
        System.out.println("Part II ?? : " + countArrange(5));
        System.out.println("Execution time: " + (System.currentTimeMillis() - startTime) + "ms");
    }
    
    static long count = 0;
    
    private static long countArrange(int wrap) {
        long res = 0;
        for (int l = 0; l < len; l++) {
            count = 0;
            char[] puzz = getPuzzle(puzzle.get(l), wrap);
            int[] cond = getCondition(condition.get(l), wrap);
            helper("", puzz, cond, 0, 0, 0);
            res += count;
        }
        return res;
    }
    
    // i -> index of current letter
    // currCount -> consecutive # count so far
    // need -> index of needed spring on array cond
    private static void helper(String test, char[] puzz, int[] cond, int i, int currCount, int need) {
        if (i == puzz.length) {
            if (need == cond.length && currCount == 0 || need == cond.length - 1 && currCount == cond[need]) {
                count++;
            }
            return;
        }
        if (puzz[i] == '?') {
            if (currCount > 0) {
                if (need < cond.length && currCount == cond[need]) {
                    helper(test + ".", puzz, cond, i + 1, 0, need + 1);
                }
            } else {
                helper(test + ".", puzz, cond, i + 1, 0, need);
            }
            helper(test + "#", puzz, cond, i + 1, currCount + 1, need);
        } else if (puzz[i] == '#') {
            helper(test + "#", puzz, cond, i + 1, currCount + 1, need);
        } else {
            if (currCount > 0) {
                if (need < cond.length && currCount != cond[need]) {
                    return;
                } else {
                    need++;
                }
            }
            helper(test + ".", puzz, cond, i + 1, 0, need);
        }
    }
    
    private static char[] getPuzzle(char[] puzz, int wrap) {
        List<Character> newPuzz = new ArrayList<>();
        for (int c = 0; c < wrap; c++) {
            for (char value : puzz) {
                newPuzz.add(value);
            }
            if (wrap > 1) {
                newPuzz.add('?');
            }
        }
        puzz = new char[newPuzz.size()];
        for (int i = 0; i < puzz.length; i++) {
            puzz[i] = newPuzz.get(i);
        }
        return puzz;
    }
    
    private static int[] getCondition(int[] cond, int wrap) {
        List<Integer> newCond = new ArrayList<>();
        for (int i = 0; i < cond.length * wrap; i++) {
            newCond.add(cond[i % cond.length]);
        }
        cond = new int[newCond.size()];
        for (int i = 0; i < cond.length; i++) {
            cond[i] = newCond.get(i);
        }
        return cond;
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