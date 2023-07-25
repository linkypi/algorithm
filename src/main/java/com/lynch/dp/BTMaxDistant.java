package com.lynch.dp;

/**
 * 给定一棵二叉树,求节点之间的最大距离
 */
public class BTMaxDistant {
    public static void main(String[] args) {

    }

    private Info process(Node x){
        if(x.left==null||x.right ==null){
            return new Info(0,0);
        }

        Info left = process(x.left);
        Info right = process(x.right);

        // 若使用根节点,则其最大距离为左边最大高度 + 右边最大高度 + 1
        int usex = left.maxHeight + 1 + right.maxHeight;
        // 若不使用根节点, 则最大距离为左边最大距离与右边最大距离中较大的一个
        int unusex = Math.max(left.maxDistant, right.maxDistant);
        int maxHeight = Math.max(left.maxHeight, right.maxHeight) + 1;
        return new Info(maxHeight, Math.max(usex, unusex));
    }

    public class Node{
        private int value;
        private Node left;
        private Node right;
    }

    public class Info{
        private int maxHeight;
        private int maxDistant;
        public Info(int height, int distant){
            this.maxDistant = distant;
            this.maxHeight = height;
        }
    }
}
