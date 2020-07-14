package com.yao.leetcode;

import java.util.*;

/**
 * @author Daniel:)
 *
 * 给出一个字符串 s（仅含有小写英文字母和括号）。
 *
 * 请你按照从括号内到外的顺序，逐层反转每对匹配括号中的字符串，并返回最终的结果。
 *
 * 注意，您的结果中 不应 包含任何括号。
 *
 * 示例 1：
 *
 * 输入：s = "(abcd)"
 * 输出："dcba"
 * 示例 2：
 *
 * 输入：s = "(u(love)i)"
 * 输出："iloveu"
 * 示例 3：
 *
 * 输入：s = "(ed(et(oc))el)"
 * 输出："leetcode"
 * 示例 4：
 *
 * 输入：s = "a(bcdefghijkl(mno)p)q"
 * 输出："apmnolkjihgfedcbq"
 */
public class Main1190 {

    public static void main(String[] args) {
        Main1190 main1190 = new Main1190();
        String result = main1190.reverseParentheses("(ed(et(oc))el)");
        System.out.println(result);
    }

    public String reverseParentheses(String s) {
        Deque<Character> stack = new ArrayDeque<>();
        for (char c : s.toCharArray()) {
            if (c == ')') {
                Queue<Character> queue = new LinkedList<>();
                while (!stack.isEmpty() && stack.peek() != '(')
                    queue.add(stack.pop());
                if (!stack.isEmpty())
                    //移除栈中的左括号
                    stack.pop();
                while (!queue.isEmpty())
                    stack.push(queue.remove());
            } else {
                stack.push(c);
            }
        }
        StringBuilder sb = new StringBuilder();
        while (!stack.isEmpty())
            sb.append(stack.pollLast());

        return sb.toString();
    }
}
