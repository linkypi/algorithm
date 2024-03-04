package com.lynch.suffix_array;

import org.junit.Test;

import java.util.Arrays;

/**
 * DC3 算法实现，参考左神： https://github.com/algorithmzuo/algorithmbasic2020/blob/master/src/class45/Code01_InsertS2MakeMostAlphabeticalOrder.java
 * @author: lynch
 * @description:
 * @date: 2024/1/31 15:03
 */
public class DC3Test {

    @Test
    public void TestDC3() throws InterruptedException {
//        String str = "abaea"; // [5,1,3,2,4]
        String str = "oapomnheygxk"; // [1, 7, 9, 6, 11, 4, 5, 0, 3, 2, 10, 8]
//        String str = "mississippi";
        int[] arr = new int[str.length()];
        for (int i = 0; i < str.length(); i++) {
            arr[i] = str.charAt(i) - 'a';
        }
        DC3 dc3 = new DC3(arr, 'z' - 'a');
        System.out.printf("       dc3: %s\n",Arrays.toString(dc3.sa));

//        Thread.sleep(200);
//        MyDC3 customDC3 = new MyDC3();
//        customDC3.set(str);
//        System.out.printf("custom dc3: %s\n",Arrays.toString(customDC3.sa));
    }
}
