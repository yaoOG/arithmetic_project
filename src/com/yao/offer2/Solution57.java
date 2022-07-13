package com.yao.offer2;

import java.util.TreeSet;

public class Solution57 {
    public boolean containsNearbyAlmostDuplicate(int[] nums, int k, int t) {
        TreeSet<Long> set = new TreeSet<>();
        for (int i = 0; i < nums.length; i++) {
            long cur = (long)nums[i];
            Long lower = set.floor(cur);
            if (lower != null && cur - lower <= t ){
                return true;
            }
            Long upper = set.ceiling(cur);
            if (upper != null && upper - cur <= t ) {
                return true;
            }
            set.add(cur);
            if (i >= k) {
                set.remove((long)nums[i-k]);
            }
        }
        return false;
    }
}
