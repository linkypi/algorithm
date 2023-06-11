package com.lynch.binary_tree;

/**
 * 给定一颗二叉树，求二叉树的最大路径和，可能的规定
 * 1. 路径必须是头节点出发，到叶子节点为止，返回最大路径和
 * 2. 路径可以从任意节点出发，但必须往下到达任意节点，返回最大路径和
 * 3. 路径可以从任意节点出发，到任意节点，返回最大路径和（沿途节点只经过一次）
 *  其中第二与第三种的解法基本一致
 * @author: linxueqi
 * @Description:
 * @createTime: 2022/3/23 20:55
 */
public class MaxSumPath {
    public static void main(String[] args) {

    }

    /**
     * 2. 路径可以从任意节点出发，但必须往下到达任意节点，返回最大路径和
     *     1. 与头节点 node 无关
     *        1.1 左树上整体最大路径和
     *        1.2 右树上整体最大路径和
     *     2. 与头节点 node 有关
     *        2.1 node 自身
     *        2.2 node 向左走
     *        2.3 node 向右走
     * @param node
     * @return
     */
    private static int maxSum2(Node node) {
        if (node == null) {
            return 0;
        }
        Info info = find2(node);
        return info.allMax;
    }

    /**
     * 3. 路径可以从任意节点出发，到任意节点，返回最大路径和（沿途节点只经过一次）
     *     1. 与头节点 node 无关
     *        1.1 左树上整体最大路径和
     *        1.2 右树上整体最大路径和
     *     2. 与头节点 node 有关
     *        2.1 node 自身
     *        2.2 node 向左走
     *        2.3 node 向右走
     *        2.4 node 即向左又向右走
     * @param node
     * @return
     */
    private static int maxSum3(Node node) {
        if (node == null) {
            return 0;
        }
        Info info = find3(node);
        return info.allMax;
    }

    private static Info find2(Node node) {
        if (node == null) {
            return null;
        }

        Info left = find2(node.left);
        Info right = find2(node.right);

        // 1. 与头节点无关，则仅需考虑整体最大值
        int p1 = Integer.MIN_VALUE, p2 = Integer.MIN_VALUE;
        if (left != null) {
            p1 = left.allMax;
        }
        if (right != null) {
            p2 = right.allMax;
        }

        // 2. 与头节点有关

        // 2.1 仅头节点自身
        int p3 = node.value;

        // 2.2-2.3 计算整颗路径
        int p4 = Integer.MIN_VALUE, p5 = Integer.MIN_VALUE;
        if (left != null) {
            p4 = left.headMax + node.value;
        }
        if (right != null) {
            p5 = right.headMax + node.value;
        }

        // 综合所有情况求出当前该树的最大值
        int allMax = Math.max(p1, p2);
        allMax = Math.max(allMax, p3);
        allMax = Math.max(allMax, p4);
        allMax = Math.max(allMax, p5);

        // 计算当前该树从头节点到叶子节点的最大值
        int headMax = Math.max(p3, p4);
        headMax = Math.max(headMax, p5);
        return new Info(allMax, headMax);
    }

    private static Info find3(Node node) {
        if (node == null) {
            return null;
        }

        Info left = find3(node.left);
        Info right = find3(node.right);

        // 1. 与头节点无关，则仅需考虑整体最大值
        int p1 = Integer.MIN_VALUE, p2 = Integer.MIN_VALUE;
        if (left != null) {
            p1 = left.allMax;
        }
        if (right != null) {
            p2 = right.allMax;
        }

        // 2. 与头节点有关

        // 2.1 仅头节点自身
        int p3 = node.value;

        // 2.2-2.3 计算整颗路径
        int p4 = Integer.MIN_VALUE, p5 = Integer.MIN_VALUE;
        if (left != null) {
            p4 = left.headMax + node.value;
        }
        if (right != null) {
            p5 = right.headMax + node.value;
        }
        // 2.4 即向左又向右
        int p6 = Integer.MIN_VALUE;
        if (left !=null && right != null) {
            p6 = left.headMax + right.headMax + node.value;
        }

        // 综合所有情况求出当前该树的最大值
        int allMax = Math.max(p1, p2);
        allMax = Math.max(allMax, p3);
        allMax = Math.max(allMax, p4);
        allMax = Math.max(allMax, p5);
        allMax = Math.max(allMax, p6);

        // 计算当前该树从头节点到叶子节点的最大值
        int headMax = Math.max(p3, p4);
        headMax = Math.max(headMax, p5);
        return new Info(allMax, headMax);
    }

    private static class Info{
        // 子树中整体最大值，不一定是包含整棵树，因为有部分节点可能是负数
        private final int allMax;
        // 包含头节点时的最大值，记录该值是为了比较整体是否可以达到最大
        private final int headMax;
        public Info(int all, int head){
            this.allMax = all;
            this.headMax = head;
        }
    }

    private static class Node{
        private final int value;
        private Node left;
        private Node right;

        public  Node(int val){
            this.value = val;
        }
    }
}
