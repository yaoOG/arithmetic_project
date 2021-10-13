package com.yao.leetcode;

import java.util.*;

/**
 * 给定一个二叉树，返回其按层次遍历的节点值。 （即逐层地，从左到右访问所有节点）。
 * 例如:
 * 给定二叉树: [3,9,20,null,null,15,7],
 * <p>
 * 3
 * / \
 * 9  20
 * /  \
 * 15   7
 * 返回其层次遍历结果：
 * <p>
 * [
 * [3],
 * [9,20],
 * [15,7]
 * ]
 *
 * @author zhuyao
 * @date 2019/03/01
 */
public class Main102 {

    /**
     * 依次遍历每一层，将该层的节点值添加到结果list中，并将该层节点的左孩子节点和右孩子节点入栈，继续循环
     * @param root
     * @return
     */
    public List<List<Integer>> levelOrder(TreeNode root) {

        if (root == null) return Collections.emptyList();
        List<List<Integer>> result = new ArrayList<>();
        Queue<TreeNode> queue = new LinkedList<>();
        //添加元素到队列尾部
        queue.offer(root);
        while (!queue.isEmpty()) {
            int curSize = queue.size();
            List<Integer> numbers = new ArrayList<>();
            //遍历每一层
            for (int i = 0; i < curSize; i++) {
                //取队首元素并删除
                TreeNode curNode = queue.poll();
                if (curNode != null) {
                    //将当前层的节点添加到结果中
                    numbers.add(curNode.val);
                    //将下一层的节点入栈
                    if (curNode.left != null) queue.offer(curNode.left);
                    if (curNode.right != null) queue.offer(curNode.right);
                }
            }
            result.add(numbers);
        }
        return result;
    }

    public static void main(String[] args) {
        Deque<Integer> deque = new LinkedList<>();
        deque.offer(1);
        deque.offer(2);
        deque.addLast(3);
        Integer pollFirst = deque.poll();
        Integer pollFirst1 = deque.pollLast();
        Integer integer = deque.peekLast();
        System.out.println(integer);
    }
}
