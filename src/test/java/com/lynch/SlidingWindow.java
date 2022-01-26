package com.lynch;

import java.util.Timer;

/**
 * Description algorithm
 * Created by troub on 2021/12/28 16:58
 */
public class SlidingWindow {
    public static void main(String[] args) {
           String source = "ADOBECODEBANC";
           String target = "ABC";
           int result = process(source,target);
           System.out.println("min window length: "+ result);

    }

    private static int process(String source, String target) {
        int count = target.length();
        int left = 0, right = 0;

        int[] map = new int[128];
        StringBuilder window = new StringBuilder("");
        for (char item : target.toCharArray()) {
            map[item]++;
        }

        int size = target.length();
        int minLength = Integer.MAX_VALUE;
        while (right < source.length()) {
            final char current = source.charAt(right);
            window.append(current);
            right++;

            if(map[current]>0){
                count --;
            }

            map[current]--;
            // 已全部包含所有target字符 则缩小窗口
            if (count == 0) {
                while(left<=right && map[current] <0){
                    map[current]++;
                    left ++;
                }
            }
        }
        return minLength;
    }
}
