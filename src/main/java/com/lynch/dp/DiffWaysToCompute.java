package com.lynch.dp;

import java.util.ArrayList;
import java.util.List;

/**
 * https://leetcode-cn.com/problems/different-ways-to-add-parentheses/
 *
 * 给你一个由数字和运算符组成的字符串 expression ，按不同优先级组合数字和运算符，
 * 计算并返回所有可能组合的结果。你可以 按任意顺序 返回答案
 *
 * 输入：expression = "2-1-1"
 * 输出：[0,2]
 * 解释：
 * ((2-1)-1) = 0
 * (2-(1-1)) = 2
 *
 * @Author: linxueqi
 * @Description:
 * @Date: create in 2022/4/28 15:15
 */
public class DiffWaysToCompute {

    public static void main(String[] args) {
        String input = "2*3-4*5";
        List<Integer> list = diffWaysToCompute(input);
        List<Integer> list2 = diffWaysToCompute2(input);
        System.out.println("");
    }

    public static List<Integer> diffWaysToCompute(String input) {
        if (input.length() == 0) {
            return new ArrayList<>();
        }
        List<Integer> result = new ArrayList<>();
        int num = 0;
        //考虑是全数字的情况
        int index = 0;
        while (index < input.length() && !isOperation(input.charAt(index))) {
            num = num * 10 + input.charAt(index) - '0';
            index++;
        }
        //将全数字的情况直接返回
        if (index == input.length()) {
            result.add(num);
            return result;
        }

        for (int i = 0; i < input.length(); i++) {
            //通过运算符将字符串分成两部分
            if (isOperation(input.charAt(i))) {
                List<Integer> result1 = diffWaysToCompute(input.substring(0, i));
                List<Integer> result2 = diffWaysToCompute(input.substring(i + 1));
                //将两个结果依次运算
                for (int j = 0; j < result1.size(); j++) {
                    for (int k = 0; k < result2.size(); k++) {
                        char op = input.charAt(i);
                        result.add(calculate(result1.get(j), op, result2.get(k)));
                    }
                }
            }
        }
        return result;
    }

    public static List<Integer> diffWaysToCompute2(String input) {
        List<Integer> numList = new ArrayList<>();
        List<Character> opList = new ArrayList<>();
        char[] array = input.toCharArray();
        int num = 0;
        for (int i = 0; i < array.length; i++) {
            if (isOperation(array[i])) {
                numList.add(num);
                num = 0;
                opList.add(array[i]);
                continue;
            }
            num = num * 10 + array[i] - '0';
        }
        numList.add(num);
        int N = numList.size(); // 数字的个数

        // 一个数字
        ArrayList<Integer>[][] dp = (ArrayList<Integer>[][]) new ArrayList[N][N];
        for (int i = 0; i < N; i++) {
            ArrayList<Integer> result = new ArrayList<>();
            result.add(numList.get(i));
            dp[i][i] = result;
        }
        // 2 个数字到 N 个数字
        for (int n = 2; n <= N; n++) {
            // 开始下标
            for (int i = 0; i < N; i++) {
                // 结束下标
                int j = i + n - 1;
                if (j >= N) {
                    break;
                }
                ArrayList<Integer> result = new ArrayList<>();
                // 分成 i ~ s 和 s+1 ~ j 两部分
                for (int s = i; s < j; s++) {
                    ArrayList<Integer> result1 = dp[i][s];
                    ArrayList<Integer> result2 = dp[s + 1][j];
                    for (int x = 0; x < result1.size(); x++) {
                        for (int y = 0; y < result2.size(); y++) {
                            // 第 s 个数字下标对应是第 s 个运算符
                            char op = opList.get(s);
                            result.add(calculate(result1.get(x), op, result2.get(y)));
                        }
                    }
                }
                dp[i][j] = result;

            }
        }
        return dp[0][N - 1];
    }


    private static int calculate(int num1, char c, int num2) {
        switch (c) {
            case '+':
                return num1 + num2;
            case '-':
                return num1 - num2;
            case '*':
                return num1 * num2;
        }
        return -1;
    }

    private static boolean isOperation(char c) {
        return c == '+' || c == '-' || c == '*';
    }


}
