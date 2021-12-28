package com.lynch.extern;

import org.w3c.dom.Node;

/**
 * 对单链表进行排序
 * Created by troub on 2021/12/28 9:51
 */
public class SortLinkList {
    public static void main(String[] args) {

        Node head = new Node(6);
        Node node1 = new Node(2);
        Node node2 = new Node(5);
        Node node3 = new Node(7);
        Node node4 = new Node(3);
        Node node5 = new Node(1);
        head.next = node1;
        node1.next = node2;
        node2.next = node3;
        node3.next = node4;
        node4.next = node5;

        System.out.print("merge sort before: ");
        Node temp = head;
        while (temp!=null){
            System.out.print(" " + temp.value);
            temp = temp.next;
        }
        System.out.println("");
        head = sort(head);
        System.out.print("merge sort result: ");
        while (head!=null){
            System.out.print(" " + head.value);
            head = head.next;
        }

    }

    private static Node sort(Node head) {

        // 终止条件
        if(head ==null || head.next == null){
            return head;
        }
        // 寻找中间节点
        Node fast = head, slow = head;
        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        Node mid = slow.next;
        slow.next = null;

        // 左边归并
        Node left = sort(head);
        // 右边归并
        Node right = sort(mid);

        // 最后对左右两边归并
        return merge(left, right);
    }

    private static Node merge(Node left,Node right) {

        Node head = new Node(0);
        Node temp = head;
        while (left != null && right != null) {
            if (left.value > right.value) {
                temp.next = right;
                right = right.next;
            } else {
                temp.next = left;
                left = left.next;
            }
            temp = temp.next;
        }

        if (left != null) {
            temp.next = left;
        }
        if (right != null) {
            temp.next = right;
        }
        return head.next;
    }

    static class Node {
        public int getValue() {
            return value;
        }

        private final int value;
        private Node next;

        public Node(int val) {
            this.value = val;
        }
    }
}
