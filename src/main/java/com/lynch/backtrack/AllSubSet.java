package com.lynch.backtrack;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * @Author: linxueqi
 * @Description:
 * @Date: create in 2023/8/18 15:54
 */
public class AllSubSet {

    @Test
    public void test() {
        int[] arr = {1, 2, 3};
        boolean[] used = new boolean[arr.length];
        findSubSet(arr, path, 0);

        System.out.println("all sub set: " + result);
    }

    @Test
    public void testNoRepeat() {
        int[] arr = {1, 2, 2, 3};
        Arrays.sort(arr);
        findSubSetNoRepeat(arr, path, 0);

        System.out.println("all sub set no repeat: " + result);

        List<List<Integer>> lists = subsetsWithDup2(arr);
        System.out.println("all sub set no repeat2: " + lists);
    }

    List<List<Integer>> res = new LinkedList<>();
    LinkedList<Integer> track = new LinkedList<>();

    public List<List<Integer>> subsetsWithDup2(int[] nums) {
        // 先排序，让相同的元素靠在一起
        Arrays.sort(nums);
        backtrack(nums, 0);
        return res;
    }

    void backtrack(int[] nums, int start) {
        // 前序位置，每个节点的值都是一个子集
        res.add(new LinkedList<>(track));

        for (int i = start; i < nums.length; i++) {
            // 剪枝逻辑，值相同的相邻树枝，只遍历第一条
            if (i > start && nums[i] == nums[i - 1]) {
                continue;
            }
            track.addLast(nums[i]);
            backtrack(nums, i + 1);
            track.removeLast();
        }
    }


    LinkedList<Integer> path = new LinkedList<>();
    List<List<Integer>> result = new LinkedList<>();

    /**
     * 因为求的所有子集，故与求排列不同
     * 1 2 3，所以第一个 1 与后面的所有数字进行组合
     * 然后轮到 2 与后面的数字进行组合，这样就防止了2再次与1进行组合，导致重复结果
     * 正因为如此，故该解法无需使用 used 数组
     * @param arr
     * @param path
     * @param start
     */
    public void findSubSet(int[] arr, LinkedList<Integer> path, int start) {

        result.add(new ArrayList<>(path));

        for (int i = start; i < arr.length; i++) {

            path.addLast(arr[i]);

            findSubSet(arr, path, i + 1);

            path.removeLast();
        }
    }

    public void findSubSetNoRepeat(int[] arr, LinkedList<Integer> path, int start) {
        result.add(new ArrayList<>(path));

        for (int i = start; i < arr.length; i++) {

            // 若出现重复则忽略
            if (i != start && arr[i] == arr[i - 1]) {
                continue;
            }

            path.addLast(arr[i]);

            findSubSetNoRepeat(arr, path, i + 1);
            path.removeLast();
        }
    }

    /**
     * ===========  代码随想录题解 ===========
     */
    List<List<Integer>> list = new ArrayList<>();// 存放符合条件结果的集合
    boolean[] used;
    public List<List<Integer>> subsetsWithDup(int[] nums) {
        if (nums.length == 0){
            list.add(path);
            return list;
        }
        Arrays.sort(nums);
        used = new boolean[nums.length];
        subsetsWithDupHelper(nums, 0);
        return list;
    }

    private void subsetsWithDupHelper(int[] nums, int startIndex){
        list.add(new ArrayList<>(path));
        if (startIndex >= nums.length){
            return;
        }
        for (int i = startIndex; i < nums.length; i++){
            if (i > 0 && nums[i] == nums[i - 1] && !used[i - 1]){
                continue;
            }
            path.add(nums[i]);
            used[i] = true;
            subsetsWithDupHelper(nums, i + 1);
            path.removeLast();
            used[i] = false;
        }
    }
}
