package com.lynch.extern;

import java.util.HashMap;
import java.util.Map;

/**
 * 给定一个数组, 有 n 个元素, 问最多有多少个不重叠的非空区间,
 * 使得每个区间内数字的异或和等于 0
 */
public class MostXor {
    public static void main(String[] args) {
//        int[] arr = new int[]{3,2,1,4,0,1,2,3};
        int[] arr = new int[]{3,2,1,4,3,1,6};
        int count = getMaxCount(arr);
        System.out.println(count);
    }

    private static int getMaxCount(int[] arr){
        // 用于记录异或和及其所对应的位置下标
        Map<Integer,Integer> map = new HashMap<>();
        int xor = 0;

        map.put(0,-1);
        int[] dp = new int[arr.length];
        for(int index = 0;index<arr.length;index++) {
            xor ^= arr[index];
            if (map.containsKey(xor)) {
                int pre = map.get(xor);
                dp[index] = pre == -1 ? 1 : (dp[pre] + 1);
            }
            if (index > 0) {
                dp[index] = Math.max(dp[index - 1], dp[index]);
            }
            map.put(xor, index);
        }

        return dp[arr.length-1];
    }
}
