package com.lynch.dp;

/**
 * 给你一个字符串s和一个字符规律p，请你来实现一个支持 '.'和'*'的正则表达式匹配。
 * '.' 匹配任意单个字符, '*' 匹配零个或多个前面的那一个元素
 * 所谓匹配，是要涵盖整个字符串s 的，而不是部分字符串。
 * @Author: linxueqi
 * @Description:
 * @Date: create in 2022/3/30 16:23
 */
public class RegexMatch {
    public static void main(String[] args) {
//        boolean match = match("aa", "a*");
        boolean match = match("aab",  "c*a*b");
        System.out.println("match: "+ match);
    }

    private static boolean match(String source, String pattern) {
        return matchInternal(source.toCharArray(), 0, pattern.toCharArray(), 0);
    }

    private static boolean matchWithDp(String source, String pattern) {
        int m = source.length();
        int n = pattern.length();

        char[] sarr = source.toCharArray();
        char[] parr = pattern.toCharArray();

        boolean[][] dp = new boolean[m + 1][n + 1];

        for (int i = 0; i <= m; i++) {
            for (int j = 0; j <= n; j++) {
                // base case
                if (j == n) {
                    dp[i][j] = i != m;
                    continue;
                }
                if (i == m) {
                    if ((n - j) % 2 == 1) {
                        return false;
                    }
                    for (int k = j + 1; k < n; k += 2) {
                        if (parr[k] != '*') {
                            return false;
                        }
                    }
                    return true;
                }

                // 当前位置可以匹配
                if (sarr[i] == parr[j] || parr[j] == '.') {
                    // 判断模式串的下一个字符是否是 * , 若存在则分两种情况
                    if (j + 1 <= n && parr[j + 1] == '*') {
                        // 存在 *
                        // 1. 匹配多次 如 s="aaa", p="a*" , 即 i 继续向前匹配， j 位置字符保持
                        // 2. 匹配 0 次，如 s="ab", p="a*c", 即 i 位置保持不变，从 * 的下一个字符开始，即 j+2
                        dp[i][j] = dp[i + 1][j] || dp[i][j + 2];
                        continue;
                    }

                    // 不存在 * , 如 s="abx", p="ac" , 只能匹配下一个字符
                    dp[i][j] = dp[i + 1][j + 1];
                    continue;
                }

                // 当前位置不可以匹配， 则需要判断模式串后面是否存在 *
                if(j+1<=n && parr[j+1]=='*'){
                    // 存在* 则匹配 0 次， 如 s="ab", p="b*ab"
                    dp[i][j] = dp[i][j + 2];
                    continue;
                }
                dp[i][j] = false;
            }
        }
        return dp[m][n];
    }

    private static boolean matchInternal(char[] source, int i, char[] pattern, int j) {

        int m = source.length;
        int n = pattern.length;
        // base case
        // 模式串已匹配完成，字符串可否匹配取决于 i 是否已走完
        if (j == n) {
            return m == i;
        }

        // 字符串已匹配完成，但是模式串还有剩余，接下来就只需要知道 j...n-1 位置能否匹配空串即可
        if (i == m) {
            // 能够匹配空串的模式串一定是成对出现，如 a*, a*x*
            if ((n - j) % 2 != 0) {
                return false;
            }

            for (int k = j + 1; k < n; k += 2) {
                if (pattern[k] != '*') {
                    return false;
                }
            }
            return true;
        }

        // 当前位置可以匹配
        if (source[i] == pattern[j] || pattern[j] == '.') {
            // pattern[j+1] 存在 * 字符 s="aa", p="a*"
            if (j + 1 < n && pattern[j + 1] == '*') {
                // 1. 匹配多次，s="aa", p="a*"
                // 2. 匹配0次，s="aa", p="a*aa"
                return matchInternal(source, i + 1, pattern, j) ||
                        matchInternal(source, i, pattern, j + 2);
            }
            // pattern[j+1] 不存在 * 字符 s="aa", p="a."
            return matchInternal(source, i + 1, pattern, j + 1);
        }

        // 当前位置不能匹配 如 s = "aa", p = "b*aa"。
        // 同样需要判断 j 后面一个字符 j+1 是否是 *

        // 1. 若 pattern[j+1] == '*' 则说明可以匹配 0 次，只能跳过该部分模式串从 j+2 位置开始继续匹配
        if (j + 1 < n && pattern[j + 1] == '*') {
            return matchInternal(source, i, pattern, j + 2);
        }
        // 2. 若 pattern[j+1] ！= '*' 说明匹配不成功
        return false;
    }
}