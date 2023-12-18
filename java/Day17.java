import java.io.*;
import java.util.*;

public class Day17 {
    static int[][] direct = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
    static int[][] grid;
    static int row;
    static int col;
    
    public static void main(String[] args) throws FileNotFoundException {
        long startTime = System.currentTimeMillis();
        Scanner in = new Scanner(new FileReader("input/day17.txt"));
        processInput(in);
        System.out.println("Part I   942 : " + part1());
        System.out.println("Part II 1082 : " + part2());
        System.out.println("Execution time: " + (System.currentTimeMillis() - startTime) + "ms");
    }
    
    private static long part1() {
        PriorityQueue<Cell> pq = new PriorityQueue<>(Comparator.comparingLong(a -> a.heat));
        Set<String> visit = new HashSet<>(); // stupid visit set took me forever
        Cell start = new Cell(0, 0, 0, 0, 0, 0);
        pq.offer(start);
        
        while (!pq.isEmpty()) {
            Cell curr = pq.poll();
            
            if (curr.r == row - 1 && curr.c == col - 1) {
                return curr.heat;
            }
            
            String key = curr.r + "," + curr.c + "," + curr.dr + "," + curr.dc + "," + curr.n;
            if (visit.contains(key)) {
                continue;
            }
            visit.add(key);
            
            for (int[] dr : direct) {
                int rr = curr.r + dr[0];
                int cc = curr.c + dr[1];
                if (rr < 0 || cc < 0 || rr >= row || cc >= col || curr.dr == -dr[0] && curr.dc == -dr[1]) {
                    continue;
                }
                
                int newDir = 1;
                if (curr.dr == dr[0] && curr.dc == dr[1]) {
                    newDir += curr.n;
                }
                if (newDir > 3) {
                    continue;
                }
                
                pq.offer(new Cell(rr, cc, curr.heat + grid[rr][cc], dr[0], dr[1], newDir));
            }
        }
        
        return Long.MAX_VALUE;
    }
    
    private static long part2() {
        PriorityQueue<Cell> pq = new PriorityQueue<>(Comparator.comparingLong(a -> a.heat));
        Set<String> visit = new HashSet<>();
        Cell start = new Cell(0, 0, 0, 0, 0, 0);
        pq.offer(start);
        
        while (!pq.isEmpty()) {
            Cell curr = pq.poll();
            
            if (curr.r == row - 1 && curr.c == col - 1 && curr.n >= 4) {
                return curr.heat;
            }
            
            String key = curr.r + "," + curr.c + "," + curr.dr + "," + curr.dc + "," + curr.n;
            if (visit.contains(key)) {
                continue;
            }
            visit.add(key);
            
            for (int[] dr : direct) {
                int rr = curr.r + dr[0];
                int cc = curr.c + dr[1];
                if (rr < 0 || cc < 0 || rr >= row || cc >= col || curr.dr == -dr[0] && curr.dc == -dr[1]) {
                    continue;
                }
                
                if (curr.dr == dr[0] && curr.dc == dr[1]) {
                    if (curr.n < 10) {
                        pq.offer(new Cell(rr, cc, curr.heat + grid[rr][cc], dr[0], dr[1], curr.n + 1));
                    }
                } else if (curr.n >= 4 || curr.n == 0) {
                    pq.offer(new Cell(rr, cc, curr.heat + grid[rr][cc], dr[0], dr[1], 1));
                }
            }
        }
        return Long.MAX_VALUE;
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

class Cell {
    int r, c;
    long heat;
    int dr, dc;
    int n;
    
    Cell(int r, int c, long heat, int dr, int dc, int n) {
        this.r = r;
        this.c = c;
        this.heat = heat;
        this.dr = dr;
        this.dc = dc;
        this.n = n;
    }
}