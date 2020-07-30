package com.yao.leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Daniel
 *
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

    public List<String> result = new ArrayList<>();

    public List<String> generateParenthesis(int n) {
        backtrack(0, 0, n, "");
        return result;
    }

    private void backtrack(int left, int right, int n, String subList) {
        if (left == n && right == n) {
            result.add(subList);
            return;
        }
        if (left < n) {
            backtrack(left + 1, right, n, subList + "(");
        }
        if (left > right) {
            backtrack(left, right + 1, n, subList + ")");
        }
    }
}
