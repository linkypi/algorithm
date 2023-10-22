package com.lynch;

import org.junit.Test;

/**
 * @Author: linxueqi
 * @Description:
 * @Date: create in 2023/8/14 13:59
 */
public class KMP_1 {

    @Test
    public void test() {
        String source = "abgabeabeabxgt";
        String match = "abeabx";
        int index = find(source.toCharArray(), match.toCharArray());
        System.out.println("index: " + index);
    }

    public int find(char[] source, char[] match) {

        int m = source.length;
        int n = match.length;
        if (m == 0 || n == 0) {
            return -1;
        }

        int[] next = getNext(match);
        int i = 0, j = 0;
        while (i < m && j < n) {
            if (j == -1 || source[i] == match[j]) {
                i++;
                j++;
            } else {
                j = next[j];
            }
        }

        if (j >= n) {
            return n - i > 0 ? (n - i) : (i - n);
        }
        return -1;
    }

    public int[] getNext(char[] arr) {
        int len = arr.length;
        if (len == 1) {
            return new int[]{-1};
        }

        int[] next = new int[len + 1];
        next[0] = -1;
        next[1] = 0;

        int k = -1;
        int index = 2;
        while (index < len) {
            if (k == -1 || arr[index - 1] == arr[k]) {
                next[index] = k + 1;
                index++;
                k++;
            } else {
                k = next[k];
            }
        }
        return next;
    }

}
