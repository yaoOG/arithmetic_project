package com.yao.LeetCode;

/**
 * @author zhuyao
 * @date 2019/03/14
 * 实现获取下一个排列的函数，算法需要将给定数字序列重新排列成字典序中下一个更大的排列。
 * 如果不存在下一个更大的排列，则将数字重新排列成最小的排列（即升序排列）。
 * 必须原地修改，只允许使用额外常数空间。
 * 以下是一些例子，输入位于左侧列，其相应输出位于右侧列。
 * 1,2,3 → 1,3,2
 * 3,2,1 → 1,2,3
 * 1,1,5 → 1,5,1
 */
public class Main31 {
    public void nextPermutation(int[] nums) {
        if (nums.length <= 1) {
            return;
        }
        int i = nums.length - 1;
        for (; i >= 1; i--) {
            if (nums[i] > nums[i - 1]) { //find first number which is smaller than it's after number
                break;
            }
        }
        if (i != 0) {
            swap(nums, i - 1); //if the number exist,which means that the nums not like{5,4,3,2,1}
        }
        reverse(nums, i);
    }

    private void swap(int[] a, int i) {
        for (int j = a.length - 1; j > i; j--) {
            if (a[j] > a[i]) {
                int t = a[j];
                a[j] = a[i];
                a[i] = t;
                break;
            }
        }
    }

    private void reverse(int[] a, int i) {//reverse the number after the number we have found
        int first = i;
        int last = a.length - 1;
        while (first < last) {
            int t = a[first];
            a[first] = a[last];
            a[last] = t;
            first++;
            last--;
        }
    }
}
