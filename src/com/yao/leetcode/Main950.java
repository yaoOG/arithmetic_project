package com.yao.leetcode;

import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;

/**
 * @author choo
 */
public class Main950 {

    public static void main(String[] args) {
        int[] var = new int[]{17,13,11,2,3,5,7};
        Main950 main950 = new Main950();
        main950.deckRevealedIncreasing(var);
    }

    public int[] deckRevealedIncreasing(int[] deck) {
        int length = deck.length;
        Deque<Integer> index = new LinkedList<>();
        for (int i = 0; i < length; ++i)
            index.add(i);

        int[] ans = new int[length];
        Arrays.sort(deck);
        for (int i = 0; i < deck.length; i++) {
            ans[index.pollFirst()] = deck[i];
            if (!index.isEmpty())
                index.add(index.pollFirst());
        }

        return ans;
    }

}
