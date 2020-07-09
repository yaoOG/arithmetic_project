package com.yao.leetcode;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * @author Daniel:)
 *
 * 实现一个基本的计算器来计算一个简单的字符串表达式的值。
 * 字符串表达式可以包含左括号 ( ，右括号 )，加号 + ，减号 -，非负整数和空格  。
 * 示例 1:
 *
 * 输入: "1 + 1"
 * 输出: 2
 * 示例 2:
 *
 * 输入: " 2-1 + 2 "
 * 输出: 3
 * 示例 3:
 *
 * 输入: "(1+(4+5+2)-3)+(6+8)"
 * 输出: 23
 * 说明：
 *
 * 你可以假设所给定的表达式都是有效的。
 * 请不要使用内置的库函数 eval。
 */
public class Main224 {


    public int calculate(String s) {
        //遇到'('入栈，遇到')'出栈，栈中存储(之前的res和res与()中的计算符号 -或者+
        Deque<Integer> stack = new ArrayDeque<>();
        //sign来标识当前res与下一个值得 + -运算
        int sign = 1;
        int result = 0;
        int length = s.length();
        for (int i = 0; i < length; i++) {
            char currentChar = s.charAt(i);
            if (Character.isDigit(currentChar)) {
                int currentVal = currentChar - '0';
                while (i + 1 < length && Character.isDigit(s.charAt(i + 1))) {
                    //循环计算出当前数字和第一个非数字字符之间所有数字的十进制值，比如 111+(1+4),就会计算出111
                    currentVal = currentVal * 10 + s.charAt(++i) - '0';
                }
                result += sign * currentVal;
            } else if (currentChar == '+') {
                sign = 1;
            } else if (currentChar == '-') {
                sign = -1;
            } else if (currentChar == '(') {
                //当字符是(的时候入栈，栈内存储(之前的计算结果以及之前的结果与()中值得计算关系
                stack.push(result);
                result = 0;
                stack.push(sign);
                sign = 1;
            } else if (currentChar == ')') {
                result = stack.pop() * result + stack.pop();
            }
        }
        return result;
    }

    public static void main(String[] args) {
        Main224 main224 = new Main224();
        main224.calculate("1+(4-3)");
    }

    //stack
    //1 sign=1 res=1
}
