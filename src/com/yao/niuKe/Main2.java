package com.yao.niuKe;

import java.util.Scanner;
import java.util.HashMap;

/**
 * 小米2017校招笔试
 * 现在有一棵合法的二叉树，树的节点都是用数字表示，现在给定这棵树上所有的父子关系，求这棵树的高度

    输入描述:

    输入的第一行表示节点的个数n（1 ≤ n ≤ 1000，节点的编号为0到n-1）组成，
    下面是n-1行，每行有两个整数，第一个数表示父节点的编号，第二个数表示子节点的编号

    输出描述:
    输出树的高度，为一个整数

 */
public class Main2 {
    public static class TreeNode{
        TreeNode left=null;
        TreeNode right=null;
        int value;
        public TreeNode(int v){
            this.value=v;
        }
    }
    public int Height(TreeNode root){
        if(root==null){
            return 0;
        }
        int left=0,right=0;
        if(root.left!=null){
            left=Height(root.left);
        }
        if(root.right!=null){
            right=Height(root.right);
        }
        return ((left>=right)?left:right)+1;
    }
    public static void main(String[] args) {
        Main2 m=new Main2();
        Scanner s=new Scanner(System.in);
        int num=s.nextInt();
        int rootval=s.nextInt();//父节点编号
        int rootfirst=s.nextInt();//子节点编号

        TreeNode root=new TreeNode(rootval);//父节点
        TreeNode rootLeft=new TreeNode(rootfirst);//子节点
        root.left=rootLeft;

        HashMap<Integer,TreeNode> nodeMap=new HashMap<Integer,TreeNode>();
        nodeMap.put(rootval, root);
        nodeMap.put(rootfirst,rootLeft);

        for(int i=0;i<num-2;i++){
            int parentval=s.nextInt();
            int childval=s.nextInt();
            TreeNode parent=nodeMap.get(parentval);
            TreeNode child=new TreeNode(childval);
            if(parent!=null){
                if(parent.left==null){
                    parent.left=child;
                }else if(parent.right==null){
                    parent.right=child;
                }
            }else{
                parent=new TreeNode(parentval);
                parent.left=child;
            }
            nodeMap.put(parentval, parent);
            nodeMap.put(childval, child);
        }
        System.out.println(m.Height(root));
        s.close();
    }
}