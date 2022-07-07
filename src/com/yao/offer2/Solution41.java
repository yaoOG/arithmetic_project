package com.yao.offer2;

import java.util.LinkedList;
import java.util.Queue;

public class Solution41 {
    class MovingAverage {

        private Queue<Integer> nums;
        private int capacity;
        private int sum;

        /** Initialize your data structure here. */
        public MovingAverage(int size) {
            nums = new LinkedList<>();
            capacity = size;

        }

        public double next(int val) {
            sum += val;
            nums.offer(val);
            if (capacity < nums.size()) {
                sum -= nums.poll();
            }
            return (double)sum/nums.size();

        }
    }
}
