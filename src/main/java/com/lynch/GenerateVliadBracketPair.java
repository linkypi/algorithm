package com.lynch;

import java.util.ArrayList;
import java.util.List;

/**
 * https://leetcode.cn/problems/generate-parentheses
 *
 * 数字 n 代表生成括号的对数，请你设计一个函数，用于能够生成所有可能的并且 有效的 括号组合。
 *
 * 示例 1：
 *   输入：n = 3
 *   输出：["((()))","(()())","(())()","()(())","()()()"]
 *
 * @Author: linxueqi
 * @Description:
 * @Date: create in 2023/3/23 10:32
 */
public class GenerateVliadBracketPair {
    public static void main(String[] args) {
//         gen("",0,0, 3);
        gen2("", 0, 0, 3);
        System.out.println("result: " + result);
    }

    private static List<String> result = new ArrayList<>();

    /**
     * @param paths
     * @param left  左括号数量，每次增加一个左括号就加一
     * @param right 右括号数量，每次增加一个右括号就加一
     * @param n
     */
    public static void gen(String paths, int left, int right, int n) {
        // 最终符合条件的是：
        // 1. 左括号数不能大于 n
        // 2. 右括号数不能大于 n
        // 3. 右括号数不能大于左括号数 或者 左括号数不能大于右括号数
        if (left > n || right > n || right > left) {
            return;
        }
        if (paths.length() == 2 * n) {
            result.add(paths);
            return;
        }

        gen(paths + "(", left + 1, right, n);
        gen(paths + ")", left, right + 1, n);
    }

    public static void gen2(String paths, int left, int right, int n) {
        // 最终符合条件的是：
        // 1. 左括号数不能大于 n
        // 2. 右括号数不能大于 n
        // 3. 右括号数不能大于左括号数 或者 左括号数不能大于右括号数
        if (left > n || right > n || left > right) {
            return;
        }
        if (paths.length() == 2 * n) {
            result.add(paths);
            return;
        }

        gen(paths + "(", left + 1, right, n);
        gen(paths + ")", left, right + 1, n);
    }
}
