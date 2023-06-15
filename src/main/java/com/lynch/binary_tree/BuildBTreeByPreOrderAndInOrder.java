package com.lynch.binary_tree;

import java.util.HashMap;
import java.util.Map;

/**
 * 根据前序遍历结果及中序遍历结果 求出整棵二叉树
 * @author: linxueqi
 * @Description:
 * @createTime: 2022/3/25 20:12
 */
public class BuildBTreeByPreOrderAndInOrder {
    public static void main(String[] args) {
//        int[] preOrders = {3, 9, 20, 15, 7};
//        int[] inOrders =  {9, 3, 15, 20, 7};
//        int[] preOrders = {1,2};
//        int[] inOrders =  {2,1};
//        int[] preOrders = {1,2};
//        int[] inOrders =  {1,2};
        int[] preOrders = {1,2,3};
        int[] inOrders =  {3,2,1};
        Node head = b(preOrders, inOrders);
        System.out.print("");
    }

    private static Node b(int[] preOrders, int[] inOrders) {
        if (preOrders == null || preOrders.length == 0 || inOrders == null || inOrders.length == 0) {
            return null;
        }
        if (preOrders.length != inOrders.length) {
            return null;
        }

        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < preOrders.length; i++) {
            map.put(inOrders[i], i);
        }
        int n = preOrders.length;
        return build(preOrders, inOrders, 0, n - 1, 0, n - 1, map);
    }

    private static Node build(int[] preOrders, int[] inOrders,int preLeft, int preRight, int inLeft, int inRight, Map<Integer,Integer> map) {

        // 前序遍历：根 左 右
        // 中序遍历：左 根 右
        if (preLeft > preRight) {
            return null;
        }
        // 根节点永远是前序遍历的头节点
        int root = preOrders[preLeft];

        // 从中序遍历结果中获取根节点位置
        // 根节点的左半部分即为左子树，根节点的右半部分即为右子树
        int index = map.get(root);

        // 左子树包含的元素长度
        int size = index - inLeft;

        // 递归调用构建左子树，其中前序结果范围是 [preLeft+1, preLeft + size], 中序结果范围是 [inLeft, index-1]
        Node left = build(preOrders, inOrders, preLeft + 1, preLeft + size, inLeft, index - 1, map);

        // 递归调用构建左子树，其中前序结果范围是 [preLeft + size + 1, preRight], 中序结果范围是 [index+1, inRight]
        Node right = build(preOrders, inOrders, preLeft + 1 + size, preRight, index + 1, inRight, map);
        return new Node(root, left, right);
    }

    private static final class Node{
        private final int value;
        private Node left;
        private Node right;

        public Node(int val){
            this.value = val;
        }

        public Node(int val, Node left, Node right){
            this.value = val;
            this.left = left;
            this.right = right;
        }
    }

    public static void prepare(int[] preOrder, int[] inOrder){
        Map<Integer, Integer> map = new HashMap<>();
        // 记录中序遍历每个元素所在位置
        for(int index=0;index< inOrder.length; index++){
            map.put(inOrder[index], index);
        }
        Node node = build2(preOrder, 0, preOrder.length - 1, inOrder, 0, inOrder.length - 1, map);
    }

    public static Node build2(int[] preOrder, int preLeft, int preRight, int[] inOrder, int inLeft,int inRight, Map<Integer,Integer> map) {
        if (preOrder == null || inOrder == null) {
            return null;
        }
        if (preOrder.length == 0 || inOrder.length == 0) {
            return null;
        }

        // 先序遍历的第一个节点必定为根节点
        int root = preOrder[preLeft];
        Node rnode = new Node(root);
        // 根节点的左右两边即对应左子树与右子树
        int index = map.get(root);
        int leftChildSize = index - inLeft;
        int rightChildSize = inRight - index;

        // 先序：中  左  右
        // 中序：左  中  右
        Node left = build2(preOrder, preLeft + 1, preLeft + leftChildSize, inOrder, inLeft, index - 1, map);
        Node right = build2(preOrder, preRight - rightChildSize, preRight, inOrder, index+1, inRight, map);
        rnode.left = left;
        rnode.right = right;
        return rnode;
    }
}
