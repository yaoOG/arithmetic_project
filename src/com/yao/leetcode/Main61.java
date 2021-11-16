package com.yao.leetcode;

import com.yao.util.ListNodeUtils;
import com.yao.bean.ListNode;

/**
 * @author Daniel:)
 *
 * 给定一个链表，旋转链表，将链表每个节点向右移动k个位置，其中k是非负数。
 * 示例1:
 * 输入: 1->2->3->4->5->NULL, k = 2
 * 输出: 4->5->1->2->3->NULL
 * 解释:
 * 向右旋转 1 步: 5->1->2->3->4->NULL
 * 向右旋转 2 步: 4->5->1->2->3->NULL
 *
 * 示例2:
 * 输入: 0->1->2->NULL, k = 4
 * 输出: 2->0->1->NULL
 * 解释:
 * 向右旋转 1 步: 2->0->1->NULL
 * 向右旋转 2 步: 1->2->0->NULL
 * 向右旋转 3 步:0->1->2->NULL
 * 向右旋转 4 步:2->0->1->NULL
 */
public class Main61 {

    public static void main(String[] args) {
        Main61 main61 = new Main61();
        ListNode head = ListNodeUtils.buildListNode();
        ListNode result = main61.rotateRight(head, 2);
    }

    public ListNode rotateRight(ListNode head, int k) {
        if (head == null || head.next == null || k == 0) {
            return head;
        }
        int length = 1;
        ListNode currentNode = head;
        while (currentNode.next != null) {
            currentNode = currentNode.next;
            length++;
        }
        int position = length - k % length;
        //如果为链表长度的整数倍直接返回head
        if (position == length) {
            return head;
        }
        //闭合为环
        currentNode.next = head;
        while (position-- > 0) {
            currentNode = currentNode.next;
        }
        ListNode result = currentNode.next;
        currentNode.next = null;
        return result;
    }
}
