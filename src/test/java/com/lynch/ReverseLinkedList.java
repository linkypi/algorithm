package com.lynch;

/**
 * @Author: linxueqi
 * @Description:
 * @Date: create in 2022/6/1 17:28
 */
public class ReverseLinkedList {
    public static void main(String[] args) {
        Node node1 = new Node(1);
        Node node2 = new Node(2);
        Node node3 = new Node(3);
        Node node4 = new Node(4);
        Node node5 = new Node(5);
//        node1.next = node2;
//        node2.next = node3;
//        node3.next = node4;
//        node4.next = node5;
        node3.next = node5;

//        reverse(node1);

        // 1,2,3,4,5; 2,4
//        reverseBetween(node1, 2,4);
        // 1,2,3,4,5; 3,5
//        reverseBetween(node1, 3,5);
//        reverseBetween(node5, 1,1);
        // 3,5; 1,2
//        reverseBetween(node3, 1,2);
//        reverseBetween2(node3, 1,2);
        reverseBetween3(node3, 1,2);
        // 1,2,3
//        reverseBetween(node1, 3,3);
        // 1,2,3; 1,2
//        reverseBetween(node1, 1,2);

        // {3,1,3,4,1,4,-1},3,7

        System.out.println("xxxx");
    }

    public static Node reverseBetween3 (Node head, int m, int n) {
        //设置虚拟头节点
        Node dummyNode = new Node(-1);
        dummyNode.next = head;
        Node pre = dummyNode;
        for (int i = 0; i < m - 1; i++) {
            pre = pre.next;
        }

        Node cur = pre.next;
        Node next = null;
        for (int i = 0; i < n - m; i++) {
            next = cur.next;
            cur.next = next.next;
            next.next = pre.next;
            pre.next = next;
        }
        return dummyNode.next;
    }

    public static Node reverseBetween (Node head, int m, int n) {
        int count = m - 1;
        Node pre = null, next = null;
        Node start = head;
        while (count != 0) {
            pre = start;
            start = start.next;
            count--;
        }

        // 已是最后一个节点
        if (start.next == null && m == n) {
            return head;
        }

        Node tmp = null;
        while (n - m + 1 != 0 && start != null) {
            next = start.next;
            start.next = tmp;
            tmp = start;
            start = next;
            m++;
        }
        if (pre != null) {
            pre.next = tmp;
        }else{
            pre = tmp;
        }
        if (tmp != null && next != null) {
            head = tmp;
            while (tmp.next != null) {
                tmp = tmp.next;
            }
            tmp.next = next;
        }

        return pre == null ? head : pre;
    }

    private static Node reverse(Node head) {
        Node next = null;
        Node temp = null;
        while (head != null) {
            next = head.next;
            head.next = temp;
            temp = head;
            head = next;
        }
        return temp;
    }

    public static class Node {
        private int val;
        Node next;

        public Node(int val) {
            this.val = val;
        }
    }
}
