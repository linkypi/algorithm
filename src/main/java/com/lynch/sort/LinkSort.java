package com.lynch.sort;

/**
 * 对单链表进行排序
 * Created by troub on 2022/2/24 13:54
 */
public class LinkSort {
    public static void main(String[] args) {
        Node node1 = new Node(6);
        Node node2 = new Node(3);
        Node node3 = new Node(4);
        Node node4 = new Node(2);
        Node node5 = new Node(1);
        node1.next = node2;
        node2.next = node3;
        node3.next = node4;
        node4.next = node5;

        print(node1, "before sort");
        final Node node = sortList(node1);
        print(node, "after sort");
    }

    private static void print(Node head, String msg) {
        StringBuilder builder = new StringBuilder();
        while (head != null) {
            builder.append(head.value);
            if (head.next != null) {
                builder.append(" -> ");
            }
            head = head.next;
        }
        System.out.println(msg + " : " + builder.toString());
    }

    private static Node sortList(Node head) {
        if (head == null || head.next == null) {
            return head;
        }

        // 快指针走两步， 慢指针走一步
        Node fast = head;
        Node slow = head;
        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        Node mid = slow.next;
        slow.next = null;
        Node left = sortList(head);
        Node right = sortList(mid);
        return merge(left, right);
    }

    private static Node merge(Node left, Node right) {
        Node head = new Node(0);
        Node temp = head;
        while (left != null && right != null) {
            if (left.value < right.value) {
                temp.next = left;
                left = left.next;
            } else {
                temp.next = right;
                right = right.next;
            }
            temp = temp.next;
        }

        if (left != null) {
            temp.next = left;
        } else {
            temp.next = right;
        }
        return head.next;
    }

    private static class Node {
        private int value;
        private Node next;

        public Node() {
        }

        private Node(int value) {
            this.value = value;
        }
    }
}
