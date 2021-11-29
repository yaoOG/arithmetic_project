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



    List<List<Integer>> result = new ArrayList<>();
    List<Integer> tmp = new ArrayList<>();

    public List<List<Integer>> subsets(int[] nums) {

        result.add(new ArrayList<>());
        dfs(nums,0);
        return result;
    }

    public void dfs(int[] nums, int start) {

        if (start == nums.length){
            return;
        }

        for (int i = start; i < nums.length; i++) {
            tmp.add(nums[i]);
            result.add(new ArrayList<>(tmp));
            dfs(nums,i + 1);
            tmp.remove(tmp.size()-1);
        }
    }

    public static void main(String[] args) {
        Main78 main78 = new Main78();
        List<List<Integer>> subsets = main78.subsets(new int[]{1, 2, 3});
        System.out.println(subsets);
    }
}
