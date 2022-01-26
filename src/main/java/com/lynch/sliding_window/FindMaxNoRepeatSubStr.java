package com.lynch.sliding_window;

/**
 * 给定一个字符串， 求该字符串不包含重复字符的最长子串长度
 */
public class FindMaxNoRepeatSubStr {
    public static void main(String[] args) {
         String source = "wearaxcsdasw";
        int max = find(source);
        System.out.print("max length: "+ max);
    }

    private static int find(String source) {
        int left = 0, right = 0;
        byte[] window = new byte[128];
        int[] pos = new int[128];
        int max = 0;

        while (right < source.length()) {
            char item = source.charAt(right);

            // 如果窗口已经存在该字符则缩小窗口
            if (window[item] == 1) {
                max = Math.max(right - left, max);
                left = Math.max(left, pos[item] + 1);
            }
            window[item] = 1;
            pos[item] = right;
            right++;
        }
        return max;
    }
    private static int find2(String source) {
        int left = 0, right = 0;
        byte[] window = new byte[128];
        int max = 0;

        while (right < source.length()) {
            char item = source.charAt(right++);
            window[item]++;

            while (window[item] > 1) {
                char x = source.charAt(left++);
                window[x]--;
            }
            max = Math.max(max, right - left + 1);
        }
        return max;
    }
}
