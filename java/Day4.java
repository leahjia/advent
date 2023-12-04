import java.io.*;
import java.util.*;

public class Day4 {
    static HashSet<Integer>[] winningCards;
    static int[][] cardsInHand;
    static int nCards;
    
    public static void main(String[] args) throws FileNotFoundException {
        long startTime = System.currentTimeMillis();
        Scanner in = new Scanner(new FileReader("input/day4.txt"));
        List<String> input = new ArrayList<>();
        while (in.hasNextLine()) {
            input.add(in.nextLine());
        }
        getCards(input);
        System.out.println("Part I  21821  : " + getPoints());
        System.out.println("Part II 5539496: " + getCopies());
        System.out.println("Execution time: " + (System.currentTimeMillis() - startTime) + "ms");
    }
    
    private static int getPoints() {
        int res = 0;
        for (int i = 0; i < nCards; i++) {
            int ct = 0;
            for (int card : cardsInHand[i]) {
                if (winningCards[i].contains(card)) {
                    ct *= 2;
                    if (ct == 0) ct = 1;
                }
            }
            res += ct;
        }
        return res;
    }
    
    private static int getCopies() {
        Map<Integer, Integer> matches = new HashMap<>();
        for (int i = 0; i < nCards; i++) {
            for (int card : cardsInHand[i]) {
                if (winningCards[i].contains(card)) {
                    matches.put(i + 1, matches.getOrDefault(i + 1, 0) + 1);
                }
            }
        }
        
        int res = nCards;
        Queue<Integer> q = new LinkedList<>();
        for (int key : matches.keySet()) {
            q.offer(key);
        }
        while (!q.isEmpty()) {
            int curr = q.poll();
            for (int i = 1; i <= matches.get(curr); i++) {
                if (matches.containsKey(curr + i)) {
                    q.offer(curr + i);
                }
                res++;
            }
        }
        return res;
    }
    
    private static void getCards(List<String> input) {
        nCards = input.size();
        winningCards = new HashSet[nCards];
        cardsInHand = new int[nCards][];
        for (int card = 0; card < nCards; card++) {
            String line = input.get(card);
            String[] allCards = line.split(": ")[1].split(" \\| ");
            winningCards[card] = new HashSet<>();
            
            String[] currList = allCards[0].trim().split("\\s+");
            for (String num : currList) {
                winningCards[card].add(Integer.parseInt(num));
            }
            
            currList = allCards[1].trim().split("\\s+");
            cardsInHand[card] = new int[currList.length];
            for (int i = 0; i < currList.length; i++) {
                cardsInHand[card][i] = Integer.parseInt(currList[i]);
            }
        }
    }
}
