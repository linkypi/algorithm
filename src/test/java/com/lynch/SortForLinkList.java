package com.lynch;

/**
 * @Author: linxueqi
 * @Description:
 * @Date: create in 2022/6/20 15:37
 */
public class SortForLinkList {
    public static void main(String[] args) {
        Node node1 = new Node(9);
        Node node2 = new Node(2);
        Node node3 = new Node(6);
        Node node4 = new Node(4);
        Node node5 = new Node(8);

        node1.next = node2;
        node2.next = node3;
        node3.next = node4;
        node4.next = node5;

        Node head = sort(node1);
        System.out.println("");
    }

    public static Node sort(Node head) {
        if (head == null || head.next == null) {
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

        Node left = sort(head);
        Node right = sort(mid);
        return merge(left, right);
    }

    public static Node merge(Node left, Node right) {
        Node head = new Node(-1);
        Node temp = head;
        while (left != null && right != null) {
            if (left.val <= right.val) {
                temp.next = left;
                temp = left;
                left = left.next;
            } else {
                temp.next = right;
                temp = right;
                right = right.next;
            }
        }

        while (left != null) {
            temp.next = left;
            temp = left;
            left = left.next;
        }
        while (right != null) {
            temp.next = right;
            temp = right;
            right = right.next;
        }
        return head.next;
    }

    public static class Node{
        int val = 0;
        Node next = null;
        public Node(){

        }
        public Node(int val){
           this.val = val;
        }
    }
}
