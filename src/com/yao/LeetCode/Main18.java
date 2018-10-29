package com.yao.LeetCode;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * @author zhuyao
 * @date 2018/10/29
 * 给定一个包含 n 个整数的数组 nums 和一个目标值 target，判断 nums 中是否存在四个元素 a，b，c 和 d ，使得 a + b + c + d 的值与 target 相等？找出所有满足条件且不重复的四元组。
 * 注意：
 * 答案中不可以包含重复的四元组。
 * 示例：
 * 给定数组 nums = [1, 0, -1, 0, -2, 2]，和 target = 0。
 * <p>
 * 满足要求的四元组集合为：
 * [
 * [-1,  0, 0, 1],
 * [-2, -1, 1, 2],
 * [-2,  0, 0, 2]
 * ]
 */
public class Main18 {
    public List<List<Integer>> fourSum(int[] num, int target) {
        Arrays.sort(num);
        List<List<Integer>> res = new LinkedList<>();
        //多加了层循环
        for (int j = 0; j < num.length - 3; j++) {
            //防止重复的
            if (j == 0 || num[j] != num[j - 1]) {
                for (int i = j + 1; i < num.length - 2; i++) {
                    //防止重复的，不再是 i == 0 ，因为 i 从 j + 1 开始
                    if (i == j + 1 || num[i] != num[i - 1]) {
                        int lo = i + 1, hi = num.length - 1, sum = target - num[j] - num[i];
                        while (lo < hi) {
                            if (num[lo] + num[hi] == sum) {
                                res.add(Arrays.asList(num[j], num[i], num[lo], num[hi]));
                                while (lo < hi && num[lo] == num[lo + 1]) {
                                    lo++;
                                }
                                while (lo < hi && num[hi] == num[hi - 1]) {
                                    hi--;
                                }
                                lo++;
                                hi--;
                            } else if (num[lo] + num[hi] < sum) {
                                lo++;
                            } else {
                                hi--;
                            }
                        }
                    }
                }
            }
        }
        return res;
    }
}
