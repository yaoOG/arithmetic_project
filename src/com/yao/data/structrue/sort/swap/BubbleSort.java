package com.yao.data.structrue.sort.swap;

import java.util.Arrays;

/**
 * 冒泡排序
 *
 * 算法步骤：
 * 比较相邻的元素。如果第一个比第二个大，就交换他们两个。
 * 对每一对相邻元素作同样的工作，从开始第一对到结尾的最后一对。这步做完后，最后的元素会是最大的数。
 * 针对所有的元素重复以上的步骤，除了最后一个。
 * 持续每次对越来越少的元素重复上面的步骤，直到没有任何一对数字需要比较。
 *
 *
 * 1.空间效率O(1).
 * 2.时间效率:
 *          最好情况(初始序列有序，比较次数为n-1，移动次数为0)下为O(n)
 *          最坏情况(初始序列逆序，比较次数为n(n-1)/2，移动次数为3n(n-1)/2)下为O(n2)
 *          平均情况下为O(n2)
 * 3.稳定。
 *
 * @author zhuyao
 * @date 2018/08/01
 */
@SuppressWarnings("unused")
public class BubbleSort {
    public static void bubbleSort1(int[] a, int n) {
        int i, j;
        //表示n次排序过程。
        for (i = 0; i < n; i++) {
            for (j = 1; j < n - i; j++) {
                //前面的数字大于后面的数字就交换
                if (a[j - 1] > a[j]) {
                    //交换a[j-1]和a[j]
                    int temp;
                    temp = a[j - 1];
                    a[j - 1] = a[j];
                    a[j] = temp;
                }
            }
        }
    }

    /**
     * 设置一个标志，如果这一趟发生了交换，则为true，否则为false。明显如果有一趟没有发生交换，说明排序已经完成。
     *
     * @param a 需要排序的数组
     * @param n 数组的长度
     */
    public static void bubbleSort2(int[] a, int n) {
        int j, k = n;
        //发生了交换就为true, 没发生就为false，第一次判断时必须标志位true。
        boolean flag = true;
        while (flag) {
            //每次开始排序前，都设置flag为未排序过
            flag = false;
            for (j = 1; j < k; j++) {
                //前面的数字大于后面的数字就交换
                if (a[j - 1] > a[j]) {
                    //交换a[j-1]和a[j]
                    int temp;
                    temp = a[j - 1];
                    a[j - 1] = a[j];
                    a[j] = temp;
                    //表示交换过数据;
                    flag = true;
                }
            }
            //减小一次排序的尾边界
            k--;
        }
    }

    private static void bubbleSort3(int[] a, int n) {
        int j, k;
        //flag来记录最后交换的位置，也就是排序的尾边界
        int flag = n;
        //排序未结束标志
        while (flag > 0) {
            //k 来记录遍历的尾边界
            k = flag;
            flag = 0;
            for (j = 1; j < k; j++) {
                //前面的数字大于后面的数字就交换
                if (a[j - 1] > a[j]) {
                    //交换a[j-1]和a[j]
                    int temp;
                    temp = a[j - 1];
                    a[j - 1] = a[j];
                    a[j] = temp;
                    //表示交换过数据;
                    //记录最新的尾边界.
                    flag = j;
                }
            }
        }
    }

    public static void main(String[] args) {
        int[] arr = new int[]{1, 6, 2, 2, 5};
        BubbleSort.bubbleSort2(arr, arr.length);
        System.out.println(Arrays.toString(arr));
    }
}
