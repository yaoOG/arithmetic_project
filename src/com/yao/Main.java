package com.yao;

/**
 * @author choo
 */
import java.util.*;
import java.io.*;

public class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String[] s = reader.readLine().split(" ");
        int n = Integer.valueOf(s[0]);
        int k = Integer.valueOf(s[1]);
        int[][] arr = new int[n][];
        for (int i = 0; i < n; i++) {
            String[] str = reader.readLine().split(" ");
            int m = str.length;
            arr[i] = new int[m];
            for (int j = 0; j < m; j++) {
                arr[i][j] = Integer.valueOf(str[j]);
            }
        }
        PriorityQueue<Tuple> maxHeap = new PriorityQueue<>((o1, o2) -> o2.val - o1.val);
        for (int i = 0; i < n; i++) {
            int len = arr[i].length;
            maxHeap.offer(new Tuple(i, len - 1, arr[i][len - 1]));
        }
        StringBuilder res = new StringBuilder();
        for (int j = 0; j < k; j++) {
            Tuple temp = maxHeap.poll();
            res.append(temp.val).append(" ");
            if (temp.index > 1) {
                maxHeap.add(new Tuple(temp.line, temp.index - 1, arr[temp.line][temp.index - 1]));
            }
        }

        System.out.println(res);
    }

    static class Tuple {

        int line;
        int index;
        int val;

        Tuple(int line, int index, int val) {
            this.line = line;
            this.index = index;
            this.val = val;
        }
    }
}
