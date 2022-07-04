package com.yao.offer2;

import com.yao.bean.ListNode;

/**
 * @author choo
 */
public class Solution25 {
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        l1 = reverseNode(l1);
        l2 = reverseNode(l2);
        ListNode reverseHead = addReversed(l1, l2);
        return reverseNode(reverseHead);


    }

    private ListNode reverseNode(ListNode head) {
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

    private ListNode addReversed(ListNode head1, ListNode head2) {
        ListNode dummyNode = new ListNode(0);
        ListNode sumNode = dummyNode;
        int carry = 0;
        while (head1 != null || head2 != null) {
            int sum = (head1 == null ? 0 : head1.val) + (head2 == null ? 0 : head2.val) + carry;
            carry = sum >= 10 ? 1 : 0;
            sum = sum >= 10 ? sum - 10 : sum;
            ListNode newNode = new ListNode(sum);
            sumNode.next = newNode;
            sumNode = sumNode.next;

            head1 = head1 == null ? null : head1.next;
            head2 = head2 == null ? null : head2.next;

        }
        if (carry > 0) {
            sumNode.next = new ListNode(carry);
        }
        return dummyNode.next;
    }
}
