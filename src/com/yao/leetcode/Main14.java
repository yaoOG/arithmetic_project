package com.yao.leetcode;

/**
 * @author zhuyao
 * @date 2018/10/22
 * 编写一个函数来查找字符串数组中的最长公共前缀。
 * 如果不存在公共前缀，返回空字符串 ""。
 * 示例 1:
 * 输入: ["flower","flow","flight"]
 * 输出: "fl"
 * 示例 2:
 * 输入: ["dog","racecar","car"]
 * 输出: ""
 * 解释: 输入不存在公共前缀。
 * 说明:
 * 所有输入只包含小写字母 a-z 。
 *
 * 思路：取出给定字符串数组中长度最小的一个字符串（或者直接取出第一个字符串），以此为基准，遍历整个字符串数组，若基准字符串是其他所有字符串的子串，则基准字符串即为所求最长公共前缀，
 * 否则，将基准字符串截去最后一个字符，重新遍历整个字符串数组，依此类推，直到找到所有字符串数组都存在的子串为止。

 */
public class Main14 {
    public String longestCommonPrefix(String[] strs) {
        if (strs == null || strs.length == 0) {
            return "";
        }
        String prefix = strs[0];
        int count = strs.length;
        for (int i = 1; i < count; i++) {
            prefix = longestCommonPrefix(prefix, strs[i]);
            if (prefix.length() == 0) {
                break;
            }
        }
        return prefix;
    }

    public String longestCommonPrefix(String str1, String str2) {
        int length = Math.min(str1.length(), str2.length());
        int index = 0;
        while (index < length && str1.charAt(index) == str2.charAt(index)) {
            index++;
        }
        return str1.substring(0, index);
    }

}