package com.lynch;

/**
 * @Author: linxueqi
 * @Description:
 * @Date: create in 2023/4/3 14:30
 */
public class RestChange {
    public static void main(String[] args) {

    }

    public static int find(int[] arr, int rest, int[] dp) {

        if (rest < 0) {
            return 0;
        }
        if (rest == 0) {
            return 1;
        }
        if(dp[rest] !=-1){
            return dp[rest];
        }

        int ways = 0;
        for (int item : arr) {
            for (int i = 1; i * item < rest; i++) {
                int result = rest - i*item;
                if(dp[result]!=-1){
                    ways += dp[result];
                }else{
                    int x = find(arr, result);
                    dp[result] = x;
                    ways += x;
                }
            }
        }
        return ways;
    }


    public static int find(int[] arr, int rest) {

        if (rest == 0) {
            return 1;
        }
        if (rest < 0) {
            return 0;
        }
        int ways = 0;
        for (int item : arr) {
            for (int i = 1; i * item < rest; i++) {
                int x = find(arr, rest - i * item);
                ways += x;
            }
        }
        return ways;
    }
}
