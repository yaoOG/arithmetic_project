package com.yao.recursive;

public class FeiBo {
    // 使用递归方法
    private static int getFibo(int i) {
        if (i == 1 || i == 2)
            return 1;
        else
            return getFibo(i - 1) + getFibo(i - 2);
    }

    public static void main(String[] args) {
        System.out.println("斐波那契数列的前20项为：");
        for (int j = 1; j <= 20; j++) {
            System.out.print(getFibo(j) + "\t");
            if (j % 5 == 0)
                System.out.println();
        }
    }

    /**
     * 动态规划
     * @param n n
     * @return result
     */
    public int Fibonacci(int n) {
        if (n <= 1) {
            return n;
        }
        int last = 1;
        int nextToLast = 0;
        int result = 1;
        for (int i = 2; i <= n; i++) {
            result = last + nextToLast;
            nextToLast = last;
            last = result;
        }
        return result;
    }
}
