package com.lynch.sort;/*
 * @Author: your name
 * @Date: 2021-06-12 10:10:43
 * @LastEditTime: 2021-06-12 10:22:49
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /algorithm/src/com.lynch.sort.SelectionSort.java
 */
public class InsertionSort extends AbstractSort{
    public InsertionSort(){}
    public InsertionSort(int[] arr) {
        this.array = arr;
    }
    public static void main(String[] argv){
        int[] arr = {6,2,9,4,7,1,8,5};
        for (int value : arr) {
            System.out.print(value + "  ");
        }
        System.out.println("");
        new InsertionSort(arr).sort();
        for (int value : arr) {
            System.out.print(value + "  ");
        }
    }

    @Override
    protected void sortInternal() {
        // 使用交换的方式实现
//        for (int begin = 1; begin < array.length; begin++) {
//            int current = begin;
//            while(current>0 && compare(current,current-1)<0){
//                swap(current, current-1);
//                current --;
//            }
//        }

        // 为减少交换次数使用挪动的方式实现
//        for (int begin = 1; begin < array.length; begin++) {
//            int current = begin;
//            int value = array[current];
//            while (current > 0 && compareValue(value, array[current - 1]) < 0) {
//                array[current] = array[current - 1];
//                current--;
//            }
//            array[current] = value;
//        }

        for (int begin = 1; begin < array.length; begin++) {
            int temp = array[begin];
            int insertIndex = binary_search(begin);
            // 将 [insertIndex, begin) 的所有元素往后移动一个位置
            for (int i = begin; i > insertIndex; i--) {
                array[i] = array[i-1];
            }
            array[insertIndex] = temp;
        }
    }

    /**
     * 使用二分查找元素插入的位置
     * @param index
     * @return
     */
    private int binary_search(int index){
        int end = index , begin = 0;
        int v = array[index];
        for (int start = 0; start < index; start++) {
            int mid = (begin + end) >> 1;
            if(v > array[mid]){
                begin = mid + 1;
            }else{
                end = mid;
            }
        }
        return begin;
    }
}