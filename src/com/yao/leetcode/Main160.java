package com.yao.leetcode;

/**
 * @author choo
 * 给你两个单链表的头节点 headA 和 headB ，请你找出并返回两个单链表相交的起始节点。如果两个链表没有交点，返回 null 。
 *
 */
public class Main160 {
    public static void main(String[] args) {
        ListNode listNode1 = new ListNode(4);
        ListNode listNode2 = new ListNode(1);
        ListNode listNode3 = new ListNode(8);
        ListNode listNode4 = new ListNode(4);
        ListNode listNode5 = new ListNode(5);
        listNode1.next = listNode2;
        listNode2.next = listNode3;
        listNode3.next = listNode4;
        listNode4.next = listNode5;

        ListNode listNode11 = new ListNode(5);
        ListNode listNode21 = new ListNode(6);
        ListNode listNode31 = new ListNode(1);
        ListNode listNode41 = new ListNode(8);
        ListNode listNode51 = new ListNode(4);
        ListNode listNode61 = new ListNode(5);
        listNode11.next = listNode21;
        listNode21.next = listNode31;
        listNode31.next = listNode41;
        listNode41.next = listNode51;
        listNode51.next = listNode61;

        Main160 main160 = new Main160();
        main160.getIntersectionNode(listNode1, listNode11);
    }
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        if (headA == null || headB == null) {
            return null;
        }
        ListNode pA = headA, pB = headB;
        while (pA != pB) {
            pA = pA == null ? headB : pA.next;
            pB = pB == null ? headA : pB.next;
        }
        return pA;
    }
}
