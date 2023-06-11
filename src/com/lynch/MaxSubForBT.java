package com.lynch;

public class MaxSubForBT {
    public static void main(String[] args) {
        TreeNode root = new TreeNode(-3);
        int search = search(root, 0);
        System.out.println("");
    }

    public static int max = 0;
    public static int find(TreeNode root){
        if(root==null){
            return 0;
        }
        // 分别计算左右两边的贡献, 贡献必须大于 0 才能使得结果最大
        int left = Math.max(find(root.left), 0);
        int right = Math.max(find(root.right), 0);

        int sum = left + right+ root.val;
        max = Math.max(sum, max);
        return root.val + Math.max(left, right);
    }

    public static int search(TreeNode root, int max) {
        if (root == null) {
            return 0;
        }
        int left = search(root.left, max);
        int right = search(root.right, max);
        if (left >= 0 && right >= 0 && root.val > 0) {
            int temp = left + right + root.val;
            return Math.max(temp, max);
        }

        if (left < 0) {
            return Math.max(right, max);
        }
        if (right < 0) {
            return Math.max(left, max);
        }
        return Math.max(max, Math.max(left, right));
    }

    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode() {
        }

        TreeNode(int val) {
            this.val = val;
        }

        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }
}
