package com.yao.leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

/**
 * @author Daniel:)
 *
 * 给定两个数组，编写一个函数来计算它们的交集。
 *
 * 示例 1：
 *
 * 输入：nums1 = [1,2,2,1], nums2 = [2,2]
 * 输出：[2]
 * 示例 2：
 *
 * 输入：nums1 = [4,9,5], nums2 = [9,4,9,8,4]
 * 输出：[9,4]
 */
public class Main349 {


    /**
     * 两个集合
     * 时间复杂度：O(m+n)O(m+n)，其中 mm 和 nn 分别是两个数组的长度。
     *            使用两个集合分别存储两个数组中的元素需要 O(m+n)O(m+n) 的时间，
     *            遍历较小的集合并判断元素是否在另一个集合中需要 O(\min(m,n))O(min(m,n)) 的时间，因此总时间复杂度是 O(m+n)O(m+n)。
     *
     * 空间复杂度：O(m+n)O(m+n)，其中 mm 和 nn 分别是两个数组的长度。空间复杂度主要取决于两个集合。
     *
     * @param nums1
     * @param nums2
     * @return
     */
    public int[] intersection(int[] nums1, int[] nums2) {
        HashSet<Integer> set = new HashSet<>();
        ArrayList<Integer> res = new ArrayList<>();
        //Add all elements to set from array 1
        for (int value : nums1)
            set.add(value);
        for (int value : nums2) {
            // If present in array 2 then add to res and remove from set
            if (set.contains(value)) {
                res.add(value);
                set.remove(value);
            }
        }
        return res.stream().mapToInt(Integer::valueOf).toArray();
    }

    /**
     * 排序+双指针
     * 时间复杂度：O(m \log m+n \log n)O(mlogm+nlogn)，其中 mm 和 nn 分别是两个数组的长度。
     *            对两个数组排序的时间复杂度分别是 O(m \log m)O(mlogm) 和 O(n \log n)O(nlogn)，
     *            双指针寻找交集元素的时间复杂度是 O(m+n)O(m+n)，因此总时间复杂度是 O(m \log m+n \log n)O(mlogm+nlogn)。
     *
     * 空间复杂度：O(\log m+\log n)O(logm+logn)，其中 mm 和 nn 分别是两个数组的长度。空间复杂度主要取决于排序使用的额外空间。
     * @param nums1
     * @param nums2
     * @return
     */
    public int[] intersection2(int[] nums1, int[] nums2) {
        Arrays.sort(nums1);
        Arrays.sort(nums2);
        int length1 = nums1.length, length2 = nums2.length;
        int[] intersection = new int[length1 + length2];
        int index = 0, index1 = 0, index2 = 0;
        while (index1 < length1 && index2 < length2) {
            int num1 = nums1[index1], num2 = nums2[index2];
            if (num1 == num2) {
                // 保证加入元素的唯一性
                if (index == 0 || num1 != intersection[index - 1]) {
                    intersection[index++] = num1;
                }
                index1++;
                index2++;
            } else if (num1 < num2) {
                index1++;
            } else {
                index2++;
            }
        }
        return Arrays.copyOfRange(intersection, 0, index);
    }
}
