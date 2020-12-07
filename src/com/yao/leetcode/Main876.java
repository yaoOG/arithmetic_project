package com.yao.leetcode;

import com.yao.util.ListNodeUtils;
import com.yao.bean.ListNode;

/**
 * @author Daniel:)
 * 链表的中间节点
 * 给定一个头结点为 head 的非空单链表，返回链表的中间结点。
 * 如果有两个中间结点，则返回第二个中间结点。
 */
public class Main876 {
    /**
     * 我们可以继续优化方法二，用两个指针 slow 与 fast 一起遍历链表。
     * slow 一次走一步，fast 一次走两步。那么当 fast 到达链表的末尾时，slow 必然位于中间。
     * @param head head节点
     * @return
     */
    public ListNode middleNode(ListNode head) {
        ListNode slow = head, fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }

    public static void main(String[] args) {
        ListNode listNode = ListNodeUtils.buildListNode();
        Main876 main876 = new Main876();
        ListNode result = main876.middleNode(listNode);
        System.out.println(result);
    }
}
