package com.yao.leetcode;

import com.yao.util.ListNodeUtils;
import com.yao.bean.ListNode;

/**
 * @author Daniel:)
 *
 * 给定一个链表和一个特定值 x，对链表进行分隔，使得所有小于 x 的节点都在大于或等于 x 的节点之前。
 * 你应当保留两个分区中每个节点的初始相对位置。
 *
 * 示例:
 * 输入: head = 1->4->3->2->5->2, x = 3
 * 输出: 1->2->2->4->3->5
 */
public class Main86 {


    public static void main(String[] args) {
        ListNode listNode = ListNodeUtils.buildListNode();
        Main86 main86 = new Main86();
        ListNode result = main86.partition(listNode, 3);
        System.out.println(result);
    }


    /**
     * 1.初始化两个指针 before 和 after。在实现中，我们将两个指针初始化为哑 ListNode。这有助于减少条件判断。
     * 2.利用head指针遍历原链表。
     * 3.若head 指针指向的元素值 小于 x，该节点应当是 before 链表的一部分。因此我们将其移到 before 中。
     * 4.否则，该节点应当是after 链表的一部分。因此我们将其移到 after 中。
     * 5.遍历完原有链表的全部元素之后，我们得到了两个链表 before 和 after。原有链表的元素或者在before 中或者在 after 中，这取决于它们的值。
     * 6.将 before 和 after 连接，组成所求的链表。
     * 注意: 由于我们从左到右遍历了原有链表，故两个链表中元素的相对顺序不会发生变化。另外值得注意的是，在图中我们完好地保留了原有链表。
     * 事实上，在算法实现中，我们将节点从原有链表中移除，并将它们添加到别的链表中。我们没有使用任何额外的空间，只是将原有的链表元素进行移动。
     *
     * @param head
     * @param x
     * @return
     */
    public ListNode partition(ListNode head, int x) {

        // before and after are the two pointers used to create the two list
        // before_head and after_head are used to save the heads of the two lists.
        // All of these are initialized with the dummy nodes created.
        ListNode beforeHead = new ListNode(0);
        ListNode before = beforeHead;
        ListNode afterHead = new ListNode(0);
        ListNode after = afterHead;

        while (head != null) {

            // If the original list node is lesser than the given x,
            // assign it to the before list.
            if (head.val < x) {
                before.next = head;
                before = before.next;
            } else {
                // If the original list node is greater or equal to the given x,
                // assign it to the after list.
                after.next = head;
                after = after.next;
            }

            // move ahead in the original list
            head = head.next;
        }

        // Last node of "after" list would also be ending node of the reformed list
        after.next = null;

        // Once all the nodes are correctly assigned to the two lists,
        // combine them to form a single list which would be returned.
        before.next = afterHead.next;

        return beforeHead.next;
    }
}