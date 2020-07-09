package com.yao.leetcode;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Daniel
 * 二叉树的后序遍历
 */
public class Main145 {

    /**
     * 迭代法
     *
     * @param root
     * @return
     */
    public List<Integer> postorderTraversal(TreeNode root) {
        LinkedList<TreeNode> stack = new LinkedList<>();
        LinkedList<Integer> output = new LinkedList<>();
        if (root == null) {
            return output;
        }
        //添加元素到队尾
        stack.add(root);
        //按照左子树右子数
        while (!stack.isEmpty()) {
            //取队尾元素并删除
            TreeNode node = stack.pollLast();
            //输出的结果添加的队列的头部
            output.addFirst(node.val);
            if (node.left != null) {
                //添加元素到队尾
                stack.add(node.left);
            }
            if (node.right != null) {
                //添加元素到队尾
                stack.add(node.right);
            }
        }
        return output;
    }

    public static void main(String[] args) {
        TreeNode root = new TreeNode(1);
        TreeNode node1 = new TreeNode(2);
        TreeNode node2 = new TreeNode(3);
        TreeNode node3 = new TreeNode(4);
        TreeNode node4 = new TreeNode(5);
        TreeNode node5 = new TreeNode(6);
        root.left = node1;
        root.right = node2;
        node1.left = node3;
        node1.right = node4;
        node2.left = node5;
        Main145 main145 = new Main145();
        List<Integer> integers = main145.postorderTraversal(root);
        System.out.println(integers);
    }
}
