package com.lynch.sliding_window;

import java.util.ArrayList;
import java.util.List;

/**
 * 给定一个字符串 Source 及一个字符串 Target， 请在Source中找出包含T所有字母的最小子串
 * Created by troub on 2021/12/20 16:58
 */
public class MinContainStr {
    public static void main(String[] args) {

    }

    private static char[] getMinStr(String source, String target){
        List<Integer> needs = new ArrayList<>();
        List<Integer> window = new ArrayList<>();
        for (char item : target.toCharArray()){
            needs.set(item -'0', 1);
        }

        int valid = 0;
        int left =0, right = 0, min = 0;

        while(right < source.length()){
            int index = source.charAt(right) - '0';
            right ++;

            final Integer exist = needs.get(index);
            if(exist != null && exist == 1){

                final Integer integer = window.get(index);
                if (integer==null){
                    window.set(index, 1);
                }else{
                    window.set(index, integer +1);
                }
                if(exist.equals(integer)) {
                    valid++;
                }
            }

            if(valid==target.length()){
                if(min > (right - left)){
                    min = right - left;
                }
                // 开始缩小范围
                left ++;

            }else{
                right ++;
            }
        }

        return null;
    }
}
