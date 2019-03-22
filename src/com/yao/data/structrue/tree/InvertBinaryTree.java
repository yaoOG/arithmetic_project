package com.yao.data.structrue.tree;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * @author zhuyao
 * @date 2019/03/18
 * 反转二叉树
 */
public class InvertBinaryTree {

    public TreeNode invertTreeRecursion(TreeNode root) {
        if (root == null) {
            return null;
        }
        TreeNode left = root.left;
        TreeNode right = root.right;
        root.left = invertTreeRecursion(right);
        root.right = invertTreeRecursion(left);
        return root;
    }

    public TreeNode invertTree(TreeNode root) {
        if (root == null) {
            return null;
        }
        final Deque<TreeNode> stack = new ArrayDeque<>();
        stack.push(root);
        while (!stack.isEmpty()) {
            TreeNode cur = stack.pop();
            TreeNode left = cur.left;
            cur.left = cur.right;
            cur.right = left;

            if (cur.left != null) {
                stack.push(cur.left);
            }
            if (cur.right != null) {
                stack.push(cur.right);
            }
        }
        return root;
    }
}
