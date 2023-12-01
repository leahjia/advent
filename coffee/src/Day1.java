import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

public class Day1 {
    static Map<Character, String[]> map;
    static Map<String, Integer> toNum;
    
    public static void main(String[] args) throws FileNotFoundException {
        String file = "input/day1.txt";
        
        map = new HashMap<>() {{
            put('o', new String[]{"one"});
            put('t', new String[]{"two", "three"});
            put('f', new String[]{"four", "five"});
            put('s', new String[]{"six", "seven"});
            put('e', new String[]{"eight"});
            put('n', new String[]{"nine"});
        }};
        
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
    }
    
    private static int getSum(Scanner in, boolean checkLetters) {
        int sum = 0;
        while (in.hasNext()) {
            StringBuilder curr = new StringBuilder();
            String line = in.nextLine();
            int len = line.length();
            int l = 0;
            int r = len - 1;
            while (l < len) {
                if (isNumber(line, l, curr, checkLetters)) {
                    break;
                }
                l++;
            }
            while (r >= 0) {
                if (isNumber(line, r, curr, checkLetters)) {
                    break;
                }
                r--;
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
        if (checkLetters && map.containsKey(ch)) {
            for (String s : map.get(ch)) {
                if (idx + s.length() <= line.length() && s.equals(line.substring(idx, idx + s.length()))) {
                    curr.append(toNum.get(s));
                    return true;
                }
            }
        }
        return false;
    }
}
