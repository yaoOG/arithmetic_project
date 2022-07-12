package com.yao.offer2;

import com.yao.data.structrue.tree.TreeNode;
import java.util.Stack;

public class Solution54 {
    //改变中序遍历的顺序，先遍历右子节点，再遍历根节点，再遍历左子节点
    public TreeNode convertBST(TreeNode root) {
        Stack<TreeNode> stack = new Stack<>();
        TreeNode cur = root;
        int sum = 0;
        while (cur != null || !stack.isEmpty()) {
            while (cur != null) {
                stack.push(cur);
                cur = cur.right;
            }
            cur = stack.pop();
            sum += cur.val;
            cur.val = sum;
            cur = cur.left;
        }
        return root;
    }
}
