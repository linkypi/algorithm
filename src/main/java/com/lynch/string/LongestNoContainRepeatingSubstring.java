package com.lynch.string;

import java.util.HashMap;

/**
 * 求给定字符串的最长不含重复字符的子串，注意是不含重复字符的最长子串
 * @author: linxueqi
 * @Description:
 * @createTime: 2022/3/23 10:46
 */
public class LongestNoContainRepeatingSubstring {
    public static void main(String[] args) {

//        int count = lengthOfLongestSubstring("abcabcbb");
//        int count = lengthOfLongestSubstring("pwwkew");
//        int count = lengthOfLongestSubstring(" ");
        int count = lengthOfLongestSubstring("abba");
//        int count = lengthOfLongestSubstring("dvdf");
//        int count = lengthOfLongestSubstring("tmmzuxt");
        System.out.println("result: "+ count);
    }

    public static int lengthOfLongestSubstring2(String s) {
        HashMap<Character,Integer> map = new HashMap<>();
        int start = -1;
        int max = 0;
        for(int i=0;i<s.length();i++){
            char x = s.charAt(i);
            if(map.containsKey(x)){
                start = Math.max(start,map.get(x));
            }
            map.put(x,i);
            max = Math.max(max,i-start);
        }
        return max;
    }

    /**
     * 使用滑动窗口处理
     * @param s
     * @return
     */
    private static int lengthOfLongestSubstring(String s) {
        int[] map = new int[128];
        char[] arr = s.toCharArray();
        int n = s.length();

        for (int i = 0; i < 128; i++) {
            map[i] = -1;
        }

        int right = 0;
        int left = 0;
        int max = 0;
        for (int i = 0; i < n; i++) {
            int mapIndex = arr[i] - ' ';
            if (map[mapIndex] == -1) {
                // 字符不存在则扩大窗口
                map[mapIndex] = i;
                // 判断窗口长度
                int len = right - left + 1;
                max = Math.max(len, max);
            } else {
                // 字符已存在, 取出之前字符出现的位置
                int index = map[mapIndex];
                // 如重复字符的位置落在窗口内则缩小窗口
                if (index >= left) {
                    max = Math.max(right - index, max);
                    left = index + 1;
                }else{
                    // 如重复字符的位置不落在窗口内则直接计算当前窗口的长度即可
                    max = Math.max(right - left + 1, max);
                }
                // 重置字符出现的最新位置
                map[mapIndex] = i;
            }
            right++;
        }
        return max;
    }

}
