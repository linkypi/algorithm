package com.lynch.dp;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * https://leetcode.cn/problems/word-break/
 *
 * 给你一个字符串 s 和一个字符串列表 wordDict 作为字典。请你判断是否可以利用字典中出现的单词拼接出 s 。
 *
 * 注意：不要求字典中出现的单词全部都使用，并且字典中的单词可以重复使用。
 *
 * @Author: linxueqi
 * @Description:
 * @Date: create in 2023/6/8 9:57
 */
public class SplitWords {
    public static void main(String[] args) {
       String s = "leetcode";
       List<String> wordDict = Arrays.asList("leet", "dog", "code", "cat");
        boolean result = matchWithDp(s, wordDict);
        result = matchWithDp2(s, wordDict);
        System.out.println("result: "+ result);
    }

    /**
     * 使用动态规划方式实现
     * dp[i] 表示s字符串中前 i 个字符是否可以使用空格拆分成若干个 wordDict 字典中出现的单词
     * @param s
     * @param wordDict
     * @return
     */
    public static boolean matchWithDp(String s, List<String> wordDict) {
        if (s == null) {
            return false;
        }
        int n = s.length();
        // dp[i] 表示s字符串中前 i 个字符是否可以使用空格拆分成若干个 wordDict 字典中出现的单词
        // 而 s 整个字符串是否都在字典中出现只要 dp[i] 为 true, 及剩余的字符串在字典中出现即可
        boolean[] dp = new boolean[n + 1];
        // dp[0] 默认为 true, 若不为true，则后面无法匹配。可以理解成是为后面状态转移做的一个初始化，
        // 因为想要匹配 i 之后的字符串 substring (i,j) 是否在 wordDict 中，就需要先保证 dp [i] 要为 true，
        // 即 i 之前的内容是可以匹配的。如果你第一步就把 dp [0] 设为 false，那就是在说 i=0 之前的字符串无法匹配，
        // i 之后字符串也就没法继续匹配了，所以其实是为了让状态转移的公式保持一致性
        dp[0] = true;

        Set<String> set = new HashSet<>(wordDict);
        for (int i = 1; i <= n; i++) {
            for (int j = 0; j < i; j++) {
                if (dp[j] && set.contains(s.substring(j, i))) {
                    dp[i] = true;
                    break;
                }
            }
        }
        return dp[n];
    }

    public static boolean matchWithDp2(String s, List<String> wordDict) {
        if (s == null) {
            return false;
        }
        int n = s.length();
        // 以 0...i 结尾的字符串是否可以在字典表中拼凑出
        boolean[] dp = new boolean[n + 1];
        dp[0] = true;

        for (int i = 1; i <= n; i++) {
            for (String word : wordDict) {
                int len = word.length();
                // 只要确保 后面len个字符可以匹配到 word, 那当前 0-(i-1)的字符串
                // 是否可以从wordDict中拼接就取决于前半段是否可以从字典表中拼接即可
                if (i - len >= 0 && s.substring(i-len, i).equals(word)) {
                    dp[i] |= dp[i - len];
                }
            }
        }
        return dp[n];
    }
}
