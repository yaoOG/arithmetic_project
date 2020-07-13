package com.yao.leetcode;

import java.util.Stack;

/**
 * @author Daniel:)
 */
public class Main1003 {
    public boolean isValid(String s) {
        Stack<Character> stack = new Stack<>();
        for (char c: s.toCharArray()) {
            if (c == 'c') {
                if (stack.isEmpty() || stack.pop() != 'b') return false;
                if (stack.isEmpty() || stack.pop() != 'a') return false;
            } else {
                stack.push(c);
            }
        }
        return stack.isEmpty();
    }

    public static void main(String[] args) {
        Main1003 main1003 = new Main1003();
        boolean result = main1003.isValid("aabcbc");
        System.out.println(result);
    }
}
