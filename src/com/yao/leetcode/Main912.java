package com.yao.leetcode;

import java.util.Stack;

/**
 * @author choo
 */
public class Main912 {

    public int[] sortArray(int[] nums) {
        sort(nums, 0, nums.length - 1);
        return nums;
    }

    public void sort(int[] nums, int left, int right) {
        int pivot;
        if (left >= right) {
            return;
        }
        Stack<Integer> stack = new Stack<>();
        stack.push(left);
        stack.push(right);
        while (!stack.isEmpty()) {
            right = stack.pop();
            left = stack.pop();
            pivot = partition(nums, left, right);
            if (left < pivot - 1) {
                stack.push(left);
                stack.push(pivot - 1);
            }
            if (right > pivot + 1) {
                stack.push(pivot + 1);
                stack.push(right);
            }
        }
    }

    public int partition(int[] nums, int left, int right) {

        int pivotValue = nums[left];
        while (right > left) {
            while (left < right && nums[right] >= pivotValue) {
                right--;
            }
            nums[left] = nums[right];
            while (left < right && nums[left] <= pivotValue) {
                left++;
            }
            nums[right] = nums[left];
        }
        nums[left] = pivotValue;
        return left;
    }
}
