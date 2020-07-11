package com.yao.leetcode;

import java.util.Stack;

/**
 * @author Daniel:)
 *
 * 给定一个整数序列：a1, a2, ..., an，一个132模式的子序列 ai, aj, ak 被定义为：当 i < j < k 时，
 * ai < ak < aj。设计一个算法，当给定有 n 个数字的序列时，验证这个序列中是否含有132模式的子序列。
 *
 * 注意：n 的值小于15000。
 *
 * 示例1:
 * 输入: [1, 2, 3, 4]
 * 输出: False
 * 解释: 序列中不存在132模式的子序列。
 * 示例 2:
 * 输入: [3, 1, 4, 2]
 * 输出: True
 * 解释: 序列中有 1 个132模式的子序列： [1, 4, 2].
 * 示例 3:
 * 输入: [-1, 3, 2, 0]
 * 输出: True
 * 解释: 序列中有 3 个132模式的的子序列: [-1, 3, 2], [-1, 3, 0] 和 [-1, 2, 0].

 */
public class Main456 {
    public boolean find132pattern(int[] nums) {
        //栈顶存储最大
        Stack<Integer> stack = new Stack<>();
        ///max存储次大的
        int max = Integer.MIN_VALUE;
        for (int i = nums.length - 1; i >= 0; i--) {
            while (!stack.isEmpty() && stack.peek() < nums[i]) {
                //因为当前元素大于栈顶元素，所以次大元素应该是栈中最大元素
                max = stack.pop();
            }
            if (nums[i] > max)
                //因为走了上面的循环，所以当前元素应该是栈中最大元素
                stack.push(nums[i]);
            if (nums[i] < max)
                return true;
        }
        return false;
    }

    public static void main(String[] args) {
        int[] param = new int[]{3, 2, 6, 4, 2};
        Main456 main456 = new Main456();
        boolean result = main456.find132pattern(param);
        System.out.println(result);
    }
}
