import java.io.*;
import java.util.*;

public class Day21 {
    static int garden;
    static int n;
    static int[] s;
    static char[][] grid;
    
    public static void main(String[] args) throws FileNotFoundException {
        long startTime = System.currentTimeMillis();
        Scanner in = new Scanner(new FileReader("input/day21.txt"));
        processInput(in);
        System.out.println("Part I  3758 : " + part1(64));
        System.out.println("Part II ?? : " + part2());
        System.out.println("Execution time: " + (System.currentTimeMillis() - startTime) + "ms");
    }
    
    private static long part1(int step) {
        int[][] direct = new int[][]{{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        Queue<int[]> q = new LinkedList<>();
        q.offer(s);
        
        while (!q.isEmpty() && step-- >= 0) {
            int iter = q.size();
            boolean[][] visit = new boolean[n][n];
            for (int i = 0; i < iter; i++) {
                int[] curr = q.poll();
                int r = curr[0];
                int c = curr[1];
                if (r < 0 || c < 0 || r >= n || c >= n || grid[r][c] == '#') {
                    continue;
                }
                if (step == -1) {
                    grid[r][c] = 'O';
                }
                if (visit[r][c]) {
                    continue;
                }
                visit[r][c] = true;
                for (int[] dr : direct) {
                    q.offer(new int[]{r + dr[0], c + dr[1]});
                }
            }
        }
        print(grid);
        return countGardens(grid);
    }
    
    private static int countGardens(char[][] matrix) {
        int count = 0;
        for (char[] list : matrix) {
            for (char ch : list) {
                if (ch == 'O') {
                    count++;
                }
            }
        }
        return count;
    }
    
    private static long part2() {
        long res = 0;
        return res;
    }
    
    private static void processInput(Scanner in) {
        List<char[]> input = new ArrayList<>();
        while (in.hasNextLine()) {
            char[] line = in.nextLine().toCharArray();
            for (int i = 0; i < line.length; i++) {
                char c = line[i];
                if (c == '.') {
                    garden++;
                }
                if (c == 'S') {
                    s = new int[]{n, i};
                }
            }
            input.add(line);
            n++;
        }
        grid = new char[n][n];
        for (int r = 0; r < n; r++) {
            grid[r] = input.get(r);
        }
    }
    
    private static void print(char[][] matrix) {
        for (char[] list : matrix) {
            System.out.println(Arrays.toString(list));
        }
        System.out.println();
    }
}