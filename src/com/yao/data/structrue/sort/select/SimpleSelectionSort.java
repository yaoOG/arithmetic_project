package com.yao.data.structrue.sort.select;

/**
 * 简单选择排序
 * 步骤：
 * 首先在未排序序列中找到最小（大）元素，存放到排序序列的起始位置。
 * 再从剩余未排序元素中继续寻找最小（大）元素，然后放到已排序序列的末尾。
 * 重复第二步，直到所有元素均排序完毕。
 *
 * 性能：
 * 1.空间效率O(1).
 * 2.元素间比较次数与序列的初始状态无关，始终是n(n-1)/2次。
 * 3.时间复杂度O(n2).
 * 4.不稳定
 *
 * @author Daniel
 */
public class SimpleSelectionSort {

    public static void main(String[] args) {
        int[] arr = {9, 1, 5, 8, 3, 7, 4, 6, 2};
        System.out.println("排序之前：");
        for (int temp : arr) {
            System.out.print(temp + " ");
        }
        // 简单选择排序
        simpleSelectionSort(arr);
        System.out.println();
        System.out.println("排序之后：");
        for (int temp : arr) {
            System.out.print(temp + " ");
        }
    }

    /**
     * 简单选择排序
     */
    private static void simpleSelectionSort(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            // 将当前下标定义为最小值下标
            int min = i;
            for (int j = i + 1; j < arr.length; j++) {
                // 如果有小于当前最小值的关键字, 则将此关键字的下标赋值给min
                if (arr[min] > arr[j]) {
                    min = j;
                }
            }
            // 若min不等于i，说明找到最小值，交换
            if (i != min) {
                swap(arr, i, min);
            }
        }
    }

    /**
     * 元素交换位置
     */
    private static void swap(int[] arr, int i, int min) {
        int tmp = arr[min];
        arr[min] = arr[i];
        arr[i] = tmp;
    }

}
