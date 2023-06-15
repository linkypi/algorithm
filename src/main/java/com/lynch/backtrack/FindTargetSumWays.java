package com.lynch.backtrack;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * 给你一个整数数组 nums 和一个整数 target 。
 * 向数组中的每个整数前添加 '+' 或 '-' ，然后串联起所有整数，可以构造一个 表达式 ：
 * 例如，nums = [2, 1] ，可以在 2 之前添加 '+' ，在 1 之前添加 '-' ，然后串联起来得到表达式 "+2-1" 。
 * 返回可以通过上述方法构造的、运算结果等于 target 的不同 表达式 的数目。
 *给你一个整数数组 nums 和一个整数 target 。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode.cn/problems/target-sum
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @Author: linxueqi
 * @Description:
 * @Date: create in 2023/6/9 14:45
 */
public class FindTargetSumWays {
    public static void main(String[] args) {

        int[] nums = {1, 1, 1, 1, 1};
        int target = 3;
        visited = new boolean[nums.length];
        LinkedList<String> ops = new LinkedList<>();

        backTrace(nums, target, 0, visited, 0, ops);
        System.out.println("result: "+ count);

        int withDp = findWithDp(nums, target);
        System.out.println("");
    }

    public static int count = 0;
    public static boolean[] visited = null;
    // 统计表达式
    public static List<List<String>> operates = new ArrayList<>();

    /**
     * 使用回溯的方式求解
     * @param nums
     * @param target
     * @param sum
     * @param visited
     * @param current
     * @param ops
     */
    public static void backTrace(int[] nums, int target, int sum,
                                 boolean[] visited, int current, LinkedList<String> ops) {

        if (current == nums.length) {
            if (sum == target) {
                operates.add(new ArrayList<>(ops));
                count++;
            }
            return;
        }

        if (visited[current]) {
            return;
        }

        // 此处的for循环可以去除，将代码平铺改为直接计算两种运算符的结果
//        for (int x = 0; x < 2; x++) {
//            visited[current] = true;
//            if (x == 0) { // 加
//                ops.add("+");
//                backTrace(nums, target, sum + nums[current], visited, current + 1, ops);
//                ops.removeLast();
//            } else {   // 减
//                ops.add("-");
//                backTrace(nums, target, sum - nums[current], visited, current + 1, ops);
//                ops.removeLast();
//            }
//            visited[current] = false;
//        }

        visited[current] = true;

        ops.add("+");
        backTrace(nums, target, sum + nums[current], visited, current + 1, ops);
        ops.removeLast();

        ops.add("-");
        backTrace(nums, target, sum - nums[current], visited, current + 1, ops);
        ops.removeLast();

        visited[current] = false;
    }

    /**
     * 使用 dp 求解
     * 假设 sum 为 nums 数组元素的总和， P 为 nums 数组中的正数集合， N 为其中的负数集合，
     * 则有 P + N = target, P - N = sum
     * 那么 P + N + P - N = target + P - N 即 2P = target + sum => P = (target+sum)/2
     * 由于 target + sum 是通过公式推导得来 故需确保 target + sum 必须大于 0 同时必须是2的倍数才有效
     * 那么原题意就可以转换为在 nums 数组中选出总和为 P 的总方法数，也即 01背包问题
     * @return
     */
    public static int findWithDp(int[] nums, int target) {
        int sum = 0;
        int n = nums.length;
        for (int num : nums) {
            sum += num;
        }

        // 此处需要注意 由于 target + sum 是通过公式推导得来
        // 故需确保 target + sum 必须大于0 同时必须是2的倍数才有效
        sum = target + sum;
        if (sum < 0 || (sum & 1) != 0) {
            return 0;
        }
        int p = (target + sum) / 2;

        // 回归到 01背包问题求解，使用一维压缩数组求解（若不好理解，可以先从二维数组求解）
        int[] dp = new int[p + 1];
        dp[0] = 1;
        for (int num : nums) {
            for (int j = p; j > 0; j--) {
                dp[j] = dp[j] + dp[j - num];
            }
        }
        return dp[p];
    }
}
