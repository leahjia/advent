import java.io.*;
import java.util.*;

public class Day10 {
    static int[] up = {-1, 0};
    static int[] down = {1, 0};
    static int[] left = {0, -1};
    static int[] right = {0, 1};
    static int[][] direct = {up, down, left, right};
    static Map<Character, int[][]> directMap;
    static int row;
    static int col;
    static int[] s;
    static char[][] grid;
    static char[][] cleanGrid;
    static long loopLength;
    static Set<Character> pointsUp = new HashSet<>(Arrays.asList('S', '|', 'J', 'L'));
    static Set<Character> pointsDown = new HashSet<>(Arrays.asList('S', '|', 'F', '7'));
    static Set<Character> pointsLeft = new HashSet<>(Arrays.asList('S', '-', 'J', '7'));
    static Set<Character> pointsRight = new HashSet<>(Arrays.asList('S', '-', 'F', 'L'));
    
    public static void main(String[] args) throws FileNotFoundException {
        long startTime = System.currentTimeMillis();
        Scanner in = new Scanner(new FileReader("input/day10.txt"));
        processInput(in);
        loopLength = part1();
        System.out.println("Part I  7005 : " + loopLength);
        System.out.println("Part II  417 : " + part2());
        System.out.println("Execution time: " + (System.currentTimeMillis() - startTime) + "ms");
    }
    
    private static long part1() {
        long res = 0;
        Queue<int[]> q = new LinkedList<>();
        q.offer(s);
        boolean[][] visit = new boolean[row][col];
        while (!q.isEmpty()) {
            int[] curr = q.poll();
            cleanGrid[curr[0]][curr[1]] = grid[curr[0]][curr[1]];
            int R = curr[0];
            int C = curr[1];
            for (int[] dr : directMap.get(grid[R][C])) {
                int r = R + dr[0];
                int c = C + dr[1];
                if (r < 0 || c < 0 || r == row || c == col || visit[r][c]) {
                    continue;
                }
                visit[r][c] = true;
                if (grid[r][c] != '.') {
                    int[] next = new int[]{r, c};
                    if (isConnected(curr, next)) {
                        q.offer(next);
                        res++;
                    }
                }
            }
        }
        return res / 2;
    }
    
    private static boolean isConnected(int[] a, int[] b) {
        char c1 = grid[a[0]][a[1]];
        char c2 = grid[b[0]][b[1]];
        return pointsUp.contains(c1) && pointsDown.contains(c2) ||
                pointsDown.contains(c1) && pointsUp.contains(c2) ||
                pointsLeft.contains(c1) && pointsRight.contains(c2) ||
                pointsRight.contains(c1) && pointsLeft.contains(c2);
    }
    
    private static long part2() {
        return getArea(getVertices()) - loopLength + 1;
    }
    
    private static long getArea(int[][] vertices) {
        long sum1 = 0;
        long sum2 = 0;
        int len = vertices.length;
        for (int i = 0; i < len; i++) {
            int next = (i + 1) % len;
            int xCurr = vertices[i][0];
            int yCurr = vertices[i][1];
            int xNext = vertices[next][0];
            int yNext = vertices[next][1];
            sum1 += (long) xCurr * yNext;
            sum2 += (long) yCurr * xNext;
        }
        return Math.abs(sum1 - sum2) / 2;
    }
    
    // must be in order...
    private static int[][] getVertices() {
        List<int[]> vertices = new ArrayList<>();
        vertices.add(s);
        
        int[] curr = s;
        int[] next = getNextVertex(null, curr);
        assert next != null;
        int[] dir = new int[]{next[0] - curr[0], next[1] - curr[1]};
        
        do {
            next = getNextVertex(dir, curr);
            if (!Arrays.equals(next, s) && next != null) {
                vertices.add(next);
            }
            if (next != null) {
                dir = new int[]{next[0] - curr[0], next[1] - curr[1]};
            }
            curr = next;
        } while (!Arrays.equals(curr, s) && next != null);
        
        int[][] res = new int[vertices.size()][];
        for (int i = 0; i < vertices.size(); i++) {
            res[i] = vertices.get(i);
        }
        return res;
    }
    
    private static int[] getNextVertex(int[] dir, int[] curr) {
        int R = curr[0];
        int C = curr[1];
        char ch = cleanGrid[R][C];
        if (ch == 'S') {
            for (int[] dr : direct) {
                int r = R + dr[0];
                int c = C + dr[1];
                if (r >= 0 && c >= 0 && r < row && c < col && cleanGrid[r][c] != '\u0000') {
                    // make sure it's actually pointing to S
                    if (Arrays.equals(dr, up) && pointsDown.contains(cleanGrid[r][c]) ||
                            Arrays.equals(dr, down) && pointsUp.contains(cleanGrid[r][c]) ||
                            Arrays.equals(dr, left) && pointsRight.contains(cleanGrid[r][c]) ||
                            Arrays.equals(dr, right) && pointsLeft.contains(cleanGrid[r][c])) {
                        return new int[]{r, c};
                    }
                }
            }
        }
        if (Arrays.equals(dir, up)) {
            if (ch == '|') {
                return new int[]{R + up[0], C + up[1]};
            }
            if (ch == 'F') {
                return new int[]{R + right[0], C + right[1]};
            }
            if (ch == '7') {
                return new int[]{R + left[0], C + left[1]};
            }
        }
        
        if (Arrays.equals(dir, down)) {
            if (ch == '|') {
                return new int[]{R + down[0], C + down[1]};
            }
            if (ch == 'L') {
                return new int[]{R + right[0], C + right[1]};
            }
            if (ch == 'J') {
                return new int[]{R + left[0], C + left[1]};
            }
        }
        
        if (Arrays.equals(dir, right)) {
            if (ch == '-') {
                return new int[]{R + right[0], C + right[1]};
            }
            if (ch == 'J') {
                return new int[]{R + up[0], C + up[1]};
            }
            if (ch == '7') {
                return new int[]{R + down[0], C + down[1]};
            }
        }
        
        if (Arrays.equals(dir, left)) {
            if (ch == '-') {
                return new int[]{R + left[0], C + left[1]};
            }
            if (ch == 'L') {
                return new int[]{R + up[0], C + up[1]};
            }
            if (ch == 'F') {
                return new int[]{R + down[0], C + down[1]};
            }
        }
        return null;
    }
    
    private static void processInput(Scanner in) {
        directMap = new HashMap<>() {{
            put('|', new int[][]{up, down});
            put('-', new int[][]{left, right});
            put('L', new int[][]{up, right});
            put('J', new int[][]{up, left});
            put('7', new int[][]{left, down});
            put('F', new int[][]{right, down});
            put('.', new int[0][0]);
            put('S', new int[][]{up, down, left, right});
        }};
        
        List<char[]> input = new ArrayList<>();
        while (in.hasNextLine()) {
            char[] chars = in.nextLine().toCharArray();
            for (int c = 0; c < chars.length; c++) {
                if (chars[c] == 'S') {
                    s = new int[]{row, c};
                }
            }
            row++;
            input.add(chars);
        }
        col = input.get(0).length;
        grid = new char[row][col];
        cleanGrid = new char[row][col];
        for (int r = 0; r < row; r++) {
            grid[r] = input.get(r);
        }
    }
}