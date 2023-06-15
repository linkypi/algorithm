package com.lynch;

/**
 * 求最近公共祖先
 * @Author: linxueqi
 * @Description:
 * @Date: create in 2023/3/21 9:48
 */
public class CommonAncestor {
    public static void main(String[] args) {

        Node root = new Node(1);
        Node node1 = new Node(2);
        Node node2 = new Node(3);
        Node node3 = new Node(4);
        Node node4 = new Node(5);
        root.left = node1;
        root.right = node2;
        node1.left = node3;
        node1.right = node4;

        Node node = find(root, node3, node2);
        System.out.println("node: " + (node == null ? "null" : node.value));
    }

    public static Node find(Node root, Node p, Node q) {
        if (root == null) {
            return null;
        }
        if (root.value == p.value || root.value == q.value) {
            return root;
        }

        Node left = find(root.left, p, q);
        Node right = find(root.right, p, q);
        if(left == null && right == null){
            return null;
        }
        if (left != null && right == null) {
            return left;
        }
        if ( right != null && left == null) {
            return right;
        }

        return root;
    }

    public static class Node{
        private Node left;
        private Node right;
        private int value;

        public Node(int val){
            this.value = val;
        }
    }
}
