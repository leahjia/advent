import java.io.*;
import java.util.*;

public class Day7 {
    static List<String> cards;
    static Map<String, Integer> bets;
    
    public static void main(String[] args) throws FileNotFoundException {
        long startTime = System.currentTimeMillis();
        Scanner in = new Scanner(new FileReader("input/day7_sample.txt"));
        processInput(in);
        System.out.println("Part I  250946742 : " + part1());
        System.out.println("Part II <252163611, not 251595872  : " + part2());
        System.out.println("Execution time: " + (System.currentTimeMillis() - startTime) + "ms");
    }
    
    private static int part1() {
        cards.sort(new CardComparator());
        int n = 0;
        int res = 0;
        while (++n <= cards.size()) {
            res += n * bets.get(cards.get(n - 1));
        }
        return res;
    }
    
    private static int part2() {
        cards.sort(new WildCardComparator());
        int n = 0;
        int res = 0;
        while (++n <= cards.size()) {
            res += n * bets.get(cards.get(n - 1));
        }
        return res;
    }
    
    private static void processInput(Scanner in) {
        bets = new HashMap<>();
        cards = new ArrayList<>();
        while (in.hasNextLine()) {
            String[] line = in.nextLine().split(" ");
            cards.add(line[0]);
            bets.put(line[0], Integer.parseInt(line[1]));
        }
    }
    
}

class CardComparator implements Comparator<String> {
    private static final Map<String, Integer> type = new HashMap<>();
    private static final Map<Character, Integer> rank = new HashMap<>();
    
    static {
        type.put("five", 7);
        type.put("four", 6);
        type.put("full", 5);
        type.put("three", 4);
        type.put("two", 3);
        type.put("one", 2);
        type.put("high", 1);
        
        rank.put('A', 13);
        rank.put('K', 12);
        rank.put('Q', 11);
        rank.put('J', 10);
        rank.put('T', 9);
        rank.put('9', 8);
        rank.put('8', 7);
        rank.put('7', 6);
        rank.put('6', 5);
        rank.put('5', 4);
        rank.put('4', 3);
        rank.put('3', 2);
        rank.put('2', 1);
    }
    
    @Override
    public int compare(String s1, String s2) {
        // compare by type
        String type1 = getType(s1);
        String type2 = getType(s2);
        int compareType = type.get(type1).compareTo(type.get(type2));
        if (compareType != 0) {
            return compareType;
        }
        
        // compare by each card
        for (int i = 0; i < 5; i++) {
            int compare = rank.get(s1.charAt(i)).compareTo(rank.get(s2.charAt(i)));
            if (compare != 0) {
                return compare;
            }
        }
        return 0;
    }
    
    private static String getType(String s) {
        Map<Character, Integer> count1 = new HashMap<>();
        String type = "high";
        boolean checkThree = false;
        int pairs = 0;
        for (char ch : s.toCharArray()) {
            count1.put(ch, count1.getOrDefault(ch, 0) + 1);
            if (count1.get(ch) == 5) {
                type = "five";
            } else if (count1.get(ch) == 4) {
                checkThree = false;
                type = "four";
            } else if (count1.get(ch) == 3) {
                checkThree = true;
            } else if (count1.get(ch) == 2) {
                pairs++;
                type = pairs == 2 ? "two" : "one";
            }
        }
        if (checkThree) {
            type = count1.size() == 2 ? "full" : "three";
        }
        return type;
    }
}

class WildCardComparator implements Comparator<String> {
    private static final Map<String, Integer> type = new HashMap<>();
    private static final Map<Character, Integer> rank = new HashMap<>();
    
    static {
        type.put("five", 7);
        type.put("four", 6);
        type.put("full", 5);
        type.put("three", 4);
        type.put("two", 3);
        type.put("one", 2);
        type.put("high", 1);
        
        rank.put('A', 13);
        rank.put('K', 12);
        rank.put('Q', 11);
        rank.put('T', 10);
        rank.put('9', 9);
        rank.put('8', 8);
        rank.put('7', 7);
        rank.put('6', 6);
        rank.put('5', 5);
        rank.put('4', 4);
        rank.put('3', 3);
        rank.put('2', 2);
        rank.put('J', 1);
    }
    
    @Override
    public int compare(String s1, String s2) {
        // compare by type
        String type1 = getType(s1);
        String type2 = getType(s2);
        int compareType = type.get(type1).compareTo(type.get(type2));
        if (compareType != 0) {
            return compareType;
        }
        
        // compare by each card
        for (int i = 0; i < 5; i++) {
            int compare = rank.get(s1.charAt(i)).compareTo(rank.get(s2.charAt(i)));
            if (compare != 0) {
                return compare;
            }
        }
        return 0;
    }
    
    private static String getType(String s) {
        Map<Character, Integer> count = new HashMap<>();
        String type = "high";
        int j = 0;
        int maxFreq = 0;
        int pairs = 0;
        for (char ch : s.toCharArray()) {
            if (ch == 'J') {
                j++;
            } else {
                count.put(ch, count.getOrDefault(ch, 0) + 1);
                maxFreq = Math.max(maxFreq, count.get(ch));
                if (count.get(ch) == 2) {
                    pairs++;
                }
                if (maxFreq == 5) {
                    System.out.println(s + " is of type five");
                    return "five"; // no need to proceed
                }
            }
        }
        if (j == 0) {
            return getTypeNoJ(s);
        }
        // if (maxFreq == 4 || j == 4) {
        if (maxFreq + j == 5) {
            System.out.println(s + " is of type five");
            // fifth one is wildcard || got 4 wildcards
            return "five";
        }
        if (maxFreq == 3) {
            System.out.println(s + " is of type four");
            return "four";
        }
        if (maxFreq == 2) {   // at least a pair in there
            if (pairs == 2) { // fifth card def J
                System.out.println(s + " is of type full");
                return "full";
            }
            if (count.size() == 3) {
                System.out.println(s + " is of type three");
                return "three";
            } else {
                System.out.println(s + " is of type four");
                return "four";
            }
        }
        System.out.println(s + " is of type " + type);
        return type;
    }
    
    private static String getTypeNoJ(String s) {
        Map<Character, Integer> count1 = new HashMap<>();
        String type = "high";
        boolean checkThree = false;
        int pairs = 0;
        for (char ch : s.toCharArray()) {
            count1.put(ch, count1.getOrDefault(ch, 0) + 1);
            if (count1.get(ch) == 5) {
                type = "five";
            } else if (count1.get(ch) == 4) {
                checkThree = false;
                type = "four";
            } else if (count1.get(ch) == 3) {
                checkThree = true;
            } else if (count1.get(ch) == 2) {
                pairs++;
                type = pairs == 2 ? "two" : "one";
            }
        }
        if (checkThree) {
            type = count1.size() == 2 ? "full" : "three";
        }
        System.out.println(s + " is of type " + type);
        return type;
    }
}
