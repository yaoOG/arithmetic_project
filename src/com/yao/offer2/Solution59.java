package com.yao.offer2;

import java.util.PriorityQueue;

public class Solution59 {
    class KthLargest {
        private PriorityQueue<Integer> minHeap;
        private int size;

        public KthLargest(int k, int[] nums) {
            size = k;
            minHeap = new PriorityQueue<>();
            for (int num : nums) {
                add(num);
            }

        }

        public int add(int val) {
            if (minHeap.size() < size) {
                minHeap.offer(val);
            } else if (val > minHeap.peek()) {
                minHeap.poll();
                minHeap.offer(val);
            }
            return minHeap.peek();
        }
    }
}
