import java.util.Comparator;
import java.util.Map;

public abstract class BaseCardComparator implements Comparator<String> {
    protected abstract Map<String, Integer> getTypeMap();
    
    protected abstract Map<Character, Integer> getRankMap();
    
    protected abstract boolean hasWildCard();
    
    @Override
    public int compare(String s1, String s2) {
        Map<String, Integer> type = getTypeMap();
        Map<Character, Integer> rank = getRankMap();
        
        // compare by type
        String type1 = CardUtils.getType(s1, hasWildCard());
        String type2 = CardUtils.getType(s2, hasWildCard());
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
}
