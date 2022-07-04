package com.yao.leetcode;

import java.util.TreeMap;

/**
 * @author choo
 */
public class Main846 {

    public static void main(String[] args) {
        Main846 main846 = new Main846();
        int[] var = new int[]{1, 2, 3, 6, 2, 3, 4, 7, 8};
        int groupSize = 3;
        main846.isNStraightHand(var,groupSize);
    }
    public boolean isNStraightHand(int[] hand, int groupSize) {
        TreeMap<Integer, Integer> count = new TreeMap<>();
        for (int card : hand) {
            if (!count.containsKey(card))
                count.put(card, 1);
            else
                count.replace(card, count.get(card) + 1);
        }

        while (count.size() > 0) {
            int first = count.firstKey();
            for (int card = first; card < first + groupSize; ++card) {
                if (!count.containsKey(card))
                    return false;
                int c = count.get(card);
                if (c == 1)
                    count.remove(card);
                else
                    count.replace(card, c - 1);
            }
        }

        return true;
    }
}
