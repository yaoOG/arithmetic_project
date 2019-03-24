package com.yao.leetcode;

/**
 * Created by zhuyao on 2018/04/27.
 *
 * 给定一个字符串 s，找到 s 中最长的回文子串。你可以假设 s 的最大长度为1000。
 *
 * 示例 1：
 * 输入: "babad"
 * 输出: "bab"
 * 注意: "aba"也是一个有效答案。
 *
 * 示例 2：
 * 输入: "cbbd"
 * 输出: "bb"
 */
public class Main5 {
    public static String longestPalindrome(String s) {
        int start = 0, end = 0;
        for (int i = 0; i < s.length(); i++) {
            int len1 = expandAroundCenter(s, i, i);
            System.out.println("len1 = "+len1);
            int len2 = expandAroundCenter(s, i, i + 1);
            System.out.println("len2 = "+len2);
            int len = Math.max(len1, len2);
            System.out.println("len = " +len);
            if (len > end - start) {
                start = i - (len - 1) / 2;
                end = i + len / 2;
                System.out.println("star = "+start);
                System.out.println("end = "+end);
            }
        }
        return s.substring(start, end + 1);
    }
    //从字符串中间向两边扩展比较
    private static int expandAroundCenter(String s, int left, int right) {
        int L = left, R = right;
        while (L >= 0 && R < s.length() && s.charAt(L) == s.charAt(R)) {
            L--;
            R++;
        }
        return R - L - 1;
    }

    public static void main(String[] args) {
        String str = "babad";
        String s = longestPalindrome(str);
        System.out.println(s);


    }
}
