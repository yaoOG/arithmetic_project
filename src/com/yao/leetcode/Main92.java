package com.yao.leetcode;

/**
 * @author Daniel:)
 * 反转链表
 * 反转从位置 m 到 n 的链表。请使用一趟扫描完成反转。
 * 说明:
 * 1 ≤m≤n≤ 链表长度。
 *
 * 示例:
 * 输入: 1->2->3->4->5->NULL, m = 2, n = 4
 * 输出: 1->4->3->2->5->NULL
 */
public class Main92 {

    public static void main(String[] args) {
        ListNode listNode1 = new ListNode(1);
        ListNode listNode2 = new ListNode(2);
        ListNode listNode3 = new ListNode(3);
        ListNode listNode4 = new ListNode(4);
        ListNode listNode5 = new ListNode(5);
        listNode1.next = listNode2;
        listNode2.next = listNode3;
        listNode3.next = listNode4;
        listNode4.next = listNode5;
        Main92 main92 = new Main92();
        ListNode listNode = main92.reverseBetween(listNode1, 2, 4);
    }

    public ListNode reverseBetween(ListNode head, int left, int right) {
        ListNode dummyNode = new ListNode(-1);
        dummyNode.next = head;
        ListNode cur = dummyNode;
        ListNode leftPreNode = null;
        ListNode rightNextNode = null;
        int i = 0;
        while (cur.next != null) {
            if (i == left - 1) {
                leftPreNode = cur;
            }
            if (i == right) {
                rightNextNode = cur.next;
                cur.next = null;
                break;
            }
            i++;
            cur = cur.next;
        }

        ListNode newHead = leftPreNode.next;
        leftPreNode.next = null;

        leftPreNode.next = reverse(newHead);
        newHead.next = rightNextNode;
        return dummyNode.next;
    }

    //和反转链表1代码一致
    public ListNode reverse(ListNode head) {
        ListNode pre = null;
        ListNode cur = head;
        while (cur != null) {
            ListNode tmp = cur.next;
            cur.next = pre;
            pre = cur;
            cur = tmp;
        }
        return pre;
    }
}
