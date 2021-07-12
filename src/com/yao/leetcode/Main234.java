package com.yao.leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * @author choo
 * 回文链表
 * 请判断一个链表是否为回文链表。
 */
public class Main234 {

    public boolean isPalindrome(ListNode head) {
        List<Integer> values = new ArrayList<>();
        ListNode cur = head;
        while (cur != null) {
            values.add(cur.val);
            cur = cur.next;
        }
        int left = 0;
        int right = values.size() - 1;

        while (left < right) {
            if (!values.get(left).equals(values.get(right))) {
                return false;
            }
            left++;
            right--;
        }
        return true;
    }

}
