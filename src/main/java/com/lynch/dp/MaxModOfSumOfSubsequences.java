package com.lynch.dp;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

/**
 * 给定一个非负数组及一个正数m，求一子序列，要求该子序列所有累加和模m最大
 *
 * 最简单求解的方式就是先求出每个子序列，即先求集合的所有子集(共有2的n次方个)
 * 然后将各个集合求和后再求其模m的值，最后比较最大值即可。
 */
public class MaxModOfSumOfSubsequences {
    public static void main(String[] args) {
        int[] arr = {1,2,3};
        getSubSet(arr);
    }

    private static List<List<Integer>> getSubSet(int[] arr){
        Deque<Integer> track = new LinkedList<>();
        _getSubSet(arr,0, track);
        return result;
    }

    private static List<List<Integer>> result =new ArrayList<>();
    private static void _getSubSet(int[] arr, int start, Deque<Integer> track) {
        if (arr == null || arr.length == 0) {
            return;
        }

        result.add(new ArrayList<>(track));
        for (int x = start; x < arr.length; x++) {
            // 做选择, 入队即添加到队列尾部
            track.offer(arr[x]);
            // 选择当前元素后，剩下只能从后面的数列中继续做选择
            _getSubSet(arr, x + 1, track);
            //撤销选择
            track.removeLast();
        }
    }

    private static int[] getResult(int[] arr, int m) {
        int sum = 0;
        for (int item : arr) {
            sum += item;
        }
        int n = arr.length;
        // 使用dp[i][j]中的row表示从 0...i 范围 内自由选择凑出数字j
        // 若可以凑出来则填true, 若不可以则填false
        boolean[][] dp = new boolean[n][sum + 1];

        // 第一行元素中只有arr[0]的位置才是 true
        for (int col = 0; col < sum+1; col++) {
            dp[0][col] = false;
        }

        dp[0][arr[0]] = true;
        for (int row = 0; row < n; row++) {
            dp[row][0] = false;
        }

        // 例如，若当前arr[4]=5, 求dp[4][12]，则表示从 arr的0到4范围内自由选择凑出整数12
        // 当前dp[4][12]能否凑出可分两种情况：
        // 1. 不使用arr[4]，前面0..3中需要凑出12，即此时dp[4][12] = dp[3][12]
        // 2. 使用arr[4]，前面0..3中仅仅需要凑出 12-arr[4] = 12-5 = 7 , 即此时dp[4][12] = dp[3][7]
        for (int row = 1; row < n; row++) {
            for (int col = 1; col < sum + 1; col++) {
                dp[row][col] = dp[row-1][col];
                if(col-arr[row] > -1){
                    dp[row][col] = dp[row][col] | dp[row][col - arr[row]];
                }
            }
        }

        int result = 0;
        // 最后一行dp[n-1][sum]为true时即为最终所有子集的累加和
        for(int i=0;i<sum+1;i++){
            if(dp[n-1][i]){
                result = Math.max(result, i%m);
            }
        }
        return null;
    }
}
