package com.lynch.backtrack;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * @Author: linxueqi
 * @Description:
 * @Date: create in 2023/8/18 15:53
 */
public class Permutation {

    /**
     * 给定一个不含重复数字的数组 nums ，返回其 所有可能的全排列 。你可以 按任意顺序 返回答案。
     */
    @Test
    public void test() {
        int[] arr = {1,2,3};
        boolean[] used = new boolean[arr.length];

        Arrays.sort(arr);
        findPermutation(arr, path, used);
        System.out.println("permutation: " + result);
    }

    /**
     *  输入一个可包含重复数字的序列 nums，请你写一个算法，返回所有可能的全排列
     *  数组元素可以重复，但是不可重复选择相同的元素
     */
    @Test
    public void testRepeatNum() {
        int[] arr = {1, 2, 2};  // [ [1,2,2],[2,1,2],[2,2,1] ]

        boolean[] used = new boolean[arr.length];
        Arrays.sort(arr);
        LinkedList<Integer> path = new LinkedList<>();
        findPermutationUnDuplicated(arr, path, used);
        System.out.println("permutation: " + result);
    }

    LinkedList<Integer> path = new LinkedList<>();
    List<List<Integer>> result = new LinkedList<>();

    /**
     * 排列中的元素不存在重复元素，不可复选
     *
     * @param arr
     * @param path
     * @param used
     */
    public void findPermutation(int[] arr, LinkedList<Integer> path, boolean[] used) {
        if (path.size() == arr.length) {
            result.add(new ArrayList<>(path));
            return;
        }

        for (int i = 0; i < arr.length; i++) {
            if (used[i]) {
                continue;
            }

            used[i] = true;
            path.addLast(arr[i]);

            findPermutation(arr, path, used);

            used[i] = false;
            path.removeLast();
        }
    }


    List<List<Integer>> result2 = new LinkedList<>();

    public List<List<Integer>> findPermutationUnDuplicated(int[] arr, LinkedList<Integer> path, boolean[] used) {
        if (path.size() == arr.length) {
            result2.add(new ArrayList<>(path));
            return result2;
        }

        for (int i = 0; i < arr.length; i++) {
            if (used[i]) {
                continue;
            }
            /**
             * [1,2,2'] 的标准全排列应该为
             *  [
             *     [1,2,2'],[1,2',2],
             *     [2,1,2'],[2,2',1],
             *     [2',1,2],[2',2,1]
             *  ]
             *  但是 [1,2,2'] 与 [1,2',2] 已经重复，只能算作一个排列
             *  为此为了保证不出现重复，解决的方法是：保证相同元素在排列中的相对位置保持不变
             *  所以 2' 必须是在其前一个元素 2 已使用的情况下才可以继续，如此便保证了相对位置的顺序
             */
            if (i > 0 && arr[i] == arr[i - 1] && !used[i - 1]) {
                continue;
            }

            used[i] = true;
            path.addLast(arr[i]);

            findPermutationUnDuplicated(arr, path, used);

            used[i] = false;
            path.removeLast();
        }
        return result2;
    }

}
