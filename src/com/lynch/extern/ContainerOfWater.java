package com.lynch.extern;

/**
 * 定义一个非负元素数据 arr,每个元素表示一个高度, 将此数组an看做一个容器, 请该容器可以装多少水
 */
public class ContainerOfWater {
    public static void main(String[] args) {
        int[] arr = {5, 1, 3, 2, 4};
        int sum = getSumOfWater(arr);
        System.out.println("result : " + sum);
    }

    private static int getSumOfWater(int[] arr) {
        int left = arr[0];
        int right = arr[arr.length - 1];
        int min = Math.min(left, right);
        int sum = 0;

        int leftIndex = 1, rightIndex = arr.length - 2;
        while (leftIndex != rightIndex) {
            if (left == min) {
                if (min > arr[leftIndex]) {
                    sum += min - arr[leftIndex];
                    leftIndex++;
                } else {
                    left = arr[leftIndex];
                    min = Math.min(left, right);
                    if (min == left) {
                        leftIndex++;
                    } else {
                        rightIndex--;
                    }
                }

            } else {
                if (min > arr[rightIndex]) {
                    sum += min - arr[rightIndex];
                    rightIndex--;
                } else {
                    right = arr[rightIndex];
                    min = Math.min(left, right);
                    if (min == left) {
                        leftIndex++;
                    } else {
                        rightIndex--;
                    }
                }

            }
        }
        return sum;
    }
}
