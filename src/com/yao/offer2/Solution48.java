package com.yao.offer2;
import com.yao.data.structrue.tree.TreeNode;

public class Solution48 {
    // Encodes a tree to a single string.
    public String serialize(TreeNode root) {
        if (root == null) {
            return "#";
        }
        String leftStr = serialize(root.left);
        String rightStr = serialize(root.right);
        return root.val + "," + leftStr + "," + rightStr;
    }

    // Decodes your encoded data to tree.
    public TreeNode deserialize(String data) {
        String[] nodeStrs = data.split(",");
        int[] i = {0};
        return dfs(nodeStrs , i);

    }

    private TreeNode  dfs(String[] strs, int[] i) {
        String str = strs[i[0]];
        i[0]++;
        if (str.equals("#")) {
            return null;
        }
        TreeNode node = new TreeNode(Integer.parseInt(str));
        node.left = dfs(strs,i);
        node.right = dfs(strs,i);
        return node;
    }
}
