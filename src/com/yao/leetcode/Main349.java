package com.yao.leetcode;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * @author Daniel:)
 *
 * 给定两个数组，编写一个函数来计算它们的交集。
 *
 * 示例 1：
 *
 * 输入：nums1 = [1,2,2,1], nums2 = [2,2]
 * 输出：[2]
 * 示例 2：
 *
 * 输入：nums1 = [4,9,5], nums2 = [9,4,9,8,4]
 * 输出：[9,4]
 */
public class Main349 {
    public int[] intersection(int[] nums1, int[] nums2) {
        HashSet<Integer> set = new HashSet<>();
        ArrayList<Integer> res = new ArrayList<>();
        //Add all elements to set from array 1
        for (int value : nums1)
            set.add(value);
        for (int value : nums2) {
            // If present in array 2 then add to res and remove from set
            if (set.contains(value)) {
                res.add(value);
                set.remove(value);
            }
        }
        return res.stream().mapToInt(Integer::valueOf).toArray();
    }
}
