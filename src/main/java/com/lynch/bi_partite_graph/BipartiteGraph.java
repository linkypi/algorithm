package com.lynch.bi_partite_graph;

import org.junit.Test;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @Author: linxueqi
 * @Description: 二分图判定
 * 在给定的「图」中，请用两种颜色将图中的所有顶点着色，且使得任意一条边的两个端点的颜色都不相同
 * 实际应用,如存储电影演员和电影之间的关系, 若使用哈希表存储,则需要两个哈希表分别存储演员到电影列表的映射
 * 以及 每部电影到演员列表的映射关系. 而使用图结构存储,将电影与参演的演员连接, 自然就会成为一副二分图.
 * 每个电影节点的相邻节点就是参演该电影的所有演员, 每个演员的相邻节点就是该演员参演过的所有电影.
 * @Date: create in 2022/12/17 10:13
 */
public class BipartiteGraph {
    @Test
    public void test1() {
        // 邻接表
        int[][] graph = {
                {1, 2, 3},
                {0, 2},
                {0, 1, 3},
                {0, 2}
        };

        boolean result = Bipartite.isBipartite(graph);
        boolean result2 = BipartiteBaseOnBFS.isBipartite(graph);
        System.out.println("is bipartite graph: "+ result);
    }

    @Test
    public void test2() {
        // 邻接表
        int[][] graph = {
                {1, 3},
                {0, 2},
                {1, 3},
                {0, 2}
        };

        boolean result = Bipartite.isBipartite(graph);
        boolean result2 = BipartiteBaseOnBFS.isBipartite(graph);
        System.out.println("is bipartite graph: "+ result);
    }

    public static class Bipartite {
        static boolean[] visited = null;
        static boolean[] color = null;
        static boolean ok = true;

        public static boolean isBipartite(int[][] graph) {
            int n = graph.length;
            visited = new boolean[n];
            color = new boolean[n];

            for (int i = 0; i < n; i++) {
                if (!visited[i]) {
                    traverse(graph, i);
                }
            }
            return ok;
        }

        public static void traverse(int[][] graph, int v) {

            if (!ok) {
                return;
            }

            visited[v] = true;
            int[] neighbors = graph[v];
            for (int neighbor : neighbors) {
                if (visited[neighbor]) {
                    // 节点已访问过,则比较节点颜色与V颜色是否相同, 若相同则不是二分图
                    if (color[neighbor] == color[v]) {
                        ok = false;
                        return;
                    }
                } else {
                    // 节点未访问过
                    color[neighbor] = !color[v];
                    traverse(graph, neighbor);
                }
            }
        }
    }

    /**
     * 基于广度优先便利的二分图判定
     */
    public static class BipartiteBaseOnBFS {
        static boolean[] visited = null;
        static boolean[] color = null;
        static boolean ok = true;

        public static boolean isBipartite(int[][] graph) {
            int n = graph.length;
            visited = new boolean[n];
            color = new boolean[n];

            for (int i = 0; i < n; i++) {
                if (!visited[i]) {
                    bfs(graph, i);
                }
            }
            return ok;
        }

        public static void bfs(int[][] graph, int start) {

            Queue<Integer> q = new LinkedList<>();
            q.offer(start);
            visited[start] = true;

            while(!q.isEmpty()){
                Integer node = q.poll();
                for (int neighbor : graph[node]) {
                    if (visited[neighbor]) {
                        // 节点已访问过,则比较节点颜色与V颜色是否相同, 若相同则不是二分图
                        if (color[neighbor] == color[node]) {
                            ok = false;
                            return;
                        }
                    } else {
                        // 节点未访问过
                        color[neighbor] = !color[node];
                        visited[neighbor] = true;
                        q.offer(neighbor);
                    }
                }
            }
        }
    }
}
