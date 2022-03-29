package com.lynch.extern;


import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * 给定一个二维矩阵，每个元素表示地形高度
 * 求整块地形最多可以容纳多少雨水
 * Description algorithm
 * Created by troub on 2022/03/29 23:14
 */
public class CollectRainWater2D {
    public static void main(String[] args) {

    }

    /**
     * 计算流程：
     * 1. 先将四周的值放到小根堆中，同时使用bool数组exists记录某个位置是否已经入堆，已经入过则忽略
     * 2. 从小根堆中弹出堆顶记为 mem, 同时增加一个max变量用于记录出现的最大值
     * 3. 若 max - mem > 0 则收集雨水，否则不收集。接着对比max及mem后更新max
     * 4. 然后将 mem 位置的上下左右四个位置的元素都放到小根堆中
     * @param arr
     * @return
     */
    private int collect(int[][] arr) {
        if (arr == null || arr.length == 0 || arr[0].length == 0) {
            return 0;
        }

        int rows = arr.length;
        int cols = arr[0].length;

        Queue<Node> heap = new PriorityQueue<>();
        boolean[][] exists = new boolean[rows][cols];
        // 首行
        for (int i = 0; i < cols; i++) {
            heap.offer(new Node(arr[0][i],0, i));
            exists[0][i] = true;
        }
        // 首列
        for (int i = 0; i < rows; i++) {
            heap.offer(new Node(arr[i][0],i, 0));
            exists[i][0] = true;
        }
        // 末尾行
        for (int i = 0; i < cols; i++) {
            heap.offer(new Node(arr[rows - 1][i], rows-1, i));
            exists[rows - 1][i] = true;
        }
        // 末尾列
        for (int i = 0; i < rows; i++) {
            heap.offer(new Node(arr[i][cols - 1], i, cols -1));
            exists[i][cols - 1] = true;
        }

        int max = 0;
        int result = 0;
        while(heap.size()!=0) {
            Node node = heap.poll();
            max = Math.max(max, node.value);

            int row = node.row;
            int col = node.col;
            // 上
            if (row - 1 > -1 && !exists[row - 1][col]) {
                result += Math.max(0, max-arr[row - 1][col]);
                exists[row - 1][col] = true;
                heap.offer(new Node(arr[row - 1][col], row - 1, col));
            }
            // 下
            if (row + 1 < rows && !exists[row + 1][col]) {
                result += Math.max(0, max-arr[row + 1][col]);
                exists[row + 1][col] = true;
                heap.offer(new Node(arr[row + 1][col], row + 1, col));
            }
            // 左
            if (col - 1 > -1 && !exists[row][col - 1]) {
                result += Math.max(0, max-arr[row][col - 1]);
                exists[row][col-1] = true;
                heap.offer(new Node(arr[row][col - 1], row, col - 1));
            }
            // 右
            if (col + 1 < cols && !exists[row][col + 1]) {
                result += Math.max(0, max-arr[row][col + 1]);
                exists[row][col+1] = true;
                heap.offer(new Node(arr[row][col + 1], row, col + 1));
            }
        }
        return result;
    }

    private static final class Node implements Comparator<Integer> {
        private final int value;
        private final int row;
        private final int col;

        public Node(int val,int r, int c) {
            this.value = val;
            this.row = r;
            this.col = c;
        }

        @Override
        public int compare(Integer o1, Integer o2) {
            return o1 - o2;
        }
    }
}
