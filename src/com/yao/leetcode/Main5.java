package com.yao.leetcode;

/**
 * Created by zhuyao on 2018/04/27.
 * 给定一个字符串 s，找到 s 中最长的回文子串。你可以假设 s 的最大长度为1000。
 * 示例 1：
 * 输入: "babad"
 * 输出: "bab"
 * 注意: "aba"也是一个有效答案。
 * 示例 2：
 * 输入: "cbbd"
 * 输出: "bb"
 */
public class Main5 {
    public String longestPalindrome(String s) {
        int maxLen = 1, begin = 0;
        for (int i = 0; i < s.length(); i++) {
            //奇数回文字符串
            int oddLen = expandAroundCenter(s, i, i);
            //偶数回文字符串
            int evenLen = expandAroundCenter(s, i, i + 1);
            int curMaxLen = Math.max(oddLen, evenLen);
            if (curMaxLen > maxLen) {
                maxLen = curMaxLen;
                begin = i - (maxLen - 1) / 2;
            }
        }
        return s.substring(begin, begin + maxLen);
    }

    //从字符串中间向两边扩展比较
    private int expandAroundCenter(String s, int left, int right) {
        // 当 left = right 的时候回文中心是一个字符，回文串的长度是奇数
        // 当 right = left + 1 的时候，此时回文中心两个字符，回文串的长度是偶数
        int L = left, R = right;
        while (L >= 0 && R < s.length() && s.charAt(L) == s.charAt(R)) {
            L--;
            R++;
        }
        // 跳出 while 循环时，恰好满足 s.charAt(i) != s.charAt(j)
        // 回文串的长度是 j - i + 1 - 2 = j - i - 1
        return R - L - 1;
    }

    public static void main(String[] args) {
        String str = "babad";
        Main5 main5 = new Main5();
        String s = main5.longestPalindrome(str);
        System.out.println(s);


    }
}
