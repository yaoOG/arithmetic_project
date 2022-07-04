package com.yao.leetcode;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * 给定一个字符串，找出不含有重复字符的 最长子串 的长度。
 *
 * 示例：
 *
 * 给定 "abcabcbb" ，没有重复字符的最长子串是 "abc" ，那么长度就是3。
 *
 * 给定 "bbbbb" ，最长的子串就是 "b" ，长度是1。
 *
 * 给定 "pwwkew" ，最长子串是 "wke" ，长度是3。请注意答案必须是一个子串，"pwke" 是 子序列 而不是子串。
 */
public class Main3 {

    /**
     * j来代表快指针，当当前字符没有重复的情况下向后遍历，同时将字符放入set存储
     * i来代表慢指针，当j所在位置的字符出现重复时，从set中删除i所在位置的字符，也就是之前遍历过的字符，直到set中不存在与当前字符重复的字符
     * set来存储遍历过程中的不重复字符串
     * @param s
     * @return
     */
    public int lengthOfLongestSubstring(String s) {
        int i = 0, j = 0, max = 0;
        Set<Character> set = new HashSet<>();

        while (j < s.length()) {
            if (!set.contains(s.charAt(j))) {
                set.add(s.charAt(j++));
                max = Math.max(max, set.size());
            } else {
                set.remove(s.charAt(i++));
            }
        }

        return max;
    }

    public static void main(String[] args) {
        Main3 main3 = new Main3();
        String str = "pwwkew";
        int len = main3.lengthOfLongestSubstring(str);
        System.out.println(len);

    }

}
