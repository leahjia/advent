import java.io.*;
import java.util.*;

class Cell {
    int r;
    int c;
    long heat;
    int[] dir;
    int sameDir;
    
    Cell(int r, int c, long heat, int[] dir, int sameDir) {
        this.r = r;
        this.c = c;
        this.heat = heat;
        this.dir = dir;
        this.sameDir = sameDir;
    }
}

public class Day17 {
    static int[] up = {-1, 0};
    static int[] down = {1, 0};
    static int[] left = {0, -1};
    static int[] right = {0, 1};
    static int[][] grid;
    static int row;
    static int col;
    
    public static void main(String[] args) throws FileNotFoundException {
        long startTime = System.currentTimeMillis();
        Scanner in = new Scanner(new FileReader("input/day17_sample.txt"));
        processInput(in);
        System.out.println("Part I  [, 953] : " + part1());
        System.out.println("Part II ?? : " + part2());
        System.out.println("Execution time: " + (System.currentTimeMillis() - startTime) + "ms");
    }
    
    private static long part1() {
        PriorityQueue<Cell> pq = new PriorityQueue<>(Comparator.comparingLong(a -> a.heat));
        long[][][] visitedHeat = new long[row][col][4];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                Arrays.fill(visitedHeat[i][j], Long.MAX_VALUE);
            }
        }
        
        boolean[][][] visited = new boolean[row][col][4];
        Cell start = new Cell(0, 0, 0, new int[]{1, 0}, 0);
        pq.offer(start);
        visitedHeat[0][0][0] = 0;
        
        while (!pq.isEmpty()) {
            Cell curr = pq.poll();
            int r = curr.r;
            int c = curr.c;
            
            if (r == row - 1 && c == col - 1) {
                return curr.heat;
            }
            
            if (visited[r][c][curr.sameDir]) {
                continue;
            }
            visited[r][c][curr.sameDir] = true;
            
            for (int[] dr : getDirect(curr.dir)) {
                int rr = r + dr[0];
                int cc = c + dr[1];
                if (rr < 0 || cc < 0 || rr >= row || cc >= col) continue;
                
                int newDir = isSameDir(curr.dir, dr) ? (curr.sameDir + 1) : 1;
                if (newDir > 3) continue;
                
                long newHeat = curr.heat + grid[rr][cc];
                Cell newCell = new Cell(rr, cc, newHeat, dr, newDir);
                
                pq.offer(newCell);
            }
        }
        
        return Long.MAX_VALUE;
    }
    
    private static int[][] getDirect(int[] currDir) {
        if (isSameDir(currDir, up)) {
            return new int[][]{up, left, right};
        }
        if (isSameDir(currDir, down)) {
            return new int[][]{down, left, right};
        }
        if (isSameDir(currDir, left)) {
            return new int[][]{up, down, left};
        }
        if (isSameDir(currDir, right)) {
            return new int[][]{up, down, right};
        }
        return new int[0][];
    }
    
    private static boolean isSameDir(int[] a, int[] b) {
        return a[0] == b[0] && a[1] == b[1];
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