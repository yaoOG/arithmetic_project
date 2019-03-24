package com.yao.leetcode;

/**
 * Created by zhuyao on 2018/05/02.
 * <p>
 * 将字符串 "PAYPALISHIRING" 以Z字形排列成给定的行数：
 * <p>
 * P   A   H   N
 * A P L S I I G
 * Y   I   R
 * 之后从左往右，逐行读取字符："PAHNAPLSIIGYIR"
 * <p>
 * 实现一个将字符串进行指定行数变换的函数:
 * <p>
 * string convert(string s, int numRows);
 * 示例 1:
 * <p>
 * 输入: s = "PAYPALISHIRING", numRows = 3
 * 输出: "PAHNAPLSIIGYIR"
 * 示例 2:
 * <p>
 * 输入: s = "PAYPALISHIRING", numRows = 4
 * 输出: "PINALSIGYAHRPI"
 * 解释:
 * <p>
 * P     I    N
 * A   L S  I G
 * Y A   H R
 * P     I
 */
public class Main6 {
    public static String convert(String s, int numRows) {
        // 如果只有一行则不需要转换
        if (numRows == 1) return s;

        // 按照行数建立n个字符串用于存放结果
        String [] res = new String[numRows];
        for (int i = 0; i < numRows; i ++) res[i] = "";

        // 按照z字形开始往字符串中添加元素
        int p = 0, q = 0;
        boolean direction = false;
        while (p < s.length()) {
            res[q] += s.charAt(p);
            if (q == 0) direction = false;
            if (q == numRows - 1) direction = true;
            q = direction ? q - 1 : q + 1;
            p++;
        }

        StringBuffer ans = new StringBuffer("");
        for (String i : res) {
            ans.append(i);
        }

        return ans.toString();
    }


    public static void main(String[] args) {
        String s = "PAYPALISHIRING";
        String solution = convert(s, 4);
        System.out.println(solution);
    }
}
