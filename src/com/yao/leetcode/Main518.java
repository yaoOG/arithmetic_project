package com.yao.leetcode;

/**
 * @author choo
 */
public class Main518 {
    //dp[x] 表示金额之和等于 x 的硬币组合数，目标是求 dp[amount]
    public int change(int amount, int[] coins) {
        int[] dp = new int[amount + 1];
        dp[0] = 1;
        for (int coin : coins) {
            for (int i = coin; i <= amount; i++) {
                dp[i] += dp[i - coin];
            }
        }
        return dp[amount];
    }

    public static void main(String[] args) {
        int[] coins = new int[]{1, 2, 5};
        int amount = 5;
        Main518 main518 = new Main518();
        int change = main518.change(amount, coins);
        System.out.println(change);

    }
}
