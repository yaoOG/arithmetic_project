package com.yao.dataStructrue.sort;

import java.util.*;

/**
 * @Author: nathan
 * @Date: 2019/1/5
 */
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
            //从后往前比较
            while (end > start && tempArray[end] >= key)  //如果没有比关键值小的，比较下一个，直到有比关键值小的交换位置，然后又从前往后比较
                end--;
            if (tempArray[end] <= key) {
                int temp = tempArray[end];
                tempArray[end] = tempArray[start];
                tempArray[start] = temp;
            }
            //从前往后比较
            while (end > start && tempArray[start] <= key)//如果没有比关键值大的，比较下一个，直到有比关键值大的交换位置
                start++;
            if (tempArray[start] >= key) {
                int temp = tempArray[start];
                tempArray[start] = tempArray[end];
                tempArray[end] = temp;
            }
            //此时第一次循环比较结束，关键值的位置已经确定了。左边的值都比关键值小，右边的值都比关键值大，但是两边的顺序还有可能是不一样的，进行下面的递归调用
        }
        //递归
        if (start > low) sort(tempArray, low, start - 1);//左边序列。第一个索引位置到关键值索引-1
        if (end < high) sort(tempArray, end + 1, high);//右边序列。从关键值索引+1到最后一个
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
        if (low >= high)
            return;
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
            while (start < end && a[end] >= pivot)
                end--;
            a[start] = a[end];
            while (start < end && a[start] <= pivot)
                start++;
            a[end] = a[start];
        }
        a[start] = pivot;
        return start;
    }

}
