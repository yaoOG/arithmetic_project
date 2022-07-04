package com.yao.offer2;

/**
 * @author choo
 */
public class Solution28 {
    class Node {
        public int val;
        public Node prev;
        public Node next;
        public Node child;
    }

    public Node flatten(Node head) {
        dfs(head);
        return head;
    }

    public Node dfs(Node node) {
        Node cur = node;
        // 记录链表的最后一个节点
        Node last = null;

        while (cur != null) {
            Node next = cur.next;
            //  如果有子节点，那么首先处理子节点
            if (cur.child != null) {
                Node childLast = dfs(cur.child);

                next = cur.next;
                //  将 node 与 child 相连
                cur.next = cur.child;
                cur.child.prev = cur;

                //  如果 next 不为空，就将 last 与 next 相连
                if (next != null) {
                    childLast.next = next;
                    next.prev = childLast;
                }

                // 将 child 置为空
                cur.child = null;
                last = childLast;
            } else {
                last = cur;
            }
            cur = next;
        }
        return last;
    }

    private Node flattenGetTail(Node head) {
        Node node = head;
        //记录链表的最后一个节点
        Node tail = null;
        while (node != null) {
            Node next = node.next;
            if (node.child != null) {
                Node child = node.child;
                //childTail维护子链表的尾节点
                Node childTail = flattenGetTail(node.child);

                node.child = null;
                node.next = child;
                child.prev = node;
                childTail.next = next;
                if (next != null) {
                    next.prev = childTail;
                }
                tail = childTail;
            } else {
                tail = node;
            }
            node = next;
        }
        return tail;
    }

}
