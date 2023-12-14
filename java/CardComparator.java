import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class CardComparator extends BaseCardComparator {
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
    protected Map<String, Integer> getTypeMap() {
        return type;
    }
    
    @Override
    protected Map<Character, Integer> getRankMap() {
        return rank;
    }
    
    @Override
    protected boolean hasWildCard() {
        return false;
    }
}
