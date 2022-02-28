package com.lynch.extern;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 给定一个不包含重复数字的数组，求其所有子集
 */
public class SubSet {
    public static void main(String[] args) {
        int[] arr = {1, 2, 3};
        List<List<Integer>> subSet2 = getSubSetByRecursion(arr, arr.length);
        print(subSet2);

        List<List<Integer>> subSet3 = getSubSetByBackTrack(arr);
        print(subSet3);
    }

    private static void print(List<List<Integer>> arr) {
        StringBuilder builder = new StringBuilder();
        if (arr == null || arr.size() == 0) {
            System.out.print(" arr is empty");
            return;
        }
        for (int i = 0; i < arr.size(); i++) {
            List<Integer> item = arr.get(i);
            String result = item.stream().map(Object::toString).collect(Collectors.joining(","));
            builder.append("[").append(result).append("]");
            if (i != arr.size() - 1) {
                builder.append(" , ");
            }
        }
        System.out.println("sub set result: " + builder.toString());
    }

    /**
     * 使用回溯方式求子集
     * @param arr
     */
    private static List<List<Integer>> getSubSetByBackTrack(int[] arr) {
//        Deque<Integer> track = new LinkedList<>();
        List<Integer> track = new ArrayList<>();
        List<List<Integer>> res = new ArrayList<>();
        backtrack(arr, 0, track, res);
        return res;
    }

    private static void backtrack(int[] arr, int start, List<Integer> track, List<List<Integer>> result) {

        result.add(new ArrayList<>(track));
        for (int i = start; i < arr.length; i++) {
            // 插入队尾
//            track.offer(arr[i]);
            track.add(arr[i]);
            backtrack(arr, i + 1, track, result);
            // 移除队尾元素
//            track.pollLast();
            // 若使用List存放arr[i] 则在移除元素时需要将其强制转换为 Object
            track.remove((Object)arr[i]);
        }
    }

    /**
     * 使用递归方式求解
     * @param arr
     * @param size
     * @return
     */
    private static List<List<Integer>> getSubSetByRecursion(int[] arr, int size) {
        if (arr == null || size == 0) {
            List<List<Integer>> objects = new ArrayList<>();
            objects.add( new ArrayList<>());
            return objects;
        }

        // 取出最后一个元素
        int last = arr[size - 1];
        // 获取前面集合的所有子集
        List<List<Integer>> subs = getSubSetByRecursion(arr, size - 1);

        // 使用最后元素last遍历与前面集合的所有子集累加
        // 如数组[1,2]的子集合为： [], [1], [2], [1,2]。 实际就是2之前的
        // 子集（[]与[1]）与 2之前的子集和2所组成新集合([2], [1,2])的并集
        // 如数组[1,2,3]的子集为：[],[1],[2],[1,2],[3],[1,3],[2,3],[1,2,3]
        // 实际就是3之前的子集([],[1],[2],[1,2]) 与 3之前的子集和3所组成新集合([3],[1,3],[2,3],[1,2,3])的并集
        int count = subs.size();
        for (int i = 0; i < count; i++) {
            List<Integer> item = subs.get(i);
            // 该步的时间复杂度为 O(N), 因为需要复制N个元素
            List<Integer> collect = new ArrayList<>(item);
            collect.add(last);
            // 数组长度为N的数组，其子集共有2的N次方个，即需要添加2的N次方次，
            // 每次添加都涉及到前面一步O(N)复杂度的集合复制，故该算法的时间复杂度为 O(2^N * N)
            subs.add(collect);
        }
        return subs;
    }
}
