package com.yao.offer2;

import java.util.*;

public class Solution61 {
    //使用最大堆时间复杂度为O(k的平方*logk)
    public List<List<Integer>> kSmallestPairs(int[] nums1, int[] nums2, int k) {
        Queue<int[]> maxHeap = new PriorityQueue<>((p1, p2) -> p2[0] + p2[1] - p1[0] - p1[1]);
        for (int i = 0; i < Math.min(k, nums1.length); i++) {
            for (int j = 0; j < Math.min(k, nums2.length); j++) {
                int curSum = nums1[i] + nums2[j];
                if (maxHeap.size() >= k) {
                    int[] root = maxHeap.peek();
                    if (root[0] + root[1] > curSum) {
                        maxHeap.poll();
                        maxHeap.offer(new int[]{nums1[i], nums2[j]});
                    }
                } else {
                    maxHeap.offer(new int[]{nums1[i], nums2[j]});
                }
            }
        }
        List<List<Integer>> result = new ArrayList<>();
        while (!maxHeap.isEmpty()) {
            int[] pollValues = maxHeap.poll();
            result.add(Arrays.asList(pollValues[0], pollValues[1]));
        }
        return result;
    }

    //使用最小堆时间复杂度为O(klogk)
    //构建小顶堆的时候用到nums1的每个元素和nums2的第一个元素构建的数对
    //while循环入小顶堆时，用的是最小和数对的nums1中的元素与nums2中的元素后面的元素
    public List<List<Integer>> kSmallestPairs2(int[] nums1, int[] nums2, int k) {
        List<List<Integer>> result = new ArrayList<>();
        //堆中每个元素是一个二维数组，数组第一个数字对应nums1中的下标，第二个数字对应nums2中的下标
        Queue<int[]> minHeap = new PriorityQueue<>((p1, p2) -> nums1[p1[0]] + nums2[p1[1]] - nums1[p2[0]] - nums2[p2[1]]);
        if (nums2.length > 0) {
            //构建大小为k的最小堆
            for (int i = 0; i < Math.min(k, nums1.length); i++) {
                //nums1中每个元素和nums2中的0下标元素组成的数对入堆
                minHeap.offer(new int[]{i, 0});
            }
        }
        //每次堆大小为k的最小堆
        while (k-- > 0 && !minHeap.isEmpty()) {
            int[] ids = minHeap.poll();
            result.add(Arrays.asList(nums1[ids[0]], nums2[ids[1]]));
            if (ids[1] < nums2.length - 1) {
                minHeap.offer(new int[]{ids[0], ids[1] + 1});
            }
        }
        return result;
    }

    public static void main(String[] args) {
        int[] nums1 = new int[]{1, 7, 11};
        int[] nums2 = new int[]{2, 4, 6};
        Solution61 solution61 = new Solution61();
        List<List<Integer>> lists = solution61.kSmallestPairs2(nums1, nums2, 3);
        lists.forEach(System.out::println);
    }
}