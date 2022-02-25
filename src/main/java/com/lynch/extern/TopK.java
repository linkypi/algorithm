package com.lynch.extern;

import java.util.HashMap;

/**
 * 求 Top K 问题可以使用大根堆实现(O(N)), 而使用小根堆使用的空间复杂度更低,只需要 O(K)
 * 当前该算法即是使用小根堆实现, 可以实时打印 Top K
 * 实现方式是使用词频表记录每个词出现位置, 使用位置表记录每个词所在堆的位置, 使用 K 个元素的小根堆来维护 Top K
 *
 */
public class TopK {

    public static void main(String[] args) {

    }

    public static class Node {
        private final String str;
        private int times;

        public Node(String str) {
            this.str = str;
            this.times = 1;
        }

        @Override
        public String toString() {
            return this.str;
        }
    }

    private static int heapSize = 0;
    private static final int K = 3;
    private static final Node[] arr = new Node[K];
    private static final HashMap<String, Node> wordFrequentMapper = new HashMap<>();
    private static final HashMap<String, Integer> positionMapper = new HashMap<>();

    public static void add(String str) {

        // 堆未满则直接入堆
        if (heapSize < K) {
            Node node = new Node(str);
            wordFrequentMapper.put(str, node);
            heapInsert(node);
            return;
        }

        // 堆已满 且词频表不存在 则只在词频表及词位置表做记录,不入堆
        if (!wordFrequentMapper.containsKey(str)) {
            Node node = new Node(str);
            wordFrequentMapper.put(str, node);
            positionMapper.put(str, -1);
            return;
        }
        // 堆已满且词频表存在该词, 则词频+1
        Node node = wordFrequentMapper.get(str);
        node.times++;
        // 该词存在堆中则该节点为根节点向下调整堆结构
        int index = positionMapper.get(node.str);
        if (index > -1) {
            shiftDown(index, node);
        } else {
            // 该词不在堆上,但是词频已经大于堆顶元素的词频则替换
            Node first = arr[0];
            if (node.times > first.times) {
                arr[0] = node;
                shiftDown(0, node);
            }
        }
    }

    private static void shiftDown(int index, Node node) {

        int count = heapSize >> 2;
        while (index <= count) {
            int left = 2 * index + 1;
            int right = 2 * index + 2;
            int smallest = arr[left].times < arr[index].times ? left : index;
            if (right < heapSize && arr[right].times < arr[smallest].times) {
                smallest = right;
            }
            if (index != smallest) {
                Node temp = arr[smallest];
                arr[smallest] = arr[index];
                arr[index] = temp;
                positionMapper.put(arr[index].str, smallest);
                positionMapper.put(arr[smallest].str, index);
            }
            index = smallest;
        }
    }

    public static void heapInsert(Node node) {
        if (heapSize < K) {
            arr[heapSize] = node;
            heapSize++;
        }

        if (heapSize == 1) {
            return;
        }

        int index = (heapSize >> 1) - 1;
        while (index > 0) {
            int left = 2 * index + 1;
            int right = 2 * index + 2;
            int smallest = arr[left].times < arr[index].times ? left : index;
            if (right < heapSize && arr[right].times < arr[smallest].times) {
                smallest = right;
            }
            if (index != smallest) {
                Node temp = arr[smallest];
                arr[smallest] = arr[index];
                arr[index] = temp;
                positionMapper.put(arr[index].str, smallest);
                positionMapper.put(arr[smallest].str, index);
            }
            index = (smallest >> 1) - 1;
        }

    }
}
