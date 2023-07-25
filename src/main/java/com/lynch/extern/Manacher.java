package com.lynch.extern;

public class Manacher {
    public static void main(String[] args) {
        String test = "abfghgfmcbaxrrxabfghgfba";
        int count = getMaxCount(test);
        System.out.println("最大回文字符串长度为: "+ count);
    }

    private static char[] init(String str){
        StringBuilder builder = new StringBuilder("#");
        for (char item : str.toCharArray()){
            builder.append(item).append("#");
        }
        return builder.toString().toCharArray();
    }

    public static int getMaxCount(String str) {
        char[] target = init(str);
        int mostRight = -1;
        int center = -1;
        int[] arr = new int[target.length];
        int max = 0;

        for (int index = 0; index < target.length; index++) {
            // index 在 mostRight 内
            arr[index] = mostRight > index ? Math.min(arr[2 * center - index], mostRight - index) : 1;

            while (index + arr[index] < target.length && index - arr[index] > -1) {
                if (target[index + arr[index]] == target[index - arr[index]]) {
                    arr[index]++;
                } else {
                    break;
                }
            }

            if (index + arr[index] > mostRight) {
                mostRight = index + arr[index];
                center = index;
            }
            max = Math.max(max, arr[index]);
        }
        return max - 1;
    }
}
