package com.lynch.binary_tree;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *  https://leetcode.cn/problems/path-sum-iii/
 *
 *  给定一个二叉树的根节点 root ，和一个整数 targetSum ，求该二叉树里节点值之和等于 targetSum 的 路径 的数目。
 *  路径 不需要从根节点开始，也不需要在叶子节点结束，但是路径方向必须是向下的（只能从父节点到子节点）。
 * @Author: linxueqi
 * @Description:
 * @Date: create in 2023/8/14 16:40
 */
public class BinarySum {

    @Test
    public void test(){

        List<Integer> arr = new ArrayList<>();
        TreeNode treeNode = new TreeNode(10);
        TreeNode treeNode1 = new TreeNode(5);
        TreeNode treeNode2 = new TreeNode(-3);
        treeNode.left = treeNode1;
        treeNode.right = treeNode2;

        TreeNode treeNode3 = new TreeNode(3);
        TreeNode treeNode4 = new TreeNode(2);
        treeNode1.left = treeNode3;
        treeNode1.right = treeNode4;

        TreeNode treeNode6 = new TreeNode(11);
        treeNode2.right = treeNode6;

        TreeNode treeNode7 = new TreeNode(3);
        TreeNode treeNode8 = new TreeNode(-2);
        treeNode3.left = treeNode7;
        treeNode3.right = treeNode8;

        TreeNode treeNode10 = new TreeNode(1);
        treeNode4.right = treeNode10;

        int count = pathSum(treeNode, 8);
        System.out.println("count； "+ count);
    }

    public int pathSum(TreeNode root, int sum) {
        HashMap<Integer, Integer> map = new HashMap<>();
        map.put(0, 1);
        return helper(root, map, sum, 0);
    }

    int helper(TreeNode root, HashMap<Integer, Integer> map, int sum, int pathSum){
        int res = 0;
        if(root == null) return 0;

        pathSum += root.val;
        res += map.getOrDefault(pathSum - sum, 0);
        map.put(pathSum, map.getOrDefault(pathSum, 0) + 1);

        int left = helper(root.left, map, sum, pathSum);
        int right = helper(root.right, map, sum, pathSum);
        res = left + right + res;
        map.put(pathSum, map.get(pathSum) - 1);
        return res;
    }


    public static class TreeNode{
        private Integer val;
        private TreeNode left;
        private TreeNode right;

        public TreeNode(Integer value){
            this.val = value;
        }
    }
}
