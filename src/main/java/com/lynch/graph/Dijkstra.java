package com.lynch.shortest_path;

import org.junit.Test;

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

    @Test
    public void test() {
//        int[][] times = new int[][]{
//                {4, 2, 76}, {1, 3, 79}, {3, 1, 81}, {4, 3, 30}, {2, 1, 47}, {1, 5, 61}, {1, 4, 99},
//                {3, 4, 68}, {3, 5, 46}, {4, 1, 6}, {5, 4, 7}, {5, 3, 44}, {4, 5, 19}, {2, 3, 13},
//                {3, 2, 18}, {1, 2, 0}, {5, 1, 25}, {2, 5, 58}, {2, 4, 77}, {5, 2, 74},
//        };
//        int n = 5;
//        int k = 3;
        int[][] times = new int[][]{
                {1, 2, 1}, {2, 3, 2}, {1, 3, 1}
        };
        int n = 3;
        int k = 2;

        int result = new Solution().networkDelayTime(times, n, k);
//        int result = new Solution2().networkDelayTime(times, n, k);
        System.out.println(result);
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


    static class Solution {
        static int INTMAX = 0x3f3f3f3f;

        /* Dijkstra优先队列优化 */
        public int networkDelayTime(int[][] times, int n, int k) {
            // 建图 - 邻接表
            Map<Integer, Map<Integer, Integer>> mp = new HashMap<>();
            for (int[] edg : times) {
                if (!mp.containsKey(edg[0]))
                    mp.put(edg[0], new HashMap<>());
                mp.get(edg[0]).put(edg[1], edg[2]);
            }
            // 记录结点最早收到信号的时间
            int[] r = new int[n + 1];
            for (int i = 1; i <= n; ++i)
                r[i] = INTMAX;

            // 队列中存放 [结点，收到信号时间]
            PriorityQueue<int[]> s = new PriorityQueue<>((a, b) -> a[1] - b[1]);
            s.add(new int[]{k, 0});

            while (!s.isEmpty()) {
                int[] cur = s.poll();
                if (r[cur[0]] != INTMAX)
                    continue;
                r[cur[0]] = cur[1];
                if (mp.containsKey(cur[0])) {
                    for (int v : mp.get(cur[0]).keySet()) {
                        // 仅当结点未收到才更新并入队
                        s.add(new int[]{v, mp.get(cur[0]).get(v) + cur[1]});
                    }
                }
            }

            int minT = -1;
            for (int i = 1; i <= n; ++i)
                minT = Math.max(minT, r[i]);
            return minT == INTMAX ? -1 : minT;
        }
    }

    static class Solution2 {
        int N = 110, M = 6010;
        // 邻接表
        int[] he = new int[N], e = new int[M], ne = new int[M], w = new int[M];
        // dist[x] = y 代表从「源点/起点」到 x 的最短距离为 y
        int[] dist = new int[N];
        // 记录哪些点已经被更新过
        boolean[] vis = new boolean[N];
        int n, k, idx;
        int INF = 0x3f3f3f3f;
        void add(int a, int b, int c) {
            e[idx] = b;
            ne[idx] = he[a];
            he[a] = idx;
            w[idx] = c;
            idx++;
        }
        public int networkDelayTime(int[][] ts, int _n, int _k) {
            n = _n; k = _k;
            // 初始化链表头
            Arrays.fill(he, -1);
            // 存图
            for (int[] t : ts) {
                int u = t[0], v = t[1], c = t[2];
                add(u, v, c);
            }
            // 最短路
            dijkstra();
            // 遍历答案
            int ans = 0;
            for (int i = 1; i <= n; i++) {
                ans = Math.max(ans, dist[i]);
            }
            return ans > INF / 2 ? -1 : ans;
        }
        void dijkstra() {
            // 起始先将所有的点标记为「未更新」和「距离为正无穷」
            Arrays.fill(vis, false);
            Arrays.fill(dist, INF);
            // 只有起点最短距离为 0
            dist[k] = 0;
            // 使用「优先队列」存储所有可用于更新的点
            // 以 (点编号, 到起点的距离) 进行存储，优先弹出「最短距离」较小的点
            PriorityQueue<int[]> q = new PriorityQueue<>((a,b)->a[1]-b[1]);
            q.add(new int[]{k, 0});
            while (!q.isEmpty()) {
                // 每次从「优先队列」中弹出
                int[] poll = q.poll();
                int id = poll[0], step = poll[1];
                // 如果弹出的点被标记「已更新」，则跳过
                if (vis[id]) continue;
                // 标记该点「已更新」，并使用该点更新其他点的「最短距离」
                vis[id] = true;
                for (int i = he[id]; i != -1; i = ne[i]) {
                    int j = e[i];
                    if (dist[j] > dist[id] + w[i]) {
                        dist[j] = dist[id] + w[i];
                        q.add(new int[]{j, dist[j]});
                    }
                }
            }
        }
    }
}
