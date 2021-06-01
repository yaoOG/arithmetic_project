package com.yao.leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * @author choo
 *
 * 合并区间
 *
 * 以数组 intervals 表示若干个区间的集合，其中单个区间为 intervals[i] = [starti, endi] 。
 * 请你合并所有重叠的区间，并返回一个不重叠的区间数组，该数组需恰好覆盖输入中的所有区间。
 *
 * 示例 1：
 * 输入：intervals = [[1,3],[2,6],[8,10],[15,18]]
 * 输出：[[1,6],[8,10],[15,18]]
 * 解释：区间 [1,3] 和 [2,6] 重叠, 将它们合并为 [1,6].
 *
 * 示例2：
 * 输入：intervals = [[1,4],[4,5]]
 * 输出：[[1,5]]
 * 解释：区间 [1,4] 和 [4,5] 可被视为重叠区间。
 *
 *
 *
 */
public class Main56 {
    /**
     *
     * 我们用数组 merged 存储最终的答案。
     * 首先，我们将列表中的区间按照左端点升序排序。
     * 然后我们将第一个区间加入 merged 数组中，并按顺序依次考虑之后的每个区间：
     * 如果当前区间的左端点在数组 merged 中最后一个区间的右端点之后，那么它们不会重合，我们可以直接将这个区间加入数组 merged 的末尾；
     * 否则，它们重合，我们需要用当前区间的右端点更新数组 merged 中最后一个区间的右端点，将其置为二者的较大值。
     *
     * 时间复杂度：O(nlogn)，其中 n 为区间的数量。除去排序的开销，我们只需要一次线性扫描，所以主要的时间开销是排序的 O(nlogn)。
     * 空间复杂度：O(logn)，其中 n 为区间的数量。这里计算的是存储答案之外，使用的额外空间。O(logn) 即为排序所需要的空间复杂度。
     *
     * @param intervals
     * @return
     *
     */
    public int[][] merge(int[][] intervals) {

        if (intervals.length == 0) {
            return new int[0][2];
        }
        Arrays.sort(intervals, Comparator.comparingInt(interval -> interval[0]));
        List<int[]> merged = new ArrayList<>();
        for (int i = 0; i < intervals.length; ++i) {
            int left = intervals[i][0], right = intervals[i][1];
            //如果最终答案的最后一个区间段的右区间（merged.get(merged.size() - 1)[1]）小于当前区间段的左区间，那么他们不会重合，
            //可以直接将这个区间加入最终答案的末尾
            if (merged.size() == 0 || merged.get(merged.size() - 1)[1] < left) {
                merged.add(new int[]{left, right});
            } else {
                int last = merged.size() - 1;
                //否则需要比较当前区间段右区间和最终答案最后一个区间段的右区间，取最大的
                merged.get(last)[1] = Math.max(merged.get(last)[1], right);
            }
        }
        return merged.toArray(new int[merged.size()][]);
    }
}
