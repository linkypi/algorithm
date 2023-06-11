package com.lynch.dp;

import java.util.*;

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

    /**
     * 该解法用于数组所有数累加和sum很大，m也很大，但arr长度相对不大的情况
     * 该情况来自案例原题，其中 1=<N=<35, sum累加和接近10的9次方，m同样接近10的9次方
     * 不论是使用第一种getResult1方法使用sum做列数，还是第二种方法getResult2使用m做列数
     * 都会导致常数量级超出 10的9次方。即前面两种方法都无法满足要求，若使用纯暴力解法，即先
     * 求出所有子集累加和，其中求子集的时间复杂度是2的N次方，当N=35时，数量级已经超出10的9次方规模
     * 而若将N分半来处理则不会超过10的9次方量级，2的18次方约等于26w，所以可以分半处理。
     * 前半部分使用18个数来求出所有累加和 %m 的结果，后半部分使用17个数来求出所有累加和 %m 的结果
     * 前、后半部分的结果都使用有序数组来记录，遍历左半部分，得到左半部分当前值item, 则只要右半部分求出 <= (m-item) 最近的值
     * 这样就可以求出每一组中 %m 后的值最大，最后将所有组结果求最大值即为答案
     * @param arr
     * @param m
     * @return
     */
    private static int getResult3(int[] arr, int m) {
        int n = arr.length;
        if (n == 1) {
            return arr[0] % m;
        }
        int mid = (n - 1) / 2;
        TreeSet<Integer> left = new TreeSet<>();
        process(arr, 0, 0, mid, m, left);
        TreeSet<Integer> right = new TreeSet<>();
        process(arr, mid + 1, 0, n - 1, m, right);

        int result = 0;
        for (Integer item : left) {
            result = Math.max(result, item + right.floor(m - 1 - item));
        }
        return result;
    }

    private static void process(int[] arr, int start, int sum, int end, int m, TreeSet<Integer> set){
        if(start ==end+1){
            set.add(sum%m);
        }else{
            // 不用当前值 arr[start]
            process(arr, start +1,sum,end,m, set);
            // 使用当前值 arr[start]
            process(arr, start +1,sum+ arr[start],end,m, set);
        }
    }

    /**
     * 该解法适用于m较小，数组长度N与所有数的累加sum的乘积，即dp表的总量超过 10 的 9 次方的情况下使用
     * 如 N 的量级为 10的6次方，sum为10的5次方，此时dp整体量级已经达到10的11次方，此时若使用 getResult1
     * 的方法求解则大概率会超时。此时需要换一种解法，尽量控制 dp 表的量级在10的9次方以内，此时可以从两外的条件
     * 入手，如 m ,假设m在10的平方以内，而dp[i][j]可以改成：在0...i的范围内自由选择可否凑出 %m 等于j，总体量级就已经控制
     * 在10的8次方以内
     * @param arr
     * @param m
     * @return
     */
    private static int getResult2(int[] arr, int m) {
        int n = arr.length;
        boolean[][] dp = new boolean[n][m];

        // 设置第一列全为 true, 即在 0...i 范围内不使用任何一个数即可凑出 %m 等于 0
        for (int i = 0; i < n; i++) {
            dp[i][0] = true;
        }

        // 第一行在对应位置设置true
        // 第一行只有(0,0)及(0,arr[i])的位置为true
        dp[0][arr[0]] = true;

        for (int i = 1; i < n; i++) {
            for (int j = 1; j < m; j++) {
                // 从0...i范围中凑出%m等于j，分几种情况
                // 1. 使用 arr[i], 则需要先算出 arr[i]%m (记作x) 的结果是否大于等于 j,
                //   1.1 若 x>=j 则需要从 0...i-1 范围中自由选择凑出 (j-x)即可，因为前面已经凑够x
                //       如 arr[4]%m=3, j=7, 则需要dp[4-1][7-3]=dp[3][4]=true
                //   1.2 若 x<j 如 m=9, arr[4]%m=8,而 j=3, 此时需要从0...3中凑出一个数a, 使得 (a+8)/9=3
                //       所以有 m+j-x, dp[4][3] = dp[3][m+j-x] = dp[3][4]
                // 2. 不使用 arr[i], 则需要从 0...i-1 范围中自由选择凑出j
                // 综合上面两种情况，若其中一种可以凑出结果j则为true

                // 若第一种情况可以直接凑出 arr[i]%m==j 则直接填true并继续下一轮计算
                int mod = arr[i] % m;
                if (mod >= j) {
                    dp[i][j] = dp[i - 1][j - mod] | dp[i - 1][j];
                } else {
                    dp[i][j] = dp[i - 1][m + j - mod] | dp[i - 1][j];
                }
            }
        }

        // 最后只需要在dp表最后一行中查找最后一个为true的值即为答案
        for (int i = m; i > -1; i--) {
            if (dp[n - 1][i]) {
                return i;
            }
        }
        return -1;
    }

    /**
     * 该钟解法只可使用在数组长度N与所有数的累加sum的乘积，即dp表的总量不能超过 10 的 9 次方的情况下使用
     * 在一般的笔试中，Java要求执行时间控制在2-4s, c/c++要求控制在1s , 表示程序在处理某一组数据量时
     * 整体的常数操作数量一定要控制在10的8次方到10的9次方之间，一旦超过10的9次方则将超出时间限制
     * @param arr
     * @param m
     * @return
     */
    private static int[] getResult1(int[] arr, int m) {
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
                // 确保索引不越界
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
