package com.yao.leetcode;

/**
 * @author choo
 * 对链表进行插入排序
 *
 * 插入排序的动画演示如上。从第一个元素开始，该链表可以被认为已经部分排序（用黑色表示）。
 * 每次迭代时，从输入数据中移除一个元素（用红色表示），并原地将其插入到已排好序的链表中。
 *
 * 插入排序算法：
 *
 * 插入排序是迭代的，每次只移动一个元素，直到所有元素可以形成一个有序的输出列表。
 * 每次迭代中，插入排序只从输入数据中移除一个待排序的元素，找到它在序列中适当的位置，并将其插入。
 * 重复直到所有输入数据插入完为止。
 *
 */
public class Main147 {

    public ListNode temo(ListNode head) {
        if (head == null) {
            return head;
        }
        ListNode temp = new ListNode(0);
        temp.next = head;
        ListNode lastSorted = head;
        ListNode curr = head.next;
        while (curr != null) {
            if (lastSorted.val <= curr.val) {
                lastSorted = lastSorted.next;
            }else {
                ListNode pre = temp;
                while (pre.next.val <= curr.val) {
                    pre = pre.next;
                }
                lastSorted.next = curr.next;
                curr.next = pre.next;
                pre.next = curr;
            }
            curr = lastSorted.next;
        }
        return temp.next;
    }

    public ListNode insertionSortList(ListNode head) {
        if (head == null) {
            return head;
        }
        //引入该节点是为了便于在head节点之前插入节点
        ListNode dummyHead = new ListNode(0);
        dummyHead.next = head;
        //维护 lastSorted 为链表的已排序部分的最后一个节点
        //维护 curr 为待插入的元素
        ListNode lastSorted = head;
        ListNode curr = head.next;
        while (curr != null) {
            if (lastSorted.val <= curr.val) {
                //说明 curr 应该位于 lastSorted 之后
                //将 lastSorted 后移一位，curr 变成新的 lastSorted
                lastSorted = lastSorted.next;
            } else {
                //从链表的头节点开始往后遍历链表中的节点，寻找插入 curr 的位置
                ListNode prev = dummyHead;
                while (prev.next.val <= curr.val) {
                    //令 prev 为插入 curr 的位置的前一个节点
                    prev = prev.next;
                }
                //进行如下操作，完成对 curr 的插入
                lastSorted.next = curr.next;
                curr.next = prev.next;
                prev.next = curr;
            }
            //令 curr = lastSorted.next，此时 curr 为下一个待插入的元素。
            curr = lastSorted.next;
        }
        return dummyHead.next;
    }

}
