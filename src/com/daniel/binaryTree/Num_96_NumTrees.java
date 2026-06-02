package com.daniel.binaryTree;

public class Num_96_NumTrees {
    /**
     * 给你一个整数 n ，求恰由 n 个节点组成且节点值从 1 到 n 互不相同的 二叉搜索树 有多少种？返回满足题意的二叉搜索树的种数。
     *
     */
    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null) return result;

        Deque<TreeNode> queue = new ArrayDeque<>();
        queue.offer(root);

        // 控制遍历方向的标志位
        boolean isOrderLeft = true;

        while (!queue.isEmpty()) {
            int size = queue.size();
            // 使用 LinkedList 方便头尾操作
            LinkedList<Integer> levelList = new LinkedList<>();

            for (int i = 0; i < size; i++) {
                TreeNode curr = queue.poll();
                if (isOrderLeft) {
                    // 正常顺序：添加到末尾
                    levelList.addLast(curr.val);
                } else {
                    // 反向顺序：添加到头部
                    levelList.addFirst(curr.val);
                }
                if (curr.left != null) queue.addLast(curr.left);
                if (curr.right != null) queue.addLast(curr.right);
            }
            result.add(levelList);
            // 每一层结束，反转方向
            isOrderLeft = !isOrderLeft;
        }

        return result;
    }
}
