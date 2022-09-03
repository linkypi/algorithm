package com.lynch.disjoint_set;

/**
 * 有 n 个城市，其中一些彼此相连，另一些没有相连。如果城市 a 与城市 b 直接相连，
 * 且城市 b 与城市 c 直接相连，那么城市 a 与城市 c 间接相连。
 * 省份 是一组直接或间接相连的城市，组内不含其他没有相连的城市。
 * 给你一个 n x n 的矩阵 isConnected ，其中 isConnected[i][j] = 1
 * 表示第 i 个城市和第 j 个城市直接相连，而 isConnected[i][j] = 0 表示二者不直接相连。
 * 返回矩阵中 省份 的数量
 * @author: linxueqi
 * @Description:
 * @createTime: 2022/7/28 23:16
 */
public class FriendCycles {
    public static void main(String[] args) {
        int[][] matrix = {
                {1, 1, 1, 0, 0},
                {0, 1, 1, 1, 1},
                {0, 0, 1, 0, 0},
                {1, 0, 0, 1, 0},
                {1, 1, 0, 1, 1}
        };
    }

    public static int find(int[][] arr) {
        UnionFind unionFind = new UnionFind(arr.length);
        for (int i = 0; i < arr.length; i++) {
            for (int j = i + 1; j < arr[i].length; j++) {
                if (arr[i][j] == 1) {
                    unionFind.union(i, j);
                }
            }
        }
        return unionFind.getSets();
    }
}
