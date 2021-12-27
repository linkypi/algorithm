package com.lynch.binary_tree;

/**
 * 将搜索二叉树转为双向链表, 将二叉树的 left 指针比作链表的 prev
 * 将二叉树的 right 指针比作链表的 next
 * 结果返回链表头结点
 */
public class BT2DoubleLinkedList2 {
    public static void main(String[] args) {

        /**
         *           1
         *         /   \
         *        2     3
         *      /  \
         *     4    5
         */
        Node parent = new Node(1);
        Node node2 = new Node(2);
        Node node3 = new Node(3);
        Node node4 = new Node(4);
        Node node5 = new Node(5);
        node2.left = node4;
        node2.right = node5;
        parent.left = node2;
        parent.right = node3;

        Info linkedList = convertToLinkedList(parent);
        System.out.println("head: " + linkedList.start.value);

        Node head = linkedList.start;

        while (head != null) {
            System.out.print(head.value);
            if (head.right != null) {
                System.out.print(" --> ");
            }
            head = head.right;
        }
        System.out.println();
    }

    private static Info convertToLinkedList(Node head) {
        if (head == null) {
            return null;
        }
        Info left = convertToLinkedList(head.left);
        Info right = convertToLinkedList(head.right);

        Node start = head, end = head;
        if (left != null) {
            left.end.right = head;
            head.left = left.end;
            start = left.start;
        }
        if (right != null) {
            head.right = right.start;
            right.start.left = head;
            end = right.end;
        }
        return new Info(start, end);
    }

    private static class Node{
        public Node(int value){
            this.value = value;
        }
        private final int value;
        private Node left;
        private Node right;
    }

    private static class Info {

        public Info(Node start, Node end){
            this.start = start;
            this.end = end;
        }
        private Node start;
        private Node end;
    }
}
