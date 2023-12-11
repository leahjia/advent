import java.io.*;
import java.util.*;

public class Day11 {
    static Map<Integer, Set<Integer>> rowToCol;
    static Map<Integer, Set<Integer>> colToRow;
    static int row;
    static int col;
    
    public static void main(String[] args) throws FileNotFoundException {
        long startTime = System.currentTimeMillis();
        Scanner in = new Scanner(new FileReader("input/day11.txt"));
        processInput(in);
        System.out.println("Part I       9522407 : " + sumDist(1)); // this affects the next call
        System.out.println("Part II 544723432977 : " + sumDist(999999));
        System.out.println("Execution time: " + (System.currentTimeMillis() - startTime) + "ms");
    }
    
    private static long sumDist(int scale) {
        expand(scale);
        List<int[]> list = new ArrayList<>();
        for (int key : rowToCol.keySet()) {
            for (int val : rowToCol.get(key)) {
                list.add(new int[]{key, val});
            }
        }
        long res = 0;
        for (int i = 0; i < list.size(); i++) {
            for (int j = i + 1; j < list.size(); j++) {
                res += getDist(list.get(i), list.get(j));
            }
        }
        return res;
    }
    
    private static long getDist(int[] a, int[] b) {
        return Math.abs(b[1] - a[1]) + Math.abs(b[0] - a[0]);
    }
    
    private static void processInput(Scanner in) {
        rowToCol = new HashMap<>();
        colToRow = new HashMap<>();
        while (in.hasNextLine()) {
            char[] chars = in.nextLine().toCharArray();
            col = chars.length;
            for (int c = 0; c < chars.length; c++) {
                if (chars[c] == '#') {
                    rowToCol.putIfAbsent(row, new HashSet<>());
                    rowToCol.get(row).add(c);
                    colToRow.putIfAbsent(c, new HashSet<>());
                    colToRow.get(c).add(row);
                }
            }
            row++;
        }
    }
    
    private static void expand(int add) {
        for (int r = row - 1; r >= 0; r--) {
            if (!rowToCol.containsKey(r)) {
                addRow(r, add);
            }
        }
        for (int c = col - 1; c >= 0; c--) {
            if (!colToRow.containsKey(c)) {
                addCol(c, add);
            }
        }
    }
    
    private static void addRow(int rowNumber, int add) {
        for (int r = row - 1; r > rowNumber; r--) {
            if (rowToCol.containsKey(r)) {
                rowToCol.putIfAbsent(r + add, new HashSet<>());
                for (int c : rowToCol.get(r)) {
                    rowToCol.get(r + add).add(c);
                    
                    colToRow.putIfAbsent(c, new HashSet<>());
                    colToRow.get(c).remove(r);
                    colToRow.get(c).add(r + add);
                }
                rowToCol.remove(r);
            }
        }
        row += add;
    }
    
    private static void addCol(int colNumber, int add) {
        for (int c = col - 1; c > colNumber; c--) {
            if (colToRow.containsKey(c)) {
                colToRow.putIfAbsent(c + add, new HashSet<>());
                for (int r : colToRow.get(c)) {
                    colToRow.get(c + add).add(r);
                    
                    rowToCol.putIfAbsent(r, new HashSet<>());
                    rowToCol.get(r).remove(c);
                    rowToCol.get(r).add(c + add);
                }
                colToRow.remove(c);
            }
        }
        col += add;
    }
}