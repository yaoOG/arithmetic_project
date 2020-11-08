package com.yao.data.structrue.sort.insert;

/**
 * 希尔排序(适用于顺序存储的线性表)
 * 1.空间复杂度为O(1).
 * 2.时间复杂度最好为O(nlog2n)，平均时间复杂度根据序列长度的不同而不同。
 * 3.不稳定。
 *  步长选择为n/2，并且对步长取半直到步长达到1
 * @author zhuyao
 * @date 2019/03/01
 */
public class ShellSort {

    public static void main(String[] args) {
        int[] arr = { 6, 5, 3, 1, 8, 7, 2, 4 };
        System.out.println("排序之前：");
        for (int temp : arr) {
            System.out.print(temp + " ");
        }
        // 希尔排序
        shellSort(arr);
        System.out.println();
        System.out.println("排序之后：");
        for (int temp : arr) {
            System.out.print(temp + " ");
        }
    }

    private static void shellSort(int[] arr) {
        int length = arr.length;
        int temp;
        for (int step = length / 2; step >= 1; step /= 2) {
            for (int i = step; i < length; i++) {
                temp = arr[i];
                int j = i - step;
                while (j >= 0 && arr[j] > temp) {
                    arr[j + step] = arr[j];
                    j -= step;
                }
                arr[j + step] = temp;
            }
        }
    }

}
