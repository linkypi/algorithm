package com.lynch.binary_tree;

import java.util.Comparator;

/**
 * @author: linxueqi
 * @Description:
 * @createTime: 2022/3/11 17:33
 */
public class BST {
    public static void main(String[] args) {

        Node head = new Node(7);
        Node node1 = new Node(5);
        Node node2 = new Node(9);
        Node node3 = new Node(8);
        Node node4 = new Node(6);
        head.left = node1;
        head.right = node2;
        node1.right = node4;
        node2.left = node3;
        boolean valid = isValid(head);
    }

    private static Node pre = null;
    private static boolean isValid(Node head) {
        if (head == null) {
            return true;
        }

        boolean left = isValid(head.left);
        if (pre != null && pre.value >= head.value) {
            return false;
        }

        pre = head;
        boolean right = isValid(head.right);
        return left && right;
    }

    public static class  Node{
        int value;
        private Node left;
        private Node right;
        public Node(int val){
            this.value = val;
        }
    }
}

