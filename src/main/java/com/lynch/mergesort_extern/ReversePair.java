package com.lynch.mergesort_extern;

/**
 * @Description: 求数组中的逆序对总数
 * @Author: linxueqi
 * @Date: create in 2022/3/22 15:35
 */
public class ReversePair {
    public static void main(String[] args) {
        int[] arr = {7, 5, 6, 4};
        int count = find(arr, 0, arr.length - 1);
        System.out.println("count: " + count);
    }

    private static int find(int[] arr, int start, int end) {
        if (start == end) {
            return 0;
        }
        int mid = (start + end) >> 1;
        int leftCount = find(arr, start, mid);
        int rightCount = find(arr, mid + 1, end);
        int lastCount = merge(arr, start, mid + 1, end);
        return leftCount + rightCount + lastCount;
    }

    /**
     * 归并排序合并操作
     *
     * @param arr
     * @param start 左边起始位置
     * @param mid   右边起始位置
     * @param end   右边结束位置
     * @return
     */
    private static int merge(int[] arr, int start, int mid, int end) {
        int count = 0;
        int rIndex = mid;
        int lIndex = start;

        int[] temp = new int[end - start + 1];
        int index = 0;

        while (lIndex < mid && rIndex <= end) {
            if (arr[lIndex] > arr[rIndex]) {
                // 左边合并后，若左边第start个元素大于右边 rIndex 位置的元素则
                // 说明从start位置到 mid 位置的元素都大于 arr[rIndex]
                count += (mid - lIndex);
                temp[index++] = arr[rIndex++];
            } else {
                temp[index++] = arr[lIndex++];
            }
        }

        while (lIndex < mid) {
            temp[index++] = arr[lIndex++];
        }
        while (rIndex <= end) {
            temp[index++] = arr[rIndex++];
        }
        for (int i = start; i <= end; i++) {
            arr[i] = temp[i - start];
        }
        return count;
    }
}
