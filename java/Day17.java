import java.io.*;
import java.util.*;
import java.util.regex.Pattern;

public class Day17 {
    static int[] up = {-1, 0};
    static int[] down = {1, 0};
    static int[] left = {0, -1};
    static int[] right = {0, 1};
    static int[][] direct = {up, down, left, right};
    static int[][] grid;
    static int row;
    static int col;
    
    public static void main(String[] args) throws FileNotFoundException {
        long startTime = System.currentTimeMillis();
        Scanner in = new Scanner(new FileReader("input/day17_sample.txt"));
        processInput(in);
        System.out.println("Part I  ?? : " + part1());
        System.out.println("Part II ?? : " + part2());
        System.out.println("Execution time: " + (System.currentTimeMillis() - startTime) + "ms");
    }
    
    static long minHeat;
    static long[][][] cache;
    
    private static long part1() {
        minHeat = Long.MAX_VALUE;
        cache = new long[row][col][4];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                Arrays.fill(cache[i][j], Long.MAX_VALUE);
            }
        }
        //        backtrack(0, 1, new boolean[row][col], 1, new int[]{0, 1}, 0);
        backtrack(1, 0, new boolean[row][col], 1, new int[]{1, 0}, 0);
        return minHeat;
    }
    
    private static void backtrack(int r, int c, boolean[][] visit, int sameDir, int[] currDir, long currHeat) {
        if (r < 0 || c < 0 || r >= row || c >= col || visit[r][c] || sameDir > 3 || currHeat >= minHeat || cache[r][c][sameDir] <= currHeat) {
            return;
        }
        visit[r][c] = true;
        currHeat += grid[r][c];
        cache[r][c][sameDir] = currHeat; // we know it's lower otherwise would've returned early
        
        if (r == row - 1 && c == col - 1) {
            // System.out.println(currHeat + " by going " + path);
            minHeat = Math.min(minHeat, currHeat);
        } else {
            for (int[] dr : getDirect(currDir)) {
                int newCount = Arrays.equals(dr, currDir) ? (sameDir + 1) : 1;
                backtrack(r + dr[0], c + dr[1], visit, newCount, dr, currHeat);
            }
        }
        visit[r][c] = false;
    }
    
    private static int[][] getDirect(int[] currDir) {
        if (Arrays.equals(currDir, up)) {
            return new int[][]{up, left, right};
        }
        if (Arrays.equals(currDir, down)) {
            return new int[][]{down, left, right};
        }
        if (Arrays.equals(currDir, left)) {
            return new int[][]{up, down, left};
        }
        if (Arrays.equals(currDir, right)) {
            return new int[][]{up, down, right};
        }
        return new int[0][];
    }
    
    private static long part2() {
        long res = 0;
        return res;
    }
    
    private static void processInput(Scanner in) {
        List<char[]> input = new ArrayList<>();
        while (in.hasNextLine()) {
            char[] chars = in.nextLine().toCharArray();
            col = chars.length;
            row++;
            input.add(chars);
        }
        grid = new int[row][col];
        for (int r = 0; r < row; r++) {
            for (int c = 0; c < col; c++) {
                grid[r][c] = Character.getNumericValue(input.get(r)[c]);
            }
        }
    }
}