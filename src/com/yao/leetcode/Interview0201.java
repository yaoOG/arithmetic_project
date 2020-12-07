package com.yao.leetcode;

import com.yao.bean.ListNode;
import com.yao.util.ListNodeUtils;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Daniel:)
 *
 * 编写代码，移除未排序链表中的重复节点。保留最开始出现的节点。
 *
 * 示例1:
 *  输入：[1, 2, 3, 3, 2, 1]
 *  输出：[1, 2, 3]
 *
 * 示例2:
 *  输入：[1, 1, 1, 1, 2]
 *  输出：[1, 2]
 *
 * 提示：
 * 链表长度在[0, 20000]范围内。
 * 链表元素在[0, 20000]范围内。
 *
 * 1-2-3-3-2-1
 */
public class Interview0201 {

    public static void main(String[] args) {
        ListNode listNode = ListNodeUtils.buildListNode();
        Interview0201 interview0201 = new Interview0201();
        interview0201.removeDuplicateNodes(listNode);
    }


    public ListNode removeDuplicateNodes(ListNode head) {
        if (head == null) {
            return head;
        }
        Set<Integer> occurred = new HashSet<>();
        occurred.add(head.val);
        ListNode pos = head;
        // 枚举前驱节点
        while (pos.next != null) {
            // 当前待删除节点
            ListNode cur = pos.next;
            if (occurred.add(cur.val)) {
                pos = pos.next;
            } else {
                pos.next = pos.next.next;
            }
        }
        pos.next = null;
        return head;
    }
}
