package com.yao.leetcode;

import java.util.Arrays;

/**
 * @author choo
 */
public class Main322 {


    public static void main(String[] args) {
        Main322 main322 = new Main322();
        int result = main322.coinChange(new int[]{1, 2, 5}, 11);
        System.out.println(result);
    }

    /**
     * dp[j]:凑足总额为j所需钱币的最少个数为dp[j]
     * @param coins
     * @param amount
     * @return
     */
    public int coinChange(int[] coins, int amount) {
        int max = amount + 1;
        int[] dp = new int[amount + 1];
        Arrays.fill(dp, max);
        dp[0] = 0;
        for (int i = 1; i <= amount; i++) {
            for (int j = 0; j < coins.length; j++) {
                if (coins[j] <= i) {
                    dp[i] = Math.min(dp[i], dp[i - coins[j]] + 1);
                }
            }
        }
        return dp[amount] > amount ? -1 : dp[amount];
    }

}
