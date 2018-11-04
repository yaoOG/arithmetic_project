package com.yao.dataStructrue.tree;

/**
 * @author zhuyao
 * @date 2018/09/16
 */
public class BiTreeArithmetic {
    private int maxLen = 0;

    // 该方法返回从root节点出发，向左或向右所能走的最远距离（该方法的返回值并非是整个树的最远距离，而是它的左子树最远距离和右子树最远距离两者中的较大值）
    // maxLen用于保存整个二叉树的最远距离
    public int findMaxLen(Node root) {
        if (root == null) {
            return 0;
        }
        if (root.left == null && root.right == null) {
            return 0;
        }
        int leftMaxLen = findMaxLen(root.left) + 1;
        int rightMaxLen = findMaxLen(root.right) + 1;

        int maxTemp = leftMaxLen + rightMaxLen;
        maxLen = Math.max(maxTemp, maxLen);

        return leftMaxLen > rightMaxLen ? leftMaxLen : rightMaxLen;
    }

    // 测试
    public static void main(String[] args) {

        /**
         *            A
         *         /     \
         *        B       C
         *       / \     /
         *      D  E    F
         *     / \  \  / \
         *       G    H   I
         *
         *
         */

        Node e = new Node("E");
        Node g = new Node("G");
        Node h = new Node("H");
        Node i = new Node("I");
        Node d = new Node("D", null, g);
        Node f = new Node("F", h, i);
        Node b = new Node("B", d, e);
        Node c = new Node("C", f, null);
        Node tree = new Node("A", b, c);
        BiTreeArithmetic test = new BiTreeArithmetic();
        int out = test.findMaxLen(tree);
        System.out.println(out);
        // maxLen返回的才是二叉树的最远距离
        System.out.println(test.maxLen);
    }

}
