package com.yao.leetcode;

/**
 * @author choo
 */
public class Offer05 {
    public int hammingWeight(int n) {
        int ret = 0;
        while (n != 0) {
            //每次运算会将n的最低1位边为0
            n &= n - 1;
            ret++;
        }
        return ret;
    }
}
