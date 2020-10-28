package com.yao.leetcode;

/**
 * @author Daniel:)
 *
 * 给定一个正整数 num，编写一个函数，如果 num 是一个完全平方数，则返回 True，否则返回 False。
 * 说明：不要使用任何内置的库函数，如 sqrt。
 *
 * 示例 1：
 * 输入：16
 * 输出：True
 *
 * 示例 2：
 * 输入：14
 * 输出：False
 *
 * 如果一个正整数 a 是某一个整数 b 的平方，那么这个正整数 a 叫做完全平方数。零也可称为完全平方数。
 */
public class Main367 {
    public boolean isPerfectSquare(int num) {
        if (num < 2) return true;
        long left = 2;
        long right = num / 2;
        long guessSquared,mid;
        while (left <= right) {
             mid = left + (right - left) / 2;
            guessSquared = mid * mid;
            if (guessSquared > num) {
                right = mid - 1;
            } else if (guessSquared < num) {
                left = mid + 1;
            } else {
                return true;
            }
        }
        return false;
    }
}
