package com.lynch.graph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * 给你一个有n个节点的 有向无环图（DAG），请你找出所有从节点 0到节点 n-1的路径并输出（不要求按特定顺序）
 * graph[i]是一个从节点 i 可以访问的所有节点的列表（即从节点 i 到节点graph[i][j] 存在一条有向边）。
 *
 * @Author: linxueqi
 * @Description:
 * @Date: create in 2022/4/7 17:31
 */
public class FindAllPaths {

    public static void main(String[] args) {
//        graph = [[1,2],[3],[3],[]]
        int[][] graph = {
                {1, 2},
                {3}, {3}, {}
        };
        List<List<Integer>> lists = allPathsSourceTarget(graph);
    }

    // 记录所有路径
    static List<List<Integer>> res = new LinkedList<>();

    public static List<List<Integer>> allPathsSourceTarget(int[][] graph) {
        // 维护递归过程中经过的路径
//        LinkedList<Integer> path = new LinkedList<>();
//        traverse(graph, 0, path);

        LinkedList<Integer> path2 = new LinkedList<>();
        List<List<Integer>> lists = find(graph, 0, path2);
        return lists;
    }

    /* 图的遍历框架 */
    static void traverse(int[][] graph, int index, LinkedList<Integer> path) {
        // 添加节点 s 到路径
        path.addLast(index);

        int n = graph.length;
        if (index == n - 1) {
            // 到达终点
            res.add(new LinkedList<>(path));
            // 可以在这直接 return，但要 removeLast 正确维护 path
            // path.removeLast();
            // return;
            // 不 return 也可以，因为图中不包含环，不会出现无限递归
        }

        // 递归每个相邻节点
        for (int v : graph[index]) {
            traverse(graph, v, path);
        }

        // 从路径移出节点 s
        path.removeLast();
    }

    static List<List<Integer>> find(int[][] graph, int index, LinkedList<Integer> path) {
        path.addLast(index);

        if (index == graph.length - 1) {
            List<List<Integer>> list = new ArrayList<>();
            list.add(new ArrayList<>(path));
            // 返回前需要清空当前路径
            path.removeLast();
            return list;
        }
        List<List<Integer>> lists = new ArrayList<>();
        for (int item : graph[index]) {
            List<List<Integer>> temp = find(graph, item, path);
            lists.addAll(temp);
        }
        path.removeLast();
        return lists;
    }
}
