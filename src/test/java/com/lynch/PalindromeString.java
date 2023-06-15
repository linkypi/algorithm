package com.lynch;

/**
 * @Author: linxueqi
 * @Description:
 * @Date: create in 2023/3/20 16:00
 */
public class PalindromeString {
    public static void main(String[] args) {
        String str = "anbuttuon";
//        String str = "cabac";
        String result = find(str);
        System.out.println("result: "+ result);
    }

    public static String find(String str) {
        if (str == null || str.length() == 0) {
            return "";
        }

        // dp[i][j] 表示从i到j之间的字符是否为回文子串
        // dp[i][j]是否为回文子串取决于 dp[i+1][j-1] 是否为回文以及 str[i] 是否与 str[j] 相同
        // 即 dp[i][j] = dp[i+1][j-1] && str[i] == str[j]
        // 但是对于只有一个及两个字符的情况需要特殊处理
        int n = str.length();
        boolean[][] dp = new boolean[n][n];

        //初始化每个字符都是回文串
        for (int i = 0; i < n; i++) {
            dp[i][i] = true;
        }

        // 由于dp[i][j]表示从第i个位置到第j个位置，则必然有 i<=j ，故只需遍历二维矩阵的右上角部分
        // 又因为 dp[i][j] 依赖于 dp[i+1][j-1] 即 dp[i][j] 依赖于其左下角位置的元素
        // 动态规划遍历的顺序就可以有两种：
        // 1. 从左往右一列一列遍历
        // 2. 从下往上一行一行遍历

        int maxLen = 0;
        int index = 0;
        // 1. 此种方式为从下往上一行一行遍历（每行从左到右）
//        for (int row = n - 1; row >= 0; row--) {
//            for (int i = row; i < n; i++) {
//                boolean equal = str.charAt(row) == str.charAt(i);
//                if (i - row < 2) {
//                    dp[row][i] = equal;
//                } else {
//                    dp[row][i] = dp[row + 1][i - 1] && equal;
//                }
//                if (dp[row][i] && maxLen < i - row + 1) {
//                    maxLen = i - row + 1;
//                    index = row;
//                }
//            }
//        }

        // 2. 此种方式为从左往右一列一列遍历（每列从上往下）
        for (int col = 0; col < n; col++) {
            for (int row = 0; row <= col; row++) {
                boolean equal = str.charAt(row) == str.charAt(col);
                if (col - row < 2) {
                    dp[row][col] = equal;
                } else {
                    dp[row][col] = dp[row + 1][col - 1] && equal;
                }
                if (dp[row][col] && maxLen < col - row + 1) {
                    maxLen = col - row + 1;
                    index = row;
                }
            }
        }
        return maxLen == 0 ? "" : str.substring(index, maxLen + index);
    }

















    public static String findMaxPalindromeStr(String str) {
        // 使用动态规划实现， dp[i][j] 表示从第i个字符到第j个字符为回文串(i<j)
        // dp[i][j] = dp[i+1][j-1] && str[i] == str[j]
        // 边界条件：
        //  1. 字符串长度为 1 即为回文子串

        if (str == null || str.length() == 0) {
            return "";
        }
        int n = str.length();
        boolean[][] dp = new boolean[n][n];
        for (int i = 0; i < n; i++) {
            dp[i][i] = true;
        }
        int maxLen = 0;
        int start = 0;
        for (int i = 1; i < n; i++) {
            for (int j = 0; j < i; j++) {

                if (str.charAt(i) == str.charAt(j)) {
                    // 处理只有两个字符的情况
                    if (i - j < 2) {
                        dp[i][j] = true;
                    } else {
                        dp[i][j] = dp[i + 1][j - 1];
                    }
                }
                if (dp[i][j] && maxLen < i - j + 1) {
                    maxLen = i - j + 1;
                    start = j;
                }
            }
        }
        return "";
    }
}
