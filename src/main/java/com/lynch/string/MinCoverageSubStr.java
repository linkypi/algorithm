package com.lynch.string;

/**
 * https://leetcode.cn/problems/minimum-window-substring/
 *
 * 给你一个字符串 s 、一个字符串 t 。返回 s 中涵盖 t 所有字符的最小子串。
 * 如果 s 中不存在涵盖 t 所有字符的子串，则返回空字符串 ""
 * 注意：
 *   对于 t 中重复字符，我们寻找的子字符串中该字符数量必须不少于 t 中该字符数量。
 *   如果 s 中存在这样的子串，我们保证它是唯一的答案。
 *
 * @Author: linxueqi
 * @Description:
 * @Date: create in 2023/3/17 10:58
 */
public class MinCoverageSubStr {
    public static void main(String[] args) {
        String source = "ADOBECODEBANC";
        String target = "ABC";
        String min = findMin(source, target);
        System.out.println("min window: " + min);

    }

    public static String findMin(String source, String target) {

        int size = target.length();
        int[] needed = new int[128];
        for (int i = 0; i < size; i++) {
            needed[target.charAt(i)] += 1;
        }

        int min = Integer.MAX_VALUE;
        int minIndex = 0;
        int left = 0, right = 0;
        while (right < source.length()) {
            char c = source.charAt(right);
            if (needed[c] > 0) {
                size--;
            }
            needed[c]--;
            if (size == 0) {
                // 开始收缩窗口，从左边开始收缩
                while (needed[source.charAt(left)] < 0) {
                    needed[source.charAt(left)]++;
                    left++;
                }
                // 累计最小覆盖长度
                if (right - left + 1 < min) {
                    min = right - left + 1;
                    minIndex = left;
                }

                // 开始统计下一个窗口
                needed[source.charAt(left)]++;
                size++;
                left++;
            }
            right++;
        }

        return min == Integer.MAX_VALUE ? "" : source.substring(minIndex, min + minIndex);
    }

    public static String findMinWindow(String str, String target) {
        int size = target.length();
        int[] cache = new int[128];

        // 记录字符出现个数
        for (int i = 0; i < size; i++) {
            cache[target.charAt(i)] = 1;
        }

        int start = 0;
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < str.length(); i++) {
            char item = str.charAt(i);
            // 首次进入
            if (cache[item] > 0 && size == target.length()) {
                start = i;
                cache[item] --;
                size --;
            }else if(cache[item] > 0){
//                cache[item] --;
                size --;
            }
            if(size == 0){
                min = Math.min(min,i-start+1);
                start = i+1;
                size = target.length();
            }
        }
        return str.substring(start, min);
    }
}
