package com.lynch;

import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * https://leetcode.cn/problems/bitwise-and-of-numbers-range/description/
 *
 * 给你两个整数 left 和 right ，表示区间 [left, right] ，返回此区间内所有数字 按位与 的结果（包含 left 、right 端点）。
 *
 * 示例 1：
 * 输入：left = 5, right = 7
 * 输出：4
 *
 * @Author: linxueqi
 * @Description:
 * @Date: create in 2023/8/16 14:57
 */
public class RangeBitwiseAnd {
    @Test
    public void test(){
        int result = calc(5, 7);
        result = calc(1, 2147483647);

        result = numDecodings("1234");

        int[] arr = {1,2,3};
        LinkedList<Integer> path = new LinkedList<>();
        boolean[] used = new boolean[arr.length];

        backTrack(arr, path,  used);

        System.out.println("result: "+ result);
    }

    List<List<Integer>> result = new ArrayList<>();
    public void backTrack(int[] arr, LinkedList<Integer> path, boolean[] used) {

        result.add(new ArrayList<>(path));

        if (path.size() == arr.length) {
            return;
        }

        for (int i = 0; i < arr.length; i++) {
            if (used[i]) {
                continue;
            }
            path.addLast(arr[i]);
            used[i] = true;
            backTrack(arr, path, used);
            used[i] = false;
            path.removeLast();
        }
    }

    public int numDecodings(String s) {
        int n = s.length();
        s = " " + s;
        char[] cs = s.toCharArray();
        int[] f = new int[n + 1];
        f[0] = 1;
        for (int i = 1; i <= n; i++) {
            // a : 代表「当前位置」单独形成 item
            // b : 代表「当前位置」与「前一位置」共同形成 item
            int a = cs[i] - '0', b = (cs[i - 1] - '0') * 10 + (cs[i] - '0');
            // 如果 a 属于有效值，那么 f[i] 可以由 f[i - 1] 转移过来
            if (1 <= a && a <= 9)
                f[i] = f[i - 1];
            // 如果 b 属于有效值，那么 f[i] 可以由 f[i - 2] 或者 f[i - 1] & f[i - 2] 转移过来
            if (10 <= b && b <= 26)
                f[i] += f[i - 2];
        }
        return f[n];
    }


    public int calc(int m, int n){
        // 因为int 最高位为符号位，故只能是 1 左移 30 位，而非左移 31 位
        int mask = 1 << 30;

        int asw = 0;
        while(mask > 0 && (mask & m) == (mask & n)){
            asw |= (mask & m);
            mask >>= 1;
        }
        return asw;
    }

}
