package com.lynch;

import org.junit.Test;

import java.util.Arrays;

/**
 * @author: leo
 * @description:
 * @ClassName: com.lynch
 * @date: 2023/12/19 15:01
 */
public class BuildArr {

    /**
     * 输入一个 int 类型的值 N, 构造一个长度为 N 的数组并返回
     * 要求: 对于任意的 i < k < j, 都满足 arr[i] + arr[j] != 2 * arr[k]
     */
    @Test
    public void test() {

        for (int i = 1; i < 20; i++) {
            int[] result = build(i);
            System.out.println(Arrays.toString(result));
            isValid(result);
        }
    }

    private int[] build(int n) {
        if (n == 1) {
            return new int[]{1};
        }
        int half = (n + 1) / 2;
        int[] base = build(half);

        int[] ans = new int[n];
        int index = 0;
        for (; index < half; index++) {
            ans[index] = 2 * base[index] + 1;
        }
        for (int i = 0; index < n; index++, i++) {
            ans[index] = 2 * base[i];
        }
        return ans;
    }

    private void isValid(int[] arr){
        int n = arr.length;
        for (int i = 0; i < n; i++) {
            for (int k = i+1; k < n; k++) {
                for (int j = k+1; j < n; j++) {
                    if(arr[i] + arr[j] == 2* arr[k]){
                        System.err.println("is invalid arr: " + Arrays.toString(arr));
                        return;
                    }
                }
            }
        }
        System.out.println("arr is valid.");
    }
}
