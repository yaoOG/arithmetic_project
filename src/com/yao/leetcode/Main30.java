package com.yao.leetcode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhuyao
 * @date 2019/03/14
 * 给定一个字符串 s 和一些长度相同的单词 words。找出 s 中恰好可以由 words 中所有单词串联形成的子串的起始位置。
 * 注意子串要与 words 中的单词完全匹配，中间不能有其他字符，但不需要考虑 words 中单词串联的顺序。
 * 示例 1：
 * 输入：
 * s = "barfoothefoobarman",
 * words = ["foo","bar"]
 * 输出：[0,9]
 * 解释：
 * 从索引 0 和 9 开始的子串分别是 "barfoor" 和 "foobar" 。
 * 输出的顺序不重要, [9,0] 也是有效答案。
 * 示例 2：
 * 输入：
 * s = "wordgoodgoodgoodbestword",
 * words = ["word","good","best","word"]
 * 输出：[]
 */
public class Main30 {


    public static List<Integer> findSubstring(String s, String[] l) {
        //TODO Waiting for thinking
        List<Integer> res = new ArrayList<>();
        if (s == null || l == null || l.length == 0) {
            return res;
        }
        int len = l[0].length(); // length of each word

        Map<String, Integer> map = new HashMap<>(16); // map for L
        for (String w : l) {
            map.put(w, map.containsKey(w) ? map.get(w) + 1 : 1);
        }

        for (int i = 0; i <= s.length() - len * l.length; i++) {
            Map<String, Integer> copy = new HashMap<>(map);
            for (int j = 0; j < l.length; j++) { // checkc if match
                String str = s.substring(i + j * len, i + j * len + len); // next word
                if (copy.containsKey(str)) { // is in remaining words
                    int count = copy.get(str);
                    if (count == 1) {
                        copy.remove(str);
                    } else {
                        copy.put(str, count - 1);
                    }
                    if (copy.isEmpty()) { // matches
                        res.add(i);
                        break;
                    }
                } else {
                    break; // not in L
                }
            }
        }
        return res;
    }

}
