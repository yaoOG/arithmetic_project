package com.yao.data.structrue.tree;

import java.util.*;

/**
 * @author zhuyao
 * @date 2019/03/01
 */
public class BinaryTreeTraversal {
    private List<List<Integer>> levelOrder(TreeNode root) {
        if (root == null) {
            return Collections.emptyList();
        }
        List<List<Integer>> result = new ArrayList<>();
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            int curSize = queue.size();
            List<Integer> numbers = new ArrayList<>();
            for (int i = 0; i < curSize; i++) {
                TreeNode curNode = queue.poll();
                if (curNode != null) {
                    numbers.add(curNode.val);
                    if (curNode.left != null) {
                        queue.offer(curNode.left);
                    }
                    if (curNode.right != null) {
                        queue.offer(curNode.right);
                    }
                }
            }
            result.add(numbers);
        }
        return result;
    }

    public static void main(String[] args) {
        //[3,9,20,null,null,15,7]
        TreeNode root = new TreeNode(3);
        TreeNode root1 = new TreeNode(9);
        TreeNode root2 = new TreeNode(20);
        TreeNode root3 = new TreeNode(15);
        TreeNode root4 = new TreeNode(7);
        root.left = root1;
        root.right = root2;
        root2.left = root3;
        root2.right = root4;
        BinaryTreeTraversal binaryTreeTraversal = new BinaryTreeTraversal();
        List<List<Integer>> lists = binaryTreeTraversal.levelOrder(root);
        for (List<Integer> list : lists) {
            System.out.println(list);
        }
        for (int i = lists.size()-1; i >= 0; i--) {
            System.out.println(lists.get(i));
        }

    }
}
