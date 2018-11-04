package com.yao.niuKe;

import java.util.Scanner;
import java.util.Stack;

/**
 * 题目描述
 * 给定一个十进制的正整数number，选择从里面去掉一部分数字，希望保留下来的数字组成的正整数最大。
 * 输入描述:
 * 输入为两行内容，第一行是正整数number，1 ≤ length(number) ≤ 50000。第二行是希望去掉的数字数量cnt 1 ≤ cnt < length(number)。
 * 输出描述:
 * 输出保留下来的结果。
 * 示例1
 * 输入
 * 325 1
 * 输出
 * 35
 */
public class Main9 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            char[] str = scanner.next().toCharArray();
            int k = scanner.nextInt();
            process(str, k);
        }
    }

    private static void process(char[] str, int k) {
        int i = 1;
        Stack<Character> stack = new Stack<>();
        stack.push(str[0]);
        while (k > 0 && i < str.length) {
            while (!stack.isEmpty() && k > 0 && stack.peek() < str[i]) {
                stack.pop();
                --k;
            }
            stack.push(str[i]);
            ++i;
        }
        while (k-- > 0) {
            stack.pop();
        }
        printStack(stack);
        printCharArr(str, i);
        System.out.println();
    }

    private static void printStack(Stack<Character> stack) {
        for (char c : stack) {
            System.out.print(c);
        }
    }

    private static void printCharArr(char[] str, int start) {
        while (start < str.length) {
            System.out.print(str[start++]);
        }
    }
}
