package com.lynch.dp;

/**
 * 给定两个字符串str1, str2, 求 str1 转为 str2 最少需要多少步操作
 * 可供选择的操作有三种：插入，删除，替换
 */
public class MinOperations {
    public static void main(String[] args) {
//        String str1 = "rad";
//        String str2 = "apple";
        String str1 = "";
        String str2 = "a";
        int minDistance = getMinDistance(str1, str2);
        System.out.print("min distance: "+ minDistance);
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
