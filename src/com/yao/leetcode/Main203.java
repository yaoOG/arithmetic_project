package com.yao.leetcode;

/**
 * @author choo
 * 移除链表元素
 *
 * 给你一个链表的头节点 head 和一个整数 val ，请你删除链表中所有满足 Node.val == val 的节点，并返回 新的头节点
 */
public class Main203 {

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

        Main203 main203 = new Main203();
        ListNode listNode = main203.removeElements(listNode1, 8);
    }
    public ListNode removeElements(ListNode head, int val) {
        ListNode dummyNode = new ListNode(-1);
        dummyNode.next = head;
        ListNode preNode = dummyNode;
        while(preNode.next != null){
            if(preNode.next.val == val){
                preNode.next = preNode.next.next;
            }else{
                preNode = preNode.next;
            }
        }
        return dummyNode.next;
    }

}
