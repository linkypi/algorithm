package com.lynch.topology;

import java.util.*;

/**
 * https://leetcode-cn.com/problems/network-delay-time/
 *
 * 有 n 个网络节点，标记为1到 n。
 *
 * 给你一个列表times，表示信号经过 有向 边的传递时间。times[i] = (ui, vi, wi)，
 * 其中ui是源节点，vi是目标节点， wi是一个信号从源节点传递到目标节点的时间。
 *
 * 现在，从某个节点K发出一个信号。需要多久才能使所有节点都收到信号？
 * 如果不能使所有节点收到信号，返回-1 。
 *
 * @author: linxueqi
 * @Description:
 * @createTime: 2022/4/9 23:53
 */
public class FindNetworkDelay {
    public static void main(String[] args) {
        // case 1
//        int[][] arr = {
//                {2, 1, 1}, {2, 3, 1}, {2, 4, 1},
//                {1, 4, 1}, {3, 4, 1}, {3, 5, 1}, {4, 5, 1}
//        };
//        int count = find(arr, 5, 2);
//        int count = findDelayTimeWithDijkstra(arr, 5, 2);

        // case 2
//        int[][] arr = {
//                {1, 2, 1}, {2, 3, 2}, {1, 3, 4}
//        };
//        int count = find(arr, 3, 1);

        // case 3
//        int[][] arr = {
//                {1,2,1},{2,1,3}
//        };
//        int count = find(arr, 2, 2);
        // case 4
//        int[][] arr = {
//                {1,2,1},{2,3,7},{1,3,4},{2,1,2}
//        };
////        int count = find(arr, 3, 1);
//        int count = find(arr, 3, 2);

        // case 5
        int[][] arr = {
                {3, 5, 78}, {2, 1, 1}, {1, 3, 0}, {4, 3, 59}, {5, 3, 85}, {5, 2, 22}, {2, 4, 23},
                {1, 4, 43}, {4, 5, 75}, {5, 1, 15}, {1, 5, 91}, {4, 1, 16}, {3, 2, 98}, {3, 4, 22},
                {5, 4, 31}, {1, 2, 0}, {2, 5, 4}, {4, 2, 51}, {3, 1, 36}, {2, 3, 59}
        };
//        int count = find(arr, 5, 5);
        int count = findDelayTimeWithDijkstra(arr, 5, 5);
        System.out.println("count: " + count);
    }

    static int find(int[][] arr, int n, int k) {
        Map<Integer, List<Integer>> map = new HashMap<>();

        int[][] dp = new int[n + 1][n + 1];

        for (int i = 1; i <= n; i++) {
            dp[k][i] = Integer.MAX_VALUE;
        }

        for (int[] item : arr) {
            int node1 = item[0];
            int node2 = item[1];

            // 移除其他节点到 k 节点的关联，防止递归出现死循环
            if (node2 == k) {
                continue;
            }
            dp[node1][node2] = item[2];
            if (!map.containsKey(node1)) {
                List<Integer> list = new ArrayList<>();
                list.add(node2);
                map.put(node1, list);
            } else {
                map.get(node1).add(node2);
            }
        }
        List<Integer> children = map.get(k);
        // 若没有可以到达的节点则返回
        if (children == null || children.size() == 0) {
            return -1;
        }

        findAdjacent(map, children, k, dp);

        int max = 0;
        for (int i = 1; i <= n; i++) {
            if (i == k) {
                continue;
            }
            max = Math.max(max, dp[k][i]);
        }
        return max == Integer.MAX_VALUE ? -1 : max;
    }

    static void findAdjacent(Map<Integer, List<Integer>> map, List<Integer> children, int k, int[][] dp) {
        for (Integer item : children) {
            if (!map.containsKey(item)) {
                continue;
            }

            List<Integer> integers = map.get(item);
            for (Integer x : integers) {
                // 仅处理路径延迟小的，对于延迟大的无需再递归遍历
                int distance = dp[k][item] + dp[item][x];
                if(distance < dp[k][x]) {
                    dp[k][x] = distance;
                    findAdjacent(map, integers, k, dp);
                }
            }
        }
    }

    static int findDelayTimeWithDijkstra(int[][] arr, int n, int start) {
        Map<Integer, List<Integer>> map = new HashMap<>();

        int[][] dp = new int[n + 1][n + 1];
        // 从 start 节点到 i 节点的最短距离
        int[] dis = new int[n + 1];

        // 初始化数组
        for (int i = 1; i <= n; i++) {
            dp[start][i] = Integer.MAX_VALUE;
            dis[i] = Integer.MAX_VALUE;
        }
        dis[start] = 0;

        // 将已有数据存入邻接矩阵
        for (int[] item : arr) {
            int node1 = item[0];
            int node2 = item[1];

            dp[node1][node2] = item[2];
            if (!map.containsKey(node1)) {
                List<Integer> list = new ArrayList<>();
                list.add(node2);
                map.put(node1, list);
            } else {
                map.get(node1).add(node2);
            }
        }

        // int[] 第一位存节点id ,第二位存起始节点到当前节点的距离
        PriorityQueue<int[]> queue = new PriorityQueue<>((a, b) -> a[1] - b[1]);

        queue.add(new int[]{start, 0});

        while (!queue.isEmpty()) {
            int[] info = queue.poll();
            int nodeId = info[0];
            int distance = info[1];
            // 若 dis 记录的距离比已有的小则跳过
            if (dis[nodeId] < distance) {
                continue;
            }
            if (!map.containsKey(nodeId)) {
                continue;
            }
            List<Integer> adjList = map.get(nodeId);
            // 找到当前节点的所有相邻节点
            for (Integer next : adjList) {
                int curDistance = dis[nodeId] + dp[nodeId][next];
                if (curDistance < dis[next]) {
                    dis[next] = curDistance;
                    queue.add(new int[]{next, curDistance});
                }
            }
        }

        int max = 0;
        for (int i = 0; i <= n; i++) {
            if (i == start || dis[i] == Integer.MAX_VALUE) {
                continue;
            }
            max = Math.max(max, dis[i]);
        }
        return max;
    }

}
