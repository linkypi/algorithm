package com.lynch;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.TransferQueue;
import java.util.concurrent.atomic.LongAdder;

/**
 * @Author: linxueqi
 * @Description:
 * @Date: create in 2022/3/23 14:27
 */
public class PrintABUseLinkedTransferQueue {
    public static void main(String[] args) {

        char[] a1 = "1234567".toCharArray();
        char[] a2 = "ABCDEFG".toCharArray();

        TransferQueue<Character> queue = new LinkedTransferQueue<>();

        new Thread(() -> {
            try {
                for (char c : a1) {
                    System.out.print(queue.take());
                    queue.transfer(c);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "t1").start();

        new Thread(() -> {
            try {
                for (char c : a2) {
                    queue.transfer(c);
                    System.out.print(queue.take());
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "t2").start();

    }
}