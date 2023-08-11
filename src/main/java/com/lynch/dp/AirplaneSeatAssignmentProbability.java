package com.lynch.dp;

import org.junit.Test;

import java.util.Arrays;

/**
 * https://leetcode-cn.com/problems/airplane-seat-assignment-probability/
 *
 * 有 n 位乘客即将登机，飞机正好有 n 个座位。第一位乘客的票丢了，他随便选了一个座位坐下。
 * 剩下的乘客将会：
 * 如果他们自己的座位还空着，就坐到自己的座位上，
 * 当他们自己的座位被占用时，随机选择其他座位
 * 第 n 位乘客坐在自己的座位上的概率是多少？
 *
 * 示例 1：
 *
 * 输入：n = 1
 * 输出：1.00000
 * 解释：第一个人只会坐在自己的位置上。
 * 示例 2：
 *
 * 输入: n = 2
 * 输出: 0.50000
 * 解释：在第一个人选好座位坐下后，第二个人坐在自己的座位上的概率是 0.5
 *
 * @Author: linxueqi
 * @Description:
 * @Date: create in 2023/8/11 15:55
 */
public class AirplaneSeatAssignmentProbability {

    @Test
    public void test() {
        int n = 10;
        double calc = calc(n);
        System.out.println("result: " + calc);


        int[] arr = {8, 5, 6, 2, 3, 4, 1};
        int len = arr.length;
        int[] dp = new int[len];
        Arrays.fill(dp, 1);

        for (int i = 1; i < len; i++) {
            for (int j = 1; j <= i; j++) {
                if (arr[j] > arr[j - 1]) {
                    dp[j] = Math.max(dp[j], dp[j - 1] + 1);
                }
            }
        }
        System.out.println("");
    }

    public double calc2(int n) {
        return n == 1 ? 1.0 : 0.5;
    }

    /**
     * 该种求解方式会超时
     * @param n
     * @return
     */
    public double calc(int n) {

        // 对于第一位乘客而言，他有三种选择
        // 1. 第一位乘客直接坐自己的位置，那么后面从第2到第n-1个人就可以顺利做下，那么第n个人可以坐到自己的位置，概率为1/n
        // 2. 第一位乘客没有坐自己的位置，坐到了第n个位置，此时若第n个人也恰好坐到了第一个位置，
        //    那么剩下的人都会坐到自己的位置，那么第n个人坐到自己位置的概率为0
        // 3. 第一位乘客没有坐自己的位置，坐到了 [1,n-1] 的位置 k. 对于第k个人而言，自己的位置已被第一个人占用，而第[2,k-1]
        //   必定先于k登机，能找到自己的位置坐下，所以当第k个人登机时，留给他的选择是第一个位置 以及 [k+1,n]的位置
        // 此时 k 同样面临三种选择：
        // 1. 坐到正确的位置，即第一个位置，那么第n个乘客就可以坐到自己的位置
        // 2. 坐到第n个乘客的位置，那么n就无法坐到自己的位置
        // 3. 坐到 [k+1, n-1]的任意一个位置
        // 如此循环往复，第k个人面临的选择与第一个人的选择一样，只不过规模不同。
        // 所以此题公式为 p [1] = 1.0; p [2] = 0.5;
        //  p[3] = 1/3 + p[2]/3 = 0.5;
        //  p[4] = 1/4 + p[2]/4 + p[3]/4 = 0.5
        //  p[5] = 1/5 + p[2]/5 + p[3]/5 + p[4]/5 = 0.5
        //
        //作者：IamValuable
        //链接：https://leetcode.cn/problems/airplane-seat-assignment-probability/solutions/43180/ju-ti-fen-xi-lai-yi-bo-by-jobhunter-4/
        //来源：力扣（LeetCode）
        //著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
        if(n == 1){
            return 1.0;
        }
        double[] p = new double[n + 1];
        p[1] = 1.0;
        p[2] = 0.5;

        for (int i = 3; i <= n; i++) {
            double temp = 1 / (i * 1.0);
            double sum = temp;
            for (int j = 1; j + 1 < i; j++) {
                sum += p[i - j] * temp;
            }
            p[i] = sum;
        }
        return p[n];
    }
}
