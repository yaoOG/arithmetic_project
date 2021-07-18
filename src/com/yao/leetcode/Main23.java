package com.yao.leetcode;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * @author Daniel
 *
 * 合并K个升序链表
 * 给你一个链表数组，每个链表都已经按升序排列。
 * 请你将所有链表合并到一个升序链表中，返回合并后的链表。
 */
public class Main23 {
    public ListNode mergeKLists(ListNode[] lists){
        return merge(lists,0,lists.length - 1);
    }

    public ListNode merge(ListNode[] lists,int left,int right){
        if(left == right) return lists[left];
        if(left > right) return null;
        int mid = (right + left) >> 1;
        return mergeTwoLists(merge(lists, left, mid), merge(lists, mid + 1, right));

    }

    public ListNode mergeTwoLists(ListNode l1,ListNode l2){
        ListNode dummyHead = new ListNode(-1);
        ListNode prev = dummyHead;
        while(l1!=null && l2!=null){
            if(l1.val <= l2.val){
                prev.next = l1;
                l1 = l1.next;
            }else{
                prev.next = l2;
                l2 = l2.next;
            }
            prev = prev.next;
        }
        prev.next = l1 == null ? l2 : l1;
        return dummyHead.next;
    }
}
