package com.lynch.extern;

import com.lynch.sort.InsertionSort;

/**
 * 中位数的中位数，该算法是实现TopK算法的最优解
 */
public class BFPRT {
    public static void main(String[] args) {

    }

    private static int select(int[] arr, int begin, int end, int i){
        if(begin == end){
            return arr[begin];
        }

        int pivot = medianOfMedians(arr,begin, end);
        int[] pivotRang = partition(arr, begin, end, pivot);
        if(i>= pivotRang[0] && i <= pivotRang[1]){
            return arr[i];
        }else if(i<pivotRang[0]){
            return select(arr, begin, pivotRang[0] -1, i);
        }else{
            return select(arr, pivotRang[1] + 1, end, i);
        }
    }

    private static int medianOfMedians(int[] arr, int begin, int end){
        int num = end - begin + 1;
        int offset = num %5==0?0:1;
        // 将 begin 到 end 范围的数按5个为一组的方式分组，分组后剩下的不足5个则也归为一组
        int[] temp =  new int[num/5 + offset];
        for(int i=0;i< temp.length;i++){
            int startIndex = begin + i*5;
            int endIndex = begin + 4;
            temp[i] = getMedian(arr, startIndex, Math.min(endIndex, end));
        }
        return select(temp, 0, temp.length - 1, temp.length/2);
    }

    private static int getMedian(int[] arr,int start, int end){
//        insertionSort(arr, start,end);
        int sum = start + end;
        int mid = (sum/2) + (sum%2);
        return arr[mid];
    }

    private static int[] partition(int[] arr, int begin, int end, int pivot){
        int small = begin -1;
        int cur = begin;
        int big = end +1;
        while (cur!=big){
            if(arr[cur]<pivot){
                swap(arr, ++small, cur++);
            }else if(arr[cur] > pivot){
                swap(arr, cur, --big);
            }else{
                cur++;
            }
        }

        int[] rang = new int[2];
        rang[0] = small + 1;
        rang[1] = big -1;
        return rang;
    }

    private static void swap(int[] arr, int a, int b){
        int temp = arr[a];
        arr[a] = arr[b];
        arr[b] = temp;
    }
}
