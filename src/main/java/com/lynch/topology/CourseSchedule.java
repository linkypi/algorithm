package com.lynch.topology;

import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * https://leetcode.cn/problems/course-schedule/
 * @Author: linxueqi
 * @Description:
 * 你这个学期必须选修 numCourses 门课程，记为 0 到 numCourses - 1 。
 * 在选修某些课程之前需要一些先修课程。 先修课程按数组 prerequisites 给出，
 * 其中 prerequisites[i] = [ai, bi] ，表示如果要学习课程 ai 则 必须 先学习课程  bi 。
 *
 * 例如，先修课程对 [0, 1] 表示：想要学习课程 0 ，你需要先完成课程 1 。
 * 请你判断是否可能完成所有课程的学习？如果可以，返回 true ；否则，返回 false 。
 *
 * 输入：numCourses = 2, prerequisites = [[1,0]]
 * 输出：true
 * 解释：总共有 2 门课程。学习课程 1 之前，你需要完成课程 0 。这是可能的。
 *
 * @Date: create in 2022/12/21 22:39
 */
public class CourseSchedule {

    boolean[] visited = null;
    boolean[] onPath = null;
    boolean canFinish = true;

    @Test
    public void test(){
        int[][] pre = {{0,1},{1,0}};
        boolean result = canFinish2(2, pre);
        System.out.println(result);
    }

    public boolean canFinish2(int n, int[][] preRequisites) {
        visited = new boolean[n];
        onPath = new boolean[n];
        int[][] graph = buildGraphArr(n, preRequisites);

        for (int i = 0; i < n; i++) {
            traverse2(graph, i);
            if (!canFinish) {
                return false;
            }
        }
        return canFinish;
    }

    public void traverse2(int[][] graph, int node) {
        if (onPath[node]) {
            canFinish = false;
            return;
        }

        if (visited[node] && canFinish) {
            return;
        }
        visited[node] = true;
        onPath[node] = true;
        int[] children = graph[node];
        for (int i = 0; i < children.length; i++) {
            traverse2(graph, children[i]);
        }
    }

    public boolean canFinish(int n, int[][] preRequisites){
        visited = new boolean[n];
        onPath = new boolean[n];
        List<List<Integer>> graph = buildGraph(n, preRequisites);

        for(int i = 0;i<n;i++){
            traverse(graph, i);
        }
        return canFinish;
    }

    public void traverse(List<List<Integer>> graph, int node) {
        if (onPath[node]) {
            canFinish = false;
            return;
        }

        if (visited[node] && canFinish) {
            return;
        }
        visited[node] = true;
        onPath[node] = true;
        List<Integer> children = graph.get(node);
        for (int i = 0; i < children.size(); i++) {
            traverse(graph, children.get(i));
        }
    }

    /**
     * 根据课程依赖关系构建成图结构
     * @param n
     * @param preRequisites
     * @return
     */
    public List<List<Integer>> buildGraph(int n, int[][] preRequisites) {

        List<List<Integer>> graph = new ArrayList<>();
//        int[][] graph = new int[n][];
        for (int i = 0; i < n; i++) {
            graph.set(i, new ArrayList<>());
        }
        for (int index = 0; index < n; index++) {
            int a = preRequisites[index][0];
            int dependOn = preRequisites[index][1];
            graph.get(dependOn).add(a);
        }
        return graph;
    }

    public int[][] buildGraphArr(int n, int[][] preRequisites) {

        int[][] graph = new int[n][];
        for (int i = 0; i < n; i++) {
            graph[i] = new int[n];
        }
        int[] size = new int[n];
        for (int index = 0; index < n; index++) {
            int a = preRequisites[index][0];
            int dependOn = preRequisites[index][1];
            graph[dependOn][size[dependOn]] = a;
            size[dependOn] ++;
        }
        return graph;
    }
}
