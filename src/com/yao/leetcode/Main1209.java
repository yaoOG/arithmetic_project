package com.yao.leetcode;

import java.util.Stack;

/**
 * @author Daniel:)
 *
 * 给你一个字符串 s，「k 倍重复项删除操作」将会从 s 中选择 k 个相邻且相等的字母，并删除它们，使被删去的字符串的左侧和右侧连在一起。
 * 你需要对 s 重复进行无限次这样的删除操作，直到无法继续为止。
 * 在执行完所有删除操作后，返回最终得到的字符串。
 * 本题答案保证唯一。
 *
 * 示例 1：
 * 输入：s = "abcd", k = 2
 * 输出："abcd"
 * 解释：没有要删除的内容。
 *
 * 示例 2：
 * 输入：s = "deeedbbcccbdaa", k = 3
 * 输出："aa"
 * 解释：
 * 先删除 "eee" 和 "ccc"，得到 "ddbbbdaa"
 * 再删除 "bbb"，得到 "dddaa"
 * 最后删除 "ddd"，得到 "aa"
 *
 * 示例 3：
 * 输入：s = "pbbcggttciiippooaais", k = 2
 * 输出："ps"
 */
public class Main1209 {

    public static void main(String[] args) {
        Main1209 main1209 = new Main1209();
        String result = main1209.removeDuplicates("deeedbbcccbdaa", 3);
        System.out.println(result);
    }


    class Pair {
        int cnt;
        char ch;
        public Pair(int cnt, char ch) {
            this.ch = ch;
            this.cnt = cnt;
        }
    }


    public String removeDuplicates(String s, int k) {
        Stack<Pair> stack = new Stack<>();
        for (int i = 0; i < s.length(); i++) {
            if (stack.empty() || s.charAt(i) != stack.peek().ch) {
                stack.push(new Pair(1,s.charAt(i)));
            } else {
                if (++stack.peek().cnt == k) {
                    stack.pop();
                }
            }
        }
        StringBuilder b = new StringBuilder();
        while (!stack.empty()) {
            Pair p = stack.pop();
            for (int i = 0; i < p.cnt; i++) {
                b.append(p.ch);
            }
        }
        return b.reverse().toString();

    }
}
