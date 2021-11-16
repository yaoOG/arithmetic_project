package com.yao.data.structrue.sort.select;

/**
 * 归并排序
 * 1.空间效率O(n).
 * 2.时间效率O(nlog2n).
 * 3.稳定。
 * 4.对n个元素进行k路归并排序，排序的趟数m满足k的m次方等于n，m等于log以k为底n向上取整。
 *
 * @author Daniel
 */
public class MergeSort {

    private void mergeSort(int[] data) {
        sort(data, 0, data.length - 1);
    }

    public void sort(int[] data, int left, int right) {
        if (left >= right) {
            return;
        }
        // 找出中间索引
        int center = (left + right) / 2;
        // 对左边数组进行递归
        sort(data, left, center);
        // 对右边数组进行递归
        sort(data, center + 1, right);
        // 合并
        merge(data, left, center, right);
    }

    /**
     * 将两个数组进行归并，归并前面2个数组已有序，归并后依然有序
     *
     * @param data   数组对象
     * @param left   左数组的第一个元素的索引
     * @param center 左数组的最后一个元素的索引，center+1是右数组第一个元素的索引
     * @param right  右数组最后一个元素的索引
     */
    public void merge(int[] data, int left, int center, int right) {
        // 临时数组
        int[] tmpArr = new int[data.length];
        // 右数组第一个元素索引
        int mid = center + 1;
        // third 记录临时数组的索引
        int tempArrayIndex = left;
        // 缓存左数组第一个元素的索引
        int copyLeftIndex = left;
        while (left <= center && mid <= right) {
            // 从两个数组中取出最小的放入临时数组
            if (data[left] <= data[mid]) {
                tmpArr[tempArrayIndex++] = data[left++];
            } else {
                tmpArr[tempArrayIndex++] = data[mid++];
            }
        }
        // 剩余部分依次放入临时数组（实际上两个while只会执行其中一个）
        while (mid <= right) {
            tmpArr[tempArrayIndex++] = data[mid++];
        }
        while (left <= center) {
            tmpArr[tempArrayIndex++] = data[left++];
        }
        // 将临时数组中的内容拷贝回原数组中
        //（原left-right范围的内容被复制回原数组）
        while (copyLeftIndex <= right) {
            data[copyLeftIndex] = tmpArr[copyLeftIndex++];
        }
    }

    public void print(int[] data) {
        for (int datum : data) {
            System.out.print(datum + "\t");
        }
        System.out.println();
    }


    public static void main(String[] args) {
        int[] data = new int[]{5, 3, 6, 2, 1, 9, 4, 8, 7};
        MergeSort mergeSort = new MergeSort();
        mergeSort.mergeSort(data);
    }


}
