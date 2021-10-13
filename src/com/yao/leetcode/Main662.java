package com.yao.leetcode;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

/**
 * @author choo
 */
public class Main662 {


    /*解法1 深度优先搜索*/
    int ans;
    //用来记录每一层最左边第一个不为空的结点位置，通过map的putIfAbsent方法特性
    Map<Integer, Integer> left;

    public int widthOfBinaryTree(TreeNode root) {
        ans = 0;
        left = new HashMap<>();
        dfs(root, 0, 0);
        return ans;
    }

    public void dfs(TreeNode root, int depth, int pos) {
        if (root == null) return;
        left.putIfAbsent(depth, pos);
        //pos为当前结点在这一层的位置  left.get(depth)为当前层最左边第一个不为空结点的位置
        ans = Math.max(ans, pos - left.get(depth) + 1);
        dfs(root.left, depth + 1, 2 * pos);
        dfs(root.right, depth + 1, 2 * pos + 1);
    }

    /*解法2 宽度优先搜索*/
    public int widthOfBinaryTree2(TreeNode root) {
        Queue<AnnotatedNode> queue = new LinkedList();
        queue.add(new AnnotatedNode(root, 0, 0));
        //left用来记录当前层最左边第一个不为空的位置
        //curDepth为标识变量，当它不等于当前层的depth的时候代表是第一个节点
        int curDepth = 0, left = 0, ans = 0;
        while (!queue.isEmpty()) {
            AnnotatedNode a = queue.poll();
            if (a.node != null) {
                queue.add(new AnnotatedNode(a.node.left, a.depth + 1, a.pos * 2));
                queue.add(new AnnotatedNode(a.node.right, a.depth + 1, a.pos * 2 + 1));
                if (curDepth != a.depth) {
                    curDepth = a.depth;
                    left = a.pos;
                }
                ans = Math.max(ans, a.pos - left + 1);
            }
        }
        return ans;
    }


    class AnnotatedNode {
        TreeNode node;
        int depth, pos;

        AnnotatedNode(TreeNode n, int d, int p) {
            node = n;
            depth = d;
            pos = p;
        }
    }

    public static void main(String[] args) {
        TreeNode treeNode = new TreeNode(1);
        TreeNode treeNode1 = new TreeNode(2);
        TreeNode treeNode2 = new TreeNode(3);
        TreeNode treeNode3 = new TreeNode(3);
        TreeNode treeNode4 = new TreeNode(5);
        TreeNode treeNode5 = new TreeNode(9);

        treeNode.left = treeNode2;
        treeNode.right = treeNode1;

        treeNode2.left = treeNode4;
        treeNode2.right = treeNode3;

        treeNode1.right = treeNode5;

        Main662 main662 = new Main662();
        main662.widthOfBinaryTree(treeNode);


    }

}
