package com.yao.offer2;
import com.yao.data.structrue.tree.TreeNode;

public class Solution49 {
    public int sumNumbers(TreeNode root) {
        return dfs(root,0);

    }

    public int dfs(TreeNode root, int path) {
        if (root == null) {
            return 0;
        }
        path = path * 10 + root.val;
        if (root.left == null && root.right == null) {
            return path;
        }
        return dfs(root.left,path) + dfs(root.right,path);
    }
}
