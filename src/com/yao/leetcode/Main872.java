package com.yao.leetcode;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * @author zhuyao
 * @date 2019/03/23
 */
public class Main872 {
    public boolean leafSimilar(TreeNode root1, TreeNode root2) {

        List<Integer> leavesList1 = new ArrayList<>();
        List<Integer> leavesList2 = new ArrayList<>();
        leavesList1 = getLeavesValue(leavesList1, root1);
        leavesList2 = getLeavesValue(leavesList2, root2);
        if (leavesList1.size() != leavesList2.size()) {
            return false;
        }
        for (int i = 0; i < leavesList1.size(); i++) {
            if (!leavesList1.get(i).equals(leavesList2.get(i))) {
                return false;
            }
        }
        return true;
    }

    public List<Integer> getLeavesValue(List<Integer> leavesList, TreeNode node) {
        if (node.left == null && node.right == null) {
            leavesList.add(node.val);
        }
        if (node.left != null) {
            getLeavesValue(leavesList, node.left);
        }
        if (node.right != null) {
            getLeavesValue(leavesList, node.right);
        }
        return leavesList;
    }


    public boolean leafSimilar2(TreeNode root1, TreeNode root2) {
        Stack<TreeNode> s1 = new Stack<>(), s2 = new Stack<>();
        s1.push(root1);
        s2.push(root2);
        while (!s1.empty() && !s2.empty()) {
            if (dfs(s1) != dfs(s2)) {
                return false;
            }
        }
        return s1.empty() && s2.empty();
    }

    private int dfs(Stack<TreeNode> s) {
        while (true) {
            TreeNode node = s.pop();
            if (node.right != null) {
                s.push(node.right);
            }
            if (node.left != null) {
                s.push(node.left);
            }
            if (node.left == null && node.right == null) {
                return node.val;
            }
        }
    }
}
