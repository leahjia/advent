import java.io.*;
import java.util.*;

public class Day16 {
    static int row;
    static int col;
    static char[][] grid;
    static int[] up = {-1, 0};
    static int[] down = {1, 0};
    static int[] left = {0, -1};
    static int[] right = {0, 1};
    
    public static void main(String[] args) throws FileNotFoundException {
        long startTime = System.currentTimeMillis();
        Scanner in = new Scanner(new FileReader("input/day16.txt"));
        processInput(in);
        System.out.println("Part I  8098 : " + part1(new Beam(new int[]{0, 0}, right)));
        System.out.println("Part II 8335 : " + part2());
        System.out.println("Execution time: " + (System.currentTimeMillis() - startTime) + "ms");
    }
    
    private static long part2() {
        long max = 0;
        for (int r = 0; r < row; r++) {
            max = Math.max(max, part1(new Beam(new int[]{r, 0}, right)));
            max = Math.max(max, part1(new Beam(new int[]{r, col - 1}, left)));
        }
        for (int c = 0; c < col; c++) {
            max = Math.max(max, part1(new Beam(new int[]{0, c}, down)));
            max = Math.max(max, part1(new Beam(new int[]{row - 1, c}, up)));
        }
        return max;
    }
    
    private static long part1(Beam beam) {
        Queue<Beam> q = new LinkedList<>();
        q.offer(beam);
        Set<String> visitCount = new HashSet<>();
        visitCount.add(beam.position[0] + "," + beam.position[1]);
        Set<String> visit = new HashSet<>();
        
        while (!q.isEmpty()) {
            Beam curr = q.poll();
            int r = curr.position[0];
            int c = curr.position[1];
            int dr = curr.direction[0];
            int dc = curr.direction[1];
            
            String key = r + "," + c + "," + dr + "," + dc;
            if (r < 0 || c < 0 || r >= row || c >= col || visit.contains(key)) {
                continue;
            }
            visitCount.add(r + "," + c);
            visit.add(key);
            
            char symbol = grid[r][c];
            if (symbol == '|' && dr == 0) {
                q.offer(add(curr, up));
                q.offer(add(curr, down));
            } else if (symbol == '-' && dc == 0) {
                q.offer(add(curr, left));
                q.offer(add(curr, right));
            } else if (symbol == '/' && Arrays.equals(curr.direction, right) || symbol == '\\' && Arrays.equals(curr.direction, left)) {
                q.offer(add(curr, up));
            } else if (symbol == '/' && Arrays.equals(curr.direction, left) || symbol == '\\' && Arrays.equals(curr.direction, right)) {
                q.offer(add(curr, down));
            } else if (symbol == '/' && Arrays.equals(curr.direction, down) || symbol == '\\' && Arrays.equals(curr.direction, up)) {
                q.offer(add(curr, left));
            } else if (symbol == '/' && Arrays.equals(curr.direction, up) || symbol == '\\' && Arrays.equals(curr.direction, down)) {
                q.offer(add(curr, right));
            } else {
                q.offer(add(curr, curr.direction));
            }
        }
        return visitCount.size();
    }
    
    private static Beam add(Beam curr, int[] dr) {
        return new Beam(new int[]{curr.position[0] + dr[0], curr.position[1] + dr[1]}, new int[]{dr[0], dr[1]});
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
    
    static class Beam {
        int[] position;
        int[] direction;
        
        Beam(int[] position, int[] direction) {
            this.position = position;
            this.direction = direction;
        }
    }
}