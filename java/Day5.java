import java.io.*;
import java.util.*;

public class Day5 {
    static String[] mapStr = new String[]{"seed-to-soil", "soil-to-fertilizer", "fertilizer-to-water", "water-to-light", "light-to-temperature", "temperature-to-humidity", "humidity-to-location"};
    static long[] seeds;
    static Map<String, Map<long[], Long>> maps;
    
    public static void main(String[] args) throws FileNotFoundException {
        long startTime = System.currentTimeMillis();
        Scanner in = new Scanner(new FileReader("input/day5.txt"));
        processInput(in);
        // System.out.println("Part I  424490994: " + part1());
        System.out.println("Part II ??: " + part2());
        System.out.println("Execution time: " + (System.currentTimeMillis() - startTime) + "ms");
    }
    
    private static long part1() {
        long res = Long.MAX_VALUE;
        for (long seed : seeds) {
            long next = seed;
            for (String mapName : mapStr) {
                next = getMapTo(maps.get(mapName), next);
            }
            res = Math.min(res, next);
        }
        return res;
    }
    
    private static long part2() {
        long res = Long.MAX_VALUE;
        List<long[]> pairs = new ArrayList<>();
        for (int i = 0; i < seeds.length - 1; i += 2) {
            pairs.add(new long[]{seeds[i], seeds[i + 1]});
        }
        
        Queue<Long> pq = new PriorityQueue<>();
        Map<Long, Long> map = new HashMap<>();
        for (long[] pair : pairs) {
            for (long seed = pair[0]; seed < pair[0] + pair[1]; seed++) {
                if (map.containsKey(seed)) {
                    continue;
                }
                long next = seed;
                for (String mapName : mapStr) {
                    next = getMapTo(maps.get(mapName), next);
                }
                res = Math.min(res, next);
                map.put(seed, next);
                //                pq.offer(next);
            }
        }
        return res;
    }
    
    private static void processInput(Scanner in) {
        String[] temp = in.nextLine().split(": ")[1].split(" ");
        seeds = new long[temp.length];
        for (int i = 0; i < temp.length; i++) {
            seeds[i] = Long.parseLong(temp[i]);
        }
        
        // <range, diff>
        maps = new HashMap<>();
        for (String key : mapStr) {
            maps.put(key, new HashMap<>());
        }
        in.nextLine();
        
        int mapId = 0;
        while (in.hasNextLine()) {
            in.nextLine();
            String key = mapStr[mapId];
            Map<long[], Long> currMap = maps.get(key);
            while (in.hasNextLine()) {
                String line = in.nextLine();
                if (line.length() == 0) {
                    mapId++;
                    break;
                }
                String[] entry = line.split(" ");
                long dst = Long.parseLong(entry[0]);
                long src = Long.parseLong(entry[1]);
                long range = Long.parseLong(entry[2]);
                currMap.put(new long[]{src, src + range - 1}, dst - src); // stupid off by 1
            }
        }
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
}
