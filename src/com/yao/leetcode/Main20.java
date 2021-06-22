package com.yao.leetcode;

import java.util.Stack;

/**
 * @author choo
 * <p>
 * 给定一个只包括 '('，')'，'{'，'}'，'['，']' 的字符串，判断字符串是否有效。
 * <p>
 * 有效字符串需满足：
 * <p>
 * 左括号必须用相同类型的右括号闭合。
 * 左括号必须以正确的顺序闭合。
 * 注意空字符串可被认为是有效字符串。
 */
public class Main20 {

    public static void main(String[] args) {
        Main20 main20 = new Main20();
        boolean result = main20.isValid("())(");
        System.out.println(result);
    }

    /**
     * 如果当前元素是[,{,(则将其对应的右括号入栈，如果是右括号则判断栈顶元素是否和该右括号相等，相等则继续，不相等则为错误结果
     *
     * @param s
     * @return
     */
    public boolean isValid(String s) {
        Stack<Character> stack = new Stack<>();
        char[] chars = s.toCharArray();
        for (char tmp : chars) {
            if (tmp == '(') {
                stack.push(')');
            } else if (tmp == '{') {
                stack.push('}');
            } else if (tmp == '[') {
                stack.push(']');
            } else if (stack.isEmpty() || stack.pop() != tmp) {
                return false;
            }
        }
        return stack.isEmpty();
    }
}
