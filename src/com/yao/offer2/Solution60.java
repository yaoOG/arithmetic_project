package com.yao.offer2;

import java.util.*;

public class Solution60 {
    public int[] topKFrequent(int[] nums, int k) {
        HashMap<Integer,Integer> numToCount = new HashMap<>();
        for (int num : nums) {
            numToCount.put(num, numToCount.getOrDefault(num,0) + 1);
        }
        Queue<Map.Entry<Integer,Integer>> minHeap = new PriorityQueue<>(
                Comparator.comparingInt(Map.Entry::getValue)
        );
        for (Map.Entry<Integer,Integer> entry : numToCount.entrySet()) {
            if (minHeap.size() < k) {
                minHeap.offer(entry);
            } else {
                if (entry.getValue() > minHeap.peek().getValue()) {
                    minHeap.poll();
                    minHeap.offer(entry);
                }
            }
        }

        int[] result = new int[k];
        int i = 0;
        for (Map.Entry<Integer,Integer> entry : minHeap) {
            result[i++] = entry.getKey();
        }

        return result;

    }
}
