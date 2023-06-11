package com.lynch.extern;

/**
 * 有n个打包机器，机器上的物品数量有多有少，由于物品数量不相同，需要人工将每个机器上的物品进行移动从而达到物品
 * 数量相等才能打包。每个物品重量太大，每次只能搬一个物品进行移动，为了省力只在相邻的机器上移动。请计算在搬动最
 * 小轮数的前提下，使得每个机器上的物品数量相等。如果不能使得每个机器上的物品数量相等则返回 -1。例如 [1,0,5]表示
 * 三个机器，每个机器上分别有 1， 0， 5个物品，经过这些轮次后每个机器上的物品会相等
 *  1. 第一轮 1 0 <- 5 即 1，1，4
 *  2. 第二轮 1 <- 1 <- 4 即 2，1，3
 *  3. 第三轮 2 1 <- 3 即 2，2，2
 * @author: linxueqi
 * @Description:
 * @createTime: 2022/3/23 23:07
 */
public class PackingMachine {
    public static void main(String[] args) {

    }

    private static int process(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }

        int n = arr.length;
        int sum = 0;
        for (int j : arr) {
            sum += j;
        }
        if (sum % n != 0) {
            return -1;
        }

        int avg = sum / n;
        int leftSum = 0;
        int result = 0;
        for (int i = 0; i < n; i++) {
            // 计算左边需要搬运的数量，正数表示需要搬出，负数表示搬入
            int leftRest = leftSum - i * avg;
            // 计算右边需要搬运的数量，正数表示需要搬出，负数表示搬入
            int rightRest = (sum - leftRest - arr[i]) - (n - i - 1) * avg;
            if (leftRest < 0 && rightRest < 0) {
                // 若左右两边数量都小于0表示左右两边物品过多，需要搬出
                // 但是当前机器i每次只能执行搬入或搬出操作，所以需要左右两边绝对值累加
                result = Math.max(result, Math.abs(leftRest) + Math.abs(rightRest));
            } else {
                // 否则对于其他三种情况，仅需要考虑左右两边其中一个最大值
                int max = Math.max(Math.abs(leftRest), Math.abs(rightRest));
                result = Math.max(result, max);
            }
            leftSum += arr[i];
        }
        return result;
    }
}
