package com.lynch.disjoint_set;

/**
 * 力扣第 323 题「 无向图中连通分量的数目」：
 *
 * 给你输入一个包含 n 个节点的图，用一个整数 n 和一个数组 edges 表示，
 * 其中 edges[i] = [ai, bi] 表示图中节点 ai 和 bi 之间有一条边。请你计算这幅图的连通分量个数。
 *
 * @author: linxueqi
 * @Description:
 * @createTime: 2022/7/30 14:29
 */
public class UndirectedGraphConnectedComponents {
    public static void main(String[] args) {

    }

    private int countComponents(int n, int[][] edges){
        UnionFind unionFind = new UnionFind(n);
        for(int[] item: edges){
            unionFind.union(item[0], item[1]);
        }
        return unionFind.getSets();
    }

}
