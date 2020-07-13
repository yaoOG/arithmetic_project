package com.yao.leetcode;

import java.util.Stack;

/**
 * @author Daniel:)
 *
 * 给定一个整数数组 A，找到 min(B) 的总和，其中 B 的范围为 A 的每个（连续）子数组。
 *
 * 由于答案可能很大，因此返回答案模 10^9 + 7。
 *
 * 示例：
 *
 * 输入：[3,1,2,4]
 * 输出：17
 * 解释：
 * 子数组为 [3]，[1]，[2]，[4]，[3,1]，[1,2]，[2,4]，[3,1,2]，[1,2,4]，[3,1,2,4]。
 * 最小值为 3，1，2，4，1，1，2，1，1，1，和为 17。
 */
public class Main907 {

    int MOD = 1000000007;

    public int sumSubarrayMins(int[] A) {
        Stack<Pair> stack = new Stack<>();
        //tmp变量记录每一轮的res值，res的全局变量记录结果
        int res = 0, tmp = 0;
        for (int i = 0; i < A.length; i++) {
            int count = 1;
            while (!stack.empty() && stack.peek().val >= A[i]) {
                Pair pop = stack.pop();
                count += pop.count;
                tmp -= pop.val * pop.count;
            }
            stack.push(new Pair(A[i], count));
            tmp += A[i] * count;
            res += tmp;
            res %= MOD;
        }
        return res;
    }


    public static void main(String[] args) {
        int[] param = {3, 1, 2, 1, 4};
        Main907 main907 = new Main907();
        int result = main907.sumSubarrayMins(param);
        System.out.println(result);
    }
}

class Pair {
    public int val;
    public int count;

    public Pair(int val, int count) {
        this.val = val;
        this.count = count;
    }
}
