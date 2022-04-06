package com.lynch.dp;

/**
 * @author: linxueqi
 * @Description:
 * @createTime: 2022/4/3 11:01
 */
public class ThrowEggs {
    public static void main(String[] args) {

        int x = find(100, 2);
        int x2 = findWithDp(100, 2);
        int x3 = findWithDpOptimize(100, 2);
        System.out.println("result: " + x);
    }

    private static int find(int n, int k) {
        int[][] dp = new int[n + 1][k + 1];
        for (int i = 0; i < 101; i++) {
            for (int j = 0; j < 3; j++) {
                dp[i][j] = -1;
            }
        }
        return process(dp, n, k);
    }

    /**
     * @param dp
     * @param n  测试的楼层数，注意不是第几层
     * @param k  可测试的鸡蛋个数
     * @return
     */
    private static int process(int[][] dp, int n, int k) {

        // 一个鸡蛋测试 n 层楼最坏情况需要测试 n 次
        if (k == 1) {
            return n;
        }
        // 没有楼层可以测试
        if (n == 0) {
            return 0;
        }
        if (dp[n][k] != -1) {
            return dp[n][k];
        }

        int result = Integer.MAX_VALUE;
        for (int i = 1; i <= n; i++) {
            // 鸡蛋碎了只能往下面的楼层尝试，剩下的可测试楼层数就是 i-1
            int broke = process(dp, i - 1, k - 1);
            // 鸡蛋没碎往上面的楼层继续尝试，剩下可测试的楼层数就是 n-i
            int unBroke = process(dp, n - i, k);
            // 统计两者最坏的情况，已经尝试过一次需要加一
            int max = Math.max(broke, unBroke) + 1;
            // 最坏的情况中选择最好的结果
            result = Math.min(result, max);
        }
        dp[n][k] = result;
        return result;
    }

    /**
     * 对find方法的递归求解改用动态规划来求解，两者的时间复杂度都为 O(KN^2)
     *
     * @param n
     * @param k
     * @return
     */
    private static int findWithDp(int n, int k) {
        // dp[i][j] 表示一共 i 层楼使用 k 个鸡蛋测试的最少次数
        int[][] dp = new int[n + 1][k + 1];

        for (int i = 0; i < n + 1; i++) {
            // 没有鸡蛋，无法测试
            dp[i][0] = 0;
            // 一个鸡蛋测试 i 层楼最坏情况需要测试 i 次
            dp[i][1] = i;
        }

        // 没有楼层可以测试
        for (int j = 0; j < k; j++) {
            dp[0][j] = 0;
        }
        dp[1][0] = 0;
        //只有一层楼时，无论有多少个鸡蛋都只需要测试 1 次
        for (int j = 1; j < k + 1; j++) {
            dp[1][j] = 1;
        }

        for (int i = 2; i <= n; i++) {
            for (int j = 2; j <= k; j++) {
                int min = Integer.MAX_VALUE;
                // 相当于 process 方法内部的 for (int i = 1; i <= n; i++)
                // 该部分可以做优化，并不需要对每一层进行尝试
                for (int x = 1; x <= i; x++) {
                    int max = Math.max(dp[x - 1][j - 1], dp[i - x][j]) + 1;
                    min = Math.min(min, max);
                }
                dp[i][k] = min;
            }

        }
        return dp[n][k];
    }


    /**
     * 对动态规划时间复杂度进行优化，将递归解法中按每层遍历的方式改用二分法快速求解
     * 其时间复杂度由 O(KN^2) 降低到 O(KNLogN)
     *
     * @param n
     * @param k
     * @return
     */
    private static int findWithDpOptimize(int n, int k) {
        // dp[i][j] 表示一共 i 层楼使用 k 个鸡蛋测试的最少次数
        int[][] dp = new int[n + 1][k + 1];

        for (int i = 0; i < n + 1; i++) {
            // 没有鸡蛋，无法测试
            dp[i][0] = 0;
            // 一个鸡蛋测试 i 层楼最坏情况需要测试 i 次
            dp[i][1] = i;
        }

        // 没有楼层可以测试
        for (int j = 0; j < k; j++) {
            dp[0][j] = 0;
        }
        dp[1][0] = 0;
        //只有一层楼时，无论有多少个鸡蛋都只需要测试 1 次
        for (int j = 1; j < k + 1; j++) {
            dp[1][j] = 1;
        }

        for (int i = 2; i <= n; i++) {
            for (int j = 2; j <= k; j++) {

                // 该部分可以做优化，并不需要对每一层进行尝试
                // dp[x - 1][j - 1], dp[i - x][j]
                //   for (int x = 1; x <= i; x++) {
                //       int max = Math.max(dp[x - 1][j - 1], dp[i - x][j]) + 1;
                //       min = Math.min(min, max);
                //   }

                // 从最开始递归版本的 process函数可以看出 T1=process(x-1,k)（即dp[x - 1][j - 1]）
                // 和 T2=process(n-x,k-1)（即dp[i - x][j]）可以看出 k 是固定值，
                // 而 T1 会随着x的递增而单调递增，T2 随着x的递增而单调递减。
                // 那么T1与T2在指定范围 1...n 内必然会有一个交点，而这个交点可以通过
                // 二分法来快速定位，首先找到一个中值 mid，如果 T1 > T2 那么结果必然
                // 在 mid 左侧，如果 T1 < T2 那么结果必然在 mid 右侧

                // res = min( max(broker, unBroker) + 1 );
                int res = Integer.MAX_VALUE;
                int low = 1, high = i;
                while (low <= high) {
                    int mid = (low + high) >> 1;
                    int broken = dp[mid - 1][j - 1];
                    int unBroken = dp[i - mid][j];
                    if (broken > unBroken) {
                        high = mid - 1;
                        res = Math.min(res, broken + 1);
                    } else {
                        low = mid + 1;
                        res = Math.min(res, unBroken + 1);
                    }
                }

                dp[i][k] = res;
            }

        }
        return dp[n][k];
    }
}
