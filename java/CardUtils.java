import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class CardUtils {
    public static String getType(String s, boolean hasWildCard) {
        if (!hasWildCard) {
            return getType(s);
        }
        Set<Character> set = new HashSet<>();
        for (char c : s.toCharArray()) {
            set.add(c);
        }
        if (!set.contains('J')) {
            return getType(s);
        }
        Map<Character, Integer> count = new HashMap<>();
        String type = "high";
        int j = 0;
        int maxFreq = 0;
        for (char ch : s.toCharArray()) {
            if (ch == 'J') {
                j++;
            } else {
                count.put(ch, count.getOrDefault(ch, 0) + 1);
                maxFreq = Math.max(maxFreq, count.get(ch));
            }
        }
        if (maxFreq + j == 5) {
            type = "five";
        } else if (maxFreq + j == 4) {
            type = "four";
        } else if (maxFreq + j == 3) {
            if (count.size() == 3) {
                type = "three";
            } else {
                type = "full";
            }
        } else if (maxFreq + j == 2) {
            type = "one";
        }
        return type;
    }
    
    public static String getType(String s) {
        Map<Character, Integer> count = new HashMap<>();
        String type = "high";
        boolean checkThree = false;
        int pairs = 0;
        for (char ch : s.toCharArray()) {
            count.put(ch, count.getOrDefault(ch, 0) + 1);
            if (count.get(ch) == 5) {
                type = "five";
            } else if (count.get(ch) == 4) {
                checkThree = false;
                type = "four";
            } else if (count.get(ch) == 3) {
                checkThree = true;
            } else if (count.get(ch) == 2) {
                pairs++;
                type = pairs == 2 ? "two" : "one";
            }
        }
        if (checkThree) {
            type = count.size() == 2 ? "full" : "three";
        }
        return type;
    }
}
