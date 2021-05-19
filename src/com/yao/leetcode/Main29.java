package com.yao.leetcode;

/**
 * 两数相除
 * 给定两个整数，被除数dividend和除数divisor。将两数相除，要求不使用乘法、除法和 mod 运算符。
 * 返回被除数dividend除以除数divisor得到的商。
 * 整数除法的结果应当截去（truncate）其小数部分，例如：truncate(8.345) = 8 以及 truncate(-2.7335) = -2
 *
 * 示例1:
 * 输入: dividend = 10, divisor = 3
 * 输出: 3
 * 解释: 10/3 = truncate(3.33333..) = truncate(3) = 3
 *
 * 示例2:
 * 输入: dividend = 7, divisor = -3
 * 输出: -2
 * 解释: 7/-3 = truncate(-2.33333..) = -2
 *
 * @author Daniel:)
 */
public class Main29 {
    public int divide(int dividend, int divisor) {

        if (dividend == Integer.MIN_VALUE && divisor == -1) {
            return Integer.MAX_VALUE;
        }

        long d = Math.abs((long) dividend), b = Math.abs((long) divisor), res = 0;

        if (1 == b) {
            return (int) (((dividend < 0) ^ (divisor < 0)) ? d * -1 : d);
        }

        while (d >= b) {
            long tmp = b, p = 1;
            while (d >= (tmp << 1)) {
                tmp <<= 1;
                p <<= 1;
            }
            d -= tmp;
            res += p;
        }

        return (int) (((dividend < 0) ^ (divisor < 0)) ? -res : res);
    }
}
