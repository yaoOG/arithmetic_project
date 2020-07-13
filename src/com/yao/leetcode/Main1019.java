package com.yao.leetcode;

import java.util.ArrayList;
import java.util.Stack;

/**
 * @author Daniel:)
 *
 * 示例 1：
 * 输入：[2,1,5]
 * 输出：[5,5,0]
 *
 * 示例 2：
 * 输入：[2,7,4,3,5]
 * 输出：[7,0,5,5,0]
 *
 * 示例 3：
 * 输入：[1,7,5,1,9,2,5,1]
 * 输出：[7,9,9,9,0,5,0,0]
 */
public class Main1019 {

    public int[] nextLargerNodes(ListNode head) {
        ArrayList<Integer> list = new ArrayList<>();
        for (ListNode node = head; node != null; node = node.next)
            list.add(node.val);
        int[] res = new int[list.size()];
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < list.size(); ++i) {
            while (!stack.isEmpty() && list.get(stack.peek()) < list.get(i))
                res[stack.pop()] = list.get(i);
            stack.push(i);
        }
        return res;
    }
}
