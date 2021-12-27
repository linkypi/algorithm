package com.lynch.sort;

public class MergeSort extends AbstractSort {
    public MergeSort() {
    }

    public MergeSort(int[] arr) {
        this.array = arr;
    }

    public static void main(String[] argv) {
        int[] arr = {6, 2, 9, 4, 7, 1, 8, 5};
        for (int value : arr) {
            System.out.print(value + "  ");
        }
        System.out.println("");
        new MergeSort(arr).sort();
        for (int value : arr) {
            System.out.print(value + "  ");
        }
    }

    /**
     * 归并排序算法复杂度  T(n) = 2*T(n/2) + 0(n)
     * 则 T(1) = O(1),  T(n)/n = T(n/2)/(n/2) + O(1)
     * 令 S(n) = T(n)/n 则
     *  S(1) = O(1)
     *  S(n) = S(n/2) + O(1)
     *       = S(n/4) + O(2)
     *       = S(n/8) + O(3)
     *       = S(n/(2的 k 次方)) + O(k)
     *       = S(1) + O(logN)
     *  由于 S(n) = T(n)/n 故 T(n) = n*S(n) = O(nlogn)
     *  由于归并排序总是平均分割子序列，故最好最坏及平均时间复杂度都为  O(nlogn) ，属于稳定排序
     *  其空间复杂度为 O(n/2 + logn) = O(n), 其中n/2用于临时存放左边数组，logn用于递归调用
     */

    @Override
    protected void sortInternal() {
        sort(0, this.array.length);
    }

    private void sort(int begin, int end) {
        if (end - begin < 2) return;

        int mid = (begin + end) >> 1;
        sort(begin, mid);
        sort(mid, end);
        merge(begin, mid, end);
    }

    private void merge(int begin, int mid, int end) {
        int[] leftArr = cloneArr(begin, mid);
        int leftIndex = 0, allIndex = begin, rightIndex = mid;
        int leftEnd = mid - begin, rightEnd = end;

        while (leftIndex < leftEnd) {
            // 若右边数组元素比左边数组元素小则将右边较小的值放到合并的数组中，并将右边索引及合并数组的索引后移一位
            if (rightIndex < rightEnd && compareValue(leftArr[leftIndex], array[rightIndex]) > 0) {
                array[allIndex++] = array[rightIndex++];
            } else {
                // 否则表明右边数组已经合并完成，此时直接将左边数组按顺序合并即可
                array[allIndex++] = leftArr[leftIndex++];
            }
        }
    }

    private int[] cloneArr(int begin, int end) {
        int count = end - begin;
        int[] arr = new int[count + 1];
        for (int i = 0; i <= count; i++) {
            arr[i] = array[begin + i];
        }
        return arr;
    }
}