package com.lynch.sort;/*
 * @Author: your name
 * @Date: 2021-06-12 10:10:43
 * @LastEditTime: 2021-06-12 10:22:49
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /algorithm/src/com.lynch.sort.SelectionSort.java
 */


public class SelectionSort extends AbstractSort{
    public SelectionSort(){}
    public SelectionSort(int[] arr){
        this.array = arr;
    }
    public static void main(String[] argv) {
        int[] arr = {6, 2, 9, 4, 7, 1, 8, 5};
        for (int value : arr) {
            System.out.print(value + "  ");
        }
        System.out.println("");
        new SelectionSort(arr).sort();
        for (int value : arr) {
            System.out.print(value + "  ");
        }
    }

    /**
     * 遍历一遍便将最大值选出,并将最大值与末尾的数做交换
     * 已交换到末尾的数保持不变,如此往复直到结束
     * 算法复杂度最好与最坏都是 O(n2)
     */
    public void selectionSort() {
        int[] arr = this.array;
        // 由于每次都需要将最大值与末尾的值交换,故需要从尾部开始递减
        for (int i = arr.length - 1; i >= 0; i--) {
            int maxIndex = 0;
            // 遍历一轮获取出最大值, 每轮遍历长度不包括末尾已经交换的位置
            // 该算法优于冒泡排序, 因为冒泡排序每次比较都需要做交换,而选择排序仅交换最大值
            for (int k = 0; k <= i; k++) {
                // 为保持算法稳定性, 此处比较使用等号, 如若数组中出现两个相同的数字时,
                // 仅使用小于号做比较就会导致两个相同数字的先后顺序发生改变,也就导致了不稳定性的发生
                // 严格来说选择排序仍然是不稳定排序, 因为在交换过程中原先两个相同的数的位置同样会发生改变, 故等号可以去除
                if (compare(maxIndex, k) < 0) {
                    maxIndex = k;
                }
            }
            if (maxIndex == i) {
                continue;
            }

            swap(i, maxIndex);
        }
    }

    @Override
    protected void sortInternal() {
        selectionSort();
    }
}