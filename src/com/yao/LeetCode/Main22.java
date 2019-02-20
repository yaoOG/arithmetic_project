package com.yao.LeetCode;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhuyao
 * @date 2019/02/20
 * 给出 n 代表生成括号的对数，请你写出一个函数，使其能够生成所有可能的并且有效的括号组合。
 * 例如，给出 n = 3，生成结果为：
 * [
 * "((()))",
 * "(()())",
 * "(())()",
 * "()(())",
 * "()()()"
 * ]
 */
public class Main22 {

    public List<String> generateParenthesis(int n) {
        List<String> list = new ArrayList<String>();
        backtrack(list, "", 0, 0, n);
        return list;
    }

    private void backtrack(List<String> list, String str, int open, int close, int max) {

        if (str.length() == max * 2) {
            list.add(str);
            return;
        }

        if (open < max) {
            backtrack(list, str + "(", open + 1, close, max);
        }
        if (close < open) {
            backtrack(list, str + ")", open, close + 1, max);
        }
    }
}
