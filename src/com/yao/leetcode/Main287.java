package com.yao.leetcode;

public class Main287 {

    public static void main(String[] args) {
        Main287 main287 = new Main287();
        int[] nums = new int[]{1, 3, 4, 2, 2};
        main287.findDuplicate(nums);


    }

    public int findDuplicate(int[] nums) {
        int left = 0;
        int right = nums.length;
        while (left < right) {
            int mid = left + (right - left)/2;
            int count = 0;
            for (int num : nums) {
                if (num <= mid) {
                    count++;
                }
            }
            if (count > mid) {
                right = mid;
            }else {
                left = mid + 1;
            }
        }
        return right;
    }
}
