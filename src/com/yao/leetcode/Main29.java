package com.yao.leetcode;

/**
 * @author zhuyao
 * @date 2019/02/22
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
