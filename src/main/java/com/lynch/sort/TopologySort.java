package com.lynch.sort;

import java.util.*;

/**
 * 拓扑排序
 * Created by troub on 2022/2/25 9:08
 */
public class TopologySort {
    public static void main(String[] args) {
        int[][] arr = new int[][]{
                {0, 3}, {1, 3},
                {1, 4}, {2, 4},
                {3, 5}, {4, 5}
        };
        final int[] result = sort(arr);
        print(result);
    }

    private static void print(int[] arr) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < arr.length; i++) {
            builder.append(arr[i]);
            if (i != arr.length - 1) {
                builder.append(" -> ");
            }
        }
        System.out.println(" topological sort result: " + builder.toString());
    }


    /**
     * 拓扑关系如下：
     * 1. 节点5依赖于节点3及节点4
     * 2. 节点3依赖于节点0及节点1
     * 3. 节点4依赖于节点1及节点2
     * 即 0，1，2的入度都为 0 ，而 3，4，5的入度都为 2
     * 使用 0 到 5共六个编号来表示不同课程的话，那可以表示为必须修完
     * 0，1，2课程后才可以修 3，4课程，也只有3，4课程修好后才可以修5课程
     *
     *    0
     *      \
     *        3
     *      /   \
     *    1       5
     *      \   /
     *        4
     *      /
     *    2
     *
     * @param arr
     * @return
     */
    private static int[] sort(int[][] arr) {
        // 记录每个节点的入度
        int[] inDegree = new int[arr.length];
        // 记录每个节点所有出度对应的节点，方便后面出队时使用
        Map<Integer, List<Integer>> map = new HashMap<>();
        for (int[] item : arr) {
            int item0 = item[0];
            int item1 = item[1];
            map.computeIfAbsent(item0, k -> new ArrayList<>());
            map.get(item0).add(item1);
            inDegree[item1]++;
        }

        // 从入度为 0 的节点开始处理，找到入度为 0 的节点后先放入队列
        Deque<Integer> deque = new ArrayDeque<>(arr.length);
        for (int i = 0; i < inDegree.length; i++) {
            if (inDegree[i] == 0) {
                deque.offer(i);
            }
        }

        List<Integer> result = new ArrayList<>();

        // 将入度为 0 的节点出对，并将相关节点入度值减 1
        // 直到入度值减少为 0 时又将该节点放入队列中处理
        while (!deque.isEmpty()) {
            final Integer pop = deque.poll();
            result.add(pop);
            // 找到该节点对应的所有有向边的对端节点
            // 并将所有对端节点的入度减 1，若节点入度减少到 0 则继续入队
            final List<Integer> list = map.get(pop);
            if (list != null) {
                for (int item : list) {
                    if (--inDegree[item] == 0) {
                        deque.push(item);
                    }
                }
            }
        }
        return result.stream().mapToInt(Integer::valueOf).toArray();
    }
}
