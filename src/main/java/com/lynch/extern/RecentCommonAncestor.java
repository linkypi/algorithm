package com.lynch.extern;

/**
 * 最近公共祖先
 * Created by troub on 2022/2/28 14:19
 */
public class RecentCommonAncestor {
    public static void main(String[] args) {

    }

    private static Node get(Node root, Node p, Node q) {
        if (root == null) {
            return null;
        }

        if (root.value == p.value || root.value == q.value) {
            return root;
        }

        Node left = get(root.left, p, q);
        Node right = get(root.right, p, q);

        // 左边为空则表示p与q都不在左边，只能从右边去查找
        if (left == null) {
            return right;
        }
        // 右边为空则表示p与q都不在右边，只能从左边去查找
        if (right == null) {
            return left;
        }
        // 若左右两边都不为空，则说明p与q分别在root节点的左右两则，当前根节点即为p与q的最近公共祖先
        if (left != null && right != null) {
            return root;
        }
        return null;
    }

    private static class Node {
        private int value;
        private Node left;
        private Node right;
    }
}
