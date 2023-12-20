import java.io.*;
import java.util.*;

public class Day13 {
    public static void main(String[] args) throws FileNotFoundException {
        long startTime = System.currentTimeMillis();
        System.out.println("Part I  30802 : " + getReflect(new Scanner(new FileReader("input/day13.txt")), 0));
        System.out.println("Part II 37876 : " + getReflect(new Scanner(new FileReader("input/day13.txt")), 1));
        System.out.println("Execution time: " + (System.currentTimeMillis() - startTime) + "ms");
    }
    
    private static int getReflect(Scanner in, int diff) {
        int res = 0;
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
            int rowsDiff = getReflectHelper(rows, diff);
            if (rowsDiff > 0) {
                res += 100 * rowsDiff;
            } else {
                res += getReflectHelper(cols, diff);
            }
        }
        return res;
    }
    
    private static int getReflectHelper(List<String> lines, int diff) {
        for (int i = 0; i < lines.size() - 1; i++) {
            int count = 0;
            int l = i;
            int r = i + 1;
            while (l >= 0 && r < lines.size()) {
                count += getLineDiff(lines.get(l--), lines.get(r++));
            }
            if (count == diff) {
                return i + 1;
            }
        }
        return -1;
    }
    
    private static int getLineDiff(String a, String b) {
        int count = 0;
        for (int i = 0; i < a.length(); i++) {
            if (a.charAt(i) != b.charAt(i)) {
                count++;
            }
        }
        return count;
    }
}