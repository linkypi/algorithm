package com.lynch.dp;

/**
 * 给定两个字符串str1, str2, 求 str1 转为 str2 最少需要多少步操作
 * 可供选择的操作有三种：插入，删除，替换
 */
public class MinDistance {
    public static void main(String[] args) {
//        String str1 = "rad";
//        String str2 = "apple";
        String str1 = "";
        String str2 = "a";
        int minDistance = getMinDistance(str1, str2);
        System.out.print("min distance: "+ minDistance);
    }

    static int minDistance(String source, String target) {
        source = source == null ? "" : source;
        target = target == null ? "" : target;

        if (source.length() == 0) {
            return target.length();
        }
        if (target.length() == 0) {
            return source.length();
        }

        int m = source.length();
        int n = target.length();
        char[] arr1 = source.toCharArray();
        char[] arr2 = target.toCharArray();

        // dp[i][j] 表示 source字符串从起始位置到i位置的字符 转变成
        // target从起始位置到j位置字符所需要的最小编辑次数
        int[][] dp = new int[m + 1][n + 1];

        for (int i = 0; i < m + 1; i++) {
            dp[i][0] = i;
        }
        for (int j = 0; j < n + 1; j++) {
            dp[0][j] = j;
        }

        for (int i = 1; i < m + 1; i++) {
            for (int j = 1; j < n + 1; j++) {
                if (arr1[i] == arr2[j]) {
                    dp[i][j] = dp[i - 1][j - 1];
                    continue;
                }
                // 当两个位置的字符不相同时就存在三种可能：

                // 1. 插入，开始时 s="ab", t="abc" ，在s后面插入一个字符 c 后使得两个字符相同
                // 字符相同后就可以互相抵消（操作一次，操作次数为 1），剩下s="ab" , t="ab"
                // 此时剩下的操作次数就应该是 s 从0...i 及 t 从 0...j-1 中获取，即 dp[i][j-1],
                // 综上有：dp[i][j] = dp[i][j-1] + 1
                // 换个角度理解，在s尾部插入一个字符等同于在t尾部删除一个字符 c
                // 所以 dp[i][j] 可以理解为 s从 0...i 位置的字符与 t 从 0...j-1 位置的字符相同，
                // 此时只要 t 一个字符即可，即 dp[i][j] = dp[i][j-1] + 1 （加 1 即表示当前插入操作）

                // 2. 删除，开始时 s="asbx", t="asb" ，在 s 后面删除字符 x 使得两者相同。
                // 字符相同后就可以互相抵消（操作一次，操作次数为 1），剩下s="asb" , t="asb"
                // 此时剩下的操作次数就应该是 s 从 0...i-1 及 t 从 0...j 中获取，即 dp[i-1][j],
                // 综上有：dp[i][j] = dp[i-1][j] + 1
                int min = Math.min(dp[i][j - 1], dp[i - 1][j]);

                // 3. 替换，开始时 s="axe", t="axb" ，在 s 后面使用 b 替换字符 e 使得两者相同.
                // 字符相同后就可以互相抵消（操作一次，操作次数为 1），剩下s="ax" , t="ax"
                // 此时剩下的操作次数就应该是 s 从 0...i-1 及 t 从 0...j-1 中获取，即 dp[i-1][j-1],
                // 综上有：dp[i][j] = dp[i-1][j-1] + 1
                min = Math.min(min, dp[i - 1][j - 1]);
                dp[i][j] = min;
            }
        }
        return dp[m][n];
    }

    /**
     * 将字符串 source 转为 target 最少需要多少步操作
     * @param source
     * @param target
     * @return
     */
    private static int getMinDistance(String source, String target) {
        int m = source.length();
        int n = target.length();
        int[][] dp = new int[m + 1][n + 1];

        for (int i = 0; i <= m; i++) {
            dp[i][0] = i;
        }
        for (int j = 0; j <= n; j++) {
            dp[0][j] = j;
        }

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (source.charAt(i-1) == target.charAt(j-1)) {
                    // 两者相同说明最小距离只能从剩余的字符串操作中获取到
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    // 比较三种操作中最小的编辑距离，取三者最小
                    // 1. dp[i-1][j] 表示删除一个字符
                    // 2. dp[i-1][j-1] 表示替换一个字符，如 str1="rad", target="apple",
                    //    将 str1[i] 替换为 target[j] 使得他们两个字符相同，即 "rae" 与 "apple"
                    //    然后两者的指针继续向前比较，所以有 dp(rad, apple) = dp(ra, appl) + 1
                    //    即 ： dp[i][j] = dp[i-1][j-1] + 1
                    // 3. dp[i][j-1] 表示插入一个字符，如 str1="rad", target="apple",
                    //    在str1后面插入一个字符 "e" 使得str1 与 target 当前指针的字符相同，即 "rade" 与 "apple"
                    //    然后两者的指针继续向前比较，所以有 dp(rad, apple) = dp(rad, appl) + 1
                    //    即 ： dp[i][j] = dp[i][j-1] + 1
                    int min = Math.min(dp[i - 1][j], dp[i - 1][j - 1]);
                    min = Math.min(min, dp[i][j - 1]);
                    dp[i][j] = min + 1;
                }
            }
        }
        return dp[m][n];
    }
}
