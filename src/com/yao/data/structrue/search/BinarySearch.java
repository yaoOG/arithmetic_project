package com.yao.data.structrue.search;

/**
 * @author zhuyao
 * 二分查找
 */
public class BinarySearch {
    private static int binarySearch(int[] list, int item) {
        int start = 0;
        int end = list.length - 1;

        while (start <= end) {
            int mid = start + (end - start) / 2;
            int guess = list[mid];
            if (guess == item) {
                return mid;
            }
            if (guess > item) {
                end = mid - 1;
            } else {
                start = mid + 1;
            }
        }
        return 0;
    }

    public static void main(String[] args) {
        int[] myList = {1, 3, 5, 7, 9};
        System.out.println(binarySearch(myList, 3));
        System.out.println(binarySearch(myList, -1));
    }
}
