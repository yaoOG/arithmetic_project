package com.yao.offer2;

import com.yao.bean.ListNode;

/**
 * @author choo
 */
public class Solution21 {
    public ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode dummyNode = new ListNode(0);
        dummyNode.next = head;

        ListNode front = head, back = dummyNode;
        for (int i = 0; i < n; i++) {
            front = front.next;
        }

        while (front != null) {
            front = front.next;
            back = back.next;
        }

        back.next = back.next.next;
        return dummyNode.next;
    }

    public static void main(String[] args) {
        Solution21 solution21 = new Solution21();
        ListNode listNode1 = new ListNode(1);
        ListNode listNode2 = new ListNode(2);
        ListNode listNode3 = new ListNode(3);
        ListNode listNode4 = new ListNode(4);
        ListNode listNode5 = new ListNode(5);
        ListNode listNode6 = new ListNode(6);

        listNode1.next = listNode2;
        listNode2.next = listNode3;
        listNode3.next = listNode4;
        listNode4.next = listNode5;
        listNode5.next = listNode6;

        ListNode listNode = solution21.removeNthFromEnd(listNode1, 3);
        System.out.println(listNode);

    }
}
