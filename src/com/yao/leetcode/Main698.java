package com.yao.leetcode;

/**
 * @author Daniel:)
 *
 * 划分为K个相等的子集
 * 给定一个整数数组nums 和一个正整数 k，找出是否有可能把这个数组分成 k 个非空子集，其总和都相等。
 *
 * 示例 1：
 *
 * 输入： nums = [4, 3, 2, 3, 5, 2, 1], k = 4
 * 输出： True
 * 说明： 有可能将其分成 4 个子集（5），（1,4），（2,3），（2,3）等于总和。
 *
 */
public class Main698 {

    public boolean canPartitionKSubsets(int[] nums, int k) {
        int sum = 0;
        for (int num : nums)
            sum += num;
        if (k <= 0 || sum % k != 0)
            return false;
        int[] visited = new int[nums.length];
        return canPartition(nums, visited, 0, k, 0, 0, sum / k);
    }

    public boolean canPartition(int[] nums, int[] visited, int startIndex, int k, int curSum, int curNum, int target) {
        if (k == 1)
            return true;
        if (curSum == target && curNum > 0)
            return canPartition(nums, visited, 0, k - 1, 0, 0, target);
        for (int i = startIndex; i < nums.length; i++) {
            if (visited[i] == 0) {
                visited[i] = 1;
                if (canPartition(nums, visited, i + 1, k, curSum + nums[i], curNum++, target))
                    return true;
                visited[i] = 0;
            }
        }
        return false;
    }


}
