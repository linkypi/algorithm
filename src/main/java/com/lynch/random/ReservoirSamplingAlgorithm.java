package com.lynch.random;

import org.junit.Test;

import java.util.Random;

/**
 * 水塘抽样算法实现
 * @Author: linxueqi
 * @Description:
 * @Date: create in 2023/10/23 17:41
 */
public class ReservoirSamplingAlgorithm {
    @Test
    public void test(){

    }

    private void getRandom(Node head, int k) {

        if (head == null) {
            return;
        }
        int[] arr = new int[k];

        // 默认选中前 k 个元素
        for (int i = 0; i < k; i++) {
            arr[i] = head.value;
            head = head.next;
        }

        Random random = new Random();
        int i = k;
        while (head != null) {
            // 随机生成 [0,i] 之间的数
            int x = random.nextInt(++i);
            // 该数小于 k 的概率为 i
            if (x < k) {
                arr[x] = head.value;
            }
            head = head.next;
        }

    }

    public class Node{
        private Node next;
        private int value;

        public Node(int value){
            this.value = value;
        }
    }
}
