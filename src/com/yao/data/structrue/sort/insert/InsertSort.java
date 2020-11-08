package com.yao.data.structrue.sort.insert;

/**
 * 直接插入排序(适用于顺序存储和链式存储，适用于基本有序但n较小)
 * 1.空间复杂度为O(1)。
 * 2.平均情况下：总的比较次数和移动次数均约为n的平方/4.
 *    最坏情况下(表中元素顺序刚好与排序结果中元素顺序相反)：需要进行比较次数是n(n-1)/2.
 *    最好的情况下(表中元素已经有序)：所需的比较次数为n-1
 * 3.时间复杂度为O(n2)。
 * 4.稳定。
 * @author daniel
 */
public class InsertSort {

    public static void main(String[] args) {
        int[] arr = {6, 5, 3, 1, 8, 7, 2, 4};
        System.out.println("排序之前：");
        for (int temp : arr) {
            System.out.print(temp + " ");
        }
        // 直接插入排序
        insertSort(arr);
        System.out.println();
        System.out.println("排序之后：");
        for (int temp : arr) {
            System.out.print(temp + " ");
        }
    }

    /**
     * 直接插入排序
     */
    private static void insertSort(int[] arr) {
        int j, // 已排序列表下标
         t; // 待排序元素
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] < arr[i - 1]) {
                // 赋值给待排序元素
                t = arr[i];
                for (j = i - 1; j >= 0 && arr[j] > t; j--) {
                    // 从后往前遍历 已排序 列表，逐个和待排序元素比较，如果已排序元素较大，则将它后移
                    arr[j + 1] = arr[j];
                }
                // 将待排序元素插入到正确的位置
                arr[j + 1] = t;
            }
        }
    }

}