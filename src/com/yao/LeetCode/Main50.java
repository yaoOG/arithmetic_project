package com.yao.LeetCode;

/**
 * @author zhuyao
 * @date 2019/02/26
 */
public class Main50 {
    /**
     * 实现 pow(x, n) ，即计算 x 的 n 次幂函数。
     * <p>
     * 示例 1:
     * <p>
     * 输入: 2.00000, 10
     * 输出: 1024.00000
     * 示例 2:
     * <p>
     * 输入: 2.10000, 3
     * 输出: 9.26100
     * 示例 3:
     * <p>
     * 输入: 2.00000, -2
     * 输出: 0.25000
     * 解释: 2-2 = 1/22 = 1/4 = 0.25
     * 说明:
     * <p>
     * -100.0 < x < 100.0
     * n 是 32 位有符号整数，其数值范围是 [−231, 231 − 1]
     * Integer.MAX_VALUE=2147483647
     * Integer.MIN_VALUE -2147483648
     *
     * @param x
     * @param n
     * @return
     */
    public double myPow(double x, int n) {
        if (n == 0)
            return 1;
        if (n == Integer.MIN_VALUE) {
            return myPow(x * x, n / 2);
        }
        if (n < 0) {
            x = 1 / x;
            n = -n;
        }
        if (n % 2 == 1) {
            return myPow(x * x, n - 1) * x;
        } else {
            return myPow(x * x, n / 2);
        }
    }

    //非递归实现
    public double myPow1(double x, int n) {
        double ans = 1;
        long absN = Math.abs((long) n);
        while (absN > 0) {
            if ((absN & 1) == 1) {
                ans *= x;
            } else {
                x *= x;
            }
            absN >>= 1;
        }
        return n < 0 ? 1 / ans : ans;
    }

    public double myPow3(double x, int n) {
        if (n < 0) {
            return 1 / pow(x, -n);
        } else {
            return pow(x, n);
        }
    }

    public double pow(double x, int n) {
        if (n == 0) {
            return 1;
        }
        double temp = pow(x, n / 2);
        if ((n & 1) == 0) {
            //如果是偶数
            return temp * temp;
        } else {
            return temp * temp * x;
        }

    }

    public static void main(String[] args) {
        Main50 main50 = new Main50();
        double pow = main50.myPow1(2, 6);
        System.out.println(pow);
    }
}