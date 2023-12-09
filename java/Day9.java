import java.io.*;
import java.util.*;

public class Day9 {
    static List<long[]> input;
    
    public static void main(String[] args) throws FileNotFoundException {
        long startTime = System.currentTimeMillis();
        Scanner in = new Scanner(new FileReader("input/day9.txt"));
        processInput(in);
        System.out.println("Part I  1819125966 : " + getHist());
        System.out.println("Part II       1140 : " + getHistBackwards());
        System.out.println("Execution time: " + (System.currentTimeMillis() - startTime) + "ms");
    }
    
    private static long getHist() {
        long res = 0;
        for (long[] line : input) {
            res += getHistHelper(line);
        }
        return res;
    }
    
    private static long getHistHelper(long[] prevLine) {
        List<long[]> history = new ArrayList<>();
        history.add(prevLine);
        while (true) {
            long[] newList = new long[prevLine.length - 1];
            for (int i = 0; i < prevLine.length - 1; i++) {
                long nextNum = prevLine[i + 1] - prevLine[i];
                newList[i] = nextNum;
            }
            if (isAllZero(newList)) {
                break;
            }
            prevLine = newList;
            history.add(newList);
        }
        long add = 0;
        for (int i = history.size() - 1; i >= 0; i--) {
            long[] nextList = history.get(i);
            add += nextList[nextList.length - 1];
        }
        return add;
    }
    
    private static long getHistBackwards() {
        long res = 0;
        for (long[] line : input) {
            res += getHistBackwardsHelper(line);
        }
        return res;
    }
    
    private static long getHistBackwardsHelper(long[] prevLine) {
        List<long[]> history = new ArrayList<>();
        history.add(prevLine);
        while (true) {
            long[] newList = new long[prevLine.length - 1];
            for (int i = prevLine.length - 2; i >= 0; i--) {
                long nextNum = prevLine[i] - prevLine[i + 1];
                newList[i] = nextNum;
            }
            prevLine = newList;
            if (isAllZero(newList)) {
                break;
            }
            history.add(newList);
        }
        long add = 0;
        for (long[] nextList : history) {
            add += nextList[0];
        }
        return add;
    }
    
    private static boolean isAllZero(long[] nums) {
        for (long value : nums) {
            if (value != 0) {
                return false;
            }
        }
        return true;
    }
    
    private static void processInput(Scanner in) {
        input = new ArrayList<>();
        while (in.hasNextLine()) {
            String[] line = in.nextLine().split(" ");
            long[] nums = new long[line.length];
            for (int i = 0; i < line.length; i++) {
                nums[i] = Long.parseLong(line[i]);
            }
            input.add(nums);
        }
    }
    
}