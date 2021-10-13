package com.yao.leetcode;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

/**
 * @author zhuyao
 * @date 2019/03/24
 * 给定一个二叉树和一个目标和，找到所有从根节点到叶子节点路径总和等于给定目标和的路径。
 * 说明: 叶子节点是指没有子节点的节点。
 * 示例:
 * 给定如下二叉树，以及目标和 sum = 22，
 * 5
 * / \
 * 4   8
 * /   / \
 * 11  13  4
 * /  \    / \
 * 7    2  5   1
 * 返回:
 * [
 * [5,4,11,2],
 * [5,8,4,5]
 * ]
 */
public class Main113 {

    public static void main(String[] args) {
        TreeNode treeNode = new TreeNode(5);
        TreeNode treeNode1 = new TreeNode(8);
        TreeNode treeNode2 = new TreeNode(4);

        TreeNode treeNode3 = new TreeNode(5);
        TreeNode treeNode4 = new TreeNode(11);
        TreeNode treeNode5 = new TreeNode(4);
        TreeNode treeNode6 = new TreeNode(13);

        TreeNode treeNode7 = new TreeNode(7);
        TreeNode treeNode8 = new TreeNode(2);
        TreeNode treeNode9 = new TreeNode(1);

        treeNode.left = treeNode2;
        treeNode.right = treeNode1;

        treeNode2.left = treeNode4;


        treeNode1.right = treeNode5;
        treeNode1.left = treeNode6;

        treeNode4.left = treeNode7;
        treeNode4.right = treeNode8;

        treeNode5.left = treeNode3;
        treeNode5.right = treeNode9;

        Main113 main113 = new Main113();
        List<List<Integer>> lists = main113.pathSum(treeNode, 22);
        System.out.println(lists.toString());


    }

    List<List<Integer>> ret = new LinkedList<List<Integer>>();
    Deque<Integer> path = new LinkedList<Integer>();

    public List<List<Integer>> pathSum(TreeNode root, int targetSum) {
        dfs(root, targetSum);
        return ret;
    }

    public void dfs(TreeNode root, int targetSum) {
        if (root == null) {
            return;
        }
        path.offerLast(root.val);
        targetSum -= root.val;
        if (root.left == null && root.right == null && targetSum == 0) {
            ret.add(new LinkedList<Integer>(path));
        }
        dfs(root.left, targetSum);
        dfs(root.right, targetSum);
        //最后一个结点出队，重新将其他兄弟结点加进来进行计算
        path.pollLast();
    }



    public List<List<Integer>> pathSum2(TreeNode root, int sum) {
        List<List<Integer>> res = new ArrayList<>();
        List<Integer> path = new ArrayList<>();
        Deque<TreeNode> stack = new LinkedList<>();
        int SUM = 0;
        TreeNode cur = root;
        TreeNode pre = null;
        while (cur != null || !stack.isEmpty()) {
            while (cur != null) {
                stack.push(cur);
                path.add(cur.val);
                SUM += cur.val;
                cur = cur.left;
            }
            cur = stack.peek();
            if (cur != null && cur.right != null && cur.right != pre) {
                cur = cur.right;
                continue;
            }
            if (cur != null && cur.left == null && cur.right == null && SUM == sum) {
                res.add(new ArrayList<Integer>(path));
            }

            pre = cur;
            stack.pop();
            path.remove(path.size() - 1);
            SUM -= cur.val;
            cur = null;

        }
        return res;
    }
}
