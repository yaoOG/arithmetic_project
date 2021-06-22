package com.yao.leetcode;

import java.util.Arrays;
import java.util.HashMap;

/**
 * @author Daniel:)
 *
 * 给定两个字符串 s 和 t ，编写一个函数来判断 t 是否是 s 的字母异位词。
 * 示例1:
 * 输入: s = "anagram", t = "nagaram"
 * 输出: true
 * 示例 2:
 * 输入: s = "rat", t = "car"
 * 输出: false
 * 说明:
 * 你可以假设字符串只包含小写字母。
 */
public class Main242 {
    /**
     * 排序的方式
     * t 是 s 的异位词等价于「两个字符串排序后相等」。
     * 因此我们可以对字符串 s 和 t 分别排序，看排序后的字符串是否相等即可判断。
     * 此外，如果 s 和 t 的长度不同，t 必然不是 s 的异位词。
     *
     * 时间复杂度：O(nlogn)，其中 n 为 s 的长度。
     *            排序的时间复杂度为 O(nlogn)，比较两个字符串是否相等时间复杂度为 O(n)，
     *            因此总体时间复杂度为 O(nlogn+n)=O(nlogn)。
     *
     * 空间复杂度：O(logn)。排序需要 O(logn) 的空间复杂度。
     *
     * @param s
     * @param t
     * @return
     */
    public boolean isAnagram(String s, String t) {
        if (s.length() != t.length()) {
            return false;
        }
        char[] str1 = s.toCharArray();
        char[] str2 = t.toCharArray();
        Arrays.sort(str1);
        Arrays.sort(str2);
        return Arrays.equals(str1, str2);
    }

    /**
     * Hash表方式
     *
     * 时间复杂度：O(n)，其中 n 为 s 的长度。
     * 空间复杂度：O(S)，其中 S 为字符集大小，此处 S=26S=26。
     * @param s
     * @param t
     * @return
     */
    public boolean isAnagram2(String s, String t) {
        if (s.length() != t.length()) {
            return false;
        }
        HashMap<Character, Integer> map = new HashMap<>();
        for (int i = 0; i < s.length(); i++) {
            char charAtS = s.charAt(i);
            map.put(charAtS, map.getOrDefault(charAtS, 0) + 1);
        }
        for (int i = 0; i < t.length(); i++) {
            char charAtT = t.charAt(i);
            int value = map.getOrDefault(charAtT, 0) - 1;
            if (value < 0) {
                return false;
            }
            map.put(charAtT, value);
        }
        return true;
    }

}
