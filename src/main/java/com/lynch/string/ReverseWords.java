package com.lynch.string;

/**
 * @Author: linxueqi
 * @Description:
 * @Date: create in 2022/3/23 14:08
 */
public class ReverseWords {
    public static void main(String[] args) {

        String str = "I am a student";
        System.out.println("before reverse: "+ str);
        String reverse = reverse(str);
        System.out.println("after reverse: "+ reverse);
    }

    private static String reverse(String str){

        int n = str.length();
        char[] arr = str.toCharArray();
        int left = 0;
        int right = n-1;

        while (left < right){
            char ch = arr[left];
            arr[left] = arr[right];
            arr[right] = ch;
            left ++;
            right--;
        }
        return new String(arr);
    }
}
