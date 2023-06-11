package com.lynch.string;

/**
 * 给定两个字符串，求将两个字符串变得相同的最少删除次数
 * @author: linxueqi
 * @Description:
 * @createTime: 2022/3/26 10:46
 */
public class DeleteTwoStrMinDistance {
    public static void main(String[] args) {
        String str1 = "sea";
        String str2 = "eat";
        int count = find(str1, str2, 0, 0);
        int count2 = findWithDp(str1, str2);
        System.out.println("result: " + count);
    }

    private static int find(String str1, String str2, int i, int j) {
        if (i > str1.length() - 1) {
            return str2.length() - j;
        }
        if (j > str2.length() - 1) {
            return str1.length() - i;
        }
        if (str1.charAt(i) == str2.charAt(j)) {
            return find(str1, str2, i + 1, j + 1);
        }
        // 删除str1[i]
        int i1 = find(str1, str2, i + 1, j);
        // 删除str2[j]
        int i2 = find(str1, str2, i, j + 1);
        // 两个字符不相同 则执行删除操作
        return Math.min(i1, i2) + 1;
    }

    private static int findWithDp(String str1, String str2) {
        if (str1 == null || str1.length() == 0) {
            return 0;
        }
        if (str2 == null || str2.length() == 0) {
            return 0;
        }

        char[] arr1 = str1.toCharArray();
        char[] arr2 = str2.toCharArray();

        int len1 = str1.length();
        int len2 = str2.length();
        int[][] dp = new int[len1+1][len2+1];

        for(int i=0;i<len1;i++){
            dp[i][0] = i;
        }
        for(int i=0;i<len2;i++){
            dp[0][i] = i;
        }

        // 通过递归求解得知，dp[i][j] 依赖与 dp[i][j+1]， dp[i+1][j] 及 dp[i+1][j+1]
        // 三个位置信息，也即其右下方的元素，所以dp遍历就可以从右下方开始往上遍历
        for (int i = len1 - 1; i > -1; i--) {
            for (int j = len2 - 1; j > -1; j--) {
                if (arr1[i] == arr2[j]) {
                    dp[i][j] = dp[i + 1][j + 1];
                    continue;
                }
//                if (i + 1 > len1 - 1) {
//                    dp[i][j] = len2 - j;
//                    continue;
//                }
//                if (j + 1 > len2 - 1) {
//                    dp[i][j] = len1 - i;
//                    continue;
//                }
                dp[i][j] = Math.min(dp[i + 1][j], dp[i][j + 1]) + 1;
            }
        }

//        for (int i = 0; i < len1; i++) {
//            for (int j = 0; j < len2; j++) {
//                if (i > len1 - 1) {
//                    dp[i][j] = len2 - j;
//                    continue;
//                }
//                if (j > len2 - 1) {
//                    dp[i][j] = len1 - i;
//                    continue;
//                }
//                if (arr1[i] == arr2[j]) {
//                    dp[i][j] = dp[i + 1][j + 1];
//                    continue;
//                }
//                dp[i][j] = Math.min(dp[i + 1][j], dp[i][j + 1]) + 1;
//            }
//        }
        return dp[0][0];
    }
}
