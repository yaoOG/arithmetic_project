package com.yao.leetcode;

/**
 * @author choo
 *
 * 给定一个非负整数数组A， A 中一半整数是奇数，一半整数是偶数。
 * 对数组进行排序，以便当A[i] 为奇数时，i也是奇数；当A[i]为偶数时，i也是偶数。
 * 你可以返回任何满足上述条件的数组作为答案。
 *
 * 示例：
 * 输入：[4,2,5,7]
 * 输出：[4,5,2,7]
 * 解释：[4,7,2,5]，[2,5,4,7]，[2,7,4,5] 也会被接受。
 *
 */
public class Main922 {
    /**
     * 时间复杂度：O(N)，其中 N 是数组 A 的长度。
     * 空间复杂度：O(1)。注意在这里我们不考虑输出数组的空间占用。
     * @param nums
     * @return
     */
    public int[] sortArrayByParityII(int[] nums) {
        int[] result = new int[nums.length];
        int i = 0;
        for (int num : nums) {
            if (num % 2 == 0) {
                result[i += 2] = num;
            }
        }
        i = 1;
        for (int num : nums) {
            if (num % 2 == 1) {
                result[i += 2] = num;
            }
        }
        return result;
    }
}
