import java.io.*;
import java.util.*;

public class Day4 {
    static HashSet<Integer>[] winning;
    static int[][] having;
    static int nCards;
    
    public static void main(String[] args) throws FileNotFoundException {
        long startTime = System.currentTimeMillis();
        Scanner in = new Scanner(new FileReader("input/day4.txt"));
        List<String> input = new ArrayList<>();
        while (in.hasNextLine()) {
            input.add(in.nextLine());
        }
        getCards(input);
        System.out.println("Part I  21821  : " + part1());
        System.out.println("Part II 5539496: " + part2());
        System.out.println("Execution time: " + (System.currentTimeMillis() - startTime) + "ms");
    }
    
    private static int part1() {
        int res = 0;
        for (int i = 0; i < nCards; i++) {
            int ct = 0;
            for (int card : having[i]) {
                if (winning[i].contains(card)) {
                    ct *= 2;
                    if (ct == 0) ct = 1;
                }
            }
            res += ct;
        }
        return res;
    }
    
    private static int part2() {
        Map<Integer, Integer> matches = new HashMap<>();
        for (int i = 0; i < nCards; i++) {
            for (int card : having[i]) {
                if (winning[i].contains(card)) {
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
        winning = new HashSet[nCards];
        having = new int[nCards][];
        for (int card = 0; card < nCards; card++) {
            String line = input.get(card);
            String[] winAndHave = line.split(": ")[1].split(" \\| ");
            winning[card] = new HashSet<>();
            for (String s : winAndHave[0].split(" ")) {
                if (s.equals("")) continue;
                winning[card].add(Integer.parseInt(s));
            }
            List<Integer> currList = new ArrayList<>();
            for (String s : winAndHave[1].split(" ")) {
                if (s.equals("")) continue;
                currList.add(Integer.parseInt(s));
            }
            having[card] = currList.stream().mapToInt(i -> i).toArray();
        }
    }
}
