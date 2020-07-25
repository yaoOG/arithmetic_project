package com.yao.leetcode;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author Daniel:)
 *
 * 给定一种规律 pattern 和一个字符串 str ，判断 str 是否遵循相同的规律。
 *
 * 这里的 遵循 指完全匹配，例如， pattern 里的每个字母和字符串 str 中的每个非空单词之间存在着双向连接的对应规律。
 *
 * 示例1:
 *
 * 输入: pattern = "abba", str = "dog cat cat dog"
 * 输出: true
 * 示例 2:
 *
 * 输入:pattern = "abba", str = "dog cat cat fish"
 * 输出: false
 *
 */
public class Main290 {
    public static void main(String[] args) {
        Main290 main290 = new Main290();
        boolean result = main290.wordPattern("abba", "dog cat cat fish");
        System.out.println(result);
    }
    public boolean wordPattern(String pattern, String str) {
        String[] words = str.split(" ");
        if (words.length != pattern.length())
            return false;
        Map<Object,Integer> index = new HashMap<>();
        for (int i=0; i<words.length; ++i)
            if (!Objects.equals(index.put(pattern.charAt(i), i),
                    index.put(words[i], i)))
                return false;
        return true;
    }
}
