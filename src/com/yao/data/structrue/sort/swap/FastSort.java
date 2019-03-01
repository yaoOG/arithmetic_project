package com.yao.data.structrue.sort.swap;

import java.util.*;

/**
 * 快速排序（与划分是否对称有关）（适用于顺序存储）
 * 1.空间效率：平均情况下为O(log2 n)
 * 2.时间效率：最坏情况下(对应于初始排序表基本有序或基本无序)的时间复杂度为O(n2)。
 *                      最好情况下(最平衡的划分下)的时间复杂度为O(nlog2 n)。
 *    两种方法可以提高算法的效率：
 *                        1⃣️当递归过程中划分得到的子序列的规模较小时不要再继续递归调用快速排序，可以直接采用直接插入排序算法进行后续的排序工作。
 *                        2⃣️尽量选取一个可以将数据中分的枢纽元素。
 * 3.快速排序算法时所有内部排序算法中平均性能最优的排序算法。
 * 4.不稳定。
 * 5.每一躺排序后基准元素将放到其最终的位置上
 * @Author: nathan
 * @Date: 2019/1/5
 */
@SuppressWarnings("unused")
public class FastSort {
    public static void main(String[] args) {
        int[] tempArray = {12, 20, 5, 16, 15, 1, 30, 45, 23, 9};
        int start = 0;
        int end = tempArray.length - 1;
        sort(tempArray, start, end);
        for (int i : tempArray) {
            System.out.println(i);
        }
    }

    /**
     * 递归实现
     *
     * @param tempArray 需要排序数组
     * @param low       排序low角标
     * @param high      排序high角标
     */
    private static void sort(int[] tempArray, int low, int high) {
        int start = low;
        int end = high;
        int key = tempArray[low];

        while (end > start) {
            //从后往前比较，如果没有比关键值小的，比较下一个，直到有比关键值小的交换位置，然后又从前往后比较
            while (end > start && tempArray[end] >= key) {
                end--;
            }
            if (tempArray[end] <= key) {
                int temp = tempArray[end];
                tempArray[end] = tempArray[start];
                tempArray[start] = temp;
            }
            //从前往后比较，如果没有比关键值大的，比较下一个，直到有比关键值大的交换位置
            while (end > start && tempArray[start] <= key) {
                start++;
            }
            if (tempArray[start] >= key) {
                int temp = tempArray[start];
                tempArray[start] = tempArray[end];
                tempArray[end] = temp;
            }
            //此时第一次循环比较结束，关键值的位置已经确定了。左边的值都比关键值小，右边的值都比关键值大，但是两边的顺序还有可能是不一样的，进行下面的递归调用
        }
        //递归
        if (start > low) {
            //左边序列。第一个索引位置到关键值索引-1
            sort(tempArray, low, start - 1);
        }
        if (end < high) {
            //右边序列。从关键值索引+1到最后一个
            sort(tempArray, end + 1, high);
        }
    }

    /**
     * 非递归实现
     *
     * @param tempArray 需要排序数组
     * @param low       排序low角标
     * @param high      排序high角标
     */
    private static void sort2(int[] tempArray, int low, int high) {
        int pivot;
        if (low >= high) {
            return;
        }
        Stack<Integer> stack = new Stack<>();
        stack.push(low);
        stack.push(high);
        while (!stack.empty()) {
            // 先弹出high,再弹出low
            high = stack.pop();
            low = stack.pop();
            pivot = partition(tempArray, low, high);
            // 先压low,再压high
            if (low < pivot - 1) {
                stack.push(low);
                stack.push(pivot - 1);
            }
            if (pivot + 1 < high) {
                stack.push(pivot + 1);
                stack.push(high);
            }
        }
    }

    private static int partition(int[] a, int start, int end) {
        int pivot = a[start];
        while (start < end) {
            while (start < end && a[end] >= pivot) {
                end--;
            }
            a[start] = a[end];
            while (start < end && a[start] <= pivot) {
                start++;
            }
            a[end] = a[start];
        }
        a[start] = pivot;
        return start;
    }

}
