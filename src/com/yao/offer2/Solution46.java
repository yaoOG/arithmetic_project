package com.yao.offer2;

import com.yao.data.structrue.tree.TreeNode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Solution46 {
    //二叉树的右侧视图其实就是从上到下每层最右边的节点
    public List<Integer> rightSideView(TreeNode root) {
        Queue<TreeNode> queue1 = new LinkedList<>();
        Queue<TreeNode> queue2 = new LinkedList<>();
        List<Integer> result = new ArrayList<>();
        if (root == null) {
            return result;
        }
        queue1.offer(root);
        while (!queue1.isEmpty()) {
            TreeNode node = queue1.poll();
            if (node.left != null) {
                queue2.offer(node.left);
            }
            if (node.right != null) {
                queue2.offer(node.right);
            }
            //当队列queue1被清空时(即queue1.isEmpty()为true时，当前这一层已经遍历完，变量node是这一层最后边的节点)
            if (queue1.isEmpty()) {
                result.add(node.val);
                queue1 = queue2;
                queue2 = new LinkedList<>();
            }
        }
        return result;

    }
}
