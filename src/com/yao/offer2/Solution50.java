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

    public static void main(String[] args) {

        TreeNode treeNode5 = new TreeNode(5);
        TreeNode treeNode2 = new TreeNode(2);
        TreeNode treeNode1 = new TreeNode(1);
        TreeNode treeNode6 = new TreeNode(6);
        TreeNode treeNode4 = new TreeNode(4);
        TreeNode treeNode3 = new TreeNode(3);
        TreeNode treeNode7 = new TreeNode(7);

        treeNode5.left = treeNode2;
        treeNode5.right = treeNode4;
        treeNode2.left = treeNode1;
        treeNode2.right = treeNode6;
        treeNode4.left =treeNode3;
        treeNode4.right = treeNode7;

        Solution50 solution50 = new Solution50();
        solution50.pathSum(treeNode5, 8);

    }
}
