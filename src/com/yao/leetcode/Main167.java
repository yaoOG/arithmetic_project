package com.yao.leetcode;

/**
 * @author Daniel:)
 * 两数之和 II - 输入有序数组
 *
 * 给定一个已按照升序排列的整数数组numbers ，请你从数组中找出两个数满足相加之和等于目标数target。
 * 函数应该以长度为2的整数数组的形式返回这两个数的下标值。numbers 的下标从1开始计数 ，
 * 所以答案数组应当满足 1 <= answer[0] < answer[1] <= numbers.length 。
 *
 * 你可以假设每个输入只对应唯一的答案，而且你不可以重复使用相同的元素。
 *
 * 示例 1：
 * 输入：numbers = [2,7,11,15], target = 9
 * 输出：[1,2]
 * 解释：2 与 7 之和等于目标数 9 。因此 index1 = 1, index2 = 2 。
 *
 * 示例 2：
 * 输入：numbers = [2,3,4], target = 6
 * 输出：[1,3]
 *
 * 示例 3：
 * 输入：numbers = [-1,0], target = -1
 * 输出：[1,2]
 */
public class Main167 {

    /**
     * 二分查找
     * 时间复杂度：O(n \log n)O(nlogn)，其中 nn 是数组的长度。需要遍历数组一次确定第一个数，
     *            时间复杂度是 O(n)O(n)，寻找第二个数使用二分查找，时间复杂度是 O(\log n)O(logn)，因此总时间复杂度是 O(n \log n)O(nlogn)。
     * 空间复杂度：O(1)O(1)。
     * @param numbers
     * @param target
     * @return
     */
    public int[] twoSum(int[] numbers, int target) {
        for (int i = 0; i < numbers.length; ++i) {
            int low = i + 1, high = numbers.length - 1;
            while (low <= high) {
                int mid = (high - low) / 2 + low;
                if (numbers[mid] == target - numbers[i]) {
                    return new int[]{i + 1, mid + 1};
                } else if (numbers[mid] > target - numbers[i]) {
                    high = mid - 1;
                } else {
                    low = mid + 1;
                }
            }
        }
        return new int[]{-1, -1};
    }

    /**
     *
     * 双指针
     * 时间复杂度：O(n)O(n)，其中 nn 是数组的长度。两个指针移动的总次数最多为 nn 次。
     * 空间复杂度：O(1)O(1)。
     *
     * @param numbers
     * @param target
     * @return
     */
    public int[] twoSum2(int[] numbers, int target) {
        int low = 0, high = numbers.length - 1;
        while (low < high) {
            int sum = numbers[low] + numbers[high];
            if (sum == target) {
                return new int[]{low + 1, high + 1};
            } else if (sum < target) {
                low++;
            } else {
                high--;
            }
        }
        return new int[]{-1, -1};
    }
}
