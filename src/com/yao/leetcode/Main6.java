package com.yao.leetcode;

import java.util.ArrayList;
import java.util.List;

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
    public String convert(String s, int numRows) {
        if (numRows == 1) return s;
        List<StringBuilder> rows = new ArrayList<>();
        for (int i = 0; i < Math.min(numRows, s.length()); i++) {
            rows.add(new StringBuilder());
        }
        //表示当前行
        int curRow = 0;
        //表示向上一行还是向下一行
        boolean goingDown = false;
        for (char c : s.toCharArray()) {
            rows.get(curRow).append(c);
            //只有当我们向上移动到最上面的行或向下移动到最下面的行时，当前方向才会发生改变。
            if (curRow == 0 || curRow == numRows - 1) {
                goingDown = !goingDown;
            }
            curRow += goingDown ? 1 : -1;
        }

        StringBuilder result = new StringBuilder();
        for (StringBuilder row : rows)
            result.append(row);
        return result.toString();
    }


    public static void main(String[] args) {
        String s = "PAYPALISHIRING";
        Main6 main6 = new Main6();
        String solution = main6.convert(s, 4);
        System.out.println(solution);
    }
}
