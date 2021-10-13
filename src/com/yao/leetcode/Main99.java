package com.yao.leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * @author choo
 */
public class Main99 {
    /**
     * 时间复杂度：O(N)，其中 N 为二叉搜索树的节点数。中序遍历需要 O(N) 的时间，判断两个交换节点在最好的情况下是 O(1)，在最坏的情况下是 O(N)，
     * 因此总时间复杂度为 O(N)。
     * 空间复杂度：O(N)。我们需要用 nums 数组存储树的中序遍历列表。

     * @param root
     */
    public void recoverTree(TreeNode root) {
        List<Integer> nums = new ArrayList<Integer>();
        inorder(root, nums);
        int[] swapped = findTwoSwapped(nums);
        recover(root, 2, swapped[0], swapped[1]);
    }

    public void inorder(TreeNode root, List<Integer> nums) {
        if (root == null) {
            return;
        }
        inorder(root.left, nums);
        nums.add(root.val);
        inorder(root.right, nums);
    }

    /**
     * 假设有一个递增序列 a=[1,2,3,4,5,6,7]。如果我们交换两个不相邻的数字，例如 22 和 66，
     * 原序列变成了 a=[1,6,3,4,5,2,7],那么显然序列中有两个位置不满足 a{i}<a{i+1}
     * 在这个序列中体现为 6>3，5>2，因此只要我们找到这两个位置,
     * 也就是找到第一次满足a{i}>a{i+1}的i，和第二次满足a{i}>a{i+1}的i+1
     * @param nums
     * @return
     */
    public int[] findTwoSwapped(List<Integer> nums) {
        int n = nums.size();
        int x = -1, y = -1;
        boolean flag = false;
        for (int i = 0; i < n - 1; ++i) {
            if (nums.get(i + 1) < nums.get(i)) {
                //y的作用是找到第二次满足a{i}>a{i+1}的i+1
                y = nums.get(i + 1);
                //flag的作用就是找到第一次满足a{i}>a{i+1}的i
                if (!flag) {
                    x = nums.get(i);
                    flag = true;
                } else {
                    break;
                }
            }
        }
        return new int[]{x, y};
    }

    public void recover(TreeNode root, int count, int x, int y) {
        if (root != null) {
            if (root.val == x || root.val == y) {
                root.val = root.val == x ? y : x;
                if (--count == 0) {
                    return;
                }
            }
            recover(root.right, count, x, y);
            recover(root.left, count, x, y);
        }
    }
}
