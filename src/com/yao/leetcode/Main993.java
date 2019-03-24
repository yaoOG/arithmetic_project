package com.yao.leetcode;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @author zhuyao
 * @date 2019/03/23
 */
@SuppressWarnings("unused")
public class Main993 {
    /**
     * BFS
     * @param root root
     * @param x x
     * @param y y
     * @return result
     */
    public boolean isCousins(TreeNode root, int x, int y) {
        if (root == null) {
            return false;
        }
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            int size = queue.size();
            boolean isAExits = false;
            boolean isBExits = false;
            for (int i = 0; i < size; i++) {
                TreeNode cur = queue.poll();
                if (cur != null) {
                    if (cur.val == x) {
                        isAExits = true;
                    }
                    if (cur.val == y) {
                        isBExits = true;
                    }
                    if (cur.left != null && cur.right != null) {
                        if (cur.left.val == x && cur.right.val == y) {
                            return false;
                        }
                        if (cur.left.val == y && cur.right.val == x) {
                            return false;
                        }
                    }
                    if (cur.left != null) {
                        queue.offer(cur.left);
                    }
                    if (cur.right != null) {
                        queue.offer(cur.right);
                    }
                }
            }
            if (isAExits && isBExits) {
                return true;
            }
        }
        return false;
    }

    /**
     * DFS
     */
    class Solution {
        private TreeNode xParent = null;
        private TreeNode yParent = null;
        private int xDepth = -1, yDepth = -1;

        public boolean isCousins2(TreeNode root, int x, int y) {
            getDepthAndParent(root, x, y, 0, null);
            return xDepth == yDepth && xParent != yParent;
        }

        private void getDepthAndParent(TreeNode root, int x, int y, int depth, TreeNode parent) {
            if (root == null) {
                return;
            }
            if (root.val == x) {
                xParent = parent;
                xDepth = depth;
            } else if (root.val == y) {
                yParent = parent;
                yDepth = depth;
            }
            getDepthAndParent(root.left, x, y, depth + 1, root);
            getDepthAndParent(root.right, x, y, depth + 1, root);
        }
    }
}
