import java.io.*;
import java.util.*;

public class Day7 {
    static List<String> cards;
    static Map<String, Integer> bets;
    
    public static void main(String[] args) throws FileNotFoundException {
        long startTime = System.currentTimeMillis();
        Scanner in = new Scanner(new FileReader("input/day7.txt"));
        processInput(in);
        System.out.println("Part I  250946742 : " + normalCard());
        System.out.println("Part II 251824095 : " + wildCard());
        System.out.println("Execution time: " + (System.currentTimeMillis() - startTime) + "ms");
    }
    
    private static int normalCard() {
        cards.sort(new CardComparator());
        int n = 0;
        int res = 0;
        while (++n <= cards.size()) {
            res += n * bets.get(cards.get(n - 1));
        }
        return res;
    }
    
    private static int wildCard() {
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

