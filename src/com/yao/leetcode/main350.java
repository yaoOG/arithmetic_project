package com.yao.leetcode;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author Daniel:)
 *
 * 给定两个数组，编写一个函数来计算它们的交集。
 *
 * 示例 1：
 * 输入：nums1 = [1,2,2,1], nums2 = [2,2]
 * 输出：[2,2]
 *
 * 示例 2:
 * 输入：nums1 = [4,9,5], nums2 = [9,4,9,8,4]
 * 输出：[4,9]
 */
public class main350 {
    public int[] intersect(int[] nums1, int[] nums2) {
        HashMap<Integer, Integer> map = new HashMap<>();
        ArrayList<Integer> result = new ArrayList<>();
        for (int value : nums1) {
            if (map.containsKey(value))
                map.put(value, map.get(value) + 1);
            else
                map.put(value, 1);
        }

        for (int value : nums2) {
            if (map.containsKey(value) && map.get(value) > 0) {
                result.add(value);
                map.put(value, map.get(value) - 1);
            }
        }
        return result.stream().mapToInt(Integer::valueOf).toArray();
    }
}
