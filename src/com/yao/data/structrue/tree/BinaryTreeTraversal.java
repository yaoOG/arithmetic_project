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
     * @param root root节点
     */
    private List<Integer> preOrderTraversal(TreeNode root) {
        List<Integer> list = new ArrayList<>();
        if(root == null) {
            return list;
        }
        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);
        while(!stack.empty()){
            root = stack.pop();
            list.add(root.val);
            if(root.right != null) {
                stack.push(root.right);
            }
            if(root.left != null) {
                stack.push(root.left);
            }
        }
        return list;
    }

    public List<Integer> preorderTraversalRecursion(TreeNode root) {
        List<Integer> pre = new LinkedList<>();
        preHelper(root,pre);
        return pre;
    }
    private void preHelper(TreeNode root, List<Integer> pre) {
        if(root==null) {
            return;
        }
        pre.add(root.val);
        preHelper(root.left,pre);
        preHelper(root.right,pre);
    }

    /**
     * 中序遍历
     *
     * @param root root节点
     */
    public List<Integer> inorderTraversalRecursion(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        // method 1: recursion
        helper(root, res);
        return res;
    }

    private void helper(TreeNode root, List<Integer> res) {
        if (root != null) {
            helper(root.left, res);
            res.add(root.val);
            helper(root.right, res);
        }
    }


    public List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> res = new LinkedList<>();
        if (root == null) {
            return res;
        }
        Stack<TreeNode> stack = new Stack<>();
        TreeNode cur = root;
        while (cur != null || !stack.isEmpty()) {
            // Travel to each node's left child, till reach the left leaf
            while (cur != null) {
                stack.push(cur);
                cur = cur.left;
            }
            // Backtrack to higher level node A
            cur = stack.pop();
            // Add the node to the result list
            res.add(cur.val);
            // Switch to A'right branch
            cur = cur.right;
        }
        return res;
    }

    /**
     * 后序遍历
     *
     * @param root root节点
     */
    public List<Integer> postOrderTraversal(TreeNode root) {
        List<Integer> list = new ArrayList<>();
        if(root == null) {
            return list;
        }
        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);
        while(!stack.empty()){
            root = stack.pop();
            list.add(0, root.val);
            if(root.left != null) {
                stack.push(root.left);
            }
            if(root.right != null) {
                stack.push(root.right);
            }
        }
        return list;
    }

    public List<Integer> postOrderTraversalRecursion(TreeNode root) {
        List<Integer> resultList = new ArrayList<>();
        if(root==null){
            return resultList;
        }

        help(resultList,root);
        return resultList;
    }

    private void help(List<Integer> resultList, TreeNode root){

        if(root==null){
            return;
        }
        help(resultList,root.left);
        help(resultList,root.right);
        resultList.add(root.val);
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

    /**
     * 两节点间的最远距离
     *
     * @param parent parentNode
     */
    private void getMaxDistance(TreeNode parent) {
        if (parent == null) {
            //叶子节点 返回
            return;
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
    }

    public static void main(String[] args) {
        /*
         *            3
         *         /     \
         *        9       20
         *       / \     /  \
         *              15  7
         *
         */
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
        List<Integer> integers = binaryTreeTraversal.preOrderTraversal(root);
        System.out.println(integers);
//        List<List<Integer>> lists = binaryTreeTraversal.levelOrder(root);
//        for (List<Integer> list : lists) {
//            System.out.println(list);
//        }
//        for (int i = lists.size() - 1; i >= 0; i--) {
//            System.out.println(lists.get(i));
//        }

    }
}
