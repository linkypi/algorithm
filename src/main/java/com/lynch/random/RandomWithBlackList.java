package com.lynch.random;

import java.util.*;

/**
 * leetcode 710 黑名单中的随机数：
 * 给定一个整数 n 和一个 无重复 黑名单整数数组blacklist。
 * 设计一种算法，从 [0, n - 1] 范围内的任意整数中选取一个
 * 未加入黑名单blacklist的整数。任何在上述范围内且不在黑
 * 名单blacklist中的整数都应该有 同等的可能性 被返回。
 *
 * 优化你的算法，使它最小化调用语言 内置 随机函数的次数。
 *
 * 实现Solution类:
 *
 * Solution(int n, int[] blacklist)初始化整数 n 和被加入黑名单blacklist的整数
 * int pick()返回一个范围为 [0, n - 1] 且不在黑名单blacklist 中的随机整数
 *
 * @author: linxueqi
 * @Description:
 * @createTime: 2022/4/17 10:07
 */
public class RandomWithBlackList {
    public static void main(String[] args) {

    }

    public static final class Solution{
        List<Integer> list = null;
        int size = 0;
        Random random = null;

        public Solution(int n, int[] blacklist) {
            random = new Random();
            list = new ArrayList<>(n);

            // 找出黑名单中范围在 [0,n-1]的元素
            Map<Integer, Integer> map = new HashMap<>();
            for (int j : blacklist) {
                if (j < n) {
                    map.put(j, 1);
                }
            }
            // 记录有效数据长度
            size = n - map.size();
            // list 数组前部分用于存放有效数据
            for (int i = 0; i < n; i++) {
                if(!map.containsKey(i)) {
                    list.add(i);
                }
            }

            for(Integer key: map.keySet()){
                list.add(key);
            }
        }

        public int pick(){
            return list.get(random.nextInt(size));
        }
    }
}
