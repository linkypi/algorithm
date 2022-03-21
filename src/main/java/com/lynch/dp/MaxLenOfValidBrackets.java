package com.lynch.dp;

/**
 * 给定一串又左括号及右括号组成的字符
 * 求其中有效括号的最长长度
 * @author: linxueqi
 * @Description:
 * @createTime: 2022/3/21 20:53
 */
public class MaxLenOfValidBrackets {
    public static void main(String[] args) {
        String str = "(((()()))((())";
        int len = find(str);
        int len2 = findOptimize(str);
        System.out.println("len: "+ len +", len2: "+ len2);
    }

    /**
     * 错误版本
     * @param str
     * @return
     */
    private static int find(String str) {
        int n = str.length();
        // dp[i] 表示到位置 i 位置可以匹配到有效括号的最大长度
        int[] dp = new int[n];

        int max = 0;
        char[] arr = str.toCharArray();
        for (int i = 1; i < n; i++) {

            // 左括号直接忽略，仅处理右括号
            if (arr[i] == ')') {
                if (arr[i - 1] == '(') {
                    // 如果右括号的前一个就是左括号则说明这是一个小的有效括号
                    // 但是在前一个位置 i-1 之前可能还存在有效的括号，所以需要加上 pre
                    dp[i] = dp[i - 1] + 2;
                } else if (dp[i - 1] > 0) {
                    int preLen = dp[i - 1];
                    int preIndex = i - preLen - 1;
                    // 若前一个位置查找到的有效括号长度大于0 则需要检查在 dp[i-1]
                    // 长度位置之前一个位置是否存在左括号可以与之匹配
                    if (arr[preIndex] == '(') {
                        dp[i] = dp[i - 1] + 2;
                        if (preIndex > 0) {
                            dp[i] += dp[preIndex - 1];
                        }
                    }
                }
            }
            max = Math.max(max, dp[i]);
        }
        return max;
    }

    /**
     * 优化版本
     * @param str
     * @return
     */
    private static int findOptimize(String str) {
        int n = str.length();
        // dp[i] 表示到位置 i 位置可以匹配到有效括号的最大长度
        int[] dp = new int[n];

        char[] arr = str.toCharArray();
        int max = 0;
        for (int i = 1; i < n; i++) {
            // 左括号直接忽略，仅处理右括号
            if (arr[i] == '(') {
                continue;
            }

            int preIndex = i - dp[i - 1] - 1;
            if (preIndex >= 0 && arr[preIndex] == '(') {
                dp[i] = dp[i - 1] + 2;
                // 需要考虑在有效范围前相邻的位置还可能存在其他有效范围, 如 ()(())
                if (preIndex > 0) {
                    dp[i] += dp[preIndex - 1];
                }
            }
            max = Math.max(dp[i], max);
        }
        return max;
    }

}
