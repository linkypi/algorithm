package com.lynch.sort;/*
 * @Author: your name
 * @Date: 2021-06-12 10:10:43
 * @LastEditTime: 2021-06-12 10:22:49
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /algorithm/src/com.lynch.sort.BubbleSort.java
 */
public class BubbleSort extends AbstractSort{
    public BubbleSort(){}
    public BubbleSort(int[] arr) {
        this.array = arr;
    }
    public static void main(String[] argv){
        int[] arr = {6,2,9,4,7,1,8,5};
        for (int value : arr) {
            System.out.print(value + "  ");
        }
        System.out.println("");
        new BubbleSort(arr).sort();
        for (int value : arr) {
            System.out.print(value + "  ");
        }
    }

    /**
     * 冒泡排序
     */
    private void bubbleSort() {
        final int[] arr = this.array;
        for (int i = arr.length - 1; i >= 0; i--) {
            for (int k = 0; k < i; k++) {
                if (compare(k+1, k) < 0) {
                    swap(k+1, k);
                }
            }
        }
    }

    /**
     *  第一种优化方式, 由于元素没遍历一轮都需要两两比较, 无论哪次比较,其比较两个数后都会取其中一个最大的数与下一次的数据进行比较
     *  故只要确定每一轮比较中没有数据进行交换那么就说明数组整体有序, 如此就可以提前退出循环, 减少比较次数
     *  注意: 该优化方式仅适用于存在提前排好序的数组, 出现概率较少
     */
    private void bubbleSort_Optimize1() {
        final int[] arr = this.array;
        for (int i = arr.length - 1; i >= 0; i--) {
            boolean order = true;
            for (int k = 0; k < i; k++) {
                if (compare(k+1, k) < 0) {
                    swap(k+1, k);
                    order = false;
                }
            }

            if(order){
                break;
            }
        }
    }

    /**
     * 该情况视针对的是数组后半部分有序的数组, 如: 6 2 5 3 7 8 9
     * 其中 789 已有序无效再比较,故只需记录其最后交换元素的位置后直接调整到
     * 该位置开始比较即可
     */
    private void bubbleSort_Optimize2() {
        final int[] arr = this.array;
        for (int i = arr.length - 1; i >= 0; i--) {
            int sortedIndex = 0;
            for (int k = 0; k < i; k++) {
                if (compare(k+1, k) < 0) {
                    swap(k+1, k);
                    sortedIndex = k + 1;
                }
            }

            i = sortedIndex;
        }
    }

    @Override
    protected void sortInternal() {
//        bubbleSort();
//        bubbleSort_Optimize1();
        bubbleSort_Optimize2();
    }
}