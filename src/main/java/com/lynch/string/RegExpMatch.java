package com.lynch.string;

import org.junit.Test;

/**
 * @author leo
 * @ClassName ExpressionMatch
 * @description: TODO
 * @date 11/3/23 10:01 AM
 */
public class RegExpMatch {
    @Test
    public void test() {
//        String source = "";
//        String pattern = "a*";
//        String source = "ab";
//        String pattern = ".*";

        String source = "mississippi";
        String pattern = "mis*is*p*.";
//        String source = "aa";
//        String pattern = "a";
//        String source = "aa";
//        String pattern = "a*";
        boolean match = isMatch(source, pattern);
        boolean match2 = isMatch2(source, pattern);
        System.out.println("match: " + match);
    }

    /**
     * 解答错误: 306 / 355 个通过的测试用例
     * @param source
     * @param pattern
     * @return
     */
    public boolean isMatch(String source, String pattern) {

        source = " " + source;
        pattern = " " + pattern;
        int m = source.length();
        int n = pattern.length();
        char[] sArr = source.toCharArray();
        char[] pArr = pattern.toCharArray();

        // 定义 dp[i][j] 表示 source 长度为 i, pattern 长度为 j 时的匹配结果
        boolean[][] dp = new boolean[m][n];
        // 空字符可以匹配
        dp[0][0] = true;
        if (n > 2 && pArr[2] == '*') {
            dp[0][1] = true;
        }
        // 处理特殊情况, source 为空字符, pattern 非空
        // 此时需要考虑 pattern 中是否存在 *
        for (int i = 2; i < n; i += 2) {
            if (pArr[i] == '*') {
                dp[0][i] = dp[0][i - 2];
            }
        }

        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                if ((sArr[i] == pArr[j] || pArr[j] == '.')) {
                    dp[i][j] = dp[i - 1][j - 1];
                }
                else if (pArr[j] == '*') {
                    // dp[i][j-2] 匹配 0 次
                    // dp[i-1][j] 匹配多次
                    dp[i][j] = dp[i][j - 2] || dp[i - 1][j];
                }
            }
        }

        return dp[m-1][n-1];
    }

    public boolean isMatch2(String source, String pattern) {

        source = " " + source;
        pattern = " " + pattern;
        int m = source.length();
        int n = pattern.length();
        char[] sArr = source.toCharArray();
        char[] pArr = pattern.toCharArray();

        // 定义 dp[i][j] 表示 source 长度为 i, pattern 长度为 j 时的匹配结果
        boolean[][] dp = new boolean[m][n];
        // 空字符可以匹配
        dp[0][0] = true;
        if (n > 2 && pArr[2] == '*') {
            dp[0][1] = true;
        }
        // 处理特殊情况, source 为空字符, pattern 非空
        // 此时需要考虑 pattern 中是否存在 *
        for (int i = 2; i < n; i += 2) {
            if (pArr[i] == '*') {
                dp[0][i] = dp[0][i - 2];
            }
        }

        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                if ((sArr[i] == pArr[j] || pArr[j] == '.')) {
                    dp[i][j] = dp[i - 1][j - 1];
                }
                else if (pArr[j] == '*') {
                    // 当前匹配字符是 * 时 需考虑两种情况
                    // 1. s当前 i 位置字符是否与 p 的 j-1 位置字符相同, 若相同则可以匹配任意次
                    // 2. 否则 dp[i][j] 的结果只能由匹配 0 次时的结果来决定
                    if ((sArr[i] == pArr[j-1] || pArr[j-1] == '.')) {
                        // dp[i][j-2] 匹配 0 次
                        // dp[i-1][j] 匹配多次
                        dp[i][j] = dp[i][j - 2] || dp[i - 1][j];
                    }else{
                        dp[i][j] = dp[i][j - 2];
                    }
                }
            }
        }

        return dp[m-1][n-1];
    }
}
