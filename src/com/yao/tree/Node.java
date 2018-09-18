package com.yao.tree;

/**
 * @author zhuyao
 * @date 2018/09/16
 */
public class Node {
    String data;
    Node left;
    Node right;

    Node(String value) {
        this.data = value;
    }

    public Node(String data, Node left, Node right) {
        this.data = data;
        this.left = left;
        this.right = right;
    }
}
