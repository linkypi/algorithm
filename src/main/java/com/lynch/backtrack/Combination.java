package com.lynch.backtrack;

import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * https://leetcode.cn/problems/combinations/
 *
 * 给定两个整数 n 和 k，返回范围 [1, n] 中所有可能的 k 个数的组合。
 * 你可以按 任何顺序 返回答案。
 *
 * 示例 1：
 * 输入：n = 4, k = 2
 * 输出：
 * [
 *   [2,4],
 *   [3,4],
 *   [2,3],
 *   [1,2],
 *   [1,3],
 *   [1,4],
 * ]
 *
 * @Author: linxueqi
 * @Description:
 * @Date: create in 2023/8/18 15:33
 */
public class Combination {

    List<List<Integer>> result = new ArrayList<>();
    LinkedList<Integer> path = new LinkedList<>();

    @Test
    public void test() {
        int n = 3;
        int k = 2;
        combine(1, n, k);
        System.out.println("combine result: " + result);
    }

    /**
     * @param start
     * @param n
     * @param k
     */
    public void combine(int start, int n, int k) {
        if (path.size() == k) {
            result.add(new ArrayList<>(path));
            return;
        }

        for (int i = start; i <= n; i++) {
            path.addLast(i);
            combine(i + 1, n, k);
            path.removeLast();
        }
    }

}
