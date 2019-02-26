package com.yao.LeetCode;


import java.util.LinkedList;

/**
 * @author zhuyao
 * @date 2019/02/25
 */
public class Main32 {
    /**
     * 给定一个只包含 '(' 和 ')' 的字符串，找出最长的包含有效括号的子串的长度。
     * 示例 1:
     * 输入: "(()"
     * 输出: 2
     * 解释: 最长有效括号子串为 "()"
     * 示例 2:
     * 输入: ")()())"
     * 输出: 4
     * 解释: 最长有效括号子串为 "()()"
     *解题思路：
     *
     * 1.需有一个变量start记录有效括号子串的起始下标，max表示最长有效括号子串长度，初始值均为0
     *
     * 2.遍历给字符串中的所有字符
     *
     *     2.1若当前字符s[index]为左括号'('，将当前字符下标index入栈（下标稍后有其他用处），处理下一字符
     *
     *     2.2若当前字符s[index]为右括号')'，判断当前栈是否为空
     *
     *         2.2.1若栈为空，则start = index + 1，处理下一字符（当前字符右括号下标不入栈）
     *
     *         2.2.2若栈不为空，则出栈（由于仅左括号入栈，则出栈元素对应的字符一定为左括号，可与当前字符右括号配对），判断栈是否为空
     *
     *             2.2.2.1若栈为空，则max = max(max, index-start+1)
     *
     *             2.2.2.2若栈不为空，则max = max(max, index-栈顶元素值)

     * @param s
     * @return
     */
    public int longestValidParentheses(String s) {
        LinkedList<Integer> stack = new LinkedList<>();
        int result = 0;
        stack.push(-1);
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == ')' && stack.size() > 1 && s.charAt(stack.peek()) == '(') {
                stack.pop();
                result = Math.max(result, i - stack.peek());
            } else {
                stack.push(i);
            }
        }
        return result;
    }

    public static void main(String[] args) {
        Main32 main32 = new Main32();
        int i = main32.longestValidParentheses(")()())");
        System.out.println(i);
    }
}
