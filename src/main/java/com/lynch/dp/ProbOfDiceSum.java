package com.lynch.dp;

import com.lynch.tools.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 把 n 个骰子扔在地上，所有骰子朝上一面的点数之和为 s。输入 n，打印出 s 的所有可能的值出现的概率。
 * 需要用一个浮点数数组返回答案，其中第 i 个元素代表这 n 个骰子所能掷出的点数集合中第 i 小的那个的概率。
 * @Author: linxueqi
 * @Description:
 * @Date: create in 2022/3/23 16:11
 */
public class ProbOfDiceSum {
    public static void main(String[] args) {
        double[] doubles = find(2);

        List<Double> list = new ArrayList<>();
        Arrays.stream(doubles).forEach(list::add);
        Utils.printArr("result: ", list);
    }

    /**
     * 使用动态规划求解，dp[i][j] 表示投 i 枚骰子时，点数和 j 出现的次数
     * 仅考虑最后一种情况，在使用 i 枚骰子投掷时，其结果是由 i-1 枚骰子投掷时
     * 加上最后一枚骰子的六个点数出现次数之和，如：
     *    f(2,6) = f(1,1) + f(1,2) + f(1,3) + f(1,4) + f(1,5) + f(1,6)
     * 而最后点数和出现的概率即为 dp[i][j]/(6^n)
     * @param n
     * @return
     */
    private static double[] find(int n) {
        int[][] dp = new int[n + 1][6 * n + 1];

        for (int j = 1; j <= 6; j++) {
            dp[1][j] = 1;
        }


        for (int k = 2; k <= n; k++) {
            for (int sum = k; sum <= 6 * k; sum++) {
                for (int j = 1; j <= 6 && j <= sum; j++) {
                    dp[k][sum] += dp[k - 1][sum - j];
                }
            }
        }

        double pow = 1.0;
        for (int i = 0; i < n; i++) {
            pow *= 6;
        }

        double[] arr = new double[6 * n - n + 1];
        for (int sum = n; sum <= 6 * n; sum++) {
            arr[sum - n] = dp[n][sum] / (pow);
        }
        return arr;
    }
}
