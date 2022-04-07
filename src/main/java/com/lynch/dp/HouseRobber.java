package com.lynch.dp;

/**
 * @Author: linxueqi
 * @Description:
 * @Date: create in 2022/4/7 09:48
 */
public class HouseRobber {
    public static void main(String[] args) {
        int[] arr = {1, 2, 3, 1};
        int result = find(arr);

        int[] arr2 = {2, 7, 9, 3, 1};
        result = findOptimize(arr2);

//        int[] arr3 = {2, 7, 9, 3, 1};
        int[] arr3 = {2, 3, 2};
        int inCycle = findInCycle(arr3);
        System.out.println("xxx");

//        TreeNode root = new TreeNode(8);
//        TreeNode node4 = new TreeNode(4);
//        TreeNode node6 = new TreeNode(6);
//        TreeNode node2 = new TreeNode(2);
//        TreeNode node3 = new TreeNode(3);
//        node4.left = node2;
//        node4.right = node3;
//        root.left = node4;
//        root.right = node6;

//        TreeNode root = new TreeNode(3);
//        TreeNode node4 = new TreeNode(4);
//        TreeNode node6 = new TreeNode(5);
//        TreeNode node1 = new TreeNode(1);
//        TreeNode node3 = new TreeNode(3);
//        node4.left = node1;
//        node4.right = node3;
//        root.left = node4;
//        root.right = node6;
//        node6.right = new TreeNode(1);

        // [4,1,null,2,null,3]
        TreeNode root = new TreeNode(4);
        TreeNode node1 = new TreeNode(1);
        TreeNode node2 = new TreeNode(2);
        TreeNode node3 = new TreeNode(3);
        root.left = node1;
        node1.left = node2;
        node2.left = node3;

        int max = rob(root);

        int[] ints = robInternal(root);
        System.out.println("max: " + max);
    }

    /**
     * 1. 给定一个正数数组，每个元素表示偷盗每间房间所获得的金额，但是相邻的两个房间
     * 只能偷盗其中一间，否则会触发防盗警告，求盗贼所能获得的最大金额
     * @param arr
     * @return
     */
    private static int find(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }

        int n = arr.length;
        // dp[i] 表示从第0到i间房屋所能偷盗的最高金额
        int[] dp = new int[n];
        dp[0] = arr[0];

        for (int i = 1; i < n; i++) {
            if (i == 1) {
                // 若只有两个房间可以抢，则只能抢金额最高的一个
                dp[i] = Math.max(arr[i], arr[i - 1]);
            } else {
                // 若抢当前房间， 则最大获利是 arr[i] + dp[i - 2]
                // 若不抢当前房间，则最大获利是 dp[i - 1]
                dp[i] = Math.max(arr[i] + dp[i - 2], dp[i - 1]);
            }
        }
        return dp[n - 1];
    }

    private static int findOptimize(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }

        int n = arr.length;
        // dp[i] 表示从第0到i间房屋所能偷盗的最高金额
        int dp0 = arr[0];
        int dp1 = 0;
        int dp2 = 0;
        for (int i = 1; i < n; i++) {
            if (i == 1) {
                // 若只有两个房间可以抢，则只能抢金额最高的一个
                dp1 = Math.max(arr[i], arr[i - 1]);
                dp2 = dp1;
            } else {
                // 若抢当前房间， 则最大获利是 arr[i] + dp[i - 2]
                // 若不抢当前房间，则最大获利是 dp[i - 1]
                dp2 = Math.max(arr[i] + dp0, dp1);
                dp0 = dp1;
                dp1 = dp2;
            }
        }
        return dp2;
    }

    /**
     * 2. 给定一个正数数组，每个元素表示偷盗每间房间所获得的金额，房屋是依次相邻围成的圆环
     * 相邻的两个房间只能偷盗其中一间，否则会触发防盗警告，求盗贼所能获得的最大金额
     * @param arr
     * @return
     */
    private static int findInCycle(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }

        if (arr.length == 1) {
            return arr[0];
        }
        if (arr.length == 2) {
            return Math.max(arr[0], arr[1]);
        }
        // 若抢当前房间，则第一间不能抢，可抢范围是 1...n-1
        int p1 = find2(arr, 1, arr.length - 1);

        // 若不抢当前房间，则第一间房可以抢，可抢范围是 0...n-2
        int p2 = find2(arr, 0, arr.length - 2);

        return Math.max(p1, p2);
    }

    /**
     * 从 start 到 end 位置中获取最大金额
     * @param arr
     * @param start
     * @param end
     * @return
     */
    private static int find2(int[] arr, int start ,int end) {

        // dp[i] 表示从第0到i间房屋所能偷盗的最高金额
        int dp0 = arr[start];
        int dp1=0, dp2=0;

        for (int i = start + 1; i <= end; i++) {
            if (i - start == 1) {
                // 若只有两个房间可以抢，则只能抢金额最高的一个
                dp1 = Math.max(arr[i], arr[i - 1]);
                dp2 = dp1;
            } else {
                // 若抢当前房间， 则最大获利是 arr[i] + dp[i - 2]
                // 若不抢当前房间，则最大获利是 dp[i - 1]
                dp2 = Math.max(arr[i] + dp0, dp1);
                dp0 = dp1;
                dp1 = dp2;
            }
        }
        return dp2;
    }

    private static int find(int[] arr, int start ,int end) {

        int n = end - start + 1;
        // dp[i] 表示从第0到i间房屋所能偷盗的最高金额
        int[] dp = new int[n];
        dp[0] = arr[start];

        for (int i = start + 1; i <= end; i++) {
            int index = i - start;
            if (index == 1) {
                // 若只有两个房间可以抢，则只能抢金额最高的一个
                dp[index] = Math.max(arr[i], arr[i - 1]);
            } else {
                // 若抢当前房间， 则最大获利是 arr[i] + dp[i - 2]
                // 若不抢当前房间，则最大获利是 dp[i - 1]
                dp[index] = Math.max(arr[i] + dp[index - 2], dp[index - 1]);
            }
        }
        return dp[n - 1];
    }

    /**
     * 3. 给定一个正数数组，每个元素表示偷盗每间房间所获得的金额，各个房间组成的是二叉树结构
     * 相邻的两个房间只能偷盗其中一间，否则会触发防盗警告，求盗贼所能获得的最大金额
     * @param root
     * @return
     */
    private static int rob(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int steal = root.val;
        if (root.left != null) {
            steal += rob(root.left.left) + rob(root.left.right);
        }
        if (root.right != null) {
            steal += rob(root.right.left) + rob(root.right.right);
        }

        int notSteal = rob(root.left) + rob(root.right);

        return Math.max(steal, notSteal);
    }

    private static int[] robInternal(TreeNode root) {
        if (root == null) {
            return new int[2];
        }
        // result[0] 表示不偷的结果  result[1] 表示偷的结果
        int[] result = new int[2];

        int[] left = robInternal(root.left);
        int[] right = robInternal(root.right);

        // 该房间不偷的结果是：左右子节点可以偷也可以不偷，两者取最大
        result[0] = Math.max(left[0], left[1]) + Math.max(right[0], right[1]);
        // 偷该房间，那么左右子节点不能偷
        result[1] = root.val + left[0] + right[0];
        return result;
    }

    public static class TreeNode{
        private int val;
        private TreeNode left;
        private TreeNode right;

        public TreeNode(int val){
            this.val = val;
        }
    }
}
