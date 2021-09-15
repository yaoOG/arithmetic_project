package com.yao.leetcode;

/**
 * @author choo
 */
public class Main24 {
    /**
     * 给定一个链表，两两交换其中相邻的节点，并返回交换后的链表。
     * 你不能只是单纯的改变节点内部的值，而是需要实际的进行节点交换。
     * 示例:
     * 给定 1->2->3->4, 你应该返回 2->1->4->3.
     * @param head
     * @return
     */
    public ListNode swapPairs(ListNode head) {
        ListNode dummyHead = new ListNode(0);
        dummyHead.next = head;
        ListNode temp = dummyHead;
        while (temp.next != null && temp.next.next != null) {
            ListNode node1 = temp.next;
            ListNode node2 = temp.next.next;
            //交换temp.next和temp.next.next
            temp.next = node2;
            node1.next = node2.next;
            node2.next = node1;

            temp = node1;
        }

        return dummyHead.next;
    }

    public ListNode swapPairs2(ListNode head) {
        ListNode dummyNode = new ListNode(0);
        dummyNode.next = head;
        ListNode cur = dummyNode;
        while (cur.next != null && cur.next.next != null) {
            ListNode node1 = cur.next;
            ListNode node2 = cur.next.next;

            cur.next=node2;
            node1.next = node2.next;
            node2.next = node1;

            cur = node1;
        }
        return dummyNode.next;
    }

}
