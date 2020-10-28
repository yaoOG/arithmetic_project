package com.yao.leetcode;

/**
 * @author Daniel:)
 * 编写一个高效的算法来判断m x n矩阵中，是否存在一个目标值。该矩阵具有如下特性：
 *
 * 每行中的整数从左到右按升序排列。
 * 每行的第一个整数大于前一行的最后一个整数。
 */
public class Main74 {
    public boolean searchMatrix(int[][] matrix, int target) {
        int m = matrix.length;
        if (m == 0) return false;
        int n = matrix[0].length;

        // 二分查找
        int left = 0, right = m * n - 1;
        int mid, midElement;
        while (left <= right) {
            mid = (left + right) / 2;
            midElement = matrix[mid / n][mid % n];
            if (target == midElement)
                return true;
            if (target < midElement)
                right = mid - 1;
            else
                left = mid + 1;
        }
        return false;
    }
}
