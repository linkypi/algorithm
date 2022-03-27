package com.lynch.dp;

import com.lynch.tools.Utils;

/**
 * 题意: 在一个整型数组中给定每枚硬币的价值, 如 [2,3,7,5,3,.....],
 * 数值可重复, 求总价值为 N 时最少需要多少枚硬币.
 */
public class CoinValue {
    public static void main(String[] args) {
//        int[] a = new int[]{2, 3, 7, 5, 3};
        int[] a = new int[]{12,2};
//        int[] a = new int[]{16, 6, 15, 8, 3};
        int x1 = process(a, 0, 12);
        int y1 = processOptimize(a, 12);
        // 比较不同解法的结果是否正确, 每次求解的入参都是随机生成
        // 一共比较 times 次
        int times = 20;
        int minValue = 1, maxValue = 25;
        boolean success = true;
        for (int index = 0; index < times; index++) {
            int[] arr = Utils.generatePositiveRandomArrNoZero(maxValue, maxValue);
            int value = arr.length * Utils.getPositiveRandomNoZero(minValue, maxValue);

            Utils.printArr("times: "+ (index+1) + ",",arr);
           if(value==0){
               System.out.println("");
           }
            int x = process(arr, 0, value);
            int y = processOptimize(arr, value);
            if (x != y) {
                success = false;
            }

            String format = String.format("times: %s, arr: %s, value: %s, way1: %s, way2: %s, result: %s",
                   index+1, arr.length, value, x, y, (x == y ? "OK" : "Failed"));
            System.out.println(format);


        }
        System.out.println("All result: " + (success ? "OK" : "Failed"));
    }

    static int find(int[] arr, int index, int rest) {
        if (rest == 0) {
            return 1;
        }
        if (rest < 0) {
            return 0;
        }
        if (index > arr.length - 1) {
            return 0;
        }

        int use = find(arr, index + 1, rest - arr[index]) + 1;
        int unuse = find(arr, index + 1, rest);
        return Math.min(use, unuse);
    }


    /**
     * 使用递归方式求解
     *
     * @param arr
     * @param index
     * @param rest
     * @return
     */
    static int process(int[] arr, int index, int rest) {

        if (0 == rest) {
            return 0;
        }
        if (rest < 0) {
            return -1;
        }
        // 已经没有硬币可选
        if (index == arr.length - 1) {
            return -1;
        }

        // 分两种情况: 使用第一枚硬币 或者 不使用第一枚硬币
        int usePre = process(arr, index + 1, rest - arr[index]);
        int unUsePre = process(arr, index + 1, rest);

        // 两种方式都无解则返回-1, 剩下的只有两者其中之一为-1
        if (unUsePre == -1 && usePre == -1) {
            return -1;
        }

        // 不使用第一枚硬币时没有找到匹配结果则返回使用第一枚硬币时的解
        if (unUsePre == -1) {
            // 选择使用当前硬币, +1 即为当前这枚硬币
            return usePre + 1;
        }
        // 使用第一枚硬币时没有找到匹配结果则返回不使用第一枚硬币时的解
        if (usePre == -1) {
            return unUsePre;
        }
        // 若两种方式都有解 则返回硬币使用量最少的一个
        return Math.min(unUsePre, usePre + 1);
    }

    /**
     * 最优解
     *
     * @param arr
     * @param value
     * @return
     */
    static int processOptimize(int[] arr, int value) {
        int n = arr.length;
        int[][] dp = new int[n + 1][value + 1];
        // 首先定义边界, 对应递归代码:
        //   if (0 == rest) {
        //      return 0;
        //   }
        for (int i = 0; i <= n; i++) {
            dp[i][0] = 0;
        }
        // 对应递归代码
        //   if (index == arr.length - 1) {
        //       return -1;
        //   }
        for (int i = 1; i <= value; i++) {
            dp[n][i] = -1;
        }

        // 上述已定义 dp 边界, 故 index及 rest 需要从下一个索引开始
        // 根据图解可知, dp 必须从最下层, 即 arr 的最后往前遍历
        for (int index = n - 1; index >= 0; index--) {
            for (int rest = 0; rest <= value; rest++) {
                // 对应递归代码 int usePre = process(arr, index + 1, rest - arr[index]);
                int unUsePre = dp[index + 1][rest];
                // 对应递归代码  int unUsePre = process(arr, index + 1, rest); 但需要考虑索引边界
                int x = rest - arr[index];
                int usePre = -1;
                if (x >= 0) {
                    usePre = dp[index + 1][x];
                }
                //两种方式都无解则返回-1, 剩下的只有两者其中之一为-1
                if (unUsePre == -1 && usePre == -1) {
                    dp[index][rest] = -1;
                }else {
                    // 不使用第一枚硬币时没有找到匹配结果则返回使用第一枚硬币时的解
                    if (unUsePre == -1) {
                        // 选择使用当前硬币, +1 即为当前这枚硬币
                        dp[index][rest] = usePre + 1;
                    }
                    // 使用第一枚硬币时没有找到匹配结果则返回不使用第一枚硬币时的解
                    if (usePre == -1) {
                        dp[index][rest] = unUsePre;
                    }
                    // 若两种方式都有解 则返回硬币使用量最少的一个
                    dp[index][rest] = Math.min(unUsePre, usePre + 1);
                }
            }
        }
        return dp[0][value];
    }

}
