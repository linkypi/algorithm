package com.lynch.topk;

/**
 * 中位数的中位数，该算法是实现TopK算法的最优解:
 * 快速排序的一趟划分，可以将整个数组分为三部分，小于主元的部分、主元以及大于主元的部分。
 * 如果大于主元的部分长度恰好是 k，那么这部分元素就是 Top k。否则，如果大于主元的部分
 * 长度大于 k，那么我们就需要在这部分再做一次划分，找到其中的 Top k。如果大于主元的部分
 * 长度小于 k，那么这部分属于 Top k，但不完整，假设其长度是 d，那么我们还需要从小于主元的部分中找到 Top k-d
 */
public class BFPRT {
    public static void main(String[] args) {

    }

    private static int bfprt(int[] arr, int begin, int end, int k) {
        if (begin == end) {
            return arr[begin];
        }

        int pivot = medianOfMedians(arr, begin, end);
        int[] pivotRang = partition(arr, begin, end, pivot);
        if (k >= pivotRang[0] && k <= pivotRang[1]) {
            return arr[k];
        } else if (k < pivotRang[0]) {
            return bfprt(arr, begin, pivotRang[0] - 1, k);
        } else {
            return bfprt(arr, pivotRang[1] + 1, end, k);
        }
    }

    private static int medianOfMedians(int[] arr, int begin, int end) {
        int num = end - begin + 1;
        int offset = num % 5 == 0 ? 0 : 1;
        // 将 begin 到 end 范围的数按5个为一组的方式分组，分组后剩下的不足5个则也归为一组
        int[] temp = new int[num / 5 + offset];
        for (int i = 0; i < temp.length; i++) {
            int startIndex = begin + i * 5;
            int endIndex = begin + 4;
            temp[i] = getMedian(arr, startIndex, Math.min(endIndex, end));
        }
        return bfprt(temp, 0, temp.length - 1, temp.length / 2);
    }

    private static int getMedian(int[] arr, int start, int end) {
//        insertionSort(arr, start,end);
        int sum = start + end;
        int mid = (sum / 2) + (sum % 2);
        return arr[mid];
    }

    private static int[] partition(int[] arr, int begin, int end, int pivot) {
        int small = begin - 1;
        int cur = begin;
        int big = end + 1;
        while (cur != big) {
            if (arr[cur] < pivot) {
                swap(arr, ++small, cur++);
            } else if (arr[cur] > pivot) {
                swap(arr, cur, --big);
            } else {
                cur++;
            }
        }

        return new int[]{small + 1, big - 1};
    }

    private static void swap(int[] arr, int a, int b) {
        int temp = arr[a];
        arr[a] = arr[b];
        arr[b] = temp;
    }
}
