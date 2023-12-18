import java.io.*;
import java.util.*;

public class Day13 {
    
    public static void main(String[] args) throws FileNotFoundException {
        long startTime = System.currentTimeMillis();
        Scanner in = new Scanner(new FileReader("input/day13.txt"));
        System.out.println("Part I  30802 : " + part1(in));
        System.out.println("Part II ?? : " + part2());
        System.out.println("Execution time: " + (System.currentTimeMillis() - startTime) + "ms");
    }
    
    private static long part1(Scanner in) {
        long res = 0;
        while (in.hasNextLine()) {
            List<String> rows = new ArrayList<>();
            while (in.hasNextLine()) {
                String line = in.nextLine();
                if (line.isEmpty()) {
                    break;
                }
                rows.add(line);
            }
            List<String> cols = new ArrayList<>();
            for (int c = 0; c < rows.get(0).length(); c++) {
                StringBuilder str = new StringBuilder();
                for (String row : rows) {
                    str.append(row.charAt(c));
                }
                cols.add(str.toString());
            }
            res += getReflect(rows, cols);
        }
        return res;
    }
    
    private static long getReflect(List<String> rows, List<String> cols) {
        long reflect = 100 * getReflectHelper(rows);
        // System.out.println("reflect for rows: " + reflect);
        if (reflect < 0) {
            reflect = getReflectHelper(cols);
            // System.out.println("    reflect for cols: " + reflect);
        }
        assert reflect > 0;
        return reflect;
    }
    
    private static long getReflectHelper(List<String> lines) {
        for (int l = 0; l < lines.size() - 1; l++) {
            if (lines.get(l).equals(lines.get(l + 1)) && isReflection(l, l + 1, lines)) {
                return l + 1;
            }
        }
        return -1;
    }
    
    private static boolean isReflection(int l, int r, List<String> lines) {
        while (l >= 0 && r < lines.size()) {
            if (!lines.get(l--).equals(lines.get(r++))) {
                return false;
            }
        }
        return true;
    }
    
    private static long part2() {
        long res = 0;
        return res;
    }
}