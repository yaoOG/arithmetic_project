package com.yao.leetcode;

import java.util.HashMap;
import java.util.Map;

public class Main1 {

    //map的key存储数组中的值，value存储数组的下标
    public static int[] twoSum(int[] nums, int target) {
        Map<Integer,Integer>  map = new HashMap<>();
        for (int i = 0 ; i < nums.length; i++) {
            if(map.containsKey(target - nums[i])){
                return new int[]{map.get(target - nums[i]) , i};
            }
            map.put(nums[i],i);
        }
        return new int[0];
    }

    public static void main(String[] args) {
        int[] nums = new int[]{2, 7, 11, 15};
        int target = 9;
        int[] nums2 = new int[2];
        nums2 = twoSum(nums, target);
        for (int i = 0; i < nums2.length; i++) {
            System.out.println(nums2[i]);
        }
    }

}
