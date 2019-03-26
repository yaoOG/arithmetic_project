package com.yao.leetcode;

/**
 * @author zhuyao
 * @date 2019/03/25
 * 根据一棵树的中序遍历与后序遍历构造二叉树。
 * <p>
 * 注意:
 * 你可以假设树中没有重复的元素。
 * <p>
 * 例如，给出
 * <p>
 * 中序遍历 inorder = [9,3,15,20,7]
 * 后序遍历 postorder = [9,15,7,20,3]
 * 返回如下的二叉树：
 * <p>
 * 3
 * / \
 * 9  20
 * /  \
 * 15   7
 */
public class Main106 {
    public TreeNode buildTree(int[] inorder, int[] postorder) {
        return buildTree(postorder.length - 1, inorder.length - 1, 0, postorder, inorder);
    }
    private TreeNode buildTree(int postStart, int inStart, int inEnd, int[] postorder, int[] inorder) {
        if (postStart < 0 || inStart < inEnd) {
            return null;
        }

        //The last element in postorder is the root.
        TreeNode root = new TreeNode(postorder[postStart]);

        //find the index of the root from inorder. Iterating from the end.
        int rIndex = inStart;
        for (int i = inStart; i >= inEnd; i--) {
            if (inorder[i] == postorder[postStart]) {
                rIndex = i;
                break;
            }
        }
        //build right and left subtrees. Again, scanning from the end to find the sections.
        root.right = buildTree(postStart - 1, inStart, rIndex + 1, postorder, inorder);
        root.left = buildTree(postStart - (inStart - rIndex) - 1, rIndex - 1, inEnd, postorder, inorder);
        return root;
    }
}
