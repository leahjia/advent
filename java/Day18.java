import java.io.*;
import java.util.*;

public class Day18 {
    static int[] up = {-1, 0};
    static int[] down = {1, 0};
    static int[] left = {0, -1};
    static int[] right = {0, 1};
    static List<Move> moves;
    
    public static void main(String[] args) throws FileNotFoundException {
        long startTime = System.currentTimeMillis();
        Scanner in = new Scanner(new FileReader("input/day18.txt"));
        processInput(in);
        System.out.println("Part I           56923 : " + getLava());
        System.out.println("Part II 66296566363189 : " + getLava());
        System.out.println("Execution time: " + (System.currentTimeMillis() - startTime) + "ms");
    }
    
    static int[] point;
    
    private static long getLava() {
        point = new int[]{0, 0};
        int[][] vertices = new int[moves.size()][];
        int i = 0;
        int boundary = 0;
        for (Move move : moves) {
            boundary += move.n;
            if (move.dr == 'U') {
                vertices[i++] = movePoint(move.n, up);
            } else if (move.dr == 'D') {
                vertices[i++] = movePoint(move.n, down);
            } else if (move.dr == 'L') {
                vertices[i++] = movePoint(move.n, left);
            } else {
                vertices[i++] = movePoint(move.n, right);
            }
        }
        return getArea(vertices) + boundary / 2 + 1;
    }
    
    private static int[] movePoint(int n, int[] direct) {
        point[0] += n * direct[0];
        point[1] += n * direct[1];
        return new int[]{point[0], point[1]};
    }
    
    public static long getArea(int[][] vertices) {
        long sum = 0;
        int len = vertices.length;
        for (int i = 0; i < len; i++) {
            int[] curr = vertices[i];
            int[] next = vertices[(i + 1) % len];
            sum += (long) curr[0] * next[1] - (long) curr[1] * next[0];
        }
        return Math.abs(sum) / 2;
    }
    
    private static void processInput(Scanner in) {
        moves = new ArrayList<>();
        char[] direction = new char[]{'R', 'D', 'L', 'U'};
        while (in.hasNextLine()) {
            String[] line = in.nextLine().split(" ");
            //            char dr = line[0].charAt(0);
            //            int n = Integer.parseInt(line[1]);
            
            // part II new direct
            String color = line[2];
            int n = (Integer.parseInt(color.substring(2, color.length() - 2), 16));
            char dr = direction[Character.getNumericValue(color.charAt(color.length() - 2))];
            
            moves.add(new Move(dr, n));
        }
    }
}

class Move {
    char dr;
    int n;
    
    Move(char dr, int n) {
        this.dr = dr;
        this.n = n;
    }
}