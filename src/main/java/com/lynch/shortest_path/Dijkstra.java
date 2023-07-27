package com.lynch.shortest_path;

import java.util.*;

/**
 * Dijkstra算法用于求最短路径，注意区别于 Prim 算法，
 * 虽然两者解法相差不大，但Dijkstra求解的是所有路径中的最短路径，
 * 而 Prim算法是确保权重总和最小的情况下保证所有节点连通
 * @Author: linxueqi
 * @Description:
 * @Date: create in 2023/3/22 11:41
 */
public class Dijkstra {
    public static void main(String[] args) {

        int[][] graph = {
                {0, 3, 7},
                {0, 4, 999},
                {0, 1, 5},
                {1, 2, 3},
                {2, 3, 8},
                {3, 4, 7},
        };
        int[] paths = find(0, graph);
        System.out.println("paths: ");
    }

//    private PriorityQueue<Info> priorityQueue = new PriorityQueue<>((a, b) -> {
//        return a.distinct - b.distinct;
//    });

    public static class Info {
        // 记录起始节点到当前节点的最小路径和
        private int distant;
        private List<Integer> list;

        public Info(int start) {
            this.distant = Integer.MAX_VALUE;
            this.list = new ArrayList<>();
            this.list.add(start);
        }
    }

    private static int[] find(int x, int[][] graph) {
        Map<Integer, List<Integer>> adj = new HashMap<>();
        Map<String, Integer> weight = new HashMap<>();

        // 记录各个节点的出度
        boolean[] ouDegree = new boolean[graph.length];

        // 初始化邻接表
        for (int[] item : graph) {
            Integer start = item[0];
            if (adj.containsKey(start)) {
                adj.get(start).add(item[1]);
            } else {
                List<Integer> list = new ArrayList<>();
                list.add(item[1]);
                adj.put(start, list);
            }

            ouDegree[start] = true;
            weight.put(start + "" + item[1], item[2]);
        }

        // 找到终止节点
        int endPoint = 0;
        for (int i = 0; i < ouDegree.length; i++) {
            if (!ouDegree[i]) {
                endPoint = i;
                break;
            }
        }

        Queue<Integer> queue = new LinkedList<>();
        queue.offer(x);

        // distant[i] 表示起始节点到当前节点 i 的最短距离
        Info[] minDis = new Info[graph.length];
        for (int i = 0; i < graph.length; i++) {
            minDis[i] = new Info(x);
        }

        while (!queue.isEmpty()) {
            int item = queue.poll();

            // 根据邻接表获取到相邻节点集合逐一遍历
            List<Integer> children = adj.get(item);

            if(children == null){
                continue;
            }
            for (Integer it : children) {
                Info info = minDis[item];
                int current = info.distant == Integer.MAX_VALUE ? 0 : info.distant;
                // 获取起始节点到当前节点it的权重
                current = current + weight.get(item + "" + it);
                // 记录权重最小值
                if(minDis[it].distant > current){
                    minDis[it].distant = current;
                    // 遇到更小权重的路径则替换
                    minDis[it].list = new ArrayList<>(info.list);
                    minDis[it].list.add(it);
                }
            }

            // 邻接节点都遍历完成 再将邻接节点入队，以便再次遍历其子节点
            for (Integer it : children) {
                queue.offer(it);
            }
        }

        return minDis[endPoint].list.stream().mapToInt(Integer::valueOf).toArray();
    }

}
