package com.yao.data.structrue.sort.select;

import java.util.Arrays;

/**
 * @author choo
 */
public class HeapSort {

    public void sort(int[] arr) {
        int length = arr.length;
        //构建大顶堆,从第一个非叶子节点开始
        for (int i = length / 2 - 1; i >= 0; i--) {
            adjust(arr, i, length);
        }
        //调整大顶堆，将堆顶的值交换到数组最后，调整其他元素构建新的大顶堆，循环往复，最后生成排序好的数组
        for (int i = length - 1; i > 0; i--) {
            swap(arr, 0, i);
            adjust(arr, 0, i);
        }
    }

    public void adjust(int[] arr, int i, int length) {
        int parentNode = arr[i];
        for (int k = i * 2 + 1; k < length; k = k * 2 + 1) {
            if (k + 1 < length && arr[k + 1] > arr[k]) {
                //parentNode的右孩子存在的情况下，值大于左孩子
                k++;
            }
            if (arr[k] > parentNode) {
                //parentNode的子节点大于父节点，将子节点的值赋值给父节点
                arr[i] = arr[k];
                i = k;
            } else {
                break;
            }
        }
        //将父节点的值放到最终位置
        arr[i] = parentNode;
    }

    public static void main(String[] args) {
        int[] arr = {1, 8, 9, 6, 5, 4, 3, 7, 2};
        HeapSort heap = new HeapSort();
        heap.sort(arr);
        System.out.println(Arrays.toString(arr));
    }

    /**
     * 交换元素
     * @param arr
     * @param a
     * @param b
     */
    public static void swap(int[] arr, int a, int b) {
        int temp = arr[a];
        arr[a] = arr[b];
        arr[b] = temp;
    }
}