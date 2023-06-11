package com.lynch.binary_tree;

import java.nio.channels.Pipe;

/**
 * 将搜索二叉树转为双向链表,并输出链表结果
 */
public class BT2DoubleLinkedList {
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

        Item linkedList = convertToLinkedList(parent);
        StringBuilder builder = new StringBuilder();
        while (linkedList != null) {
            builder.append(linkedList.value);
            linkedList = linkedList.next;
            if (linkedList != null) {
                builder.append("---> ");
            }
        }

        System.out.println(builder.toString());
    }

    private static Item convertToLinkedList(Node head) {
        if (head == null) {
            return null;
        }
        Item left = convertToLinkedList(head.left);
        Item right = convertToLinkedList(head.right);

        Item current = new Item(head.value);
        if (left != null) {
            // 首先找到左边最大节点,即双向链表最后一个元素
            Item end = left;
            while(end.next!=null){
                end = end.next;
            }
            end.next = current;
            current.prev = end;
        }
        if (right != null) {
            current.next = right;
            right.prev = current;
        }
        return left != null ? left : current;
    }

    private static class Node{
        public Node(int value){
            this.value = value;
        }
        private final int value;
        private Node left;
        private Node right;
    }

    private static class Item{

        public Item(int value){
            this.value = value;
        }

        private int value;
        private Node cur;
        private Item prev;
        private Item next;
    }
}
