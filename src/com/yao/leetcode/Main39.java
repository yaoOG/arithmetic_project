package com.yao.leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * @author choo
 */
public class Main39 {

    List<List<Integer>> res = new ArrayList<>(); //记录答案
    List<Integer> path = new ArrayList<>();  //记录路径

    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        dfs(candidates,0, target);
        return res;
    }
    //变量u表示当前枚举的数字下标，target是递归过程中维护的目标数
    public void dfs(int[] candidates, int current, int target) {
        if(target < 0) return ;
        if(target == 0)
        {
            res.add(new ArrayList<>(path));
            return ;
        }
        for(int i = current; i < candidates.length; i++){
            if( candidates[i] <= target)
            {
                path.add(candidates[i]);
                dfs(candidates,i,target -  candidates[i]); // 因为可以重复使用，所以还是i
                path.remove(path.size()-1); //回溯，恢复现场
            }
        }
    }

    public static void main(String[] args) {
        Main39 main39 = new Main39();
        List<List<Integer>> result = main39.combinationSum(new int[]{2, 3, 6, 7}, 7);
        System.out.println(result);
    }
}
