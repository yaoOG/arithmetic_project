package com.yao.offer2;

import com.yao.data.structrue.tree.TreeNode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Solution44 {
    public List<Integer> largestValues(TreeNode root) {
        //存放当前层的节点
        Queue<TreeNode> queue1 = new LinkedList<>();
        //存放下一层的节点
        Queue<TreeNode> queue2 = new LinkedList<>();

        Integer max = Integer.MIN_VALUE;

        List<Integer> result  = new ArrayList<>();

        if (root != null) {
            queue1.add(root);
        }

        while (!queue1.isEmpty()) {
            TreeNode node = queue1.poll();
            max = Math.max(max,node.val);

            if (node.left != null) {
                queue2.offer(node.left);
            }

            if (node.right != null) {
                queue2.offer(node.right);
            }

            if (queue1.isEmpty()) {
                result.add(max);
                max = Integer.MIN_VALUE;
                queue1 = queue2;
                queue2 = new LinkedList<>();
            }
        }

        return result;

    }
}
