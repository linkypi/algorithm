package com.lynch.string;

/**
 * 给定两个字符串，求他们的最长公共子串
 * @author: linxueqi
 * @Description:
 * @createTime: 2022/3/25 23:42
 */
public class LongestCommonSubstring {
    public static void main(String[] args) {
        String str1 = "anrsetc";
        String str2 = "miqsetx";
        String result = find(str1, str2);
        System.out.println("longest common substr: "+ result);
    }

    /**
     * 将动态规划精简为从上往下的斜对角线进行遍历
     * @param str1
     * @param str2
     * @return
     */
    private static String find(String str1, String str2) {
        if ((str1 == null || str1.length() == 0) || str2 == null || str2.length() == 0) {
            return "";
        }

        char[] arr1 = str1.toCharArray();
        char[] arr2 = str2.toCharArray();

        int len1 = arr1.length;
        int len2 = arr2.length;
        int row = 0;
        int col = len2 - 1;

        int max = 0;
        // 记录str1最长子串的终止位置
        int end = 0;

        while (row < len1) {

            int i = row;
            int j = col;
            int len = 0;
            // 计算每一行斜线
            while (i < len1 && j < len2) {
                // 不断对比下方对角的字符是否相同
                if (arr1[i++] == arr2[j++]) {
                    len++;
                    end = i;
                    max = Math.max(len, max);
                }
            }
            // 每行斜线计算完成后继续计算下一行
            col--;
            // 当列已到达边界则换下一行继续
            if (col < 0) {
                row++;
                col = 0;
            }
        }
        return max > 0 ? str1.substring(end - max, end) : "";
    }
}
