package com.yao.dataStructrue.search;

/**
 * @author zhuyao
 * @date 2018/08/01
 */
public class BinarySearch {
    public static Integer binarySearch(int[] list, int item) {
        int low = 0;
        int high = list.length - 1;

        while (low <= high) {
            int mid = (low + high) / 2;
            int guess = list[mid];
            if (guess == item) {
                return mid;
            }
            if (guess > item) {
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        int[] myList = {1, 3, 5, 7, 9};
        System.out.println(binarySearch(myList, 3));
        System.out.println(binarySearch(myList, -1));

    }
}
