package com.yao.leetcode;

/**
 * @author zhuyao
 * @date 2018/10/22
 * 编写一个函数来查找字符串数组中的最长公共前缀。
 * <p>
 * 如果不存在公共前缀，返回空字符串 ""。
 * <p>
 * 示例 1:
 * <p>
 * 输入: ["flower","flow","flight"]
 * 输出: "fl"
 * 示例 2:
 * <p>
 * 输入: ["dog","racecar","car"]
 * 输出: ""
 * 解释: 输入不存在公共前缀。
 * 说明:
 * <p>
 * 所有输入只包含小写字母 a-z 。
 *
 * 思路：取出给定字符串数组中长度最小的一个字符串（或者直接取出第一个字符串），以此为基准，遍历整个字符串数组，若基准字符串是其他所有字符串的子串，则基准字符串即为所求最长公共前缀，
 * 否则，将基准字符串截去最后一个字符，重新遍历整个字符串数组，依此类推，直到找到所有字符串数组都存在的子串为止。

 */
public class Main14 {
    public static String longestCommonPrefix(String[] strs) {
        if (strs.length == 0) return "";
        String prefix = strs[0];
        for (int i = 1; i < strs.length; i++) {
            // 找出S1与Si间的最长公共字符串
            // indexOf：当存在串时返回>0；不存串时返回-1
            // 只要不存在串，就缩减串的规模，再进行查找
            while (strs[i].indexOf(prefix) != 0) {
                prefix = prefix.substring(0, prefix.length() - 1);
                if (prefix.isEmpty()) return "";
            }
        }
        return prefix;
    }

    public static void main(String[] args) {
        String s = longestCommonPrefix(new String[]{"leets", "leetcode", "leet", "leets"});
        System.out.println(s);
    }

}