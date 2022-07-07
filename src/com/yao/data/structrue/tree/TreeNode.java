package com.yao.data.structrue.tree;

/**
 * @author zhuyao
 * @date 2019/03/01
 */
public class TreeNode {
    public int val;
    public TreeNode left;
    public TreeNode right;
    /**
     * 左子树的最长距离
     */
    int nMaxLeft;
    /**
     * 右子树的最长距离
     */
    int nMaxRight;

    public TreeNode(int x) {
        val = x;
    }
}
