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
    public int jump(int[] nums) {
        if (nums.length == 1)
            return 0;
        if (nums[0] >= nums.length - 1)
            return 1;
        int step = 1;//记录到达数组末尾所需要的最少步数
        int last = nums[0];//记录历史能到达的最远位置
        int curr = 0;//记录当前能到达的最远位置
        for (int i = 1; i < nums.length; i++) {
            curr = Math.max(curr, i + nums[i]);
            if (i == last) {
                last = curr;
                step++;
                if (curr >= nums.length - 1) {
                    return step;
                }
            }
        }
        return -1;
    }
}
