package com.yao.offer2;

import com.yao.bean.ListNode;

/**
 * @author choo
 */
public class Solution24 {

    public ListNode reverseList(ListNode head) {
        ListNode prev = null;
        ListNode cur = head;
        while (cur != null) {
            ListNode next = cur.next;
            cur.next = prev;
            prev = cur;
            cur = next;
        }
        return prev;
    }

}
