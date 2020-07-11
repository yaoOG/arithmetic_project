package com.yao.leetcode;

import java.util.*;

/**
 * @author Daniel:)
 */
public class Main496 {

    public int[] nextGreaterElement(int[] nums1, int[] nums2) {
        Map<Integer, Integer> map = new HashMap<>(); // map from x to next greater element of x
        Stack<Integer> stack = new Stack<>();
        for (int num : nums2) {
            //当前元素是栈中元素右边最大值，存入map<目标数，目标数右边第一个比它大的数>
            while (!stack.isEmpty() && stack.peek() < num)
                map.put(stack.pop(), num);
            stack.push(num);
        }
        for (int i = 0; i < nums1.length; i++)
            nums1[i] = map.getOrDefault(nums1[i], -1);
        return nums1;
    }

    public static void main(String[] args) {
        int[] findNums = {4,1,2};
        int[] nums = {1,3,4,2};
        Main496 main496 = new Main496();
        int[] result = main496.nextGreaterElement(findNums, nums);
        System.out.println(result);

    }

}
