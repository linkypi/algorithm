package com.lynch.dp;

/**
 * 给定一个整数 n, 求其按如下分裂方式来分裂一共存在多少种分法
 * 注意后面的数不能小于前面的数
 * 如:
 *   1 可分裂为  (1)
 *   2 可分裂为  (1,1), (2)
 *   3 可分裂为  (1,1,1), (1,2), (3)
 *   4 可分裂为  (1,1,1,1), (1,1,2), (1,3), (2,2), (4)
 */
public class NumericalDivision {
    public static void main(String[] args) {
        int ways = getWaysByRecursive(1, 7);
        int ways2 = getWaysByDp(7);
        int ways3 = getWaysByDpOptimize(7);
        System.out.println("ways1: "+ ways);
        System.out.println("ways2: "+ ways2);
        System.out.println("ways3: "+ ways3);
    }

    /**
     * 使用递归方式求解
     * @param pre 前一个数
     * @param rest 剩余的数
     * @return 返回实际可分裂的方法数
     */
    private static int getWaysByRecursive(int pre ,int rest) {
        // 当剩余为0时则说明已经找到一种分离方式
        if (rest == 0) {
            return 1;
        }
        // 若前一个数比后面的大则该种分裂方式无效
        if (pre > rest) {
            return 0;
        }

        int ways = 0;
        for (int i = pre; i <= rest; i++) {
            ways += getWaysByRecursive(i, rest - i);
        }
        return ways;
    }

    /**
     * 从递归求解中分析可知，在求解某个值时，其结果会依赖于多个值，如
     * f(1,5) = f(1,4) + f(2,3) + f(3,2) + f(4,1) + f(5,0)
     * f(1,4) = f(1,3) + f(2,2) + f(3,1) + f(4,0)
     * f(1,3) = f(1,2) + f(2,1) + f(3,0)
     * 通过dp表画图可知，每个数的结果都依赖于左侧下方的结果，为了求得该值
     * 则必须把其依赖项先求出，即先从下往上，从左往有右计算所有可能依赖项
     * base 是dp表的第一列全是 1 ，pre大于rest的位置全为 0
     *
     *                                   rest
     *        +-------+-------+-------+-------+-------+-------+-------+
     *        |       |   0   |   1   |   2   |   3   |   4   |   5   |
     *        +-------+-------+-------+-------+-------+-------+-------+
     *        |   0   |       |       |       |       |       |       |
     *        +-------+-------+-------+-------+-------+-------+-------+
     *        |   1   |       |       |       | (1,3) | (1,4) | (1,5) |
     *        +-------+-------+-------+-------+-------+-------+-------
     *   pre  |   2   |       |       | (2,2) | (2,3) |       |       |
     *        +-------+-------+-------+-------+-------+-------+-------+
     *        |   3   |       | (3,1) | (3,2) |       |       |       |
     *        +-------+-------+-------+-------+-------+-------+-------+
     *        |   4   | (4,0) | (4,1) |       |       |       |       |
     *        +-------+-------+-------+-------+-------+-------+-------+
     *        |   5   | (5,0) |       |       |       |       |       |
     *        +-------+-------+-------+-------+-------+-------+-------+
     * @param n
     * @return
     */
    private static int getWaysByDp(int n) {
        int[][] dp = new int[n + 1][n + 1];

        // 第一列全为 1
        for (int i = 1; i < n + 1; i++) {
            dp[i][0] = 1;
        }

        for (int pre = n; pre > 0; pre--) {
            for (int rest = pre; rest <= n; rest++) {
                for (int i = pre; i <= rest; i++) {
                    dp[pre][rest] += dp[i][rest - i];
                }
            }
        }
        return dp[1][n];
    }

    /**
     * dp斜率优化，通过观察临近几个位置：
     *
     * f(2,4) = f(2,2) + f(3,1) + f(4,0)
     * f(1,4) = f(1,3) + f(2,2) + f(3,1) + f(4,0)
     *
     * f(2,5) = f(2,3) + f(3,2) + f(4,1) + f(5,0)
     * f(1,5) = f(1,4) + f(2,3) + f(3,2) + f(4,1) + f(5,0)
     *
     * 得出：
     *  f(1,4) = f(1,3) +  f(2,4)
     *  f(1,5) = f(1,4) +  f(2,5)
     *  通过该方法也就可以把 getWaysByDp 最内层的for循环消除
     * @param n
     * @return
     */
    private static int getWaysByDpOptimize(int n) {
        int[][] dp = new int[n + 1][n + 1];

        // 第一列全为 1
        for (int i = 1; i < n + 1; i++) {
            dp[i][0] = 1;
        }

        // (x,x)即前一个数pre=x, 还剩余rest=x需要分裂，此时也只有一种情况，
        // 即只能分裂为 x , 因为pre大于等于rest
        for (int i = 1; i < n + 1; i++) {
            dp[i][i] = 1;
        }

        for (int pre = n-1; pre > 0; pre--) {
            for (int rest = pre+1; rest <= n; rest++) {
               dp[pre][rest] = dp[pre][rest - pre] + dp[pre + 1][rest];
            }
        }
        return dp[1][n];
    }
}
