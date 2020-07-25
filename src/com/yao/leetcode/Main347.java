package com.yao.leetcode;

import java.util.*;

/**
 * @author Daniel:)
 *
 * 给定一个非空的整数数组，返回其中出现频率前 k 高的元素。
 *
 * 示例 1:
 * 输入: nums = [1,1,1,2,2,3], k = 2
 * 输出: [1,2]
 *
 * 示例 2:
 * 输入: nums = [1], k = 1
 * 输出: [1]
 */
public class Main347 {
    public int[] topKFrequent(int[] nums, int k) {
        HashMap<Integer, Integer> countMap = new HashMap<>();
        List<Integer> topK = new LinkedList<>();
        for (int currentNum : nums) {
            countMap.put(currentNum, countMap.getOrDefault(currentNum, 0) + 1);
        }
        PriorityQueue<Integer> heap = new PriorityQueue<>(Comparator.comparingInt(countMap::get).reversed());
        heap.addAll(countMap.keySet());
        for (int i = 1; i <=k; i++) {
            topK.add(heap.poll());
        }
        return topK.stream().mapToInt(Integer::valueOf).toArray();
    }

    public static void main(String[] args) {
        Main347 main347 = new Main347();
        int[] result = main347.topKFrequent(new int[]{1, 1, 1, 2, 2, 3}, 2);
    }

}
