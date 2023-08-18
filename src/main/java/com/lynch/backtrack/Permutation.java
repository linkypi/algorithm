package com.lynch.backtrack;

import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @Author: linxueqi
 * @Description:
 * @Date: create in 2023/8/18 15:53
 */
public class Permutation {

    @Test
    public void test(){
        int[] arr = {1,2,3};
        boolean[] used = new boolean[arr.length];
        findPermutation(arr, path, used);
        System.out.println("permutation: "+ result);
    }

    LinkedList<Integer> path = new LinkedList<>();
    List<List<Integer>> result = new LinkedList<>();

    public void findPermutation(int[] arr, LinkedList<Integer> path, boolean[] used){
        if(path.size() == arr.length){
            result.add(new ArrayList<>(path));
            return;
        }

        for (int i = 0; i < arr.length; i++) {
            if(used[i]){
                continue;
            }

            used[i] = true;
            path.addLast(arr[i]);

            findPermutation(arr, path, used);

            used[i] = false;
            path.removeLast();
        }
    }

}
