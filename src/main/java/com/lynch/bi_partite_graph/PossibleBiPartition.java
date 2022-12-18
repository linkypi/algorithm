package com.lynch.bi_partite_graph;

import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

/**
 * @Author: linxueqi
 * @Description:
 * 给定一组 N 人(编号为 1，2，...，)， 我们想把每个人分进任意大小的两组
 * 每个人都可能讨厌其他人，那么他们不应该属于同一组。
 * 形式上，如果 dislikes[i] = [a，b]，表示 a 和 b 互相讨厌对方，不应该把他们分进同一组。
 * 当可以将所有人分进两组时，返回 true ;否则返回 false。
 * 示例 1:
 * 输入:N = 4，dislikes = [[1,2],[1,3],[2,4]] 输出: true
 * 解释: group1 [1,4]，group2 [2,3]
 * 示例2:
 * 输入:N = 3，dislikes = [[1,2],[1,3],[2,3]] 输出: false
 * @Date: create in 2022/12/17 12:18
 */
public class PossibleBiPartition {

    @Test
    public void test() {
        int[][] dislikes = {{1, 2}, {1, 3}, {2, 3}};
        boolean result = isBipartite(3, dislikes);
        System.out.println("result: "+ result);
    }

    @Test
    public void test2() {
        int[][] dislikes = {{1, 2}, {1, 3}, {2, 4}};
        boolean result = isBipartite(4, dislikes);
        System.out.println("result: " + result);
    }
    
    boolean[] visited = null;
    boolean[] color = null;
    boolean ok = true;

    public boolean isBipartite(int n, int[][] dislikes) {
        visited = new boolean[n+1];
        color = new boolean[n+1];

        List<Integer>[] graph = buildGraph(n, dislikes);
        for (int i = 1; i <= n; i++) {
            if (!visited[i]) {
                traverse(graph, i);
            }
        }
        return ok;
    }

    /**
     * 将两人之间的不喜欢关系构建为二分图
     * @param n
     * @param dislikes
     * @return
     */
    public List<Integer>[] buildGraph(int n, int[][] dislikes){
        List<Integer>[] graph = new LinkedList[n+1];
        for(int i=1;i<=n;i++){
            graph[i] = new LinkedList<>();
        }
        for(int[] edge: dislikes){
            int v = edge[1];
            int w = edge[0];
            graph[v].add(w);
            graph[w].add(v);
        }
        return graph;
    }

    public void traverse(List<Integer>[] graph, int v) {
        if (!ok) {
            return;
        }

        visited[v] = true;
        for (int neighbor : graph[v]) {
            if (visited[neighbor]) {
                if (color[neighbor] == color[v]) {
                    ok = false;
                    return;
                }
            } else {
                color[neighbor] = !color[v];
                traverse(graph, neighbor);
            }
        }
    }
}
