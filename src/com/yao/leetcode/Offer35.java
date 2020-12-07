package com.yao.leetcode;

/**
 * @author Daniel:)
 */
public class Offer35 {
    class Node {
        int val;
        Node next;
        Node random;

        public Node(int val) {
            this.val = val;
            this.next = null;
            this.random = null;
        }
    }


    /**
     * 拼接+拆分
     *
     * 算法流程：
     * 1.复制各节点，构建拼接链表：
     *  设原链表node1->node2->...,构建的拼接链表如下所示:node1->node1(new)->node2->node2(new)
     *
     * 2.构建新链表各节点的random指向:
     * 当访问原节点的随机指向节点cur.random时，对应新节点cur.next的随机指向节点为cur.new.random。
     *
     * 3.拆分原/新链表
     * 设置pre/cur分别指向原/新链表头节点，遍历执行pre.next = pre.next.next
     * 和 cur.next = cur.next.next将两个链表拆开
     *
     * 4.返回新链表的头节点res即可。
     *
     * 性能：
     * 时间复杂度O(N):三轮遍历链表，使用O(N)时间。
     * 空间复杂度O(1):节点引用变量使用常数大小的额外空间。
     *
     * @param head head
     * @return node
     */
    public Node copyRandomList(Node head) {
        if (head == null) return null;
        Node cur = head;
        // 1. 复制各节点，并构建拼接链表
        while (cur != null) {
            Node tmp = new Node(cur.val);
            tmp.next = cur.next;
            cur.next = tmp;
            cur = tmp.next;
        }
        // 2. 构建各新节点的 random 指向
        cur = head;
        while (cur != null) {
            if (cur.random != null)
                cur.next.random = cur.random.next;
            cur = cur.next.next;
        }
        // 3. 拆分两链表
        cur = head.next;
        Node pre = head, res = head.next;
        while (cur.next != null) {
            pre.next = pre.next.next;
            cur.next = cur.next.next;
            pre = pre.next;
            cur = cur.next;
        }
        pre.next = null; // 单独处理原链表尾节点
        return res;      // 返回新链表头节点
    }


}



