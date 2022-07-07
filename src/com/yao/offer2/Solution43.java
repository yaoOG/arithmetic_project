package com.yao.offer2;

import com.yao.data.structrue.tree.TreeNode;

import java.util.LinkedList;
import java.util.Queue;

public class Solution43 {
    class CBTInserter {

        //队列中保存左右子节点有一个为空的父节点
        private Queue<TreeNode> queue;
        private TreeNode root;

        public CBTInserter(TreeNode root) {
            this.root = root;
            queue = new LinkedList<>();
            queue.offer(root);
            while (queue.peek().left != null && queue.peek().right != null) {
                //如果父节点的左右子节点都不为空，则将父节点出队，子节点入队
                TreeNode node = queue.poll();
                queue.offer(node.left);
                queue.offer(node.right);
            }

        }

        public int insert(int v) {
            TreeNode parent = queue.peek();
            TreeNode node = new TreeNode(v);
            if (parent.left == null) {
                parent.left = node;
            } else {
                parent.right = node;
                queue.poll();
                queue.offer(parent.left);
                queue.offer(parent.right);
            }
            return parent.val;

        }

        public TreeNode get_root() {
            return this.root;

        }
    }

}
