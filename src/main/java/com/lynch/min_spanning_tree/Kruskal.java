package com.lynch.min_spanning_tree;

import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Kruscal 算法，最小生成树算法实现之一，通过并查集实现
 * 如何将所有给定节点连通，并确保权重总和最小
 * @Author: linxueqi
 * @Description:
 * @Date: create in 2023/7/27 11:35
 */
public class Kruskal {

    @Test
    public void testKruskal() {

        int[][] metrics = {
                {1, 2, 5}, // 1 到 2 的权重为 5
                {1, 3, 2},
                {2, 3, 6}
        };

        int minCost = findMinCostWithKruscal(metrics);
        System.out.println("min cost: "+ minCost);

    }

    public int findMinCostWithKruscal(int[][] metrics){
        int weight = 0;

        // 根据权重升序排序
        Arrays.sort(metrics, (a, b) -> {
            return a[2] - b[2];
        });

        int m = metrics.length;
        Set<Integer> set = new HashSet<>();
        UnionFind unionFind = new UnionFind(m * 2);
        for (int[] item : metrics) {
            boolean connect = unionFind.isConnect(item[0], item[1]);
            if (connect) {
                continue;
            }
            set.add(item[0]);
            set.add(item[1]);
            unionFind.union(item[0], item[1]);
            weight += item[2];
        }

        // 随便取一个节点来获取父节点，通过父节点获取到当前集合节点总数
        // 若所有节点都已连通，则返回最小权重，否则返回-1
        int p = unionFind.find(metrics[m - 1][0]);
        int count = unionFind.size[p];
        return count == set.size() ? weight : -1;
    }

    @Test
    public void testUnionFind(){
        UnionFind unionFind = new UnionFind(10);
        unionFind.union(1,6);
        unionFind.union(8,3);
        unionFind.union(5,9);
        unionFind.union(4,7);

        unionFind.union(1,9);
        int node = unionFind.find(1);
        boolean connect = unionFind.isConnect(5, 6);
        System.out.println("connected: "+ connect);
    }

    public static class UnionFind{
        // 记录每个节点的父节点
        int[] parent = null;
        // 记录父节点 i 所在集合的节点数 size[i]
        int[] size = null;
        int[] help = null;

        public UnionFind(int n){
            parent = new int[n];
            size = new int[n];
            help = new int[n];
            for (int i = 0; i < n; i++) {
                parent[i] = i; // 每个顶点默认指向其自身
                size[i] = 1;
            }
        }

        public boolean isConnect(int a,int b) {
            int i = find(a);
            int j = find(b);
            return i == j;
        }

        public int find(int a) {

            int hi = 0;
            // 找到最顶端节点
            while (a != parent[a]) {
                // 记录顶端节点挂载的子节点, 该行代码必须放在 a = parent[a] 之前，否则原始的 a 会被覆盖
                help[hi++] = a;
                a = parent[a];
            }

            // 将由多个节点所形成的链摊平，将复杂度 O(n) 转变为 O(1)
            for (int i = 0; i < hi; i++) {
                parent[help[i]] = a;
            }
            return a;
        }

        public void union(int a, int b) {
            int i = find(a);
            int j = find(b);
            if (i == j) {
                return;
            }

            // 将数量小的集合挂载到数量大的集合上
            if (size[i] > size[j]) {
                parent[j] = i;
                size[i] += size[j];
                size[j] = 0;
            } else {
                parent[i] = j;
                size[j] += size[i];
                size[i] = 0;
            }
        }

    }
}
