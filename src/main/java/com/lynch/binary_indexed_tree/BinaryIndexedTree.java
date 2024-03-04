package com.lynch.binary_indexed_tree;

/**
 * @Author: linxueqi
 * @Description:
 * @Date: create in 2023/8/9 10:34
 */
public class BinaryIndexedTree {
    int n = 0;
    int[] c = null;

    public BinaryIndexedTree(int n) {
        this.n = n+1;
        c = new int[n+1];
    }

    public int lowbit(int x) {
        return x & -x;
    }

    /**
     * 对数组某个位置进行更新
     * 某个节点的后继节点为其所有祖先节点
     * 树状数组直接后继节点为 c[x + lowbit(x)]，也即其父节点
     * 更新某个节点的同时，需要不断更新其父节点
     * @param x
     * @param value
     */
    public void add(int x, int value) {
        for (; x <= n; x += lowbit(x)) {
            c[x] += value;
        }
    }

    /**
     * 查询数组前 x 个数的和
     * 某个节点的前驱节点为其左侧所有子树的根
     * 树状数组直接前驱节点为 c[x - lowbit(x)]
     * 由于 c[x] 可以存储的区间个数为 2^k (k为 x 的二进制表示末尾有 k 个连续的0，可通过lowbit函数计算)
     * 故查询数组前 x 个数的和就等价于求 c[x] 的所有前驱节点的累加和
     * @param x
     * @return
     */
    public int query(int x) {
        int res = 0;
        for (; x > 0; x -= lowbit(x)) {
            res += c[x];
        }
        return res;
    }
}
