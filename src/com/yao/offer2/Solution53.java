package com.yao.offer2;

import com.yao.data.structrue.tree.TreeNode;
import java.util.Stack;

public class Solution53 {

    //通过二叉树的中序遍历，用一个boolean变量foundFlag来记录已经遍历到节点P。
    // 该变量初始化为false，遍历到节点P就将它设为true，在这个变量变成true之后遍历到的第一个节点就是要找的节点
    public TreeNode inorderSuccessor(TreeNode root, TreeNode p) {
        Stack<TreeNode> stack = new Stack<>();
        TreeNode cur = root;
        boolean foundFlag = false;
        while (cur != null || !stack.isEmpty()) {
            while (cur != null) {
                stack.push(cur);
                cur = cur.left;
            }
            cur = stack.pop();
            if (foundFlag) {
                break;
            } else if (p == cur) {
                foundFlag = true;
            }
            cur = cur.right;
        }
        return cur;
    }

    /**
     * 从根节点开始，每到达一个节点就比较根节点的值和节点p的值。
     * 如果当前节点的值小于或等于节点p的值，那么节点p的下一个节点应该在它的右子树。
     * 如果当前节点的值大于p的值，那么当前节点有可能是它的下一个节点，此时当前节点的值比节点p的值大，但节点p的下一个节点是所有比它大的节点中值最小的一个。
     * 因此接下来前往当前节点的左子树，确定是否能找到值更小但仍然大于节点p的值的节点
     * @param root
     * @param p
     * @return
     */
    public TreeNode inorderSuccessor2(TreeNode root, TreeNode p) {
        TreeNode cur = root;
        TreeNode result = null;
        while (cur != null) {
            if (cur.val > p.val) {
                result = cur;
                cur = cur.left;
            } else {
                cur = cur.right;
            }
        }
        return result;

    }
}
