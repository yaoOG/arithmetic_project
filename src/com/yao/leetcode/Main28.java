package com.yao.leetcode;

/**
 * @author zhuyao
 * @date 2019/02/22
 */
public class Main28 {
    /**
     * 实现 strStr() 函数。
     * 给定一个 haystack 字符串和一个 needle 字符串，在 haystack 字符串中找出 needle 字符串出现的第一个位置 (从0开始)。如果不存在，则返回  -1。
     * 示例 1:
     * 输入: haystack = "hello", needle = "ll"
     * 输出: 2
     * 示例 2:
     * 输入: haystack = "aaaaa", needle = "bba"
     * 输出: -1
     * @param haystack
     * @param needle
     * @return
     */
    public int strStr(String haystack, String needle) {
        int l1 = haystack.length(), l2 = needle.length();
        if (l1 < l2) {
            return -1;
        } else if (l2 == 0) {
            return 0;
        }
        int threshold = l1 - l2;
        for (int i = 0; i <= threshold; ++i) {
            if (haystack.substring(i,i+l2).equals(needle)) {
                return i;
            }
        }
        return -1;
    }

    public int strStr2(String haystack, String needle) {
        int l1 = haystack.length(),l2 = needle.length();
        if (l1 < l2){
            return  -1;
        }else if (l2 == 0){
            return 0;
        }
        int threshold = l1 - l2;
        for (int i = 0; i <= threshold; i++) {
            if (haystack.substring(i, i + l2).equals(needle)) {
                return i;
            }
        }
        return  -1;
    }
}
