package com.yao.leetcode;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhuyao
 * @date 2018/10/22
 * 罗马数字包含以下七种字符: I， V， X， L，C，D 和 M。
 * <p>
 * 字符          数值
 * I             1
 * V             5
 * X             10
 * L             50
 * C             100
 * D             500
 * M             1000
 * 例如， 罗马数字 2 写做 II ，即为两个并列的 1。12 写做 XII ，即为 X + II 。 27 写做  XXVII, 即为 XX + V + II 。
 */
public class Main13 {
    Map<Character, Integer> symbolValues = new HashMap<Character, Integer>() {{
        put('I', 1);
        put('V', 5);
        put('X', 10);
        put('L', 50);
        put('C', 100);
        put('D', 500);
        put('M', 1000);
    }};

    public int romanToInt(String s) {
        int result = 0;
        int length = s.length();
        for (int i = 0; i < length; i++) {
            int value = symbolValues.get(s.charAt(i));
            if (i < length - 1 && value < symbolValues.get(s.charAt(i + 1))) {
                result -= value;
            }else {
                result += value;
            }
        }
        return result;
    }
}
