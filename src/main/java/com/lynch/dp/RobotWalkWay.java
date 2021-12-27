package com.lynch.dp;

import com.lynch.tools.Utils;

/**
 * 题意: 给定一个位置 N 表示机器人可以走动的位置, N 从 1 开始到 N.
 * 机器人从 start 位置开始走到 end 位置必须走 K 步才可以到达, 求机器人有多少种走法 ?
 */
public class RobotWalkWay {

    public static void main(String[] args) {

        // 比较不同解法的结果是否正确, 每次求解的入参都是随机生成
        // 一共比较 times 次
        int times = 20;
        int minValue = 5, maxValue = 12;
        boolean success = true;
        for (int index = 0; index < times; index++) {
            int n = Utils.getRandom(minValue, maxValue);
            int k = Utils.getRandom(minValue, maxValue);
            int start = Utils.getRandom(1, n - 2); // n-2 防止start与 end 距离太近导致获取end随机数时死循环
            int end = Utils.getRandom(start + 1, n);

            int x = walk(n, start, end, k);
//           int x = walkOptimize(n, start, end, k);
            int y = walkWithCache(n, start, end, k);
            if (x != y) {
                success = false;
            }

            String format = String.format("n: %s, start: %s, end: %s, k: %s, way1: %s, way2: %s, result: %s",
                    n, start, end, k, x, y, (x == y ? "OK" : "Failed"));
            System.out.println(format);
        }
        System.out.println("All result: " + (success ? "OK" : "Failed"));
    }

    /**
     * 使用缓存求解
     * @param n
     * @param start
     * @param end
     * @param k
     * @return
     */
    static int walkWithCache(int n, int start, int end, int k){
        int[][] dp = new int[n + 1][k + 1];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < k; j++) {
                dp[i][j] = -1;
            }
        }

        return walkWithCache(n, start, end, k, dp);
    }

    /**
     * 暴力递归
     * @param n
     * @param current
     * @param end
     * @param rest
     * @return
     */
    public static int walk(int n, int current, int end, int rest) {
        // 已经没有可走的步数, 若此时 current 刚好在 end 的位置则表示该走法可取
        if (rest == 0) {
            return end == current ? 1 : 0;
        }
        // 若是边缘位置则只能往前或者往后走, 即一种选择
        if (current == 1) { // N 从 1 开始
            return walk(n, 2, end, rest - 1);
        }
        if (current == n) {
            return walk(n, current - 1, end, rest - 1);
        }
        // 若是中间位置则可前可后, 即两种选择
        return walk(n, current + 1, end, rest - 1) + walk(n, current - 1, end, rest - 1);
    }

    public static int walkWithCache(int n, int current, int end, int rest, int[][] dp) {
        // 已经没有可走的步数, 若此时 current 刚好在 end 的位置则表示该走法可取
        if (rest == 0) {
            int ret = end == current ? 1 : 0;
            dp[current][rest] = ret;
            return ret;
        }
        // 若是边缘位置则只能往前或者往后走, 即一种选择
        if (current == 1) { // N 从 1 开始
            dp[current][rest] = walkWithCache(n, 2, end, rest - 1, dp);
        } else if (current == n) {
            dp[current][rest] = walkWithCache(n, current - 1, end, rest - 1, dp);
        } else {
            // 若是中间位置则可前可后, 即两种选择
            dp[current][rest] = walkWithCache(n, current + 1, end, rest - 1, dp)
                    + walkWithCache(n, current - 1, end, rest - 1, dp);
        }
        return dp[current][rest];
    }

    // 最优解 以位置 current 为 X 轴, 剩余步数 rest 为 Y 轴, 根据递推公式推导得出以下规律
    public static int walkOptimize(int n, int start, int end, int k) {

        int[][] dp = new int[n + 1][k + 1];
        for (int i = 0; i <= n; i++) {
            for (int j = 0; j <= k; j++) {
                // 已经没有可走的步数, 若此时 current 刚好在 end 的位置则表示该走法可取, 对应前面的递归代码是
                // dp[current][rest] = end == current ? 1 : 0;
                if (j == 0) {
                    if (end == i) {
                        dp[i][j] = 1;
                    } else {
                        dp[i][j] = 0;
                    }
                }
            }
        }

        for (int j = 1; j <= k; j++) {
            for (int i = 1; i <= n; i++) {
                if (i == n) {
                    // 对应 dp[current][rest] = walk(n, current-1, rest-1);
                    dp[i][j] = dp[i - 1][j - 1];
                } else if (i == 1) {
                    // 对应 dp[current][rest]= walk(n, 2, rest-1);
                    dp[i][j] = dp[2][j - 1];
                } else {
                    // 对应  dp[current][rest] = walk(arr, current+1, rest-1) + walk(arr, current-1, rest-1);
                    dp[i][j] = dp[i - 1][j - 1] + dp[i + 1][j - 1];
                }
            }
        }
        return dp[start][k];
    }
}
