package com.lynch;

/**
 * @Author: linxueqi
 * @Description:
 * @Date: create in 2023/3/22 10:29
 */
public class UnionFind {

    // 并查集父
    private int[] parent;
    // 联通量
    private int count;
    int[] size = null;

    public UnionFind(int[] arr) {

        int n = arr.length;
        parent = new int[n];
        size = new int[n];
        count = n;
        for (int item : arr) {
            parent[item] = item;
            size[item] = 1;
        }
    }

    public boolean connected(int p, int q) {
        int a = find(p);
        int b = find(q);
        return a == b;
    }

    public int find(int x) {
        while (parent[x] != x) {
            // 路径压缩， 使得路径尽量扁平化
            parent[x] = parent[parent[x]];
            // 继续向前遍历
            x = parent[x];
        }
        return x;
    }

    public void union(int p, int q) {
        int a = find(p);
        int b = find(q);
        if (a == b) {
            return;
        }

        // 根据每个连通分量的节点数来判断应该如何挂载
        if (size[p] > size[q]) {
            parent[q] = p;
            size[p] += size[q];
        } else {
            parent[p] = q;
            size[q] += size[p];
        }
        count--;
    }

    public static void main(String[] args) {

    }

    public static class UnionFind2 {
        private int[] parent;
        private int count;

        public UnionFind2(int n) {
            count = n;
            parent = new int[n];
            for (int i = 0; i < n; i++) {
                parent[i] = i;
            }
        }

        public boolean connected(int p, int q) {
            int a = find(p);
            int b = find(p);
            return a == b;
        }

        public void union(int p, int q) {
            int a = find(p);
            int b = find(p);
            if (a == b) {
                return;
            }
            parent[p] = q;
            count --;
        }

        public int find(int x) {
            if (parent[x] != x) {
                // 扁平化，找到根节点后将 x 直接指向根节点
                parent[x] = find(parent[x]);
            }
            // 最后返回根节点
            return parent[x];
        }
    }
}
