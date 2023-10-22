package com.lynch.string;

import org.junit.Test;

/**
 * 给你一个只包含 '(' 和 ')' 的字符串，找出最长有效（格式正确且连续）括号子串的长度。
 *
 * 示例 1：
 * 输入：s = "(()"
 * 输出：2
 * 解释：最长有效括号子串是 "()"
 *
 * @author: lynch
 * @description:
 * @date: 2023/8/11 23:31
 */
public class LongestValidParentheses {
    @Test
    public void test(){
//        String str = "))()())";
//        String str = ")((()))(";
//        String str = "()(())";
        String str = "(()())";
        int maxValidLen = getMaxValidLen(str);
        System.out.println("max valid len: "+ maxValidLen);
    }

    public int getMaxValidLen(String str){
        int n = str.length();
        // dp[i] 表示截止 i 位置的有效括号数
        int[] dp = new int[n];
        int max = 0;
        for (int i = 1; i < n; i++) {
            char item = str.charAt(i);
            if (item == ')') {
                // 遇到右括号则查看其左边是否存在与之匹配的左括号
                if (str.charAt(i - 1) == '(') {
                    // 存储左括号则说明匹配 ()
                    // 但需考虑存在 i-1 前一个位置 i-2 的长度，如 ()()
                    // 若 i-2 之前是一个有效括号() 则仍需累加到最终有效长度中
                    dp[i] = i - 2 > 0 ? dp[i - 2] + 2 : 2;
                    max = Math.max(max, dp[i]);
                    continue;
                }
                // 查找最左边是否为左括号
                int index = i - dp[i - 1] - 1;
                if (index >= 0 && str.charAt(index) == '(') {
                    // (...)
                    dp[i] += dp[i - 1] + 2;
                    // 同样需考虑最左边匹配括号的前一个位置的有效括号长度
                    // 若 index 之前是一个有效括号() 则仍需累加到最终有效长度中
                    if (index - 1 > 0) {
                        dp[i] += dp[index - 1];
                    }
                    max = Math.max(max, dp[i]);
                }
            }
        }
        return max;
    }
}
