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
        int[] arr = {99, 5, 69, 33, 56, 13, 22, 55, 77, 48, 12, 88, 2,69,99};
        System.out.println("排序之前：");
        for (int temp : arr) {
            System.out.print(temp + " ");
        }
        // 希尔排序
        insertionSort(arr);
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

    public static int[] insertionSort(int[] arr) {
        if (arr == null || arr.length <= 1) {
            return arr;
        }
        //希尔排序  升序
        for (int step = arr.length / 2; step > 0; step /= 2) { //step：增量  7   3   1
            for (int i = step; i < arr.length; i++) {
                //i:代表即将插入的元素角标，作为每一组比较数据的最后一个元素角标
                //j:代表与i同一组的数组元素角标
                for (int j = i - step; j >= 0; j -= step) { //在此处-step 为了避免下面数组角标越界
                    if (arr[j] > arr[j + step]) {// j+step 代表即将插入的元素所在的角标
                        //符合条件，插入元素（交换位置）
                        int temp = arr[j];
                        arr[j] = arr[j + step];
                        arr[j + step] = temp;
                    }
                }
            }
        }
        return arr;
    }
}
