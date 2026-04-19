package com.daniel.binaryTree;

import com.yao.data.structrue.tree.TreeNode;

import java.util.*;

public class BinaryTreeTraversal {

    /**
     * 中序遍历
     * @param root
     * @return
     */
    public List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        Deque<TreeNode> stack = new ArrayDeque<>();
        TreeNode cur = root;
        while (cur != null || !stack.isEmpty()){
            while (cur != null) {
                stack.push(cur);
                cur = cur.left;
            }
            cur = stack.poll();
            res.add(cur.val);
            cur = cur.right;
        }
        return res;
    }

    /**
     * 前序遍历
     * @param root
     * @return
     */
    public List<Integer> preorderTraversal(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        if (root == null) return res;
        Deque<TreeNode> stack = new ArrayDeque<>();
        stack.push(root);
        while (!stack.isEmpty()) {
            TreeNode node = stack.pop();
            res.add(node.val); // 访问根
            if (node.right != null) stack.push(node.right); // 先压右
            if (node.left != null) stack.push(node.left);  // 后压左
        }
        return res;
    }

    /**
     * 后序遍历
     * @param root
     * @return
     */
    public List<Integer> postorderTraversal(TreeNode root) {
        LinkedList<Integer> res = new LinkedList<>();
        if (root == null) return res;
        Deque<TreeNode> stack = new ArrayDeque<>();
        stack.push(root);
        while (!stack.isEmpty()) {
            TreeNode node = stack.pop();
            res.addFirst(node.val); // 插入到列表头部，相当于反转
            if (node.left != null) stack.push(node.left); // 注意这里先压左
            if (node.right != null) stack.push(node.right);
        }
        return res;
    }

    /**
     * 层序
     * @param root
     * @return
     */
    public List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null) return result;
        Deque<TreeNode> queue = new ArrayDeque<>();
        queue.addLast(root);
        while(!queue.isEmpty()){
            int size = queue.size();
            List<Integer> level = new ArrayList<>();

            for (int i = 0 ; i < size; i++) {
                TreeNode curr = queue.pollFirst();
                level.add(curr.val);
                if(curr.left != null) queue.addLast(curr.left);
                if(curr.right != null) queue.addLast(curr.right);
            }
            result.add(level);

        }
        return result;
    }
}
