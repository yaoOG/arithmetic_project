package com.yao.leetcode;

/**
 * Created by zhuyao on 2018/04/28.
 *
 * 给定两个大小为 m 和 n 的有序数组 nums1 和 nums2 。
 *
 * 请找出这两个有序数组的中位数。要求算法的时间复杂度为 O(log (m+n)) 。
 *
 * 示例 1:
 *
 * nums1 = [1, 3]
 * nums2 = [2]
 *
 * 中位数是 2.0
 * 示例 2:
 *
 * nums1 = [1, 2]
 * nums2 = [3, 4]
 *
 * 中位数是 (2 + 3)/2 = 2.5
 */
public class Main4 {
    /**
     * 等价于求两个数组合起来的第 (n+m)/2(n+m)/2 小的数，奇偶加个判断就行
     *
     * 所以这道题重点在于求 【在两个有序数组中，求第k小数。】
     *
     * 不失一般性：我们先从 nums1nums1 和 nums2nums2 中各取前 k/2k/2 个元素：
     *
     * 有以下三种情况：
     *
     * 1.如果 nums1[k/2−1]<nums2[k/2−1]，说明 nums1 中的前 k/2 个元素一定都小于第 k 小数
     *
     * 【简短证明：nums1中小于等于nums1[k/2−1]有k/2个，nums2中小于等于nums2[k/2−1]有k/2个，
     * 而nums1[k/2−1]<nums2[k/2−1]，说明总共小于等于nums1[k/2−1]严格小于k个，即nums1[k/2−1]及之前的元素均不是答案】
     *
     * 2.如果 nums1[k/2−1]>nums2[k/2−1]，说明 nums2 中的前 k/2 个元素一定都小于第 k 小数
     *
     * 以上两种情况，每次可淘汰k/2个数
     *
     * 3.如果 nums1[k/2−1]=nums2[k/2−1]=x，则x就是目标值，这种情况直接归在上两种的判断内即可
     *
     * 具体边界处理，tips已加注释
     *
     * 时间复杂度：k=(m+n)/2，且每次递归 k 的规模都减少一半，因此时间复杂度是 O(log(m+n))
     *
     * @param nums1
     * @param nums2
     * @return
     */
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        // Deal with invalid corner case.
        if (nums1 == null && nums2 == null || nums1.length == 0 && nums2.length == 0) return 0.0;

        int m = nums1.length, n = nums2.length;
        int l = (m + n + 1) / 2; //left half of the combined median
        int r = (m + n + 2) / 2; //right half of the combined median

        // If the nums1.length + nums2.length is odd, the 2 function will return the same number
        // Else if nums1.length + nums2.length is even, the 2 function will return the left number and right number that make up a median
        return (getKth(nums1, 0, nums2, 0, l) + getKth(nums1, 0, nums2, 0, r)) / 2.0;
    }

    private double getKth(int[] nums1, int start1, int[] nums2, int start2, int k) {
        // This function finds the Kth element in nums1 + nums2

        // If nums1 is exhausted, return kth number in nums2
        if (start1 > nums1.length - 1)
            return nums2[start2 + k - 1];

        // If nums2 is exhausted, return kth number in nums1
        if (start2 > nums2.length - 1)
            return nums1[start1 + k - 1];

        // If k == 1, return the first number
        // Since nums1 and nums2 is sorted, the smaller one among the start point of nums1 and nums2 is the first one
        if (k == 1) return Math.min(nums1[start1], nums2[start2]);

        int mid1 = Integer.MAX_VALUE;
        int mid2 = Integer.MAX_VALUE;

        if (start1 + k / 2 - 1 < nums1.length)
            mid1 = nums1[start1 + k / 2 - 1];

        if (start2 + k / 2 - 1 < nums2.length)
            mid2 = nums2[start2 + k / 2 - 1];

        // Throw away half of the array from nums1 or nums2. And cut k in half
        if (mid1 < mid2) {
            return getKth(nums1, start1 + k / 2, nums2, start2, k - k / 2); //nums1.right + nums2
        } else {
            return getKth(nums1, start1, nums2, start2 + k / 2, k - k / 2); //nums1 + nums2.right
        }
    }

    public static void main(String[] args) {
        Main4 main4 = new Main4();
        double result = main4.findMedianSortedArrays(new int[]{4}, new int[]{3, 5, 8, 10});
        System.out.println(result);
    }
/*    public double findMedianSortedArrays2(int[] nums1, int[] nums2) {
        int length1 = nums1.length, length2 = nums2.length;
        int totalLength = length1 + length2;
        if (totalLength % 2 == 1) {
            int midIndex = totalLength / 2;
            double median = getKthElement(nums1, nums2, midIndex + 1);
            return median;
        } else {
            int midIndex1 = totalLength / 2 - 1, midIndex2 = totalLength / 2;
            double median = (getKthElement(nums1, nums2, midIndex1 + 1) + getKthElement(nums1, nums2, midIndex2 + 1)) / 2.0;
            return median;
        }
    }

    public int getKthElement(int[] nums1, int[] nums2, int k) {
        *//* 主要思路：要找到第 k (k>1) 小的元素，那么就取 pivot1 = nums1[k/2-1] 和 pivot2 = nums2[k/2-1] 进行比较
     * 这里的 "/" 表示整除
     * nums1 中小于等于 pivot1 的元素有 nums1[0 .. k/2-2] 共计 k/2-1 个
     * nums2 中小于等于 pivot2 的元素有 nums2[0 .. k/2-2] 共计 k/2-1 个
     * 取 pivot = min(pivot1, pivot2)，两个数组中小于等于 pivot 的元素共计不会超过 (k/2-1) + (k/2-1) <= k-2 个
     * 这样 pivot 本身最大也只能是第 k-1 小的元素
     * 如果 pivot = pivot1，那么 nums1[0 .. k/2-1] 都不可能是第 k 小的元素。把这些元素全部 "删除"，剩下的作为新的 nums1 数组
     * 如果 pivot = pivot2，那么 nums2[0 .. k/2-1] 都不可能是第 k 小的元素。把这些元素全部 "删除"，剩下的作为新的 nums2 数组
     * 由于我们 "删除" 了一些元素（这些元素都比第 k 小的元素要小），因此需要修改 k 的值，减去删除的数的个数
     *//*

        int length1 = nums1.length, length2 = nums2.length;
        int index1 = 0, index2 = 0;
        int kthElement = 0;

        while (true) {
            // 边界情况
            if (index1 == length1) {
                return nums2[index2 + k - 1];
            }
            if (index2 == length2) {
                return nums1[index1 + k - 1];
            }
            if (k == 1) {
                return Math.min(nums1[index1], nums2[index2]);
            }

            // 正常情况
            int half = k / 2;
            int newIndex1 = Math.min(index1 + half, length1) - 1;
            int newIndex2 = Math.min(index2 + half, length2) - 1;
            int pivot1 = nums1[newIndex1], pivot2 = nums2[newIndex2];
            if (pivot1 <= pivot2) {
                k -= (newIndex1 - index1 + 1);
                index1 = newIndex1 + 1;
            } else {
                k -= (newIndex2 - index2 + 1);
                index2 = newIndex2 + 1;
            }
        }
    }*/

}
