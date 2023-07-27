package com.lynch.dp;

import java.util.Arrays;
import java.util.HashMap;

/**
 * https://leetcode.cn/problems/count-all-possible-routes/description/
 * 给你一个 互不相同 的整数数组，其中 locations[i] 表示第 i 个城市的位置。同时给你 start，finish
 * 和 fuel 分别表示出发城市、目的地城市和你初始拥有的汽油总量
 *
 * 每一步中，如果你在城市 i ，你可以选择任意一个城市 j ，满足  j != i 且 0 <= j < locations.length ，
 * 并移动到城市 j 。从城市 i 移动到 j 消耗的汽油量为 |locations[i] - locations[j]|，|x| 表示 x 的绝对值。
 *
 * 请注意， fuel 任何时刻都 不能 为负，且你 可以 经过任意城市超过一次（包括 start 和 finish ）。
 * 请你返回从 start 到 finish 所有可能路径的数目。
 * 由于答案可能很大， 请将它对 10^9 + 7 取余后返回。
 *
 * @Author: linxueqi
 * @Description:
 * @Date: create in 2023/7/25 14:25
 */
public class MinPath_1575 {
    public static void main(String[] args) {

        try {
            int[] locations = new int[]{2, 3, 6, 8, 4};
            int n = locations.length;
            int start = 1, finish = 3, fuel = 5;

            cache = new int[n][fuel + 1];

            for (int i = 0; i < n; i++) {
                Arrays.fill(cache[i], -1);
            }

//            int ways = dfsNormal(locations, start, finish, fuel);
            int ways = dfsOptimize(locations, start, finish, fuel);
            int ways2 = findWithDP(locations, start, finish, fuel);
            System.out.println("ways: " + ways2);
        } catch (Exception ex) {
            System.out.println("");
        }
    }

    static int MOD = 1000000007;

    // cache[i][j] 表示在第i个城市开始，剩余油量为j的情况下，可以到达终点的路径数
    static int[][] cache = null;

    private static int dfsNormal(int[] locations, int current, int end , int fuel) {
        try {
            // 缓存已存在则直接返回
            if (cache[current][end] != -1) {
                return cache[current][end];
            }

            // 油已耗尽仍未找到则返回0
            if (fuel == 0 && current != end) {
                cache[current][fuel] = 0;
                return 0;
            }

            // 油未耗尽，但是无法去到其他任一城市则返回 0
            boolean hasNext = false;
            for (int i = 0; i < locations.length; i++) {
                if (i != current) {
                    int cost = Math.abs(locations[end] - locations[i]);
                    if (fuel >= cost) {
                        hasNext = true;
                        break;
                    }
                }
            }
            if (fuel != 0 && !hasNext) {
                cache[current][fuel] = current == end ? 1 : 0;
                return 0;
            }

            int sum = current == end ? 1 : 0;
            for (int i = 0; i < locations.length; i++) {
                if (i != current) {
                    int need = Math.abs(locations[current] - locations[i]);
                    int cost = fuel - need;
                    if (cost >= 0) {
                        sum += dfsNormal(locations, i, end, cost);
                        sum = sum % MOD;
                    }
                }
            }
            cache[current][fuel] = sum;
            return sum;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return 0;
    }

    private static int dfsOptimize(int[] locations, int current, int end ,int fuel) {

        // 缓存已存在则直接返回
        if (cache[current][end] != -1) {
            return cache[current][end];
        }

        // 若当前位置到终点所需油量大于剩余油量则说明即使绕路也无法到达
        // 因为绕路所耗费的油量只会更多，也就无法到达终点.
        // 该情况已包含 dfsNormal 中的两种场景：
        // 1.  油已耗尽仍未找到则返回0
        // 2.  油未耗尽，但是无法去到其他任一城市则返回 0
        int need = Math.abs(locations[end] - locations[current]);
        if(need > fuel){
            cache[current][fuel] = 0;
            return 0;
        }

//        // 1. 油已耗尽仍未找到则返回0
//        if (fuel == 0 && current != end) {
//            cache[current][fuel] = 0;
//            return 0;
//        }
//
//        // 2. 油未耗尽，但是无法去到其他任一城市则返回 0
//        boolean hasNext = false;
//        for (int i = 0; i < locations.length; i++) {
//            if (i != current) {
//                int cost = Math.abs(locations[end] - locations[current]);
//                if (fuel >= cost) {
//                    hasNext = true;
//                    break;
//                }
//            }
//        }
//        if (fuel != 0 && !hasNext) {
//            cache[current][fuel] = 0;
//            return 0;
//        }

        // 选择一个可以到达的城市，然后统计这部分城市到达终点的路径总数
        int sum = current == end ? 1 : 0;
        for (int i = 0; i < locations.length; i++) {
            if (i != current) {
                need = Math.abs(locations[current] - locations[i]);
                int cost = fuel - need;
                if (cost >= 0) {
                    sum += dfsOptimize(locations, i, end, cost);
                    sum = sum % MOD;
                }
            }
        }
        cache[current][fuel] = sum;
        return sum;
    }

    /**
     * 通过观察前两种解法可知 dfsNormal，dfsOptimize 实际变化的是 start 及 fuel，而locations及 end始终保持不变
     * 通过 start, fuel 就可以得到 dp 的定义，即 dp[i][j] 表示在第 i 个城市出发，剩余油量为 j 时，可以到达终点的路径总数
     * 即 start, fuel 是 dp 循环的两个变量，通过dfs递归方式可知油量是递减的，而 dp[i][j] 依赖于 dp[k][fule-cost]
     * 也就是要想得到 dp[i][j] 的值，则必须先把 fuel 更小的值求出来方可得到答案，该遍历求解方式刚好与递归相反
     * 题解又变成了递归转动态规划的过程(同样的递归转动态规范的转换过程可以参考 {@link com.lynch.dp.CoinValue})
     * @param locations
     * @param start
     * @param finish
     * @param fuel
     */
    private static int findWithDP(int[] locations, int start, int finish, int fuel) {

        int n = locations.length;
        int[][] dp = new int[n][fuel + 1];

        for (int f = 0; f <= fuel; f++) { // 油量从小到大遍历，因为 dp[i][j] 依赖于前者, 此处的f可以理解为 dfsOptimize 的入参 fuel
            for (int current = 0; current < n; current++) { // 此处的i可以理解为 dfsOptimize 的入参 current

                // 若当前位置到终点所需油量大于剩余油量则说明即使绕路也无法到达
                // 因为绕路所耗费的油量只会更多，也就无法到达终点.
                int need = Math.abs(locations[finish] - locations[current]);
                if (need > f) {
                    dp[current][f] = 0;
                    continue;
                }

                // 选择一个可以到达的城市，然后统计这部分城市到达终点的路径总数
                int sum = current == finish ? 1 : 0;
                for (int j = 0; j < n; j++) {
                    if (j != current) {
                        need = Math.abs(locations[current] - locations[j]);
                        int cost = f - need;
                        if (cost >= 0) {
                            sum += dp[j][cost];
                            sum = sum % MOD;
                        }
                    }
                }
                dp[current][f] = sum;
            }
        }

        return dp[start][fuel];
    }
}
