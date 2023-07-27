package com.lynch.disjoint_set;

import lombok.Data;

/**
 * @author: linxueqi
 * @Description:
 * @createTime: 2022/7/30 14:31
 */
@Data
public class UnionFind {
    // parent[i] = k 表示 i 的父节点为 k
    private final int[] parent;
    // size[i] = k 表示以 i 为父节点时，其集合大小
    private final int[] size;
    private final int[] help;
    private int sets;

    public UnionFind(int N) {
        parent = new int[N];
        size = new int[N];
        help = new int[N];
        sets = N;
        for (int i = 0; i < N; i++) {
            parent[i] = i;
            size[i] = 1;
        }
    }

    public int find(int i) {
        int hi = 0;
        while (i != parent[i]) {
            // 记录路径
            help[hi++] = i;
            i = parent[i];
        }
        // 路径压缩
        for (hi--; hi >= 0; hi--) {
            parent[help[hi]] = i;
        }
        return i;
    }

    /**
     * 判断两个元素是否联通
     * @param a
     * @param b
     * @return
     */
    public boolean isConnected(int a, int b){
        int x = find(a);
        int y = find(b);
        return x == y;
    }

    public void union(int i, int j) {
        int a = find(i);
        int b = find(j);
        if (a != b) {
            if (size[a] >= size[b]) {
                size[a] += size[b];
                parent[b] = a;
            } else {
                size[b] += size[a];
                parent[a] = b;
            }
            sets--;
        }

    }
}
