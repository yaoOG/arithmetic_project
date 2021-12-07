package com.yao.leetcode;


import java.util.Deque;
import java.util.LinkedList;

/**
 * @author zhuyao
 * @date 2019/02/25
 */
public class Main32 {


    public int longestValidParentheses(String s) {
        int maxans = 0;
        Deque<Integer> stack = new LinkedList<>();
        stack.push(-1);
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '(') {
                stack.push(i);
            } else {
                stack.pop();
                if (stack.isEmpty()) {
                    stack.push(i);
                } else {
                    maxans = Math.max(maxans, i - stack.peek());
                }
            }
        }
        return maxans;
    }

    public static void main(String[] args) {
        Main32 main32 = new Main32();
        int i = main32.longestValidParentheses(")()())");
        System.out.println(i);
    }
}
