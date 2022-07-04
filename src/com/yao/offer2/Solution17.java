package com.yao.offer2;

import java.util.HashMap;

/**
 * @author choo
 */
public class Solution17 {

    /**
     * 移动第一个指针的时机：start和end之间已经包含字符串t
     * 移动第二个指针的时机：start和end之间没有包含字符串t
     * @param s
     * @param t
     * @return
     */
    public String minWindow(String s, String t) {
        HashMap<Character, Integer> charToCount = new HashMap<>();
        //扫描较短字符串，每扫到一个字符就把该字符在哈希表中对应的值加1
        for (char ch : t.toCharArray()) {
            charToCount.put(ch, charToCount.getOrDefault(ch, 0) + 1);
        }
        //变量count是出现在字符串t中但没有出现在s中的子字符串中的字符的个数
        //当变量count=0时 两个指针之间的子字符串就包含字符串t中的所有字符
        int count = charToCount.size();
        //变量start相当于第一个指针，变量end相当于第二个指针，指向字符串s的子字符串中的最后一个字符
        int start = 0, end = 0, minStart = 0, minEnd = 0;
        int minLength = Integer.MAX_VALUE;
        while (end < s.length() || (count == 0 && end == s.length())) {
            if (count > 0) {
                char endCh = s.charAt(end);
                //扫描长字符串，如果哈希表中存在该字符，则把该字符在哈希表中对应的值减1
                if (charToCount.containsKey(endCh)) {
                    charToCount.put(endCh, charToCount.get(endCh) - 1);
                    if (charToCount.get(endCh) == 0) {
                        count--;
                    }
                }
                end++;
            } else {
                //记录最小长度
                if (end - start < minLength) {
                    minLength = end - start;
                    minStart = start;
                    minEnd = end;
                }
                char startCh = s.charAt(start);
                if (charToCount.containsKey(startCh)) {
                    charToCount.put(startCh, charToCount.get(startCh) + 1);
                    if (charToCount.get(startCh) == 1) {
                        count++;
                    }
                }
                start++;
            }
        }
        return minLength < Integer.MAX_VALUE ? s.substring(minStart, minEnd) : "";
    }

    public static void main(String[] args) {
        Solution17 solution17 = new Solution17();
        solution17.minWindow("ADOBECODEBANC", "ABC");
    }
}
