import java.io.*;
import java.util.*;

public class Day1 {
    static Map<String, Integer> toNum;
    
    public static void main(String[] args) throws FileNotFoundException {
        long startTime = System.currentTimeMillis();
        String file = "input/day1.txt";
        toNum = new HashMap<>() {{
            put("one", 1);
            put("two", 2);
            put("three", 3);
            put("four", 4);
            put("five", 5);
            put("six", 6);
            put("seven", 7);
            put("eight", 8);
            put("nine", 9);
        }};
        
        System.out.println("Part I  sum: " + getSum(new Scanner(new FileReader(file)), false));
        System.out.println("Part II sum: " + getSum(new Scanner(new FileReader(file)), true));
        System.out.println("Execution time: " + (System.currentTimeMillis() - startTime) + "ms");
    }
    
    private static int getSum(Scanner in, boolean checkLetters) {
        int sum = 0;
        while (in.hasNext()) {
            StringBuilder curr = new StringBuilder();
            String line = in.nextLine();
            int l = 0;
            int r = line.length() - 1;
            while (l < line.length()) {
                if (isNumber(line, l++, curr, checkLetters)) {
                    break;
                }
            }
            while (r >= 0) {
                if (isNumber(line, r--, curr, checkLetters)) {
                    break;
                }
            }
            sum += Integer.parseInt(curr.toString());
        }
        return sum;
    }
    
    private static boolean isNumber(String line, int idx, StringBuilder curr, boolean checkLetters) {
        char ch = line.charAt(idx);
        if (Character.isDigit(ch)) {
            curr.append(ch);
            return true;
        }
        if (checkLetters) {
            for (String s : toNum.keySet()) {
                if (line.substring(idx).startsWith(s)) {
                    curr.append(toNum.get(s));
                    return true;
                }
            }
        }
        return false;
    }
}
