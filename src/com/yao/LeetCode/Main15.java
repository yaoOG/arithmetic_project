package com.yao.LeetCode;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * @author zhuyao
 * @date 2018/10/22
 * 给定一个包含 n 个整数的数组 nums，判断 nums 中是否存在三个元素 a，b，c ，使得 a + b + c = 0 ？找出所有满足条件且不重复的三元组。
 * <p>
 * 注意：答案中不可以包含重复的三元组。
 * <p>
 * 例如, 给定数组 nums = [-1, 0, 1, 2, -1, -4]，
 * <p>
 * 满足要求的三元组集合为：
 * [
 * [-1, 0, 1],
 * [-1, -1, 2]
 * ]
 *
 * 主要思想是，遍历数组，用 0 减去当前的数，作为 sum ，然后再找两个数使得和为 sum。
 *
 * 这样看来遍历需要 O（n），再找两个数需要 O（n²）的复杂度，还是需要 O（n³）。
 *
 * 巧妙之处在于怎么找另外两个数。
 *
 * 最最优美的地方就是，首先将给定的 num 排序。
 *
 * 这样我们就可以用两个指针，一个指向头，一个指向尾，去找这两个数字，这样的话，找另外两个数时间复杂度就会从 O（n²），降到 O（n）。
 *
 * 而怎么保证不加入重复的 list 呢？
 *
 * 要记得我们的 nums 已经有序了，所以只需要找到一组之后，当前指针要移到和当前元素不同的地方。其次在遍历数组的时候，如果和上个数字相同，也要继续后移。文字表述比较困难，可以先看下代码。
 */
public class Main15 {
    public List<List<Integer>> threeSum(int[] num) {
        Arrays.sort(num); //排序
        List<List<Integer>> res = new LinkedList<>();
        for (int i = 0; i < num.length-2; i++) {
            //为了保证不加入重复的 list,因为是有序的，所以如果和前一个元素相同，只需要继续后移就可以
            if (i == 0 || (i > 0 && num[i] != num[i-1])) {
                //两个指针,并且头指针从i + 1开始，防止加入重复的元素
                int lo = i+1, hi = num.length-1, sum = 0 - num[i];
                while (lo < hi) {
                    if (num[lo] + num[hi] == sum) {
                        res.add(Arrays.asList(num[i], num[lo], num[hi]));
                        //元素相同要后移，防止加入重复的 list
                        while (lo < hi && num[lo] == num[lo+1]) lo++;
                        while (lo < hi && num[hi] == num[hi-1]) hi--;
                        lo++; hi--;
                    } else if (num[lo] + num[hi] < sum) lo++;
                    else hi--;
                }
            }
        }
        return res;
    }

}
