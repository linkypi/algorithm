package com.lynch.tools;

public class Generator {
    public static int[] generateRandomArr(int maxSize, int maxvalue) {
        int[] arr = new int[(int) ((maxSize + 1) * Math.random())];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) ((maxvalue + 1) * Math.random() - (int) (maxvalue * Math.random()));
        }
        return arr;
    }
}
