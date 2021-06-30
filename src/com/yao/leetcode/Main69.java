package com.yao.leetcode;

/**
 * @author Daniel:)
 * 实现int sqrt(int x)函数。
 *
 * 计算并返回x的平方根，其中x 是非负整数。
 *
 * 由于返回类型是整数，结果只保留整数的部分，小数部分将被舍去。
 *
 * 示例 1:
 *
 * 输入: 4
 * 输出: 2
 * 示例 2:
 *
 * 输入: 8
 * 输出: 2
 * 说明: 8 的平方根是 2.82842...,
 *     由于返回类型是整数，小数部分将被舍去。
 */
public class Main69 {
    /**
     * 由于 x 平方根的整数部分 是满足 k^2 <= x的最大 k 值，因此我们可以对 k 进行二分查找，从而得到答案。
     * @param x
     * @return
     */
    public int mySqrt(int x) {
        int left = 0;
        int right = x;
        int result = -1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if ((long)mid * mid <= x) {
                result = mid;
                left = mid + 1;
            }else {
                right = mid - 1;
            }
        }
        return result;
    }
}
