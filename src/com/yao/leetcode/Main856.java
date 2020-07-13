package com.yao.leetcode;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * @author Daniel:)
 *
 * 给定一个平衡括号字符串 S，按下述规则计算该字符串的分数：
 *
 * () 得 1 分。
 * AB 得 A + B 分，其中 A 和 B 是平衡括号字符串。
 * (A) 得 2 * A 分，其中 A 是平衡括号字符串。
 *  
 *
 * 示例 1：
 *
 * 输入： "()"
 * 输出： 1
 * 示例 2：
 *
 * 输入： "(())"
 * 输出： 2
 * 示例 3：
 *
 * 输入： "()()"
 * 输出： 2
 * 示例 4：
 *
 * 输入： "(()(()))"
 * 输出： 6
 */
public class Main856 {

    public int scoreOfParentheses(String S) {
        //栈来维护当前所在的深度，以及每一层深度的得分
        Deque<Integer> stack = new ArrayDeque<>();
        stack.push(0); // The score of the current frame

        for (char c: S.toCharArray()) {
            if (c == '(')
                //遇到一个左括号 ( 时，我们将深度加一，并且新的深度的得分置为 0
                stack.push(0);
            else {
                //当我们遇到一个右括号 ) 时，我们将当前深度的得分乘二并加到上一层的深度。这里有一种例外情况，如果遇到的是 ()，那么只将得分加一。
                int v = stack.pop();
                int w = stack.pop();
                stack.push(w + Math.max(2 * v, 1));
            }
        }

        return stack.pop();
    }
}
