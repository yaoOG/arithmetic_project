package com.yao.leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * @author choo
 *
 * 给你一个无重叠的 ，按照区间起始端点排序的区间列表。
 * 在列表中插入一个新的区间，你需要确保列表中的区间仍然有序且不重叠（如果有必要的话，可以合并区间）。
 *
 * 示例1：
 *
 * 输入：intervals = [[1,3],[6,9]], newInterval = [2,5]
 * 输出：[[1,5],[6,9]]
 *
 * 示例 2：
 * 输入：intervals = [[1,2],[3,5],[6,7],[8,10],[12,16]], newInterval = [4,8]
 * 输出：[[1,2],[3,10],[12,16]]
 * 解释：这是因为新的区间 [4,8] 与 [3,5],[6,7],[8,10]重叠。
 *
 * 示例 3：
 * 输入：intervals = [], newInterval = [5,7]
 * 输出：[[5,7]]
 *
 * 示例 4：
 * 输入：intervals = [[1,5]], newInterval = [2,3]
 * 输出：[[1,5]]
 *
 * 示例 5：
 * 输入：intervals = [[1,5]], newInterval = [2,7]
 * 输出：[[1,7]]
 *
 */
public class Main57 {

    //intervals = [[1,2],[3,5],[6,7],[8,10],[12,16]], newInterval = [4,8]
    public static void main(String[] args) {
        int intervals[][] = {{1, 2}, {3, 5}, {6, 7}, {8, 10}, {12, 16}};
        int newInterval[] = {4, 8};
        insert(intervals, newInterval);


    }

    /**
     * placed变量的作用
     * 那么我们应当在什么时候将区间 S 加入答案呢？
     * 由于我们需要保证答案也是按照左端点排序的，因此当我们遇到第一个 满足 li > right的区间时，说明以后遍历到的区间不会与 S 重叠，
     * 并且它们左端点一定会大于 S 的左端点。此时我们就可以将 S 加入答案。特别地，如果不存在这样的区间，我们需要在遍历结束后，将 S 加入答案。
     *
     * @param intervals
     * @param newInterval
     * @return
     */
    public static int[][] insert(int[][] intervals, int[] newInterval) {
        int left = newInterval[0];
        int right = newInterval[1];
        //标识newInterval是否加入了intervals
        boolean placed = false;
        List<int[]> ansList = new ArrayList<>();
        for (int[] interval : intervals) {
            if (interval[0] > right) {
                // 在插入区间的右侧且无交集
                if (!placed) {
                    ansList.add(new int[]{left, right});
                    placed = true;
                }
                ansList.add(interval);
            } else if (interval[1] < left) {
                // 在插入区间的左侧且无交集
                ansList.add(interval);
            } else {
                // 与插入区间有交集，计算它们的并集
                left = Math.min(left, interval[0]);
                right = Math.max(right, interval[1]);
            }
        }
        //如果newInterval没有加入，则最后加入
        if (!placed) {
            ansList.add(new int[]{left, right});
        }
        return ansList.toArray(new int[ansList.size()][]);
    }

}
