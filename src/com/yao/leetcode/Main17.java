package com.yao.leetcode;

import java.util.*;

/**
 * @author zhuyao
 * @date 2018/10/29
 * 给定一个仅包含数字 2-9 的字符串，返回所有它能表示的字母组合。
 * 给出数字到字母的映射如下（与电话按键相同）。注意 1 不对应任何字母。
 * 示例:
 * 输入："23"
 * 输出：["ad", "ae", "af", "bd", "be", "bf", "cd", "ce", "cf"].
 */
public class Main17 {

    public static void main(String[] args) {
        Main17 main17 = new Main17();
        List<String> list = main17.letterCombinations("23");
        for (String s : list) {
            System.out.print(s);
        }
    }

    public List<String> letterCombinations(String digits) {
        List<String> result = new LinkedList<String>();
        if(digits == null || digits.length() == 0)
            return result;
        String[] mapping = { " ", "*", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"};
        result.add("");
        for(int i = 0; i < digits.length(); i++){
            String mapStr = mapping[digits.charAt(i) - '0'];
            int size = result.size();
            for(int j = 0; j<size ; j++ ){
                String temp = result.remove(0);
                for(int k = 0; k < mapStr.length(); k++){
                    result.add(temp + mapStr.charAt(k));
                }
            }
        }
        return result;
    }
}
