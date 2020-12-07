package com.yao.leetcode;

import com.yao.util.ListNodeUtils;
import com.yao.bean.ListNode;

/**
 * @author Daniel:)
 *
 * 删除排序链表中的重复元素 II
 *
 * 给定一个排序链表，删除所有含有重复数字的节点，只保留原始链表中 没有重复出现 的数字。
 *
 * 示例 1:
 * 输入: 1->2->3->3->4->4->5
 * 输出: 1->2->5
 *
 * 示例 2:
 * 输入: 1->1->1->2->3
 * 输出: 2->3
 */
public class Main82 {


    public static void main(String[] args) {
        ListNode listNode = ListNodeUtils.buildListNode();
        ListNode resultNode = deleteDuplicates(listNode);
        System.out.println(resultNode);
    }

    public static ListNode deleteDuplicates(ListNode head) {
        if (head == null) return null;
        ListNode fakeHead = new ListNode(0);
        fakeHead.next = head;
        ListNode pre = fakeHead;
        //cur找到重复节点的最后面那个节点
        ListNode cur = head;
        while (cur != null) {
            while (cur.next != null && cur.val == cur.next.val) {
                cur = cur.next;
            }
            if (pre.next == cur) {
                pre = pre.next;
            } else {
                pre.next = cur.next;
            }
            cur = cur.next;
        }
        return fakeHead.next;
    }

}