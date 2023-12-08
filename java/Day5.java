import java.io.*;
import java.util.*;

public class Day5 {
    static String[] mapNames = new String[]{"seed-to-soil", "soil-to-fertilizer", "fertilizer-to-water", "water-to-light", "light-to-temperature", "temperature-to-humidity", "humidity-to-location"};
    static long[] seeds;
    static Map<String, Map<long[], Long>> maps; // <name, <range, diff>>
    
    public static void main(String[] args) throws FileNotFoundException {
        long startTime = System.currentTimeMillis();
        Scanner in = new Scanner(new FileReader("input/day5.txt"));
        processInput(in);
        
        System.out.println("Part I  424490994 : " + part1());
        System.out.println("Part II  15290096 : " + part2());
        System.out.println("Execution time: " + (System.currentTimeMillis() - startTime) + "ms");
    }
    
    private static long part1() {
        long res = Long.MAX_VALUE;
        for (long seed : seeds) {
            long next = seed;
            for (String mapName : mapNames) {
                next = getMapTo(maps.get(mapName), next);
            }
            res = Math.min(res, next);
        }
        return res;
    }
    
    private static long getMapTo(Map<long[], Long> map, long n) {
        for (Map.Entry<long[], Long> e : map.entrySet()) {
            long[] range = e.getKey();
            if (range[0] <= n && n <= range[1]) {
                return n + e.getValue();
            }
        }
        return n;
    }
    
    private static long part2() {
        long res = Long.MAX_VALUE;
        List<long[]> mapped = new ArrayList<>();
        for (int i = 0; i < seeds.length - 1; i += 2) {
            mapped.add(new long[]{seeds[i], seeds[i] + seeds[i + 1] - 1});
        }
        for (String mapName : mapNames) {
            List<long[]> newMapped = getMapToRange(maps.get(mapName), mapped);
            mapped = new ArrayList<>(newMapped);
        }
        for (long[] range : mapped) {
            res = Math.min(res, range[0]);
        }
        return res;
    }
    
    private static List<long[]> getMapToRange(Map<long[], Long> map, List<long[]> ranges) {
        List<long[]> mapped = new ArrayList<>();
        for (long[] range : ranges) {
            long x1 = range[0];
            long y1 = range[1];
            for (Map.Entry<long[], Long> e : map.entrySet()) {
                long[] mapRange = e.getKey();
                long x0 = mapRange[0];
                long y0 = mapRange[1];
                long diff = e.getValue();
                if (hasOverlap(range, mapRange)) {
                    long localMin = Math.max(x0, x1) + diff;
                    long localMax = Math.min(y0, y1) + diff;
                    mapped.add(new long[]{localMin, localMax});
                }
            }
        }
        return mapped;
    }
    
    private static boolean hasOverlap(long[] a, long[] b) {
        return b[0] <= a[0] && a[0] <= b[1] || b[0] <= a[1] && a[1] <= b[1] ||
                a[0] <= b[0] && b[0] <= a[1] || a[0] <= b[1] && b[1] <= a[1];
    }
    
    private static void processInput(Scanner in) {
        String[] temp = in.nextLine().split(": ")[1].split(" ");
        seeds = new long[temp.length];
        for (int i = 0; i < temp.length; i++) {
            seeds[i] = Long.parseLong(temp[i]);
        }
        
        // <range, diff>
        maps = new HashMap<>();
        for (String key : mapNames) {
            maps.put(key, new HashMap<>());
        }
        in.nextLine();
        
        int mapId = 0;
        while (in.hasNextLine()) {
            in.nextLine();
            String key = mapNames[mapId];
            Map<long[], Long> currMap = maps.get(key);
            
            long lowEnd = Long.MAX_VALUE;
            long highEnd = Long.MIN_VALUE;
            
            while (in.hasNextLine()) {
                String line = in.nextLine();
                if (line.length() == 0) {
                    mapId++;
                    break;
                }
                String[] entry = line.split(" ");
                long src = Long.parseLong(entry[1]);
                long end = src + Long.parseLong(entry[2]) - 1;
                long offBy = Long.parseLong(entry[0]) - src;
                currMap.put(new long[]{src, end}, offBy); // stupid off by 1
                
                lowEnd = Math.min(lowEnd, src - 1);
                highEnd = Math.max(highEnd, end + 1);
            }
            // just trying to cover all ranges
            currMap.put(new long[]{Long.MIN_VALUE, lowEnd}, (long) 0);
            currMap.put(new long[]{highEnd, Long.MAX_VALUE}, (long) 0);
        }
    }
}
