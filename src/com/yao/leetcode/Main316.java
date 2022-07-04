package com.yao.leetcode;

import java.util.*;

/**
 * @author Daniel:)
 *
 * 给你一个仅包含小写字母的字符串，请你去除字符串中重复的字母，使得每个字母只出现一次。需保证返回结果的字典序最小（要求不能打乱其他字符的相对位置）。
 *
 * 示例 1:
 *
 * 输入: "bcabc"
 * 输出: "abc"
 * 示例 2:
 *
 * 输入: "cbacdcbc"
 * 输出: "acdb"
 *
 *
 */
public class Main316 {

    /**
     * 贪心 + 栈
     * 时间复杂度：O(N)
     * 空间复杂度：O(1)
     * 用栈来存储最终返回的字符串，并维持字符串的最小字典序。每遇到一个字符，如果这个字符不存在于栈中，就需要将该字符压入栈中。
     * 但在压入之前，需要先将之后还会出现，并且字典序比当前字符小的栈顶字符移除，然后再将当前字符压入。
     *
     * @param s
     * @return
     */
    public String removeDuplicateLetters(String s) {

        Deque<Character> stack = new ArrayDeque<>();

        //空间换时间：利用HashSet来对当前元素是否在栈中进行优化，HashSet的查找时间复杂度是O(1)
        HashSet<Character> seen = new HashSet<>();

        //存储每一个字符最后出现的位置
        HashMap<Character, Integer> lastOccurrence = new HashMap<>();

        for (int i = 0; i < s.length(); i++)
            lastOccurrence.put(s.charAt(i), i);

        for (int i = 0; i < s.length(); i++) {
            char currentChar = s.charAt(i);
            if (!seen.contains(currentChar)) {
                // 将之后还会出现，并且字典序比当前字符小的栈顶字符移除
                // 判断当前字符是不是小于栈顶字符 currentChar < stack.peek()
                // 判断栈顶字符是不是参数s中最后一个字符
                while (!stack.isEmpty() && currentChar < stack.peek() && lastOccurrence.get(stack.peek()) > i) {
                    seen.remove(stack.pop());
                }
                seen.add(currentChar);
                stack.push(currentChar);
            }
        }
        StringBuilder sb = new StringBuilder(stack.size());
        while (!stack.isEmpty()) {
            sb.append(stack.pollLast());
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        Main316 main316 = new Main316();
        String result = main316.removeDuplicateLetters("bcabc");
        System.out.println(result);
    }
}
