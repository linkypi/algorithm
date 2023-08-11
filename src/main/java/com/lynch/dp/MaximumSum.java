package com.lynch.dp;

import org.junit.Test;

/**
 * 给你一个整数数组，返回它的某个 非空 子数组（连续元素）在执行一次可选的删除操作后，
 * 所能得到的最大元素总和。换句话说，你可以从原数组中选出一个子数组，并可以决定要不要
 * 从中删除一个元素（只能删一次哦），（删除后）子数组中至少应当有一个元素，
 * 然后该子数组（剩下）的元素总和是所有子数组之中最大的。
 *
 * 注意，删除一个元素后，子数组 不能为空。
 *
 * 示例 1：
 *
 * 输入：arr = [1,-2,0,3]
 * 输出：4
 * 解释：我们可以选出 [1, -2, 0, 3]，然后删掉 -2，这样得到 [1, 0, 3]，和最大。
 *
 * @Author: linxueqi
 * @Description:
 * @Date: create in 2023/8/11 18:19
 */
public class MaximumSum {

    @Test
    public void test(){
       int[] arr = {1, -2, 0, 3};
        int res = find(arr);
        System.out.println("res: "+ res);
    }

    /**
     * 假设：
     *    1. f(i)表示前 i 个元素不删除元素时的累加和
     *    2. g(i)表示前 i 个元素删除一个元素后的累加和
     * 那么必定有：
     *    1. f(i) = max( f(i-1) + arr[i], arr[i] )
     *    2. g(i) = max( g(i-1) + arr[i], f(i-1) ) 即前 i 个元素删除一个元素时的累加和
     *    取决于前 i-1 个元素删除一个加上当前元素所得的和 与 前 i-1个 元素一个也不删除的累加和 中的最大值
     *    g(i)也可理解为 删除第i个元素的累加和 与 删除前 i-1 个元素的累加和 之间的最大值
     * @param arr
     * @return
     */
    public int find(int[] arr){

        if(arr == null || arr.length ==0){
            return 0;
        }

        int n = arr.length;
        int[] f = new int[n];
        int[] g = new int[n];
        f[0] = arr[0];
        g[0] = -1000000007;
        int max = arr[0];
        for (int i = 1; i < arr.length; i++) {
            f[i] = Math.max(f[i-1] + arr[i], arr[i]);
            g[i] = Math.max(g[i-1] + arr[i], f[i-1]);
            int tmp = Math.max(f[i], g[i]);
            max = Math.max(tmp, max);
        }
        return max;
    }

}
