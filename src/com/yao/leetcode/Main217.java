package com.yao.leetcode;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Daniel:)
 *
 * 存在重复元素
 *
 * 给定一个整数数组，判断是否存在重复元素。
 * 如果任意一值在数组中出现至少两次，函数返回 true 。如果数组中每个元素都不相同，则返回 false 。
 *
 * 示例 1:
 * 输入: [1,2,3,1]
 * 输出: true
 *
 * 示例 2:
 * 输入: [1,2,3,4]
 * 输出: false
 *
 * 示例 3:
 * 输入: [1,1,1,3,3,4,3,2,4,2]
 * 输出: true
 */
public class Main217 {
    public boolean containsDuplicate(int[] nums) {
        Set<Integer> set = new HashSet<>(nums.length);
        for (int x: nums) {
            if (set.contains(x)) return true;
            set.add(x);
        }
        return false;
    }

    public boolean test(int[] nums) {
        HashSet<Integer> set = new HashSet<>(nums.length);
        for (int currentNum : nums) {
            if (set.contains(currentNum)) {
                return true;
            } else {
                set.add(currentNum);
            }
        }
        return false;
    }
}
