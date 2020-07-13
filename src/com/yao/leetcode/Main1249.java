package com.yao.leetcode;

import java.util.Stack;

/**
 * @author Daniel:)
 *
 * 给你一个由 '('、')' 和小写字母组成的字符串 s。
 *
 * 你需要从字符串中删除最少数目的 '(' 或者 ')' （可以删除任意位置的括号)，使得剩下的「括号字符串」有效。
 *
 * 请返回任意一个合法字符串。
 * 示例 1：
 *
 * 输入：s = "lee(t(c)o)de)"
 * 输出："lee(t(c)o)de"
 * 解释："lee(t(co)de)" , "lee(t(c)ode)" 也是一个可行答案。
 * 示例 2：
 *
 * 输入：s = "a)b(c)d"
 * 输出："ab(c)d"
 * 示例 3：
 *
 * 输入：s = "))(("
 * 输出：""
 * 解释：空字符串也是有效的
 * 示例 4：
 *
 * 输入：s = "(a(b(c)d)"
 * 输出："a(b(c)d)"
 */
public class Main1249 {

    /**
     * 1.遇到字母一律不用考虑，影响结果的只有括号
     * 2.遍历字符串，遇到字母跳过，遇到'（'入栈其对应的索引，遇到'（'判断栈里面有没有左括号，如果有则弹出栈顶元素，如果没有那么这个'）'就必须删除，我们将该位置的'）'换为*。
     * 3.遍历完之后，如果此时栈非空，那么栈里面的索引对应的‘（’全要换为*。
     * 4.将s中的*全删除即的所需字符串。
     * @param s
     * @return
     */
    public String minRemoveToMakeValid(String s) {
        StringBuilder sb = new StringBuilder(s);
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < sb.length(); ++i) {
            if (sb.charAt(i) == '(')
                stack.push(i);
            if (sb.charAt(i) == ')') {
                if (!stack.empty())
                    stack.pop();
                else
                    sb.setCharAt(i, '*');
            }
        }
        while (!stack.empty())
            sb.setCharAt(stack.pop(), '*');
        return sb.toString().replaceAll("\\*", "");
    }


}
