package com.yao.util;

import com.yao.bean.ListNode;

/**
 * @author Daniel:)
 */
public class ListNodeUtils {

    public static ListNode buildListNode() {
        ListNode listNode1 = new ListNode(1);
        ListNode listNode2 = new ListNode(4);
        ListNode listNode3 = new ListNode(3);
        ListNode listNode4 = new ListNode(2);
        ListNode listNode5 = new ListNode(5);
        ListNode listNode6 = new ListNode(2);

        listNode1.next = listNode2;
        listNode2.next = listNode3;
        listNode3.next = listNode4;
        listNode3.next = listNode5;
        listNode5.next = listNode6;

        return listNode1;
    }
}
