package com.yao.leetcode;

import java.util.HashMap;
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
