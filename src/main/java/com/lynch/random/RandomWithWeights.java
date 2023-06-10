package com.lynch.random;

import java.util.HashSet;
import java.util.Random;

/**
 * 给定一个从下标0开始的正整数权重数组 w, 其中 w[i] 代表第 i 个下标的权重
 * 请你实现一个函数 pickIndex, 它可以随机从闭区间 [0, w.length-1] 选出并返回一个下标，
 * 选取下标 i 的概率为 w[i]/sum(w).
 *
 * 例如，对于 w = [1,3], 选取下标 0 的概率为 1/(1+3)=0.25 ,
 * 而选取下标 1 的概率为 3/(1+3)=0.75
 * @author: linxueqi
 * @Description:
 * @createTime: 2022/4/16 21:56
 */
public class RandomWithWeights {
    public static void main(String[] args) {
       int[] arr = {1,3,2,1};
       Solution solution = new Solution(arr);
        int value = solution.pickIndex();
        System.out.println(value);

    }

    public static final class Solution{

        private int[] arr;
        private int[] preSum;
        public Solution(int[] arr) {
            this.arr = arr;

            int n = arr.length;
            preSum = new int[n + 1];
            // 保留一位
            preSum[0] = 0;
            for (int i = 1; i <= n; i++) {
                preSum[i] = preSum[i - 1] + arr[i - 1];
            }
        }

        public int pickIndex() {
            Random random = new Random();
            // 在闭区间 [1, preSum[n-1]] 中选一个数
            int item = random.nextInt(preSum[arr.length - 1]) + 1;

            // 在前缀数组中找到大于等于 item 的最小值,即在数组中寻找左边界
            int left = 0;
            int right = arr.length;
            while (left < right) {
                int mid = (left + right) >> 1;
                if (preSum[mid] == item) {
                    right = mid;
                } else if (preSum[mid] > item) {
                    right = mid;
                } else if (preSum[mid] < item) {
                    left = mid + 1;
                }
            }

            return left -1;
        }
    }
}
