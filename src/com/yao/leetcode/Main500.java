package com.yao.leetcode;

import java.util.*;

/**
 * @author choo
 */
public class Main500 {

    public String[] findWords(String[] words) {
        Map<Character, Integer> map = new HashMap<>();
        map.put('q', 1); map.put('w', 1); map.put('e', 1); map.put('r', 1); map.put('t', 1);
        map.put('y', 1); map.put('u', 1); map.put('i', 1); map.put('o', 1); map.put('p', 1);
        map.put('a', 2); map.put('s', 2); map.put('d', 2); map.put('f', 2); map.put('g', 2);
        map.put('h', 2); map.put('j', 2); map.put('k', 2); map.put('l', 2); map.put('z', 3);
        map.put('x', 3); map.put('c', 3); map.put('v', 3); map.put('b', 3); map.put('n', 3);
        map.put('m', 3);
        List<String> res = new ArrayList<>();
        for (String word : words) {
            Set<Integer> set = new HashSet<>();
            for (int index = 0; index < word.length(); index++) {
                char val = word.charAt(index);
                val = Character.isLowerCase(val) ? val : (char) (val + 32);
                set.add(map.get(val));
            }
            if (set.size() == 1) res.add(word);
        }
        return res.toArray(new String[0]);
    }
}
