package com.lynch.subsequence;

import java.util.Arrays;
import java.util.Comparator;

/**
 * 给定一个二位数组 envelopes, 其中 envelopes[i] = [w,h]
 * 表示第 i 个信封的宽度和高度。当另外一个信封的高度和宽度都比这个封信大的时候
 * 这个信封就可以放进另一个信封里，请计算最多可以有多少个信封能组成一组俄罗斯套娃
 * 信封。注意：不允许旋转信封
 * @author: linxueqi
 * @Description:
 * @createTime: 2022/3/29 22:42
 */
public class MaxEnvelopes {
    public static void main(String[] args) {

    }

    private static int find(int[][] envelopes) {

        Arrays.sort(envelopes, new Comparator<int[]>() {
            @Override
            public int compare(int[] a, int[] b) {
                // 先按宽度升序，宽度相同则按高度降序
                return a[0] == b[0] ? b[1] - a[1] : a[0] - b[0];
            }
        });

        // 宽度都按升序排列后 后面只要计算高度的最长递增子序列即为答案
        int[] harr = new int[envelopes[0].length];
        for (int index = 0; index < envelopes.length; index++) {
            harr[index] = envelopes[index][1];
        }

        return getLCIS(harr);
    }

    /**
     * 求最长递增公共子序列
     * @param arr
     * @return
     */
    private static int getLCIS(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        int[] buckets = new int[arr.length];
        int size = arr.length;

        for (int item : arr) {
            int left = 0;
            int right = size;
            while (left < right) {
                int mid = (left + right) >> 1;
                if (item <= buckets[mid]) {
                    right = mid;
                } else {
                    left = mid + 1;
                }
            }
            if (left >= size) {
                size++;
            }
            buckets[left] = item;
        }
        return size;
    }
}
