package com.lynch;

import java.awt.geom.GeneralPath;

public class MaxSumInArr {
    public static void main(String[] args) {

        int[] arr = {1,-2,3,10,-4,7,2,-5};
        System.out.println(getMaxSum(arr));
    }

    /**
     * 获取连续子数组和的最大值
     * @param arr
     * @return
     */
    public static int getMaxSum(int[] arr){

        int current = 0, sum = 0;
        for (int index=0;index<arr.length;index++){
            if(current<=0){
                current=arr[index];
            }else{
                current+=arr[index];
            }
            if(current>sum)
                sum = current;
        }
        return sum;
    }
}
