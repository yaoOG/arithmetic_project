package com.yao.leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Daniel:)
 * 给定一组不含重复元素的整数数组nums，返回该数组所有可能的子集（幂集）。
 *
 * 说明：解集不能包含重复的子集。
 *
 * 示例:
 *
 * 输入: nums = [1,2,3]
 * 输出:
 * [
 *  [3],
 *  [1],
 *  [2],
 *  [1,2,3],
 *  [1,3],
 *  [2,3],
 *  [1,2],
 *  []
 * ]
 */
public class Main78 {

    public List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> ans = new ArrayList<>();
        if (nums == null) {
            return ans;
        }
        dfs(ans, nums, new ArrayList<>(), 0);
        return ans;
    }

    private void dfs(List<List<Integer>> ans, int[] nums, List<Integer> list, int index) {
        // 终止条件
        if (index == nums.length) {
            ans.add(new ArrayList<>(list));
        }
        // 处理当前层
        dfs(ans, nums, list, index + 1);
        list.add(nums[index]);
        dfs(ans, nums, list, index + 1);
        // reverse the current state
        list.remove(list.size() - 1);

    }
}
