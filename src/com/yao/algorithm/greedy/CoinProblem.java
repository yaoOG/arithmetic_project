package com.yao.algorithm.greedy;

import java.util.Arrays;

/**
 * @author choo
 */
public class CoinProblem {

    /**
     * @param total 金额
     * @param coins 币种数组，从大到小排序
     * @return 返回币数，如果返回-1表示无法凑够total
     */
    private static int getMinCoinCountOfValueHelper(int total, int[] coins) {
        if (coins.length == 0) {
            return -1;
        }

        //当前币值
        int currentCoin = coins[0];

        //使用当前币值数量
        int useCurrentCoinCount = total / currentCoin;

        int restTotal = total - useCurrentCoinCount * currentCoin;
        // 如果restTotal为0，表示余额已除尽，组合完成
        if (restTotal == 0) {
            return useCurrentCoinCount;
        }

        // 其他币种数量
        int coinCount = -1;
        // 剩余的币种
        int[] restCoins = Arrays.copyOfRange(coins, 1, coins.length);
        while (useCurrentCoinCount >= 0) {
            // 否则尝试用剩余面值求当前余额的硬币总数
            coinCount = getMinCoinCountOfValueHelper(restTotal, restCoins);

            // 如果后续没有有可用组合,退一步，当前useCurrentCoinCount币数减1
            if (coinCount == -1) {
                // 否则尝试把当前面值数-1
                useCurrentCoinCount--;
                // 重新计算restTotal
                restTotal = total - useCurrentCoinCount * currentCoin;

            } else {
                return useCurrentCoinCount + coinCount;
            }
        }

        return -1;
    }

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
