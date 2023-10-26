package com.lynch.regex;

import org.junit.Test;

/**
 * https://leetcode.cn/problems/regular-expression-matching/description/
 *
 * 给你一个字符串 s 和一个字符规律 p，请你来实现一个支持 '.' 和 '*' 的正则表达式匹配。
 *
 * '.' 匹配任意单个字符
 * '*' 匹配零个或多个前面的那一个元素
 * 所谓匹配，是要涵盖 整个 字符串 s 的，而不是部分字符串。
 *
 * @Author: linxueqi
 * @Description:
 * @Date: create in 2023/10/25 15:22
 */
public class RegexMatch {
    @Test
    public void test() {
        boolean success = match("abc", "ab*c");
        success = match("abbc", "ab*c");
        success = match("ac", "ab*c");
        success = match("ac", "ab*c");
        success = match("c", ".*c");
        success = match("vc", ".*c");

        System.out.println("result: " + success);
    }

    private boolean match(String source, String pattern) {
        return dp(source, pattern, 0, 0);
    }

    private boolean dp(String source, String pattern, int i, int j) {
        // 处理边界
        if (i == source.length() - 1 && j == pattern.length() - 1) {
            return source.charAt(i) == pattern.charAt(j) || pattern.charAt(j) == '.';
        }

        // 原始字符串已经匹配完成，但是模式串 pattern 有可能还未匹配结束
        if (i == source.length()) {
            // 此时仅需判断模式串 后面的字符串是否可匹配 0 次即可
            for (int k = j + 1; k < pattern.length(); k += 2) {
                if (pattern.charAt(k) != '*') {
                    return false;
                }
            }
            return true;
        }

        // 原始字符串未匹配完成，但模式串已匹配完成
        if (j == pattern.length()) {
            return false;
        }

        // 当前字符相同，或者遇到任意字符串 ‘.’ 则继续匹配
        if (source.charAt(i) == pattern.charAt(j) || pattern.charAt(j) == '.') {
            return dp(source, pattern, i + 1, j + 1);
        }

        // 若当前字符不相同
        if (pattern.charAt(j) == '*') {

            // 匹配0次
            // s = "acx"
            // p = "ab*cx"
            if (dp(source, pattern, i, j)) {
                return true;
            }


            // 匹配一次
            // s = "abc"
            // p = "ab*c"
            if (source.charAt(i) == pattern.charAt(j + 1)) {
                return dp(source, pattern, i + 1, j + 2);
            }

            // 匹配多次
            // s = "abbbc"
            // p = "ab*c"
            while (source.charAt(i) == pattern.charAt(j - 1) || pattern.charAt(j - 1) == '.') {
                i++;
            }
            return dp(source, pattern, i, j + 1);

        }

        return false;
    }

    // 翻译成java代码
    public static class Main {
        public static void main(String[] args) {
            System.out.println(match("a*b", "aab")); // 匹配 "a*b" 和 "aab"
            System.out.println(match("a*b", "aaab")); // 匹配 "a*b" 和 "aaab"
            System.out.println(match("a*b", "ab")); // 不匹配 "a*b" 和 "ab"
            System.out.println(match("a*b", "")); // 不匹配 "a*b" 和 ""
            System.out.println(match("a*b", "a")); // 不匹配 "a*b" 和 "a"
        }

        /**
         * 判断文本是否匹配给定的模式。
         *
         * @param pattern 模式
         * @param text    文本
         * @return 如果文本匹配模式，则返回true；否则返回false
         */
        public static boolean match(String pattern, String text) {
            int m = pattern.length();
            int n = text.length();
            boolean[][] dp = new boolean[m + 1][n + 1];

            dp[0][0] = true; // 初始化dp数组
            for (int i = 1; i <= m; i++) {
                if (pattern.charAt(i - 1) == '*') {
                    dp[i][0] = dp[i - 2][0]; // '*'匹配零个字符
                }
            }

            for (int i = 1; i <= m; i++) {
                for (int j = 1; j <= n; j++) {
                    if (pattern.charAt(i - 1) == '.' || pattern.charAt(i - 1) == text.charAt(j - 1)) {
                        dp[i][j] = dp[i - 1][j - 1]; // 当前字符匹配
                    } else if (pattern.charAt(i - 1) == '*') {
                        dp[i][j] = dp[i - 2][j] || (dp[i][j - 1] && (pattern.charAt(i - 2) == '.' || pattern.charAt(i - 2) == text.charAt(j - 1))); // '*'匹配零个或多个字符
                    }
                }
            }

            return dp[m][n]; // 返回结果
        }
    }

}
