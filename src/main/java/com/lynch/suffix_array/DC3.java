package com.lynch.suffix_array;

import java.util.Arrays;

/**
 * @author: lynch
 * @description:
 * @date: 2024/2/2 0:13
 */

// 为了计算一个字符串或一组数字的后缀排名，如 "abca" 对应的后缀子串有："a","ca","bca","abca", 为求出所有子串对应的字典序
// （升序）排名通常需要 O(N^2*lgN)的复杂度，其中O(N)用于拆分子串，得到子串后还需要使用排序算法来得到最终结果，而排序算法最快
// 也需要 O(N*lgN) 的复杂度，所以其复杂度实在太高，难以接受。为了可以更快求出所有后缀子串的字典序排名，所以有了DC3算法，其使
// 用了类似分治的算法思想将本应复杂的计算过程巧妙的拆分为小单元计算，最后再合并得到最终结果。
//
// 具体流程是，将子串分为3个字符为一组，其中下标模3余0的记为S0，下标模3余1的记为S1，下标模3余2的记为S2. 首先假设我们已经求得
// S1与S2的排名，那么S0的排名就能通过S12的排名来获得，因为S0是下标模3余0的字符，其后面紧跟的是S1及S2的排名，若S0排名相同则可以
// 通过已经计算好的S1来获得排名，若S1排名再相同那么最终可以通过S2来获取到最终排名。所以问题最终就转换为：S12的排名如何求得？
//
// 求解S12排名：因为流程已经将字符串拆分为3个一组，即可以理解为一组三维数据（不足三个的后面会补充最小的数，通常补0），为了
// 求出他们的排名既可以轮流比较每一维度的大小, 哪个数字小就排在前面. 而使用基数排序可以更快的完成所有维度的数据排序，因为基数
// 排序本身就是基于桶排序，并且属于稳定排序。为了使得三维数据排序结果更加稳定，所以首先从最后一维逆序来计算最终排名。在前面分析
// 得知S0可能存在重复排名，同样在S12也同样会存在重复排名。如字符串 "mississippi", 其S12对应下标是 [1,2,4,5,7,8,10],
// 经过基数排序后得到排名为 [10,7,1,4,8,2,5], 即下标为10的三个子串排在第一位，下标为7的三个子串排在第二位，以此类推
// index:  0  1  2  3  4  5  6  7  8  9  10
// string: m  i  s  s  i  s  s  i  p  p  i
// 对应的子串分别是(其中下标10不足三个的后面会补0):
//  index   substr   rank
//   10     i00       1    S1
//    7     ipp       2    S1
//    1     iss       3    S1  <-- 重复
//    4     iss       3    S1  <-- 重复
//    8     ppi       4    S2
//    2     ssi       5    S2  <-- 重复
//    5     ssi       5    S2  <-- 重复
//
// 到此，S12排名就出现了重复，为了解决重复问题，可以将S12分组，然后再次递归求解。具体做法是：将S1与S2的排名组成一个新数组，
// S1的按索引顺序放在左边，S2的索引顺序放在右边（S1与S2中的索引就算没有安装顺序放置，实际结果还是不变）。以此得到一个排名数组
//               S1        S2
// old index:  1 4 7 10   2 5 8
//      rank:  3 3 2  1   5 5 4
// 这么做的巧妙之处就是在相同排名下如何获得后面更多信息来决策处最终排名，如索引1与索引4的排名都是3，对应子串是 "iss" ,
// 而他们为了决策处最终排名，此时就需要索引1子串后面的信息，其后面的信息刚好就是索引4的子串。同样，索引4子串后面的信息
// 就是索引7的子串。即将他们的信息缩小为排名然后再以排名来计算最终排名。另外，就算排名相同的两个子串分别处于S1和S2，其
// 计算的结果仍然正确
//               S1        S2
// old index:  1 4 7 10   2 5 8
//  old rank:  3 3 2  1   5 5 4
//  new rank:  4 3 2  1   7 6 5 <--
//

public class DC3 {
    public int[] sa;

    public int[] rank;

    public int[] height;

    // 构造方法的约定:
    // 数组叫nums，如果你是字符串，请转成整型数组nums
    // 数组中，最小值必须 >= 1
    // 如果不满足，处理成满足的，也不会影响使用
    // max, nums里面最大值是多少， max 决定了基数排序中桶的数量
    public DC3(int[] nums, int max) {
        sa = sa(nums, max);
        rank = rank();
        height = height(nums);
    }

    private int[] sa(int[] nums, int max) {
        int n = nums.length;
        // 在排序的时候需要处理字符串的末尾，需要在原始数组的末尾添加两个额外的字符（通常是 0）
        // 这两个额外的字符的作用是用于保证所有的字符都参与到排序过程中，同时也用于处理边界情况
        // 因为模3最多剩下2，也就是最多需要补充两个额外的字符就能保证边界处可以得到完整的三个数
        int[] arr = new int[n + 3];
        for (int i = 0; i < n; i++) {
            arr[i] = nums[i];
        }
        return skew(arr, n, max);
    }

    private int[] skew(int[] nums, int n, int K) {
        // 模3余0的个数
        int n0 = (n + 2) / 3;
        // 模3余1的个数
        int n1 = (n + 1) / 3;
        // 模3余2的个数
        int n2 = n / 3;
        int n02 = n0 + n2;
//            int n02 = n1 + n2;

        // 用于存放子串的排名，sa12[i] 表示子串索引，下标i表示排名。
        // 注意此时得到的排名可能重复，如不同索引下标出现了同样的子串 "ssi"，
        // 此时他们的排名应该是一样的, 所以仍需处理排名重复的情况
        int[] sa12 = new int[n02 + 3];
        // 用于存放基于基数排序后的排名，排名可能存在重复，若存在重复则需再次递归排名
        int[] s12 = new int[n02 + 3];

        // 找到 S12 所有索引
        for (int i = 0, j = 0; i < n + (n0 - n1); ++i) {
            if (0 != i % 3) {
                s12[j++] = i;
            }
        }
        System.out.printf(" dc3, before radix sort: %s\n", Arrays.toString(s12));
        radixPass(nums, s12, sa12, 2, n02, K);
        radixPass(nums, sa12, s12, 1, n02, K);
        radixPass(nums, s12, sa12, 0, n02, K);
        System.out.printf(" dc3, after radix sort: %s\n", Arrays.toString(sa12));

        // 至此，sa12 存放的就是S12第一次基数排序后的排名结果，下标为排名，对应元素为字符串的索引值
        // 接下来判断sa12排名是否存在重复，并将排名结果放到按照S1（即模3余1的索引）在前，S2（即模3余2的索引）
        // 在后的组合形式放入到 s12 中，以便在出现重复时可以使用s12继续递归求解
        // 使用三个字符判断前后两个子串是否相同，子串相同则排名相同，最后得出各个子串的排名
        int name = 0, c0 = -1, c1 = -1, c2 = -1;
        for (int i = 0; i < n02; ++i) {
            if (c0 != nums[sa12[i]] || c1 != nums[sa12[i] + 1] || c2 != nums[sa12[i] + 2]) {
                name++;
                c0 = nums[sa12[i]];
                c1 = nums[sa12[i] + 1];
                c2 = nums[sa12[i] + 2];
            }
            // 将排名按S12索引顺序记录在s12中
            //           S1       S2
            // index: 1 4 7 10   2 5 8
            if (1 == sa12[i] % 3) {
                // 处理S1部分排名
                s12[sa12[i] / 3] = name;
            } else {
                // 存放S2部分排名，由于s12左边已经存放了S1排名，所以存放S2时必须加上左边S1部分的长度
                s12[sa12[i] / 3 + n0] = name;
            }
        }

        if (name < n02) {
            // 排名有重复则递归求解
            System.out.printf(" dc3, before radix re-sort: %s\n", Arrays.toString(s12));
            sa12 = skew(s12, n02, name);
            for (int i = 0; i < n02; i++) {
                s12[sa12[i]] = i + 1;
            }
        } else {
            // Example:
            // index: 0 1 2 3 4  <- S1-S2组合而成的数组索引下标
            // S1-S2: 1 4 7 2 5  <- [S1|S2] S1在前 S2在后
            //   s12: 3 5 1 2 4  <- S1-S2组合经过基数排序后的结果排名， 当前索引对应的是S1-S2的索引，s12[i] 才是具体排名
            //  sa12: 2 3 0 4 1  <- sa12记录的就是S1-S2组合中排名对应的索引值，即sa12[i]为S1-S2组合中的索引，i为具体排名
            for (int i = 0; i < n02; i++) {
                sa12[s12[i] - 1] = i;
            }
        }
        System.out.printf(" dc3, s12 final range: %s\n", Arrays.toString(s12));
        // 求出s0的排名
        // 为什么此处可以直接使用S12求出的排名来求解S0，为什么没有考虑模3余0时的各个字符的大小情况？？？
        // 经过自我实现后才最终明白，此处是利用了S12的排名来直接计算结果。正如前面计算S12排名一样，通常
        // 需要三个维度逐维度计算，而到此S12已经有结果，所有可以直接使用S12的结果作为第0维的计算入参即可
        int[] s0 = new int[n0], sa0 = new int[n0];
        for (int i = 0, j = 0; i < n02; i++) {
            if (sa12[i] < n0) {// 找到索引小于
                s0[j++] = 3*sa12[i];
            }
        }
        System.out.printf(" dc3, before sort s0: %s\n", Arrays.toString(s0));
        radixPass(nums, s0, sa0, 0, n0, K);
        System.out.printf(" dc3, after sort s0: %s\n", Arrays.toString(sa0));
        System.out.printf(" dc3, before merge s0: %s\n", Arrays.toString(sa0));
        System.out.printf(" dc3, before merge s12: %s\n", Arrays.toString(s12));


        // 最后使用归并算法求出总排名
        int[] sa = new int[n];
        for (int p = 0, t = n0 - n1, k = 0; k < n; k++) {
            int i = sa12[t] < n0 ? sa12[t] * 3 + 1 : (sa12[t] - n0) * 3 + 2;
            int j = sa0[p];
            if (sa12[t] < n0 ? leq(nums[i], s12[sa12[t] + n0], nums[j], s12[j / 3])
                    : leq(nums[i], nums[i + 1], s12[sa12[t] - n0 + 1], nums[j], nums[j + 1], s12[j / 3 + n0])) {
                sa[k] = i;
                t++;
                if (t == n02) {
                    for (k++; p < n0; p++, k++) {
                        sa[k] = sa0[p];
                    }
                }
            } else {
                sa[k] = j;
                p++;
                if (p == n0) {
                    for (k++; t < n02; t++, k++) {
                        sa[k] = sa12[t] < n0 ? sa12[t] * 3 + 1 : (sa12[t] - n0) * 3 + 2;
                    }
                }
            }
        }
        return sa;
    }

    private void radixPass(int[] nums, int[] input, int[] output, int offset, int n, int k) {
        int[] cnt = new int[k + 1];
        for (int i = 0; i < n; ++i) {
            int num = nums[input[i] + offset];
            cnt[num]++;
        }
        for (int i = 0, sum = 0; i < cnt.length; ++i) {
            int t = cnt[i];
            cnt[i] = sum;
            sum += t;
        }
        for (int i = 0; i < n; ++i) {
            int num = nums[input[i] + offset];
            output[cnt[num]++] = input[i];
        }
    }

    private void radixPass2(int[] nums, int[] input, int[] output, int offset, int n, int k) {
        int[] cnt = new int[k + 1];
        for (int i = 0; i < n; ++i) {
            int num = nums[input[i] + offset];
            cnt[num]++;
        }
        for (int i = 0, sum = 0; i < cnt.length; ++i) {
            int t = cnt[i];
            cnt[i] = sum;
            sum += t;
        }
        for (int i = 0; i < n; i++) {
            int num = nums[input[i] + offset];
            int x = --cnt[num];
            output[x] = input[i];
        }
    }

    private boolean leq(int a1, int a2, int b1, int b2) {
        return a1 < b1 || (a1 == b1 && a2 <= b2);
    }

    private boolean leq(int a1, int a2, int a3, int b1, int b2, int b3) {
        return a1 < b1 || (a1 == b1 && leq(a2, a3, b2, b3));
    }

    private int[] rank() {
        int n = sa.length;
        int[] ans = new int[n];
        for (int i = 0; i < n; i++) {
            ans[sa[i]] = i;
        }
        return ans;
    }

    private int[] height(int[] s) {
        int n = s.length;
        int[] ans = new int[n];
        for (int i = 0, k = 0; i < n; ++i) {
            if (rank[i] != 0) {
                if (k > 0) {
                    --k;
                }
                int j = sa[rank[i] - 1];
                while (i + k < n && j + k < n && s[i + k] == s[j + k]) {
                    ++k;
                }
                ans[rank[i]] = k;
            }
        }
        return ans;
    }
}
