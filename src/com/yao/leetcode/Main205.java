package com.yao.leetcode;

import java.util.HashMap;

/**
 * @author Daniel:)
 *
 * 给定两个字符串 s 和 t，判断它们是否是同构的。
 *
 * 如果 s 中的字符可以被替换得到 t ，那么这两个字符串是同构的。
 *
 * 所有出现的字符都必须用另一个字符替换，同时保留字符的顺序。两个字符不能映射到同一个字符上，但字符可以映射自己本身。
 *
 * 示例 1:
 * 输入: s = "egg", t = "add"
 * 输出: true
 * 示例 2:
 * 输入: s = "foo", t = "bar"
 * 输出: false
 * 示例 3:
 * 输入: s = "paper", t = "title"
 * 输出: true
 */
public class Main205 {

    public boolean isIsomorphic(String s, String t) {
        if (s == null || s.length() <= 1) return true;
        HashMap<Character, Character> map = new HashMap<>();
        for (int i = 0; i < s.length(); i++) {
            char a = s.charAt(i);
            char b = t.charAt(i);
            if (map.containsKey(a)) {
                //如果map的key中包含当前元素，说明当前元素之前已经有相同元素，所以比较之前元素的value是不是和当前的value一致
                if (!map.get(a).equals(b))
                    return false;
            } else {
                //如果map的key不存在当前key，map的value存在当前value，说明t和s不一致
                if (map.containsValue(b)) {
                    return false;
                } else {
                    map.put(a, b);
                }
            }
        }
        return true;
    }

    public static void main(String[] args) {
        Main205 main205 = new Main205();
        boolean result = main205.isIsomorphic("egg", "add");
        System.out.println(result);
    }

}
