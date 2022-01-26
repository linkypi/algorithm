package com.lynch.sliding_window;

/**
 * 给定一个source及target字符串，求source中是否存在一个子串，
 * 包含T中所有字符且不包含其他字符
 */
public class FindSubStr {
    public static void main(String[] args) {
        String source = "hello world";
        String target = "rol";
        boolean result = find(source, target);
        System.out.print("result: " + result);
    }

    private static boolean find(String source, String target) {

        byte[] need = new byte[128];
        byte[] window = new byte[128];
        for (char c : target.toCharArray()) {
            need[c]++;
        }
        int left = 0, right = 0;
        int valid = 0;

        while (right < source.length()) {
            char current = source.charAt(right);

            if (need[current] > 0) {
                window[current]++;
                if (window[current] == need[current]) {
                    valid++;
                }
            }

            // 目标字符已经凑齐，开始缩小窗口
            while (valid == target.length()) {
                // 是有效字符则判断长度是否一致，一致则表面source包含target字符串中的所有字符
                if (right - left + 1 == target.length()) {
                    return true;
                }

                char item = source.charAt(left);
                // 是有效字符则判断有效数量是否需要更新，不是有效字符则继续缩小窗口（left++）
                if (need[item] > 0) {
                    if (window[item] == need[item]) {
                        valid--;
                    }
                    window[item]--;
                }
                left++;
            }
            right++;
        }
        return false;
    }
}
