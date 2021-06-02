package com.yao.leetcode;

import java.util.Arrays;

/**
 * @author choo
 *
 * 输入一个非负整数数组，把数组里所有数字拼接起来排成一个数，打印能拼接出的所有数字中最小的一个。
 *
 * 示例 1:
 * 输入: [10,2]
 * 输出: "102"
 *
 * 示例2:
 * 输入: [3,30,34,5,9]
 * 输出: "3033459"
 */
public class Offer45 {
    public static String minNumber(int[] nums) {
        String[] strs = new String[nums.length];
        for (int i = 0; i < nums.length; i++)
            strs[i] = String.valueOf(nums[i]);
        Arrays.sort(strs, (x, y) -> (x + y).compareTo(y + x));
        StringBuilder res = new StringBuilder();
        for (String s : strs)
            res.append(s);
        return res.toString();
    }

    public static void main(String[] args) {
        int[] nums = new int[]{1, 2, 231, 321, 12};
        minNumber(nums);

/*        String[] strs = new String[]{"21", "12"};
        Arrays.sort(strs, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                System.out.println(o2.compareTo(o1));
                return o2.compareTo(o1);
            }
        });
        System.out.println(strs);*/
    }
}
