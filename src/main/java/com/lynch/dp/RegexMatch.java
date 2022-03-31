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
//        boolean match = match("aab",  "c*a*b");
//        boolean match2 = matchDP("aaabc".toCharArray(),  "a*b.c".toCharArray());
        boolean match2 = isMatch("aaabc",  "a*b.c");
        System.out.println("match: "+ match2);
    }

    private static boolean match(String source, String pattern) {
        return matchInternal(source.toCharArray(), 0, pattern.toCharArray(), 0);
    }

    /**
     *              0   1   2   3   4   5
     *        +---+---+---+---+---+---+---+
     *        |   | 0 | a | * | b | . | c |
     *        +---+---+---+---+---+---+---+
     *      0 | 0 | 1 | 0 | 1 | 0 | 0 | 0 |
     *      1 | a | 0 | 1 | 1 |   |   |   |
     *      2 | a | 0 |   |   |   |   |   |
     *      3 | a | 0 |   |   |   |   |   |
     *      4 | b | 0 |   |   |   |   |   |
     *      5 | c | 0 |   |   |   |   |   |
     *        +---+---+---+---+---+---+---+
     * @param source
     * @param pattern
     * @return
     */
    static boolean myMatch(String source, String pattern) {

        if (source == null || pattern == null) {
            return false;
        }
        int m = source.length();
        int n = pattern.length();
        char[] schars = source.toCharArray();
        char[] pchars = pattern.toCharArray();

        // dp[i][j] 表示source前i个字符与pattern前j个字符的匹配结果
        boolean[][] dp = new boolean[m + 1][n + 1];
        // 空字符与空模式串匹配
        dp[0][0] = true;

        // 注意 dp第0行第0列是空串，所以实际匹配需要从非空模式串开始
        for (int i = 0; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                // 当前字符是 * ，可匹配0次或多次
                if (pchars[j - 1] == '*') {
                    // 匹配 0 次，如 s="aa" p="aab*"
                    // 模式串是一个有效的模式串，所以出现*时必然成对出现，即 j>=2
                    if (j > 1) {
                        dp[i][j] |= dp[i][j - 2];
                    }
                    // 匹配 多次，如 s="aaa" p="a*"，即判断模式串前一个位置字符与source[i] 字符是否相同
                    // 相同则抵消，此时匹配结果就由source的前一个位置i-1字符匹配结果来决定
                    if (i > 0 && j > 1 && schars[i - 1] == pchars[j - 2] || pchars[j - 2] == '.') {
                        dp[i][j] |= dp[i - 1][j];
                    }
                    continue;
                }

                // 当前字符不是 *，则可能存在以下情况：
                // 1. 两个字符相同，相同则抵消，此时匹配结果就由两个字符串的前一个位置匹配结果来决定 dp[i-1][j-1]
                // 2. 模式串当前字符是 . 可任意匹配，此时匹配结果就由两个字符串的前一个位置匹配结果来决定 dp[i-1][j-1]
                if (i > 0 && schars[i - 1] == pchars[j - 1] || pchars[j - 1] == '.') {
                    dp[i][j] = dp[i - 1][j - 1];
                }
            }
        }
        return dp[m][n];
    }

    public static boolean isMatch(String source, String pattern) {

        int n = source.length();
        int m = pattern.length();
        char[] schars = source.toCharArray();
        char[] pchars = pattern.toCharArray();
        boolean[][] dp = new boolean[n + 1][m + 1];

        for (int i = 0; i <= n; i++) {
            for (int j = 0; j <= m; j++) {
                //分成空正则和非空正则两种
                if (j == 0) {
                    dp[i][j] = i == 0;
                    continue;
                }
                //非空正则分为两种情况 * 和 非*
                if (pchars[j - 1] != '*') {
                    // 若是正常字符，或者存在 . 则说明两个字符相同，结果取决于dp前一个位置的匹配结果 dp[i - 1][j - 1]
                    if (i > 0 && (schars[i - 1] == pchars[j - 1] || pchars[j - 1] == '.')) {
                        dp[i][j] = dp[i - 1][j - 1];
                    }
                } else {
                    // 存在 * ，则分为两种情况： 匹配多次，匹配 0 次
                    // 1. 匹配 0 次，直接跳过该位置，实际结果取决于模式串往前两个位置
                    if (j >= 2) {
                        dp[i][j] |= dp[i][j - 2];
                    }
                    // 2. 匹配多次，此时需要查看模式串前一个字符是否相同，相同则说明
                    //    可以匹配多个，最后的结果就取决于前面一个位置匹配的结果
                    if (i >= 1 && j >= 2 && (schars[i - 1] == pchars[j - 2] || pchars[j - 2] == '.')) {
                        dp[i][j] |= dp[i - 1][j];
                    }
                }
            }
        }
        return dp[n][m];
    }

    private static boolean matchDp(String source, String pattern) {

        int m = source.length();
        int n = pattern.length();
        char[] schars = source.toCharArray();
        char[] pchars = pattern.toCharArray();

        boolean[][] dp = new boolean[m + 1][n + 1];
        dp[0][0] = true;

        // 若以当前位置可否匹配来做判断则需要增加一个预处理：第0行空字符需要特殊处理
        // 因为双重for循环中没有都已将i=0的情况过滤

        for (int i = 0; i <= m; i++) {
            for (int j = 0; j <= n; j++) {
                // 当前位置可以匹配
                if (i > 0 && j > 0 && (schars[i - 1] == pchars[j - 1] || pchars[j - 1] == '.')) {
                    // 存在 * ，可以匹配多次或0次
                    if (pchars[j - 1] == '*') {
                        // 匹配多次
                        dp[i][j] = dp[i - 1][j];
                        if (j > 1) {
                            // 匹配0次
                            dp[i][j] |= dp[i][j - 2];
                        }
                    } else {
                        // 不存在 * 两者都是普通字符，相互抵消，结果由前一个位置决定
                        dp[i][j] = dp[i - 1][j - 1];
                    }
                } else {
                    // 当前位置不可以匹配，可能存在 *, s="ba" , p="a*"
                    // 存在 * ，仅匹配0次
                    if (j > 0 && i>1 && pchars[j - 1] == '*' && schars[i-2] == pchars[j-1]) {
                        dp[i][j] = dp[i - 1][j];
                    }
                }
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

        // 当前位置不能匹配 如 s = "aa", p = "b*"。
        // 同样需要判断 j 后面一个字符 j+1 是否是 *

        // 1. 若 pattern[j+1] == '*' 则说明可以匹配 0 次，只能跳过该部分模式串从 j+2 位置开始继续匹配
        if (j + 1 < n && pattern[j + 1] == '*') {
            return matchInternal(source, i, pattern, j + 2);
        }
        // 2. 若 pattern[j+1] ！= '*' 说明匹配不成功
        return false;
    }
}