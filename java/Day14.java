import java.io.*;
import java.util.*;

public class Day14 {
    static int row;
    static int col;
    static char[][] grid;
    
    public static void main(String[] args) throws FileNotFoundException {
        long startTime = System.currentTimeMillis();
        Scanner in = new Scanner(new FileReader("input/day14.txt"));
        processInput(in);
        System.out.println("Part I  109638 : " + part1());
        System.out.println("Part II 102657 : " + part2(1000)); // brute forced it
        System.out.println("Execution time: " + (System.currentTimeMillis() - startTime) + "ms");
    }
    
    private static long part1() {
        for (int r = 0; r < row; r++) {
            for (int c = 0; c < col; c++) {
                if (grid[r][c] == '.') {
                    moveNorth(r, c);
                }
            }
        }
        return getDist();
    }
    
    
    private static long part2(long cycle) {
        for (int i = 0; i < cycle; i++) {
            for (int r = 0; r < row; r++) {
                for (int c = 0; c < col; c++) {
                    if (grid[r][c] == '.') {
                        moveNorth(r, c);
                    }
                }
            }
            for (int r = 0; r < row; r++) {
                for (int c = 0; c < col; c++) {
                    if (grid[r][c] == '.') {
                        moveWest(r, c);
                    }
                }
            }
            for (int r = 0; r < row; r++) {
                for (int c = 0; c < col; c++) {
                    if (grid[r][c] == '.') {
                        moveSouth(r, c);
                    }
                }
            }
            for (int r = 0; r < row; r++) {
                for (int c = 0; c < col; c++) {
                    if (grid[r][c] == '.') {
                        moveEast(r, c);
                    }
                }
            }
        }
        return getDist();
    }
    
    private static void moveWest(int r, int right) {
        int curr = right;
        while (curr < col) {
            while (curr < col && grid[r][curr] == '.') {
                curr++;
            }
            if (curr == col || grid[r][curr] == '#') {
                return;
            }
            grid[r][right] = 'O';
            grid[r][curr] = '.';
            right++;
        }
    }
    
    private static void moveEast(int r, int left) {
        int curr = left;
        while (curr >= 0) {
            while (curr >= 0 && grid[r][curr] == '.') {
                curr--;
            }
            if (curr < 0 || grid[r][curr] == '#') {
                return;
            }
            grid[r][left] = 'O';
            grid[r][curr] = '.';
            left--;
        }
    }
    
    private static void moveSouth(int bottom, int c) {
        int curr = bottom;
        while (curr >= 0) {
            while (curr >= 0 && grid[curr][c] == '.') {
                curr--;
            }
            if (curr < 0 || grid[curr][c] == '#') {
                return;
            }
            grid[bottom][c] = 'O';
            grid[curr][c] = '.';
            bottom--;
        }
    }
    
    private static void moveNorth(int top, int c) {
        int curr = top;
        while (curr < row) {
            while (curr < row && grid[curr][c] == '.') {
                curr++;
            }
            if (curr == row || grid[curr][c] == '#') {
                return;
            }
            grid[top][c] = 'O';
            grid[curr][c] = '.';
            top++;
        }
    }
    
    private static long getDist() {
        long res = 0;
        for (int r = 0; r < row; r++) {
            for (int c = 0; c < col; c++) {
                if (grid[r][c] == 'O') {
                    res += row - r;
                }
            }
        }
        return res;
    }
    
    private static void processInput(Scanner in) {
        List<char[]> input = new ArrayList<>();
        while (in.hasNextLine()) {
            input.add(in.nextLine().toCharArray());
        }
        row = input.size();
        col = input.get(0).length;
        grid = new char[row][col];
        for (int r = 0; r < row; r++) {
            grid[r] = input.get(r);
        }
    }
    
    private static void print() {
        for (char[] list : grid) {
            System.out.println(Arrays.toString(list));
        }
        System.out.println();
    }
}