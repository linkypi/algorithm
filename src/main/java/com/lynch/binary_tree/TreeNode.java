package com.lynch.binary_tree;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;

/**
 * @Author: linxueqi
 * @Description:
 * @Date: create in 2023/3/15 16:45
 */
public class TreeNode {

    Integer val;
    TreeNode left;
    TreeNode right;

    public TreeNode(Integer[] arr) {
        if (arr == null || arr.length == 0) {
            return;
        }

        this.val = arr[0];

        Deque<TreeNode> queue = new LinkedList<>();
        queue.offer(this);

        boolean isLeft = true;
        for (int index = 1; index < arr.length; index++) {
            TreeNode root = queue.getFirst();
            if (isLeft) {
                if (arr[index] != null) {
                    root.left = new TreeNode(arr[index]);
                    queue.offer(root.left);
                }
                isLeft = false;
            } else {
                if (arr[index] != null) {
                    root.right = new TreeNode(arr[index]);
                    queue.offer(root.right);
                }
                isLeft = true;
                // 遍历完每一层后移除父节点元素
                queue.pollFirst();
            }
        }
    }

    TreeNode() {
    }

    TreeNode(Integer val) {
        this.val = val;
    }

    TreeNode(Integer val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }
}
