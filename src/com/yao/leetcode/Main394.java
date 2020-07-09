package com.yao.leetcode;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * @author Daniel:)
 *
 * 给定一个经过编码的字符串，返回它解码后的字符串。
 * 编码规则为: k[encoded_string]，表示其中方括号内部的 encoded_string 正好重复 k 次。注意 k 保证为正整数。
 * 你可以认为输入字符串总是有效的；输入字符串中没有额外的空格，且输入的方括号总是符合格式要求的。
 * 此外，你可以认为原始数据不包含数字，所有的数字只表示重复的次数 k ，例如不会出现像 3a 或 2[4] 的输入。
 * 示例 1：
 * 输入：s = "3[a]2[bc]"
 * 输出："aaabcbc"
 * 示例 2：
 * 输入：s = "3[a2[c]]"
 * 输出："accaccacc"
 * 示例 3：
 * 输入：s = "2[abc]3[cd]ef"
 * 输出："abcabccdcdcdef"
 * 示例 4：
 * 输入：s = "abc3[cd]xyz"
 * 输出："abccdcdcdxyz"
 *
 * 讲解地址
 * https://www.youtube.com/watch?v=Qoz3ujccNQY
 */
public class Main394 {

    public String decodeString(String s) {
        //存储s中的'['前的数字也就是重复的次数
        Deque<Integer> repeatedTimeStack = new ArrayDeque<>();
        //存储[]之间的string
        Deque<String> strStack = new ArrayDeque<>();
        //记录到当前为止字符串的长度
        StringBuilder result = new StringBuilder();
        int paramLength = s.length();
        for (int i = 0; i < paramLength; i++) {
            char currentChar = s.charAt(i);
            //如果当前字符是数字
            if (Character.isDigit(currentChar)) {
                //ASCII表的排列方式是:字符“9”的值比“0”的值大9;字符“8”的值比“0”的值大8;等等。
                //因此，您可以通过减去“0”得到一个十进制数字char的整型值。
                int currentNum = currentChar - '0';
                while (i + 1 < paramLength && Character.isDigit(s.charAt(i + 1))) {
                    int nextNum = s.charAt(i + 1) - '0';
                    currentNum = currentNum * 10 + nextNum;
                    i++;
                }
                //将下一个[]中字符串的重复次数入栈
                repeatedTimeStack.push(currentNum);
            } else if (currentChar == '[') {
                //如果当前字符是'['，需要将之前记录的字符串入栈，并将result置空
                strStack.push(result.toString());
                result = new StringBuilder();
            } else if (currentChar == ']') {
                //如果当前字符是']',将之前存储的[]之间字符串出栈，并且将这次[]之间的字符串追加到result后面
                StringBuilder tmp = new StringBuilder(strStack.pop());
                int repeatedTimes = repeatedTimeStack.pop();
                for (int j = 0; j < repeatedTimes; j++) {
                    tmp.append(result);
                }
                result = tmp;
            } else {
                //如果当前字符是字母则直接追加到result后面
                result.append(currentChar);
            }

        }
        return result.toString();
    }
    public static void main(String[] args) {
        Main394 main394 = new Main394();
        String result = main394.decodeString("3[a]2[bc]");
        System.out.println(result);
    }
}
