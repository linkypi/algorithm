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
        String str = "mississippi";
        int[] arr = new int[str.length()];
        for (int i = 0; i < str.length(); i++) {
            arr[i] = str.charAt(i) - 'a';
        }
        DC3 dc3 = new DC3(arr, 's' - 'a');
        System.out.printf("       dc3: %s\n",Arrays.toString(dc3.sa));

//        Thread.sleep(200);
//        MyDC3 customDC3 = new MyDC3();
//        customDC3.set(str);
//        System.out.printf("custom dc3: %s\n",Arrays.toString(customDC3.sa));
    }
}
