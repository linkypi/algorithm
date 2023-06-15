package com.lynch;

import java.util.Arrays;

/**
 * 查询无重复最长子串
 * @Author: linxueqi
 * @Description:
 * @Date: create in 2023/3/27 17:31
 */
public class MaxNoRepeatStr {
    public static void main(String[] args) {

//        String max = find("auxabcdx");
        String max = find2("auxabcdx");
//        String max = find("abxnamuxo");
        System.out.println("max: "+ max);
    }

    public static String find(String str) {
        if (str == null || str.equals("")) {
            return "";
        }

        int left = 0;
        int right = 0;

        int n = str.length();

        int[] arr = new int[128];
        Arrays.fill(arr, -1);

        int maxLen = Integer.MIN_VALUE;
        int start = 0;
        // 基于滑动窗口完成：
        // 1. 右指针先右移指针来扩大窗口
        // 2. 当遇到重复字符时再右移左指针来缩小窗口
        // 3. 最后统计当前窗口大小
        while (right < n) { // abcabx
            char item = str.charAt(right);
            int index = item - '0';
            if (arr[index] != -1) {
                start = arr[index];
            }

            int len = right - start + 1;
            maxLen = Math.max(len, maxLen);
            arr[index] = right;

            right++;
        }
        return str.substring(start, start + maxLen);
    }

    public static String find2(String str){
        // 用于存放某个字符出现的位置
        int[] map = new int[128];
        Arrays.fill(map, -1);
        int max = 0;
        int start = 0;

        int current = 0;
        for(char item: str.toCharArray()){
            int index = item - 'a';
            if(map[index] != -1){
                start = map[index] + 1;
                max = Math.max(current - map[index], max);
            }

            map[index] = current;
            current ++;
        }
        return str.substring(start, max + start);
    }
}
