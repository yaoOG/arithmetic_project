package com.yao.offer2;

import com.yao.data.structrue.tree.TreeNode;

import java.util.HashMap;
import java.util.Map;

public class Solution50 {
    public int pathSum(TreeNode root, int targetSum) {
        Map<Integer,Integer> map = new HashMap<>();
        map.put(0,1);
        return dfs(root,targetSum,map,0);

    }

    public int dfs(TreeNode root,int sum,Map<Integer,Integer> map,int path) {
        if (root == null) {
            return 0;
        }
        path += root.val;
        int count = map.getOrDefault(path-sum,0);
        map.put(path,map.getOrDefault(path,0)+1);
        count += dfs(root.left,sum,map,path);
        count += dfs(root.right,sum,map,path);
        map.put(path,map.get(path) - 1);
        return count;
    }
}
