package com.yao.leetcode;

/**
 * @author Daniel:)
 *
 * 在排序数组中查找元素的第一个和最后一个位置
 * 给定一个按照升序排列的整数数组 nums，和一个目标值 target。找出给定目标值在数组中的开始位置和结束位置。
 * 如果数组中不存在目标值 target，返回[-1, -1]。
 * 进阶：
 * 你可以设计并实现时间复杂度为O(log n)的算法解决此问题吗？
 *
 * 示例 1：
 * 输入：nums = [5,7,7,8,8,10], target = 8
 * 输出：[3,4]
 *
 * 示例2：
 * 输入：nums = [5,7,7,8,8,10], target = 6
 * 输出：[-1,-1]
 *
 * 示例 3：
 * 输入：nums = [], target = 0
 * 输出：[-1,-1]
 */
public class Main34 {

    /**
     * 二分查找
     *
     * 二分查找中，
     * 寻找 leftIdx  即为在数组中寻找第一个大于等于target 的下标，
     * 寻找 rightIdx 即为在数组中寻找第一个大于target的下标，然后将下标减一。
     *
     * 两者的判断条件不同，为了代码的复用，我们定义 binarySearch(nums, target, lower) 表示在nums 数组中二分查找
     * target 的位置，如果lower 为true，则查找第一个大于等于target 的下标，否则查找第一个大于target 的下标。
     *
     * 时间复杂度：O(logn) ，其中 n 为数组的长度。二分查找的时间复杂度为O(logn)，
     * 一共会执行两次，因此总时间复杂度为 O(logn)。
     *
     * 空间复杂度：O(1)O(1) 。只需要常数空间存放若干变量
     *
     * @param nums
     * @param target
     * @return
     */

    public int[] searchRange(int[] nums, int target) {
        int leftIdx = binarySearch(nums, target, true);
        int rightIdx = binarySearch(nums, target, false) - 1;
        if (leftIdx <= rightIdx && rightIdx < nums.length && nums[leftIdx] == target && nums[rightIdx] == target) {
            return new int[]{leftIdx, rightIdx};
        }
        return new int[]{-1, -1};
    }

    //如果lower为true，则查找第一个大于等于 target 的下标，否则查找第一个大于target 的下标。
    public int binarySearch(int[] nums, int target, boolean lower) {
        int left = 0, right = nums.length - 1;
        int ans = nums.length;
        while (left <= right) {
            int mid = left +(right - left) / 2;
            if (nums[mid] > target || (lower && nums[mid] >= target)) {
                right = mid - 1;
                ans = mid;
            } else {
                left = mid + 1;
            }
        }
        return ans;
    }
}
