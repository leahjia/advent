import java.io.*;
import java.util.*;

public class Day5 {
    static String[] mapNames;
    static long[] seeds;
    static Map<String, List<long[]>> mappings; // <mapName, [start, end, diff]>
    
    public static void main(String[] args) throws FileNotFoundException {
        long startTime = System.currentTimeMillis();
        processInput(new Scanner(new FileReader("input/day5.txt")));
        System.out.println("Part I  424490994 : " + getLowestSeed());
        System.out.println("Part II  15290096 : " + getLowestInRange());
        System.out.println("Execution time: " + (System.currentTimeMillis() - startTime) + "ms");
    }
    
    private static long getLowestSeed() {
        long res = Long.MAX_VALUE;
        for (long seed : seeds) {
            for (String mapName : mapNames) {
                seed = getMapTo(mappings.get(mapName), seed);
            }
            res = Math.min(res, seed);
        }
        return res;
    }
    
    private static long getMapTo(List<long[]> ranges, long n) {
        for (long[] range : ranges) {
            if (range[0] <= n && n <= range[1]) {
                return n + range[2];
            }
        }
        return n;
    }
    
    private static long getLowestInRange() {
        List<long[]> mapped = new ArrayList<>();
        for (int i = 0; i < seeds.length - 1; i += 2) {
            mapped.add(new long[]{seeds[i], seeds[i] + seeds[i + 1] - 1});
        }
        for (String mapName : mapNames) {
            mapped = getMapToRange(mappings.get(mapName), mapped);
        }
        long lowest = Long.MAX_VALUE;
        for (long[] range : mapped) {
            lowest = Math.min(lowest, range[0]);
        }
        return lowest;
    }
    
    private static List<long[]> getMapToRange(List<long[]> map, List<long[]> ranges) {
        List<long[]> mapped = new ArrayList<>();
        for (long[] range : ranges) {
            for (long[] mapRange : map) {
                long offBy = mapRange[2];
                if (hasOverlap(range, mapRange)) {
                    mapped.add(new long[]{Math.max(mapRange[0], range[0]) + offBy, Math.min(mapRange[1], range[1]) + offBy});
                }
            }
        }
        return mapped;
    }
    
    private static boolean hasOverlap(long[] range1, long[] range2) {
        return !(range1[1] < range2[0] || range2[1] < range1[0]);
    }
    
    private static void processInput(Scanner in) {
        mapNames = new String[]{"seed-to-soil", "soil-to-fertilizer", "fertilizer-to-water", "water-to-light", "light-to-temperature", "temperature-to-humidity", "humidity-to-location"};
        seeds = Arrays.stream(in.nextLine().split(": ")[1].split(" ")).mapToLong(Long::parseLong).toArray();
        mappings = new HashMap<>();
        for (String key : mapNames) {
            mappings.put(key, new ArrayList<>());
        }
        in.nextLine();
        
        int mapId = 0;
        while (in.hasNextLine()) {
            in.nextLine();
            String key = mapNames[mapId];
            List<long[]> ranges = mappings.get(key);
            
            long lowEnd = Long.MAX_VALUE;
            long highEnd = Long.MIN_VALUE;
            
            while (in.hasNextLine()) {
                String line = in.nextLine();
                if (line.isEmpty()) {
                    mapId++;
                    break;
                }
                String[] entry = line.split(" ");
                long src = Long.parseLong(entry[1]);
                long dst = src + Long.parseLong(entry[2]) - 1; // stupid off by 1
                long offBy = Long.parseLong(entry[0]) - src;
                ranges.add(new long[]{src, dst, offBy});
                
                lowEnd = Math.min(lowEnd, src - 1);
                highEnd = Math.max(highEnd, dst + 1);
            }
            // just trying to cover all ranges
            ranges.add(new long[]{Long.MIN_VALUE, lowEnd, (long) 0});
            ranges.add(new long[]{highEnd, Long.MAX_VALUE, (long) 0});
        }
    }
}
