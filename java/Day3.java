import java.io.*;
import java.util.*;

public class Day3 {
    static char[][] grid;
    static int row;
    static int col;
    static final int[][] DIRECT = {{1, 0}, {0, 1}, {-1, 0}, {0, -1}, {1, 1}, {-1, -1}, {1, -1}, {-1, 1}};
    
    public static void main(String[] args) throws FileNotFoundException {
        long startTime = System.currentTimeMillis();
        Scanner in = new Scanner(new FileReader("input/day3.txt"));
        List<String> input = new ArrayList<>();
        while (in.hasNextLine()) {
            input.add(in.nextLine());
        }
        
        System.out.println("Part I  514969  : " + sumPartNumber(input));
        System.out.println("Part II 78915902: " + sumGearRatio(input));
        System.out.println("Execution time: " + (System.currentTimeMillis() - startTime) + "ms");
    }
    
    private static int sumPartNumber(List<String> input) {
        getGrid(input);
        int res = 0;
        for (int r = 0; r < row; r++) {
            for (int c = 0; c < col; c++) {
                if (!Character.isDigit(grid[r][c]) && grid[r][c] != '.') {
                    for (int[] dr : DIRECT) {
                        int rr = r + dr[0];
                        int cc = c + dr[1];
                        if (rr >= 0 && rr < row && cc >= 0 && cc < col && Character.isDigit(grid[rr][cc])) {
                            res += getSurroundingNum(rr, cc);
                        }
                    }
                }
            }
        }
        return res;
    }
    
    private static int sumGearRatio(List<String> input) {
        getGrid(input);
        int res = 0;
        List<Integer> currList = new ArrayList<>();
        for (int r = 0; r < row; r++) {
            for (int c = 0; c < col; c++) {
                if (grid[r][c] == '*') {
                    for (int[] dr : DIRECT) {
                        int rr = r + dr[0];
                        int cc = c + dr[1];
                        if (rr >= 0 && rr < row && cc >= 0 && cc < col && Character.isDigit(grid[rr][cc])) {
                            currList.add(getSurroundingNum(rr, cc));
                        }
                    }
                    if (currList.size() == 2) {
                        res += currList.get(0) * currList.get(1);
                    }
                    currList = new ArrayList<>();
                }
            }
        }
        return res;
    }
    
    private static int getSurroundingNum(int r, int c) {
        while (c - 1 >= 0 && Character.isDigit(grid[r][c - 1])) {
            c--;
        }
        StringBuilder res = new StringBuilder();
        while (c < col && Character.isDigit(grid[r][c])) {
            res.append(grid[r][c]);
            grid[r][c] = 'N';
            c++;
        }
        return Integer.parseInt(res.toString());
    }
    
    private static void getGrid(List<String> input) {
        row = input.size();
        col = input.get(0).length();
        grid = new char[row][input.get(0).length()];
        for (int r = 0; r < row; r++) {
            grid[r] = input.get(r).toCharArray();
        }
    }
}
