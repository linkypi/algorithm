package com.lynch.segment_tree;

import org.junit.Test;

import java.util.*;

/**
 * // https://leetcode.cn/problems/falling-squares/
 * // 在二维平面上的 x 轴上，放置着一些方块。
 * // 给你一个二维整数数组 positions ，其中 positions[i] = [lefti, sideLengthi] 表示：
 * // 第 i 个方块边长为 sideLengthi ，其左侧边与 x 轴上坐标点 lefti 对齐。
 * // 每个方块都从一个比目前所有的落地方块更高的高度掉落而下。方块沿 y 轴负方向下落，
 * // 直到着陆到 另一个正方形的顶边 或者是 x 轴上 。一个方块仅仅是擦过另一个方块的左侧边或右侧边不算着陆。
 * // 一旦着陆，它就会固定在原地，无法移动。
 * // 在每个方块掉落后，你必须记录目前所有已经落稳的 方块堆叠的最高高度 。
 * // 返回一个整数数组 ans ，其中 ans[i] 表示在第 i 块方块掉落后堆叠的最高高度。
 * // 示例1：
 * // 输入：positions = [[1,2],[2,3],[6,1]]
 * // 输出：[2,5,5]
 * // 解释：
 * // 第 1 个方块掉落后，最高的堆叠由方块 1 组成，堆叠的最高高度为 2 。
 * // 第 2 个方块掉落后，最高的堆叠由方块 1 和 2 组成，堆叠的最高高度为 5 。
 * // 第 3 个方块掉落后，最高的堆叠仍然由方块 1 和 2 组成，堆叠的最高高度为 5 。
 * // 因此，返回 [2, 5, 5] 作为答案。
 * @author: lynch
 * @description:
 * @date: 2024/2/23 21:22
 */
public class FallingSquares {

    @Test
    public void Test() {
//        int[][] positions = {{1, 2}, {2, 3}, {6, 1}};
        int[][] positions = {{100, 100}, {200,  100}};
//        List<Integer> result = fallingSquares(positions);
//        System.out.print("real result: ");
//        System.out.println(result);

        List<Integer> result1 = myFallingSquares(positions);
        System.out.print("my result: ");
        System.out.println(result1);
    }

    public List<Integer> myFallingSquares(int[][] positions) {

        int n = positions.length;

        List<Integer> result = new ArrayList<>(n);
        int max = 0;
        int xn=0;
        for (int[] arr : positions) {
            int x = arr[0];
            int h = arr[1];
            xn = Math.max(xn, x+h);
        }
        // 以该种方式求解会超出内存限制，因为 x <= 10^8
        // 故为了处理该问题需将数据范围缩小
        SegmentTree segmentTree = new SegmentTree(xn);
        for (int[] arr : positions) {
            int x = arr[0];
            int h = arr[1];
            int y = x + h - 1;
            int m = segmentTree.query(x, y, 1, xn, 1) + h;
            max = Math.max(max, m);
            result.add(max);
            segmentTree.update(x, y, m, 1, xn, 1);
        }
        return result;
    }

    public List<Integer> fallingSquares(int[][] positions) {
        HashMap<Integer, Integer> map = index(positions);
        int N = map.size();
        SegmentTree segmentTree = new SegmentTree(N);
        int max = 0;
        List<Integer> res = new ArrayList<>();
        // 每落一个正方形，收集一下，所有东西组成的图像，最高高度是什么
        for (int[] arr : positions) {
            int L = map.get(arr[0]);
            int R = map.get(arr[0] + arr[1] - 1);
            int height = segmentTree.query(L, R, 1, N, 1) + arr[1];
            max = Math.max(max, height);
            res.add(max);
            segmentTree.update(L, R, height, 1, N, 1);
        }
        return res;
    }

    public HashMap<Integer, Integer> index(int[][] positions) {
        TreeSet<Integer> pos = new TreeSet<>();
        for (int[] arr : positions) {
            pos.add(arr[0]);
            pos.add(arr[0] + arr[1] - 1);
        }
        HashMap<Integer, Integer> map = new HashMap<>();
        int count = 0;
        for (Integer index : pos) {
            map.put(index, ++count);
        }
        return map;
    }

    static class SegmentTree{
        int[] max;
        int[] change;
        boolean[] update;

        public SegmentTree(int n) {
            int size = n + 1; // 默认第一个元素不存储数据
            max = new int[size << 2];
            change = new int[size << 2];
            update = new boolean[size << 2];
        }

        public void update( int start, int end, int h, int l, int r, int root) {
            if (start <= l && end >= r) {
                this.update[root] = true;
                this.change[root] = h;
                this.max[root] = h;
                return;
            }

            int mid = (l + r) >> 1;
            pushDown(l, r, root);

            if (mid >= start) {
                this.update(start, end, h, l, mid, root << 1);
            }
            if (end > mid) {
                this.update(start, end, h, mid + 1, r, root << 1 | 1);
            }
            pushUp(l, r, root);
        }

        private void pushDown(int l, int r,int root){
            if(update[root]){
                this.update[root<<1] = true;
                this.update[root<<1|1] = true;
                this.max[root<<1] = this.change[root];
                this.max[root<<1|1] = this.change[root];
                this.change[root<<1] = this.change[root];
                this.change[root<<1|1] = this.change[root];
                this.update[root] = false;
            }
        }

        private void pushUp(int l,int r, int root){
            this.max[root] = Math.max(this.max[root<<1], this.max[root<<1|1]);
        }

        public int query(int start, int end, int l,int r,int root) {
            if (start <= l && end >= r) {
                return this.max[root];
            }

            int mid = (l + r) >> 1;
            pushDown(l, r, root);
            int left = 0;
            int right = 0;
            if (start <= mid) {
                left = query(start, end,l, mid, root << 1);
            }
            if (end > mid) {
                right = query(start, end,mid + 1, r, root << 1 | 1);
            }
            return Math.max(left,right);
        }
    }
}
