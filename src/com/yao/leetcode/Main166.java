package com.yao.leetcode;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Daniel:)
 *
 * 分数到小数
 *
 * 给定两个整数，分别表示分数的分子 numerator 和分母 denominator，以字符串形式返回小数。
 *
 * 如果小数部分为循环小数，则将循环的部分括在括号内。
 * 示例 1:
 * 输入: numerator = 1, denominator = 2
 * 输出: "0.5"
 *
 * 示例 2:
 * 输入: numerator = 2, denominator = 1
 * 输出: "2"
 *
 * 示例 3:
 * 输入: numerator = 2, denominator = 3
 * 输出: "0.(6)"
 */
public class Main166 {
    public static void main(String[] args) {
        Main166 main166 = new Main166();
        String result = main166.fractionToDecimal(2, 3);
        System.out.println(result);
    }

    public String fractionToDecimal1(int numerator, int denominator) {
        if (numerator == 0) {
            return "0";
        }
        StringBuilder result = new StringBuilder();
        //true异或true = false; false异或false = true
        //true异或false = true
        result.append((numerator > 0) ^ (denominator > 0) ? "-" : "");
        long num = Math.abs((long)numerator);
        long den = Math.abs((long)denominator);
        result.append(num / den);
        num %= den;
        if (num == 0) {
            return result.toString();
        } else {
            result.append(".");
        }
        //map来存储余数以及余数所在的位置
        Map<Long, Integer> map = new HashMap<>();
        map.put(num, result.length());
        while (num != 0) {
            //当除不尽的时候
            num *= 10;
            result.append(num / den);
            num %= den;
            if (map.containsKey(num)) {
                Integer index = map.get(num);
                result.insert(index, "(");
                result.append(")");
                break;
            }else {
                map.put(num, result.length());
            }
        }
        return result.toString();
    }

    public String fractionToDecimal(int numerator, int denominator) {
        if (numerator == 0) {
            return "0";
        }
        StringBuilder res = new StringBuilder();
        // "+" or "-"
        res.append(((numerator > 0) ^ (denominator > 0)) ? "-" : "");
        long num = Math.abs((long) numerator);
        long den = Math.abs((long) denominator);

        // integral part
        res.append(num / den);
        num %= den;
        if (num == 0) {
            return res.toString();
        }

        // fractional part
        res.append(".");
        HashMap<Long, Integer> map = new HashMap<Long, Integer>();
        map.put(num, res.length());
        while (num != 0) {
            num *= 10;
            res.append(num / den);
            num %= den;
            if (map.containsKey(num)) {
                int index = map.get(num);
                res.insert(index, "(");
                res.append(")");
                break;
            } else {
                map.put(num, res.length());
            }
        }
        return res.toString();
    }
}
