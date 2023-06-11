package com.lynch.binary_tree;

/*
 给定一个二叉树, 找到其最大搜索子树, 并输出子树节点个数
 */
public class MaxChild {
    public static void main(String[] args) {
        /*
         *                7
         *             /     \
         *            /       \
         *          4          11
         *        /  \       /    \
         *       3    5     9      13
         *     /         /  \
         *    2         8    12
         *               \
         *                10
         */
        Node head = new Node(7);
        Node node2 = new Node(2);
        Node node3 = new Node(3);
        Node node4 = new Node(4);
        Node node5 = new Node(5);

        Node node8 = new Node(8);
        Node node9 = new Node(9);
        Node node10 = new Node(10);
        Node node11 = new Node(11);
        Node node12 = new Node(12);
        Node node13 = new Node(13);

        node4.left = node3;
        node4.right = node5;
        node3.left = node2;

        node8.right = node10;
        node9.left = node8;
        node9.right = node12;
        node11.left = node9;
        node11.right = node13;

        head.left = node4;
        head.right = node11;

        Info result = process(head);
        if (result != null) {
            System.out.println(result.maxSize);
        } else {
            System.out.println("none child");
        }
    }

    private static Info process(Node head) {
        if (head == null) {
            return null;
        }

        Info left = process(head.left);
        Info right = process(head.right);
        int min = head.value;
        int max = head.value;

        if (left != null) {
            min = Math.min(min, left.min);
            max = Math.max(max, left.max);
        }
        if (right != null) {
            min = Math.min(min, right.min);
            max = Math.max(max, right.max);
        }

        int maxSize = 0;
        Node maxHead = null;
        if (left != null) {
            maxSize = left.maxSize;
            maxHead = left.head;
        }
        if (right != null && right.maxSize > maxSize) {
            maxSize = right.maxSize;
            maxHead = right.head;
        }

        boolean isBST = false;
        if ((left == null || left.isBST) && (right == null || right.isBST)) {
            if ((left == null || left.max < head.value) && (right == null || right.min > head.value)) {
                int leftSize = left == null ? 0 : left.maxSize;
                int rightSize = right == null ? 0 : right.maxSize;
                maxSize = leftSize + rightSize + 1;
                maxHead = head;
                isBST = true;
            }
        }

        return new Info(maxSize, isBST, max, min, maxHead);
    }

    public static class Node{

        public Node(int value){
            this.value = value;
        }
        private int value;
        private Node left;
        private Node right;
    }

    public static class Info {

        public Info(int size){
            this.maxSize = size;
        }
        public Info(boolean isBST, int max, int min, Node head) {
            this.isBST = isBST;
            this.max = max;
            this.min = min;
            this.head = head;
        }

        public Info(int size, boolean isBST, int max, int min, Node head) {
            this.maxSize = size;
            this.isBST = isBST;
            this.max = max;
            this.min = min;
            this.head = head;
        }

        private Node head;
        private int maxSize;
        private int max;
        private int min;
        private boolean isBST;
    }
}
