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

    /**
     * 使用非递归的方式来实现归并排序
     * @param arr
     */
    private static void  mergeSort(int[] arr) {
        int mergeSize = 1;
        int n = arr.length;
        while (mergeSize < n) {
            // left 表示左边数组的起始位置， rightEnd表示右边数组末尾位置的下一个位置
            int left = 0, rightEnd = 0;
            while (rightEnd < n) {
                rightEnd = Math.min(n, rightEnd + mergeSize * 2);
                // 后续剩余的数组刚好在左边待合并数组范围内，说明右边已经超出范围可退出进入下一个mergeSize的合并
                if (n - left <= mergeSize) {
                    break;
                }
                merge2(arr, left, left + mergeSize, rightEnd);
                left += mergeSize * 2;
            }
            // 不断扩大合并范围
            mergeSize <<= 1;
        }
    }

    /**
     * 合并左右两部分数组
     * @param arr
     * @param start 左边数组起始位置
     * @param mid   右边起始位置，也即左边部分末尾的下一个位置
     * @param end   右边部分末尾位置
     */
    private static void merge2(int[] arr, int start, int mid ,int end) {
        int[] temp = new int[end - start];
        int index = 0, leftStart = start;
        int rightStart = mid;
        while (leftStart < mid && rightStart < end) {
            if (arr[leftStart] > arr[rightStart]) {
                temp[index++] = arr[rightStart++];
            } else {
                temp[index++] = arr[leftStart++];
            }
        }
        if (leftStart < mid) {
            for (int i = leftStart; i < mid; i++) {
                temp[index++] = arr[i];
            }
        }
        if (rightStart < end) {
            for (int i = rightStart; i < end; i++) {
                temp[index++] = arr[i];
            }
        }

        for (int i = start; i < end; i++) {
            arr[i] = temp[i - start];
        }
    }

}