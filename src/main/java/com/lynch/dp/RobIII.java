package com.lynch.dp;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: linxueqi
 * @Description:
 * @Date: create in 2023/8/16 9:53
 */
public class RobIII {

    @Test
    public void test(){
        List<Integer> arr = new ArrayList<>();
        TreeNode root = new TreeNode(4);
        TreeNode treeNode1 = new TreeNode(1);
        root.left = treeNode1;

        TreeNode treeNode2 = new TreeNode(2);
        treeNode1.left = treeNode2;

        TreeNode treeNode3 = new TreeNode(3);
        treeNode2.left = treeNode3;

        int rob = rob(root);
        int[] result = find(root);
        System.out.println("max value: "+ rob);
    }

    public int rob(TreeNode root) {

        Map<String, Integer> cache = new HashMap<>();

        int a = find(root,true, 0,cache);
        int b = find(root,false, 0,cache);
        return Math.max(a,b);
//        int a = find(root,true, 0);
//        int b = find(root,false, 0);
//        return Math.max(a,b);
    }

    public int[] find(TreeNode root) {
        if (root == null) {
            return new int[]{0, 0};
        }

        int[] left = find(root.left);
        int[] right = find(root.right);

        // dp[0] 表示不盗取当前root节点可以获得的价值
        // dp[1] 表示盗取当前root节点可以获得的价值
        int[] dp = new int[2];

        // 不盗取当前节点，那么其子节点可盗可不盗，两者取最大
        dp[0] = Math.max(left[0], left[1]) + Math.max(right[0], right[1]);
        // 盗取当前节点，那么其左右子节点就不能盗取
        dp[1] = root.val + left[0] + right[0];
        return dp;
    }

    public int find(TreeNode root, boolean parentHasRob, int max, Map<String, Integer> cache) {

        if (root == null) {
            return 0;
        }
        // 若父节点已被盗取则子节点不能再盗取
        if (parentHasRob) {
            String key = getKey(root);
            if (cache.containsKey(key)) {
                return cache.get(key);
            }
            int left = find(root.left, false, max, cache);
            int right = find(root.right, false, max, cache);
            int val = max + left + right;
            cache.put(key, val);
            return val;
        }

        // 若父节点未被盗取则子节点有两种选择：盗 与 不盗
        String robKey = getKey(root);
        if (!cache.containsKey(robKey)) {
            int left = find(root.left, true, max, cache);
            int right = find(root.right, true, max, cache);
            int rob = left + right + root.val;
            cache.put(robKey, rob);
        }

        String unRobKey = getKey(root);
        if (!cache.containsKey(unRobKey)) {
            int left = find(root.left, false, max, cache);
            int right = find(root.right, false, max, cache);
            int unRob = left + right;
            cache.put(unRobKey, unRob);
        }

        return max + Math.max(cache.get(robKey), cache.get(unRobKey));
    }

    private String getKey(TreeNode root) {
        String key = root.val + "";
        if (root.left != null) {
            key += root.left.val;
        }
        if (root.right != null) {
            key += root.right.val;
        }
        return key;
    }

    /**
     * 使用递归方式容易超时
     * @param root
     * @param parentHasRob
     * @param max
     * @return
     */
    public int find(TreeNode root, boolean parentHasRob, int max){

        if(root == null ){
            return 0;
        }
        // 若父节点已被盗取则子节点不能再盗取
        if(parentHasRob){
            int left = find(root.left, false, max);
            int right = find(root.right, false, max);
            return max + left + right;
        }

        // 若父节点未被盗取则子节点有两种选择：盗 与 不盗
        int left = find(root.left, true, max);
        int right = find(root.right, true, max);
        int rob = left + right + root.val;

        left = find(root.left, false, max);
        right = find(root.right, false, max);
        int unRob = left + right;

        return max + Math.max(rob, unRob);
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
