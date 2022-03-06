package com.lynch.dp;

/**
 * 给定一个字符串求最长不重复子串的长度
 */
public class LongestNoRepeatStr {
    public static void main(String[] args) {
        String str = "abcwerkglsaurclms";
        int maxLen = getMaxLen(str);
        int maxLen2 = getMaxLen2(str);
        System.out.println("maxLen: "+ maxLen+ ", maxLen2: "+ maxLen2);
    }

    private static int getMaxLen(String str) {
        if (str == null || str.length() == 0) {
            return 0;
        }

        char[] arr = str.toCharArray();
        // 使用数组来记录每个字符最近一次出现的位置，下标为字符与开始字符'a'的差值，value为字符出现的位置
        int[] posMap = new int[26];
        // 初始位置全部为 -1
        for (int i = 0; i < 26; i++) {
            posMap[i] = -1;
        }

        int n = arr.length;
        // 记录首个字符位置
        posMap[arr[0] - 'a'] = 0;
        int preSize = 1;
        int max = 1;
        int start = 0, end = 0;
        int preStart = 0, preEnd = 0;
        for (int i = 1; i < n; i++) {
            // 查找当前字符最近一次出现的位置
            int span = arr[i] - 'a';
            int index = posMap[span];
            // 此处需要考虑两种情况：
            // 1. 当前字符所在位置与最近一次出现的位置的差值即为当前不重复子串的长度，记作 dp[i]，即第i位置下的最长不重复子串长度
            // 2. 但是第一种情况包含的子串中可能存在重复字符，即此时当前位置下的最长不重复子串长度应该取决与
            //   其前一个位置的最长不重复子串长度，所以有dp[i]的长度应该为 dp[i-1]
            // 综合两种情况即得当前位置的最长不重复子串长度应该为前面两种情况中的较小值
            preSize = Math.min(i - index, preSize + 1);
            max = Math.max(preSize, max);
            posMap[span] = i;
        }
        System.out.println("start: " + start + ", end: " + end);
        return max;
    }

    /**
     * 使用滑动窗口求解
     * 窗口不断扩大，在遇到相同字符时，记录当前子串长度
     * 并移动左侧指针到最近一次出现相同字符的位置
     * @param str
     * @return
     */
    private static int getMaxLen2(String str) {
        if (str == null || str.length() == 0) {
            return 0;
        }

        char[] arr = str.toCharArray();
        int[] pos = new int[26];
        int n = arr.length;
        for (char c : arr) {
            pos[c - 'a'] = -1;
        }

        //记录滑动窗口左侧指针
        int left = 0;
        int max = 0;
        for (int i = 0; i < n; i++) {
            int index = arr[i] - 'a';
            // 若当前位置字符已经出现过，则移动左指针到
            // 最近一次出现相同字符的位置，即left尽量大
            if (pos[index] != -1) {
                left = Math.max(left, pos[index]);
            }
            max = Math.max(max, i - left);
            pos[index] = i;
        }
        return max;
    }
}
