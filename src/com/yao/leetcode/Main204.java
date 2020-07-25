package com.yao.leetcode;

/**
 * @author Daniel:)
 * 统计所有小于非负整数 n 的质数的数量。
 * 示例:
 * 输入: 10
 * 输出: 4
 * 解释: 小于 10 的质数一共有 4 个, 它们是 2, 3, 5, 7 。
 * 质数：在大于1的自然数中，除了1和该数自身外，无法被其他自然数整除的数
 */
public class Main204 {

    public int countPrimes(int n) {
        boolean[] notPrime = new boolean[n];
        int count = 0;
        for (int i = 2; i < n; i++) {
            //notPrime[i]为false代表是质数
            if (!notPrime[i]) {
                count++;
                for (int j = 2; i*j < n; j++) {
                    notPrime[i*j] = true;
                }
            }
        }

        return count;
    }

    public static void main(String[] args) {
        Main204 main204 = new Main204();
        int result = main204.countPrimes(10);
        System.out.println(result);
    }
}
