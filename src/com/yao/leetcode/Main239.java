package com.yao.leetcode;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * @author Daniel
 * <p>
 * 给定一个数组 nums，有一个大小为 k 的滑动窗口从数组的最左侧移动到数组的最右侧。你只可以看到在滑动窗口内的 k 个数字。滑动窗口每次只向右移动一位。
 * 返回滑动窗口中的最大值。
 * 示例:
 * 输入: nums = [1,3,-1,-3,5,3,6,7], 和 k = 3
 * 输出: [3,3,5,5,6,7]
 * 解释:
 * 滑动窗口的位置                最大值
 * ---------------               -----
 * [1  3  -1] -3  5  3  6  7       3
 * 1 [3  -1  -3] 5  3  6  7       3
 * 1  3 [-1  -3  5] 3  6  7       5
 * 1  3  -1 [-3  5  3] 6  7       5
 * 1  3  -1  -3 [5  3  6] 7       6
 * 1  3  -1  -3  5 [3  6  7]      7
 *  
 * 提示：
 * 1 <= nums.length <= 10^5
 * -10^4 <= nums[i] <= 10^4
 * 1 <= k <= nums.length
 */
public class Main239 {
    /**
     * 暴力法
     * 最简单直接的方法是遍历每个滑动窗口，找到每个窗口的最大值。一共有 N - k + 1 个滑动窗口，每个有 k 个元素，于是算法的时间复杂度为 {O}(N k)O(Nk)，表现较差。
     *
     * @param nums
     * @param k
     * @return
     */
    public int[] maxSlidingWindow1(int[] nums, int k) {
        int n = nums.length;
        if (n * k == 0) return new int[0];

        int[] output = new int[n - k + 1];
        for (int i = 0; i < n - k + 1; i++) {
            int max = Integer.MIN_VALUE;
            for (int j = i; j < i + k; j++)
                max = Math.max(max, nums[j]);
            output[i] = max;
        }
        return output;
    }

    ArrayDeque<Integer> deq = new ArrayDeque<Integer>();
    int[] nums;

    public void cleanDeque(int i, int k) {
        // remove indexes of elements not from sliding window
        // 移除非滑动窗口中的元素索引
        if (!deq.isEmpty() && deq.getFirst() == i - k)
            deq.removeFirst();

        // remove from deq indexes of all elements which are smaller than current element nums[i]
        // 从deq索引中删除所有元素小于当前元素nums[i]
        while (!deq.isEmpty() && nums[i] > nums[deq.getLast()])
            deq.removeLast();
    }

    /**
     * 使用双端队列，该数据结构可以从两端以常数时间压入/弹出元素。
     * 存储双向队列的索引
     *
     * 第一步：处理前 k 个元素，初始化双向队列。
     * 第二步：遍历整个数组。在每一步 :
     *  清理双向队列 :
     *   - 只保留当前滑动窗口中有的元素的索引。
     *   - 移除比当前元素小的所有元素，它们不可能是最大的。
     * 第三步：将当前元素添加到双向队列中。
     * 第四步：将 deque[0] 添加到输出中。
     * 第五步：返回输出数组。

     * @param nums
     * @param k
     * @return
     */
    public int[] maxSlidingWindow2(int[] nums, int k) {
        int length = nums.length;
        if (length * k == 0)
            return new int[0];
        if (k == 1)
            return nums;

        // init deque and output
        this.nums = nums;
        int max_idx = 0;
        for (int i = 0; i < k; i++) {
            cleanDeque(i, k);
            deq.addLast(i);
            // compute max in nums[:k]
            if (nums[i] > nums[max_idx])
                max_idx = i;
        }
        int[] output = new int[length - k + 1];
        output[0] = nums[max_idx];

        // build output
        for (int i = k; i < length; i++) {
            cleanDeque(i, k);
            deq.addLast(i);
            output[i - k + 1] = nums[deq.getFirst()];
        }
        return output;
    }

    /**
     * 双端队列法
     * 双端队列存储nums的索引
     * @param nums
     * @param k
     * @return
     */
    public int[] maxSlidingWindow3(int[] nums, int k) {
        if (nums == null || k <= 0) {
            return new int[0];
        }
        int length = nums.length;
        int[] result = new int[length-k+1];
        int resultIndex = 0;
        // deque的作用是存储nums的索引
        Deque<Integer> deque = new ArrayDeque<>();
        for (int i = 0; i < nums.length; i++) {
            // remove numbers out of range k
            // 队列的头元素如果小于i-k+1认为超出窗口k的范围。因为将当前i作为窗口的最后一个元素索引，窗口第一个元素索引是i-k+1
            // 每次都是从队尾加入元素，所以队列头应该是最早放进去的元素
            while (!deque.isEmpty() && deque.peek() < i - k + 1) {
                //移出队列头的元素
                deque.poll();
            }
            // remove smaller numbers in k range as they are useless
            while (!deque.isEmpty() && nums[deque.peekLast()] < nums[i]) {
                //移出队列尾部的元素
                deque.pollLast();
            }
            // q contains index... result contains content
            // 把元素加到队列尾部
            deque.offer(i);
            // 保证第一个窗口循环到窗口末尾的元素
            if (i >= k - 1) {
                result[resultIndex++] = nums[deque.peek()];
            }
        }
        return result;
    }

    public static void main(String[] args) {
        Main239 main239 = new Main239();
        int[] nums = new int[]{1, 3, -1, -3, 5, 3, 6, 7};
        int[] result = main239.maxSlidingWindow3(nums, 3);
        System.out.println(result);
    }


}
