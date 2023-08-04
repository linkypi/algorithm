package com.lynch.min_spanning_tree;

import org.junit.Test;

import java.util.*;

/**
 * Prim 算法，最小生成树算法实现之一
 * 注意区别于 Dijkstra 算法，虽然两者解法相差不大，但 Dijkstra 求解的是所有路径中的最短路径，
 * 而 Prim算法是确保在权重总和最小的情况下让所有节点都连通
 * @Author: linxueqi
 * @Description:
 * @Date: create in 2023/7/27 11:12
 */
public class Prim {

    @Test
    public void testPrim(){
        int[][] metrics = {
                {1, 2, 5}, // 1 到 2 的权重为 5
                {1, 3, 2},
                {2, 3, 6}
        };

        int minCostWithPrim = minCostWithPrim(metrics);
        System.out.println("min cost: "+ minCostWithPrim);
    }

    private int minCostWithPrim(int[][] metrics) {

        // 记录每个节点的邻接节点
        HashMap<Integer, List<Edge>> adjs = new HashMap<>();
        for (int[] item : metrics) {
            int from = item[0];
            int to = item[1];
            int w = item[2];
            initAdj(adjs, from, to, w);
            initAdj(adjs, to, from, w);
        }

        // 记录已加入的节点数，防止重复加入
        Set<Integer> set = new HashSet<>();

        PriorityQueue<Edge> queue = new PriorityQueue<Edge>((a, b) -> a.weight - b.weight);
        // 随机选择一个端点加入
        List<Edge> edges = adjs.get(metrics[0][0]);
        for (Edge item : edges) {
            queue.offer(item);
        }
        set.add(metrics[0][0]);

        int min = 0;
        while (!queue.isEmpty()) {
            Edge edge = queue.poll();

            // 已访问过的节点则跳过
            if (set.contains(edge.to)) {
                continue;
            }
            min += edge.weight;

            set.add(edge.to);
            List<Edge> edges1 = adjs.get(edge.to);
            for (Edge item : edges1) {
                queue.offer(item);
            }
        }
        return min;
    }

    private void initAdj(HashMap<Integer, List<Edge>> adjs, int from, int to, int w) {
        List<Edge> edges = new ArrayList<>();
        Edge edge = new Edge(from, to, w);
        if (adjs.containsKey(from)) {
            edges = adjs.get(from);
            if (edges == null) {
                edges = new ArrayList<>();
            }
            edges.add(edge);
        } else {
            edges.add(edge);
            adjs.put(from, edges);
        }
    }

    public class Edge{
        private int from;
        private int to;
        private int weight;

        public Edge(int from, int to , int weight){
            this.from = from;
            this.to = to;
            this.weight = weight;
        }
    }
}
