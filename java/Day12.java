import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.*;

public class Day12 {
    static List<String> puzzles;
    static List<List<Integer>> conditions;
    static long count = 0;
    
    public static void main(String[] args) throws IOException {
        List<String> input = Files.readAllLines(Paths.get("input/day12.txt"));
        long startTime = System.currentTimeMillis();
        processInput(input, 1);
        System.out.println("Part I           6958 : " + countArrange() + "\nExecution time: " + (System.currentTimeMillis() - startTime) + "ms");
        processInput(input, 5);
        System.out.println("Part II 6555315065024 : " + countWithCache() + "\nExecution time: " + (System.currentTimeMillis() - startTime) + "ms");
    }
    
    private static long countWithCache() {
        long res = 0;
        for (int l = 0; l < puzzles.size(); l++) {
            res += getArrangeWithCache(puzzles.get(l), conditions.get(l));
        }
        return res;
    }
    
    private static final Map<String, Long> cache = new HashMap<>();
    
    private static long getArrangeWithCache(String puzzle, List<Integer> condition) {
        if (condition.isEmpty()) {
            return puzzle.contains("#") ? 0 : 1;
        }
        if (puzzle.isEmpty()) {
            return 0;
        }
        
        String key = puzzle + condition;
        if (cache.containsKey(key)) {
            return cache.get(key);
        }
        
        long res = 0;
        char ch = puzzle.charAt(0);
        int req = condition.get(0);
        if (ch == '.' || ch == '?') {
            res += getArrangeWithCache(puzzle.substring(1), condition);
        }
        if (ch == '#' || ch == '?') { // check: 1) all #, and 2) no more chars or next one is not #
            if (req <= puzzle.length() && !puzzle.substring(0, req).contains(".")) {
                if (req == puzzle.length() || puzzle.charAt(req) != '#') {
                    res += getArrangeWithCache(puzzle.substring(Math.min(req + 1, puzzle.length())), condition.subList(1, condition.size()));
                }
            }
        }
        cache.put(key, res);
        return res;
    }
    
    // only works for part 1 but faster
    private static long countArrange() {
        for (int l = 0; l < puzzles.size(); l++) {
            countArrange(puzzles.get(l), conditions.get(l), 0, 0, 0);
        }
        return count;
    }
    
    private static void countArrange(String puzz, List<Integer> cond, int i, int currCount, int need) {
        if (i == puzz.length()) {
            if (need == cond.size() && currCount == 0 || need == cond.size() - 1 && currCount == cond.get(need)) {
                count++;
            }
            return;
        }
        if (puzz.charAt(i) == '?') {
            if (currCount > 0) {
                if (need < cond.size() && currCount == cond.get(need)) {
                    countArrange(puzz, cond, i + 1, 0, need + 1);
                }
            } else {
                countArrange(puzz, cond, i + 1, 0, need);
            }
            countArrange(puzz, cond, i + 1, currCount + 1, need);
        } else if (puzz.charAt(i) == '#') {
            countArrange(puzz, cond, i + 1, currCount + 1, need);
        } else {
            if (currCount > 0) {
                if (need < cond.size() && currCount != cond.get(need)) {
                    return;
                } else {
                    need++;
                }
            }
            countArrange(puzz, cond, i + 1, 0, need);
        }
    }
    
    private static void processInput(List<String> input, int wrap) {
        puzzles = new ArrayList<>();
        conditions = new ArrayList<>();
        for (String line : input) {
            String[] parts = line.split(" ");
            puzzles.add(String.join("?", Collections.nCopies(wrap, parts[0])));
            conditions.add(Stream.of(String.join(",", Collections.nCopies(wrap, parts[1])).split(",")).map(Integer::parseInt).collect(Collectors.toList()));
        }
    }
}