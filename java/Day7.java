import java.io.*;
import java.util.*;

public class Day7 {
    // static char[] rank = new char[]{'A', 'K', 'Q', 'J', 'T', '9', '8', '7', '6', '5', '4', '3', '2'};
    static List<String> cards;
    static Map<String, Integer> bets;
    
    public static void main(String[] args) throws FileNotFoundException {
        long startTime = System.currentTimeMillis();
        Scanner in = new Scanner(new FileReader("input/day7.txt"));
        processInput(in);
        System.out.println("Part I  250946742 : " + part1());
        System.out.println("Part II ??  : " + part2());
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
        return -1;
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
                checkThree = false;
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
