package com.yao.offer2;

import com.yao.data.structrue.tree.TreeNode;

import java.util.LinkedList;
import java.util.Queue;

public class Solution45 {
    public int findBottomLeftValue(TreeNode root) {
        Queue<TreeNode> queue1 = new LinkedList<>();
        Queue<TreeNode> queue2 = new LinkedList<>();
        //bootomLeft来保存每一层最左边节点的值
        int bootomLeft = root.val;
        queue1.offer(root);
        while (!queue1.isEmpty()) {
            TreeNode node = queue1.poll();
            if (node.left != null) {
                queue2.offer(node.left);
            }
            if (node.right != null) {
                queue2.offer(node.right);
            }
            if (queue1.isEmpty()) {
                queue1 = queue2;
                queue2 = new LinkedList<>();
                if (!queue1.isEmpty()) {
                    bootomLeft = queue1.peek().val;
                }
            }
        }
        return bootomLeft;
    }
}
