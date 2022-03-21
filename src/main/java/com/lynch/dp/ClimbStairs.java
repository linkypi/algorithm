package com.lynch.dp;

/**
 * 使用最低花费爬楼梯：
 * 数组每个下标作为一个台阶，第i个台阶对应一个非负数的体力花费值 cost[i].
 * 每当爬上一个台阶都要花费对应的体力值，一旦支付了相应的体力值就可以向上
 * 爬一个或两个台阶。请找出达到楼顶的最低花费，在开始时，你可以选择将下标
 * 0 或 1 的元素作为初始台阶
 * @author: linxueqi
 * @Description:
 * @createTime: 2022/3/10 20:33
 */
public class ClimbStairs
{
    public static void main(String[] args) {
        int[] arr = {1, 100, 1, 1, 1, 100, 1, 1, 100, 1};
        int minCost = getMinCost(arr);
        System.out.print("min cost: "+ minCost);
    }

    private static int p(int n) {
        int[] dp = new int[n + 1];

        dp[0] = 0;
        dp[1] = 1;
        for (int i = 2; i < n + 1; i++) {
            dp[i] = dp[i - 1] + dp[i - 2];
        }
        return dp[n];
    }

    private static int getMinCost(int[] arr) {
        int n = arr.length;
        // dp[i] 表示到达第i层台阶所需花费的最小体力值
        int[] dp = new int[n];

        dp[0] = arr[0];
        // 在 第二层花费的体力值只能选择 arr[i] ,
        // 因为只有选择自己所花费的体力值才最小
        dp[1] = arr[1];
        for (int i = 2; i < n; i++) {
            // 每当爬上一个台阶都要花费指定体力值，所以需要加上 arr[i]
            // 第 i 层花费体力值最小必定是在前面选择爬一个台阶和选择爬两个台阶中所花费体力的最小值
            dp[i] = arr[i] + Math.min(dp[i - 1], dp[i - 2]);
        }

        // 最后一步的选择在于前面两个选择中花费体力最小的那个
        return Math.min(dp[n - 1], dp[n - 2]);
    }
}
