package com.lynch.binary_tree;

import java.util.ArrayList;
import java.util.List;

/**
 * https://leetcode.cn/problems/er-cha-shu-zhong-he-wei-mou-yi-zhi-de-lu-jing-lcof
 *
 * 给你二叉树的根节点 root 和一个整数目标和 targetSum ，
 * 找出所有 从根节点到叶子节点 路径总和等于给定目标和的路径。
 * 叶子节点 是指没有子节点的节点。
 *
 * @Author: linxueqi
 * @Description:
 * @Date: create in 2023/3/15 16:42
 */
public class BTSum {

    public static void main(String[] args) {
//        TreeNode root = new TreeNode(new Integer[]{5, 4, 8, 11, null, 13, 4, 7, 2, null, null, 5, 1});
//        List<List<Integer>> lists = pathSum(root, 22);

        TreeNode root = new TreeNode(new Integer[]{1});
        List<List<Integer>> lists = pathSum(root, 1);

        System.out.println("result: ");
    }

    public static List<List<Integer>> pathSum(TreeNode root, int target) {
        if (root == null) {
            return new ArrayList();
        }

        traverse(root, target, new ArrayList());
        return targets;
    }

    private static List<List<Integer>> targets = new ArrayList();

    private static void traverse(TreeNode root, int target, List<Integer> list) {
        if (root == null) {
            return;
        }
        target = target - root.val;
        if (target < 0) {
            return;
        }

        list.add(root.val);
        if (target == 0 && root.left == null && root.right == null) {
            targets.add(new ArrayList<>(list));
            // 退出前需要移除当前加入的元素
            list.remove(list.size() - 1);
            return;
        }

        traverse(root.left, target, list);
        traverse(root.right, target, list);
        // 退出前需要移除当前加入的元素
        list.remove(list.size() - 1);
    }
}
