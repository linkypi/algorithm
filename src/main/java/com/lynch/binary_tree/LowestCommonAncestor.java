package com.lynch.binary_tree;

/**
 * 给定一个二叉树，找到该二叉树中两个指定节点的最近公共祖先
 * @author: linxueqi
 * @Description:
 * @createTime: 2022/3/25 22:46
 */
public class LowestCommonAncestor {
    public static void main(String[] args) {
        Node head = new Node(3);
        Node node1 = new Node(5);
        Node node2 = new Node(1);
        Node node3 = new Node(6);
        Node node4 = new Node(2);
        Node node5 = new Node(0);
        Node node6 = new Node(8);
        Node node7 = new Node(7);
        Node node8 = new Node(4);
        Node node9 = new Node(9);

        head.left = node1;
        head.right = node2;
        node1.left = node3;
        node1.right = node4;
        node2.left = node5;
        node2.right = node6;
        node4.left = node7;
        node4.right = node8;

        Node node = find(head, node3, node9);
        System.out.println("result: " + (node == null ? "null" : node.value));
    }

    private static Node find(Node head, Node p, Node q) {
        if (head == null) {
            return null;
        }
        if (head == p || head == q) {
            return head;
        }
        Node left = find(head.left, p, q);
        Node right = find(head.right, p, q);

        if (left != null && right != null) {
            return head;
        }
        if (left == null && right == null) {
            return null;
        }

        return left == null ? right : left;
    }

    private static final class Node{
        private final int value;
        private Node left;
        private Node right;

        public Node(int val){
            this.value = val;
        }
    }
}
