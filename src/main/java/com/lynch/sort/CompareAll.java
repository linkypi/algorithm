package com.lynch.sort;

import java.util.Arrays;
import java.util.Random;

public class CompareAll {
    public static void main(String[] args) {
        int[] randomArr = getRandomArr(10000);
        testSorts(randomArr, new SelectionSort(), new BubbleSort(), new HeapSort(), new InsertionSort(),new QuickSort());
    }

    private static void testSorts(int[] arr, AbstractSort... sorts){
        for (AbstractSort sort: sorts){
            sort.sort(arr.clone());
        }

        Arrays.sort(sorts);
        for (AbstractSort sort: sorts){
            System.out.println(sort);
        }
    }

    public static int[] getRandomArr(int seed) {
        int[] arr = new int[seed];
        Random random = new Random(seed);
        for (int i = 0; i < seed; i++) {
            arr[i] = random.nextInt();
        }
        return arr;
    }

}
