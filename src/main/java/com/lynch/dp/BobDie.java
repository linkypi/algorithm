package com.lynch.dp;

/**
 * 给定一个 N * M 区域，Bob当前所在位置为(a,b), 他可以在其中任一位置向左右上下四个方向走，
 * 若Bob走的位置超出该区域则死亡，求Bob活下来的概率有多少
 * @author: linxueqi
 * @Description:
 * @createTime: 2022/3/26 22:14
 */
public class BobDie {
    public static void main(String[] args) {

    }

    static String findWay1(int m, int n, int x, int y, int k) {
        long all = (long) Math.pow(4, k);
        long live = findWays(m, n, x, y, k);
        long gcd = getGreatestCommonDivisor(all, live);
        return String.valueOf((live / gcd) + "/" + all / gcd);
    }

    static int findWays(int m, int n, int x, int y, int rest) {
        if (x < 0 || x > m - 1 || y < 0 || y > n - 1) {
            return 0;
        }

        if (rest == 0) {
            return 1;
        }

        return findWays(m, n, x + 1, y, rest - 1) +
                findWays(m, n, x, y + 1, rest - 1) +
                findWays(m, n, x - 1, y, rest - 1) +
                findWays(m, n, x, y - 1, rest - 1);
    }

    private static String findWaysWithDp(int m, int n, int a, int b, int k) {
        // dp[i][j][k] 从(i,j)位置走k步存活下来的方法数
        int[][][] dp = new int[m + 1][n + 1][k + 1];

        // 在(i,j)原地不动，存活方法数是 1
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                dp[i][j][0] = 1;
            }
        }
        for (int r = 1; r <= k; r++) {
            for (int i = 1; i <= m; i++) {
                for (int j = 1; j <= n; j++) {
                    dp[i][j][r] = dp[i + 1][j][r - 1]
                            + dp[i][j + 1][r - 1]
                            + dp[i - 1][j][r - 1]
                            + dp[i][j - 1][r - 1];
                }
            }
        }
        long all = (long) Math.pow(4, k);
        long live = dp[a + 1][b + 1][k];
        long gcd = getGreatestCommonDivisor(all, live);
        return String.valueOf((live / gcd) + "/" + all / gcd);
    }

    /**
     * 获取两个数的最大公约数
     * @param m
     * @param n
     * @return
     */
    private static long getGreatestCommonDivisor(long m, long n) {
        return n == 0 ? m : getGreatestCommonDivisor(n, m % n);
    }
}
