package com.yao.NiuKe;


import java.util.Scanner;

/**
 * Java实现：n个正整数，将它们连接成一排，组成一个最大的多位整数。（输入：n（n个整数），依次输入n个整数）
 */
public class Main1 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int[] array = new int[n];
        for (int i = 0; i < array.length; i++) {
            array[i] = in.nextInt();
        }
        //第一次，将最小值放到数组最后一个位置

        //第二次，将第二小值放到数组倒数第二个位置

        //依次类推，最后数组降序排列

        //注意：这里的大小顺序是为满足题设要求自定义的一种比较方式（即为了连接组成最大多位数而定义的一种排序规则）

        for (int j = 0; j < n - 1; j++) {
            for (int i = 0; i < n - 1 - j; i++) {
                if (compareInt(array[i], array[i + 1]) < 0) {
                    int temp = array[i + 1];
                    array[i + 1] = array[i];
                    array[i] = temp;
                }
            }
        }
        for (int i = 0; i < n; i++) {
            System.out.print(array[i]);
        }

        System.out.println();
    }


    /**
     * 自定义整数比较方法
     *
     */
    public static int compareInt(int num1, int num2) {
        String str1 = num1 + "";
        String str2 = num2 + "";
        int temp1 = Integer.parseInt(str1 + str2);
        int temp2 = Integer.parseInt(str2 + str1);

        return temp1 - temp2;
    }
}