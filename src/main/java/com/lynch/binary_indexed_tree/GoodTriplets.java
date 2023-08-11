package com.lynch.binary_indexed_tree;

import org.junit.Test;

/**
 * https://leetcode.cn/problems/count-good-triplets-in-an-array/description/
 *
 * 给你两个下标从 0 开始且长度为 n 的整数数组 nums1 和 nums2 ，两者都是 [0, 1, ..., n - 1] 的 排列 。
 * 好三元组 指的是 3 个 互不相同 的值，且它们在数组 nums1 和 nums2 中出现顺序保持一致。
 * 换句话说，如果我们将 pos1v 记为值 v 在 nums1 中出现的位置，pos2v 为值 v 在 nums2 中的位置，
 * 那么一个好三元组定义为 0 <= x, y, z <= n - 1 ，且 pos1x < pos1y < pos1z 和 pos2x < pos2y < pos2z 都成立的 (x, y, z) 。
 *
 * 请你返回好三元组的 总数目 。
 *
 * 示例 1：
 *
 * 输入：nums1 = [2,0,1,3], nums2 = [0,1,2,3]
 * 输出：1
 * 解释：
 * 总共有 4 个三元组 (x,y,z) 满足 pos1x < pos1y < pos1z ，分别是 (2,0,1) ，(2,0,3) ，(2,1,3) 和 (0,1,3) 。
 * 这些三元组中，只有 (0,1,3) 满足 pos2x < pos2y < pos2z 。所以只有 1 个好三元组。
 *
 * @Author: linxueqi
 * @Description:
 * @Date: create in 2023/8/8 11:51
 */
public class GoodTriplets {

    @Test
    public void test() {
        int[] num1 = {4, 0, 1, 3, 2};
        int[] num2 = {4, 1, 0, 2, 3};

        long count = goodTriplets(num1, num2);
        System.out.println("count: " + count);
    }

//    public long goodTriplets(int[] nums1, int[] nums2) {
//        int n = nums1.length;
//        int[] p = new int[n];
//        for (int i = 0; i < n; ++i)
//            p[nums1[i]] = i;
//        long ans = 0L;
//        int[] tree = new int[n + 1];
//        for (int i = 1; i < n - 1; ++i) {
//            for (int j = p[nums2[i - 1]] + 1; j <= n; j += j & -j) // 将 p[nums2[i-1]]+1 加入树状数组
//                ++tree[j];
//            int y = p[nums2[i]];
//            int less = 0;
//            for (int j = y; j > 0; j &= j - 1) // 计算 less
//                less += tree[j];
//            ans += (long) less * (n - 1 - y - (i - less));
//        }
//        return ans;
//    }

    static class FenWick {
        int[] c;
        int n;

        public FenWick(int n) {
            this.n = n + 10;
            c = new int[n + 10];
        }

        public void add(int x, int val) {
            // 树状数组直接后继节点为 c[x + lowbit(x)]，也即其父节点
            // 更新某个节点的同时，需要不断更新其父节点
            for (; x < n; x += lowbit(x)) {
                c[x] += val;
            }
        }

        /**
         * 查询前 x 个元素的和
         *
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

        public int lowbit(int x) {
            return x & -x;
        }
    }

    public long goodTriplets(int[] nums1, int[] nums2) {
        long res = 0;
        int n = nums1.length;
        int[] map = new int[n];

        for (int i = 0; i < n; i++) {
            map[nums2[i]] = i + 1;
        }

        FenWick fw = new FenWick(n);
        for (int i = 0; i < n; i++) {
            int tmp = map[nums1[i]]; // 对应nums1在nums2的相对位置
            int l = fw.query(tmp);   // 查询相对位置tmp前面出现的个数
            int r = n - tmp - (i - l); // l表示左边出现个数, (i - l)就是出现在右边的个数
            res += 1L * l * r;
            fw.add(tmp, 1);
        }
        return res;
    }

}
