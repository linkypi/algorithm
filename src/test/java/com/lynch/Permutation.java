package com.lynch;

import java.util.*;

/**
 * 实现全排列
 * @Author: linxueqi
 * @Description:
 * @Date: create in 2023/3/21 15:54
 */
public class Permutation {

    public static void main(String[] args) {
        int[] arr = {1, 2, 3};
        List<List<Integer>> permute = permute(arr);
    }

    public static List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        List<Integer> list = new ArrayList<>();
        travers(nums, new HashMap<>(), result, list);
        return result;
    }

    public static void travers(int[] nums, Map<Integer, Integer> used, List<List<Integer>> result, List<Integer> list) {
        if(nums.length == list.size()){
            result.add(new ArrayList<>(list));
            return;
        }
        for (int i = 0; i < nums.length; i++) {
            if (used.containsKey(nums[i])) {
                continue;
            }
            used.put(nums[i], 1);
            list.add(nums[i]);
            travers(nums, used, result, list);
            used.remove(nums[i]);
            list.remove(list.size() - 1);
        }
    }
}
