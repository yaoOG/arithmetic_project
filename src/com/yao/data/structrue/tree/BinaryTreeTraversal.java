package com.yao.data.structrue.tree;

import java.util.*;

/**
 * @author zhuyao
 * @date 2019/03/01
 */
@SuppressWarnings("unused")
public class BinaryTreeTraversal {
    private static int nMaxLen;

    /**
     * Hierarchical traversal
     *
     * @param root root节点
     * @return 层次遍历List
     */
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

    /**
     * 前序遍历
     *
     * @param parent root节点
     */
    private static void preOrder(TreeNode parent) {
        if (parent == null) {
            return;
        }
        System.out.println(parent.val + " ");
        preOrder(parent.left);
        preOrder(parent.right);
    }

    /**
     * 中序遍历
     *
     * @param parent root节点
     */
    private static void inOrder(TreeNode parent) {
        if (parent == null) {
            return;
        }
        inOrder(parent.left);
        System.out.println(parent.val + " ");
        inOrder(parent.right);
    }

    /**
     * 后序遍历
     *
     * @param parent root节点
     */
    private static void postOrder(TreeNode parent) {
        if (parent == null) {
            return;
        }
        postOrder(parent.left);
        postOrder(parent.right);
        System.out.println(parent.val + " ");
    }

    /**
     * @param parent root 节点
     * @return 返回树的叶子节点数
     */
    private int allLeaves(TreeNode parent) {

        System.out.println(parent.val);
        if (parent.left == null && parent.right == null) {
            return 1;
        }
        int leftCount = (parent.left == null ? 0 : allLeaves(parent.left));
        int rightCount = (parent.right == null ? 0 : allLeaves(parent.right));
        return leftCount + rightCount;
    }


    private int maxDepth(TreeNode root) {
        if (root == null) {
            return 0;
        }
        return 1 + Math.max(maxDepth(root.left), maxDepth(root.right));
    }

    private int minDepth(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int left = minDepth(root.left);
        int right = minDepth(root.right);
        return (left == 0 || right == 0) ? left + right + 1 : Math.min(left, right) + 1;
    }


    //两节点间的最远距离
    private int getMaxDistance(TreeNode parent) {
        if (parent == null) {
            //叶子节点 返回
            return 0;
        }
        if (parent.left == null) {
            parent.nMaxLeft = 0;
        }
        if (parent.right == null) {
            parent.nMaxRight = 0;
        }
        if (parent.left != null) {
            //如果左子树不为空，递归寻找左子树最长距离
            getMaxDistance(parent.left);
        }
        if (parent.right != null) {
            //如果右子树不为空，递归寻找右子树最长距离
            getMaxDistance(parent.right);
        }
        if (parent.left != null) {
            parent.nMaxLeft = Math.max(parent.left.nMaxLeft, parent.left.nMaxRight) + 1;
        }
        if (parent.right != null) {
            parent.nMaxRight = Math.max(parent.right.nMaxLeft, parent.right.nMaxRight) + 1;
        }
        nMaxLen = Math.max(parent.nMaxLeft + parent.nMaxRight, nMaxLen);
        return nMaxLen;
    }

    public static void main(String[] args) {
        /**
         *            3
         *         /     \
         *        9       20
         *       / \     /  \
         *              15  7
         *
         */
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
        int count = binaryTreeTraversal.allLeaves(root);
        System.out.println(count);
//        List<List<Integer>> lists = binaryTreeTraversal.levelOrder(root);
//        for (List<Integer> list : lists) {
//            System.out.println(list);
//        }
//        for (int i = lists.size() - 1; i >= 0; i--) {
//            System.out.println(lists.get(i));
//        }

    }
}
