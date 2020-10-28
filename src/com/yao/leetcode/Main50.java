package com.yao.leetcode;

/**
 * @author Daniel:)
 * 计算 x 的 n 次幂函数。
 */
public class Main50 {
    public double pow(double x, int n) {
        if (n == 0)
            return 1;
        if (n < 0) {
            n = -n;
            x = 1 / x;
        }
        return (n % 2 == 0) ? pow(x * x, n / 2) : x * pow(x * x, n / 2);
    }

    public static void main(String[] args) {
        Main50 main50 = new Main50();
        double pow = main50.pow(2, 6);
        System.out.println(pow);
    }
}