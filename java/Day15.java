import java.io.*;
import java.util.*;

public class Day15 {
    static List<String> input;
    static final int LEN = 256;
    
    public static void main(String[] args) throws FileNotFoundException {
        long startTime = System.currentTimeMillis();
        Scanner in = new Scanner(new FileReader("input/day15.txt"));
        processInput(in);
        System.out.println("Part I  503154 : " + hash());
        System.out.println("Part II 251353 : " + part2());
        System.out.println("Execution time: " + (System.currentTimeMillis() - startTime) + "ms");
    }
    
    private static long hash() {
        long res = 0;
        for (String line : input) {
            res += getHash(line);
        }
        return res;
    }
    
    private static int getHash(String line) {
        int curr = 0;
        for (char ch : line.toCharArray()) {
            curr += ch;
            curr *= 17;
            curr %= LEN;
        }
        return curr;
    }
    
    static Node[] boxes;
    
    private static int part2() {
        boxes = new Node[LEN];
        for (int i = 0; i < LEN; i++) {
            boxes[i] = new Node("bruh", i);
        }
        
        int res = 0;
        for (String token : input) {
            if (token.endsWith("-")) {
                remove(token.substring(0, token.length() - 1));
            } else {
                String[] split = token.split("=");
                insert(split[0], Integer.parseInt(split[1]));
            }
        }
        
        for (int b = 0; b < LEN; b++) {
            Node head = boxes[b].next;
            int slot = 1;
            while (head != null) {
                res += (b + 1) * slot++ * head.focal;
                head = head.next;
            }
        }
        
        return res;
    }
    
    private static void remove(String label) {
        int hashCode = getHash(label);
        Node head = boxes[hashCode];
        while (head.next != null) {
            if (head.next.label.equals(label)) {
                head.next = head.next.next;
                return;
            }
            head = head.next;
        }
    }
    
    private static void insert(String label, int focal) {
        int hashCode = getHash(label);
        Node head = boxes[hashCode];
        while (head.next != null) {
            if (head.next.label.equals(label)) {
                head.next.focal = focal;
                return;
            }
            head = head.next;
        }
        head.next = new Node(label, focal);
    }
    
    private static void processInput(Scanner in) {
        input = new ArrayList<>();
        while (in.hasNextLine()) {
            input.addAll(Arrays.asList(in.nextLine().split(",")));
        }
    }
}

class Node {
    Node next;
    String label;
    int focal;
    
    Node(String label, int focal) {
        this.label = label;
        this.focal = focal;
    }
}