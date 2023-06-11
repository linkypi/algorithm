package com.lynch.binary_tree;

import java.util.Deque;
import java.util.LinkedList;

/**
 * 求二叉树最大高度
 */
public class BinaryTreeMaxLevel {
    public static void main(String[] args) {
         Node n1 = new Node(1);
         Node n2 = new Node(2);
         Node n3 = new Node(3);
         Node n4 = new Node(4);
         Node n5 = new Node(5);
         n1.left = n2;
         n1.right = n3;
         n2.left = n4;
         n4.left = n5;
        int maxLevel = getMaxLevel(n1);
        int minLevel = getMinLevel(n1);
        System.out.print("max level: "+ maxLevel + ", min level: "+ minLevel);
    }

    private static int getMaxLevel(Node head) {
        if (head == null) {
            return 0;
        }
        if (head.left == null && head.right == null) {
            return 0;
        }

        int leftMax = getMaxLevel(head.left) + 1;
        int rightMax = getMaxLevel(head.right) + 1;
        return Math.max(leftMax, rightMax);
    }

    private static int getMinLevel(Node head) {
        if (head == null) {
            return 0;
        }
        Deque<Node> queue = new LinkedList<>();
        queue.offer(head);

        int level = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();
            // 遍历每一层的所有节点，遍历完后高度加一
            for (int i = 0; i < size; i++) {
                Node node = queue.poll();
                if (node.left == null && node.right == null) {
                    return level;
                }
                if (node.left != null) {
                    queue.offer(node.left);
                }
                if (node.left != null) {
                    queue.offer(node.right);
                }
            }
            //遍历完一层所有元素后高度加一
            level++;
        }
        return level;
    }

    static final class Node{
        private int value;
        private Node left;
        private Node right;

        public Node(){}
        public Node(int value){
            this.value = value;
        }
    }
}
