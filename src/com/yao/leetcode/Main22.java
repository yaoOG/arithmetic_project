package com.yao.leetcode;

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
        List<String> result = new ArrayList<>();
        backtrack(result, "", n, n);
        return result;
    }

    private void backtrack(List<String> result, String subList, int left, int right) {
        if (left == 0 && right == 0) {
            result.add(subList);
            return;
        }
        if (left > 0) {
            backtrack(result, subList + "(", left - 1, right);
        }
        if (right > left) {
            backtrack(result, subList + ")", left, right - 1);
        }
    }
}
