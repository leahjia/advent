import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day3 {
    public static void main(String[] args) throws FileNotFoundException {
        long startTime = System.currentTimeMillis();
        
        Scanner in = new Scanner(new FileReader("input/day3.txt"));
        List<String> input = new ArrayList<>();
        while (in.hasNextLine()) input.add(in.nextLine());
        
        int total = part11(input);
        int remove = part1(input);
        System.out.println("total: " + total);
        System.out.println("removing: " + remove);
        System.out.println("Part I : " + (total - remove));
        //        System.out.println("Part II: " + part2(input));
        //        System.out.println("Execution time: " + (System.currentTimeMillis() - startTime) + "ms");
    }
    
    private static int part11(List<String> input) {
        int row = input.size();
        int col = input.get(0).length();
        char[][] grid = new char[row][input.get(0).length()];
        for (int r = 0; r < row; r++) {
            grid[r] = input.get(r).toCharArray();
        }
        for (int r = 0; r < row; r++) {
            for (int c = 0; c < col; c++) {
                if (!Character.isDigit(grid[r][c]) && grid[r][c] != '.') {
                    grid[r][c] = '.';
                }
            }
        }
        
        Pattern pattern = Pattern.compile("\\d+");
        int total = 0;
        for (char[] chars : grid) {
            String line = new String(chars);
            Matcher matcher = pattern.matcher(line);
            while (matcher.find()) {
                int newNum = Integer.parseInt(matcher.group());
                //                System.out.println(newNum);
                total += newNum;
            }
        }
        return total;
    }
    
    private static int part1(List<String> input) {
        int row = input.size();
        int col = input.get(0).length();
        char[][] grid = new char[row][input.get(0).length()];
        for (int r = 0; r < row; r++) {
            grid[r] = input.get(r).toCharArray();
        }
        
        int[][] direct = {{1, 0}, {0, 1}, {-1, 0}, {0, -1}, {1, 1}, {-1, -1}, {1, -1}, {-1, 1}};
        for (int r = 0; r < row; r++) {
            for (int c = 0; c < col; c++) {
                //  && grid[r][c] != 'T'
                if (!Character.isDigit(grid[r][c]) && grid[r][c] != '.') {
                    //                    System.out.println(r + ", " + c + ": " + grid[r][c]);
                    for (int[] dr : direct) {
                        int rr = r + dr[0];
                        int cc = c + dr[1];
                        if (rr >= 0 && rr < row && cc >= 0 && cc < col && Character.isDigit(grid[rr][cc])) {
                            grid[rr][cc] = 'T';
                        }
                    }
                    grid[r][c] = 'T';
                }
            }
        }
        
        int res = 0;
        for (int r = 0; r < row; r++) {
            for (int c = 0; c < col; c++) {
                if (grid[r][c] == 'T') {
                    int idx = c + 1;
                    while (idx < col && Character.isDigit(grid[r][idx])) {
                        grid[r][idx] = 'T';
                        idx++;
                    }
                    idx = c - 1;
                    while (idx >= 0 && Character.isDigit(grid[r][idx])) {
                        grid[r][idx] = 'T';
                        idx--;
                    }
                }
                if (Character.isDigit(grid[r][c])) {
                    StringBuilder part = new StringBuilder();
                    int idx = c;
                    while (idx < col) {
                        if (grid[r][idx] == '.') {
                            c = idx;
                            System.out.println(Integer.parseInt(part.toString()));
                            res += Integer.parseInt(part.toString());
                            break;
                        } else if (grid[r][idx] == 'T') {
                            break;
                        } else {
                            part.append(grid[r][idx]);
                            //                            grid[r][idx] = 'N';
                            idx++;
                        }
                    }
                }
            }
        }
        
        printer(grid);
        
        return res; // not 515900, 508206
    }
    
    private static void printer(char[][] grid) {
        for (char[] chars : grid) {
            System.out.println(Arrays.toString(chars));
        }
    }
    
    private static int part2(List<String> input) {
        return -1;
    }
}
