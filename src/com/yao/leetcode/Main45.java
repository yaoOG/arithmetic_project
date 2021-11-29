package com.yao.leetcode;

/**
 * @author Daniel:)
 *
 * 给定一个非负整数数组，你最初位于数组的第一个位置。
 *
 * 数组中的每个元素代表你在该位置可以跳跃的最大长度。
 *
 * 你的目标是使用最少的跳跃次数到达数组的最后一个位置。
 *
 * 示例:
 *
 * 输入: [2,3,1,1,4]
 * 输出: 2
 * 解释: 跳到最后一个位置的最小跳跃数是 2。
 * 从下标为 0 跳到下标为 1 的位置，跳1步，然后跳3步到达数组的最后一个位置。
 *

 */
public class Main45 {

    /**
     * 为什么i的范围不包括数组的最后一个元素：因为i=0的时候已经将step加一了，为什么i=0加一，因为题目给了我们前提我们一定会跳出最后一步并且到达终点
     * @param nums
     * @return
     */
    public int jump(int[] nums) {
        int length = nums.length;
        // 纪录位置本次跳跃可以抵达的右边界
        int end = 0;
        // 纪录位置 i 可以跳到的最远距离
        int maxPosition = 0;
        // 纪录跳跃次数
        int steps = 0;
        for (int i = 0; i < length - 1; i++) {
            maxPosition = Math.max(maxPosition, i + nums[i]);
            if (i == end) {// i == end 表示本次跳跃的所有可能都已经尝试, 那么就需要更新 end 和 step
                end = maxPosition;
                steps++;
            }
        }
        return steps;
    }

    public static void main(String[] args) {
        Main45 main45 = new Main45();
        main45.jump(new int[]{2,3,1,2,4,2,3});
    }

}
