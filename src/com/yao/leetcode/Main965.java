package com.yao.leetcode;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @author zhuyao
 * @date 2019/03/23
 */
@SuppressWarnings("unused")
public class Main965 {
    //DFS
    public boolean isUnivalTreeByDfs(TreeNode root) {
        return dfs(root, root.val);
    }

    private boolean dfs(TreeNode node, int value) {
        if (node == null) {
            return true;
        }
        if (node.val != value) {
            return false;
        }
        return dfs(node.left, value) && dfs(node.right, value);
    }

    //BFS
    public boolean isUnivalTreeByBfs(TreeNode root) {
        Queue<TreeNode> queue = new LinkedList<>();
        int value = root.val;
        queue.offer(root);
        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            if (node.val != value) {
                return false;
            }
            if (node.left != null) {
                queue.offer(node.left);
            }
            if (node.right != null) {
                queue.offer(node.right);
            }
        }
        return true;
    }
}
