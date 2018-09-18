package com.yao.tree;

import java.util.LinkedList;
import java.util.Queue;

public class BinTreeTest {


    public final static int MAX = 40;
    private Object data;//数据元数
    private BinTreeTest left, right;
    private int nMaxLeft;//左子树的最长距离
    private int nMaxRight;//右子树的最长距离;
    private int nMaxLen; //最长距离
    private int treeWidth;//二叉树宽度


    public BinTreeTest() {}

    public BinTreeTest(Object data) {
        this.data = data;
        left = null;
        right = null;
    }

    public BinTreeTest(Object data, BinTreeTest left, BinTreeTest right) {
        this.data = data;
        this.left = left;
        this.right = right;
    }

    @Override
    public String toString() {
        return data.toString();
    }

    //层次遍历二叉树 ，并获取二叉树的宽度
    public void LayerOrder(BinTreeTest root) {
        if (root == null) {
            return;
        }
        Queue<BinTreeTest> queue = new LinkedList<BinTreeTest>();
        queue.add(root);
        while (queue.size() != 0) {
            int len = queue.size();
            treeWidth = Math.max(treeWidth, len);
            for (int i = 0; i < len; i++) {
                BinTreeTest temp = queue.poll();
                System.out.println(temp.data);
                if (temp.left != null) {
                    queue.add(temp.left);
                }
                if (temp.right != null) {
                    queue.add(temp.right);
                }
            }
        }

    }


    //前序遍历
    public static void preOrder(BinTreeTest parent) {

        if (parent == null) {
            return;
        }
        System.out.println(parent.data + " ");
        preOrder(parent.left);
        preOrder(parent.right);

    }

    //中序遍历

    public static void inOrder(BinTreeTest parent) {
        if (parent == null) {
            return;
        }
        inOrder(parent.left);
        System.out.println(parent.data + " ");
        inOrder(parent.right);
    }

    //后序遍历
    public static void postOrder(BinTreeTest parent) {
        if (parent == null) {
            return;
        }
        postOrder(parent.left);
        postOrder(parent.right);
        System.out.println(parent.data + " ");
    }


    //返回树的叶子节点数
    public int Allleaves() {

        System.out.print(this.data);
        if (left == null && right == null) {
            return 1;
        }

        //左边节点数
        int leftCount = (left == null ? 0 : left.Allleaves());
        int rightCount = (right == null ? 0 : right.Allleaves());
        return 1 + leftCount + rightCount;
    }

    // 获取高度
    public int height() {
        int heightOfTree;
        int leftHeight = (left == null ? 0 : left.height());
        int rightHeight = (right == null ? 0 : right.height());
        heightOfTree = Math.max(leftHeight, rightHeight);
//        System.out.println("value==" + this.data + " left right==" + leftHeight + " right height==" + rightHeight);
        return 1 + heightOfTree;

    }

    //两节点间的最远距离
    public int getMaxDistance(BinTreeTest parent) {

        if (parent == null) {
            return 0; //叶子节点 返回
        }
        if (parent.left == null) {
            parent.nMaxLeft = 0;
        }
        if (parent.right == null) {
            parent.nMaxRight = 0;
        }
        if (parent.left != null) {
            getMaxDistance(parent.left); //如果左子树不为空，递归寻找左子树最长距离
        }
        if (parent.right != null) {
            getMaxDistance(parent.right);//如果右子树不为空，递归寻找右子树最长距离
        }
        if (parent.left != null) {
            parent.nMaxLeft = Math.max(parent.left.nMaxLeft, parent.left.nMaxRight) + 1;
        }
        if (parent.right != null) {
            parent.nMaxRight = Math.max(parent.right.nMaxLeft, parent.right.nMaxRight) + 1;
        }
        nMaxLen = Math.max(parent.nMaxLeft + parent.nMaxRight, nMaxLen);
        return nMaxLen;
    }

    public static void main(String[] args) {
        // TODO Auto-generated method stub

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

        BinTreeTest e = new BinTreeTest("E");
        BinTreeTest g = new BinTreeTest("G");
        BinTreeTest h = new BinTreeTest("H");
        BinTreeTest i = new BinTreeTest("I");
        BinTreeTest d = new BinTreeTest("D", null, g);
        BinTreeTest f = new BinTreeTest("F", h, i);
        BinTreeTest b = new BinTreeTest("B", d, e);
        BinTreeTest c = new BinTreeTest("C", f, null);
        BinTreeTest tree = new BinTreeTest("A", b, c);

//        System.out.println("前序遍历二叉树结果：\n");
//        tree.preOrder(tree);
//
//        System.out.println("中序遍历二叉树结果:\n");
//        tree.inOrder(tree);
//
//
//        System.out.println("后序遍历二叉树结果：\n");
//        tree.postOrder(tree);
//        System.out.println("层级遍历二叉树:");
//        tree.LayerOrder(tree);
//        System.out.println("二叉树的宽度:" + tree.treeWidth);
//        System.out.println("二叉树的高度:" + tree.height());
//        System.out.println();
//
//        System.out.println("二叉树的节点数为；" + tree.Allleaves());
//
//        System.out.println("二叉树的所有的节点数为:" + tree.Allleaves());
//
//        System.out.println("获取二叉树的最大距离：\n");
        int m = tree.getMaxDistance(tree);
//        System.out.println("MaxLen=" + tree.nMaxLen);
//        System.out.println(m);
    }

}
