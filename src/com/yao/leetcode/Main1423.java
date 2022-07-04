package com.yao.leetcode;

import java.util.Arrays;

/**
 * @author choo
 */
public class Main1423 {

    public static void main(String[] args) {
        Main1423 main1423 = new Main1423();
        int[] var = new int[]{1, 2, 3, 4, 5, 6, 1};
        main1423.maxScore(var,3);
    }
    public int maxScore(int[] cardPoints, int k) {
        int n = cardPoints.length;
        // 滑动窗口大小为 n-k
        int windowSize = n - k;
        // 选前 n-k 个作为初始值
        int sum = 0;
        for (int i = 0; i < windowSize; ++i) {
            sum += cardPoints[i];
        }
        int minSum = sum;
        for (int i = windowSize; i < n; ++i) {
            // 滑动窗口每向右移动一格，增加从右侧进入窗口的元素值，并减少从左侧离开窗口的元素值
            sum += cardPoints[i] - cardPoints[i - windowSize];
            minSum = Math.min(minSum, sum);
        }
        return Arrays.stream(cardPoints).sum() - minSum;
    }
}
