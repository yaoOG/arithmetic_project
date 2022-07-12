package com.yao.offer2;

import com.yao.data.structrue.tree.TreeNode;

import java.util.Stack;

public class Solution52 {

    public TreeNode increasingBST(TreeNode root) {
        Stack<TreeNode> stack = new Stack<>();
        //prev表示前一个遍历到的节点
        TreeNode prev = null;
        //变量first表示第一个被遍历到的节点
        TreeNode first = null;
        TreeNode cur = root;
        while (cur != null || !stack.isEmpty()) {
            while (cur != null) {
                stack.push(cur);
                cur = cur.left;
            }

            cur = stack.pop();
            if (prev == null) {
                first = cur;
            } else {
                prev.right = cur;
            }

            prev = cur;
            cur.left = null;
            cur = cur.right;
        }
        return first;
    }
}
