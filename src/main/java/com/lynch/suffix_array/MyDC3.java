package com.lynch.suffix_array;

import org.junit.Test;

import java.util.*;
import java.util.logging.Level;

/**
 * @author: lynch
 * @description:
 * @date: 2024/2/2 0:11
 */
public class MyDC3 {

    @Test
    public void TestDC3(){
        String str = "mississippi";
        MyDC3 customDC3 = new MyDC3();
        customDC3.set(str);
        System.out.printf("custom dc3: %s\n",Arrays.toString(customDC3.sa));
    }

    public MyDC3(){}

    public int[] sa;
    public int[] rank;

    /**
     * 该入参仅接收由大小写英文字母组成的字符串
     * @param str
     */
    public void set(String str) {
        int n = str.length();
        // 处理边界问题，为确保模3余2的数也可以参与到运算中
        int count = n + 3;
        int max = 0;
        int[] nums = new int[count];
        for (int i = 0; i < str.length(); i++) {
            // 为方便基数排序无需分配过多的内存空间, 统一将所有字符都减去 a
            nums[i] = str.charAt(i) - 'a';
            if (max < nums[i]) {
                max = nums[i];
            }
        }
        this.sa = skew(nums, n, max);
    }

    /**
     *
     * @param nums 后面补充0
     * @param n 实际有效长度
     * @param max
     * @return
     */
    private int[] skew(int[] nums, int n, int max) {
        // 下标模3余0的个数
        int n0 = (n + 2) / 3;
        int n1 = (n + 1) / 3;
        int n2 = n / 3;
        int n12 = n1 + n2;

        // 为处理边界问题，他们长度都需加上3，防止越界
        int[] s12 = new int[n12 + 3];
        int[] sa12 = new int[n12 + 3];

        // 找到 S12 下标, 基于基数排序, 首先求出S12排名
        for (int i = 0, j = 0; i < n; i++) {
            if (i % 3 != 0) {
                s12[j++] = i;
            }
        }

        System.out.printf(" custom dc3, before radix sort: %s\n", Arrays.toString(s12));
        sa12 = radixSort(nums, s12, n12, 2, max);
        s12 = radixSort(nums, sa12, n12, 1, max);
        sa12 = radixSort(nums, s12, n12, 0, max);

        System.out.printf(" custom dc3, after radix sort: %s\n", Arrays.toString(sa12));
        // 判断排名是否存在重复, 即是否存在相同的子串
        int range = 0;
        int c0 = -1, c1 = -1, c2 = -1;
        for (int i = 0; i < n12; i++) {
            int index = sa12[i];
            if (c0 != nums[index] || c1 != nums[index + 1] || c2 != nums[index + 2]) {
                range++;
                c0 = nums[index];
                c1 = nums[index + 1];
                c2 = nums[index + 2];
            }
            // 将排名结果放到 S12, S1 在前,S2 在后
            if (sa12[i] % 3 == 1) {
                s12[sa12[i] / 3] = range;
            } else {
                s12[sa12[i] / 3 + n1] = range;
            }
        }

        // 存在重复则递归重排
        if (range < n12) {
            System.out.printf(" custom dc3, before radix re-sort: %s\n", Arrays.toString(s12));
            sa12 = skew(s12, n12, range);
            // 将结果转换为实际下标对应的排名，以便后面归并排序
            for (int i = 0; i < n12; i++) {
                int index12 = 3 * i + 1;
                if (index12 > n) {
                    index12 = 3 * (i - n1) + 2;
                }
                s12[sa12[i]] = index12;
            }
        } else {
            // 还原S12排名
            for (int i = 0; i < n12; i++) {
                s12[i] = sa12[i];
            }
        }
        System.out.printf(" custom dc3, s12 final range: %s\n", Arrays.toString(s12));

        // 求出S0排名
        // 此处是利用了S12的排名来直接计算结果。正如前面计算S12排名一样，通常
        // 需要三个维度逐维度计算，而到此S12已经有结果，所有可以直接使用S12的结果作为第0维的计算入参即可
        int[] s0 = new int[n0];
        for (int i = 0, j = 0; i < n; i++) {
            if (i % 3 < n1) {
//                s0[j++] = s12[];
            }
        }

        // 首先对比s0的首个字符的排名
        System.out.printf(" custom dc3, before sort s0: %s\n", Arrays.toString(s0));
        int[] sa0 = radixSort(nums, s0, n0, 0, max);

        for (int i = 0; i < n0; i++) {
            s0[i] = sa0[i];
        }

        System.out.printf(" custom dc3, after sort s0: %s\n", Arrays.toString(s0));
        System.out.printf(" custom dc3, before merge s0: %s\n", Arrays.toString(s0));
        System.out.printf(" custom dc3, before merge s12: %s\n", Arrays.toString(s12));

        // 合并 S0 与 S12 排名
        int[] sa = new int[n];
        range = 0;
        int i = 0, j = 0;
        for (; i < n0 && j < n12; ) {
            int index0 = s0[i];
            int index12 = s12[j];
            boolean done= false;
            // 头一个字符相同则依次往后对比,最多比较三次即可得到结果
            while (nums[index0] == nums[index12]) {
                index0++;
                index12++;
                // 只要一个出现在 S0 区别，那么只能逐个字符比较，无法利用S12结果比较
                if (index0 % 3 == 0 || index12 % 3 == 0) {
                    continue;
                }

                int r0 = -1;
                for (int k = 0; k < n0; k++) {
                    if (s12[k] == index0) {
                        r0 = k;
                        break;
                    }
                }
                int r12 = -1;
                for (int k = 0; k < n12; k++) {
                    if (s12[k] == index12) {
                        r12 = k;
                        break;
                    }
                }
                if (r0 < r12) {
                    sa[range++] = index0 - 1;
                    i++;
                } else {
                    sa[range++] = index12 - 1;
                    j++;
                }
                done = true;
            }
            if (done){
                continue;
            }
            if (nums[index0] < nums[index12]) {
                sa[range++] = s0[i];
                i++;
            } else {
                sa[range++] = s12[j];
                j++;
            }
        }

        while (i < n0) {
            sa[range++] = s0[i++];
        }
        while (j < n12) {
            sa[range++] = s12[j++];
        }

        return sa;
    }

    /**
     * 使用基数排序时,其内部的元素不能存在负数,否则会出错
     * 若使用场景存在负数,则需要将负数转正后再排序, 可以统一将所有数减去数组中最小的负数,待排序完成后再通过该负数还原
     * @param nums 原始数组
     * @param arr 待排序的索引下标
     * @param offset 三个元素一组, offset 表示对第几个元素进行排序
     * @param max 待排序数组中的最大值
     * @return
     */
    private int[] radixSort2(int[] nums, int[] arr, int n, int offset, int max) {
        int[] count = new int[max + 1];

        for (int i = 0; i < n; i++) {
            try {
                int num = nums[arr[i] + offset];
                count[num]++;
            } catch (Exception ex) {
                System.out.print(ex);
            }
        }

        for (int i = 1; i < count.length; i++) {
            count[i] += count[i - 1];
        }

        int[] result = new int[n+3];
        for (int i = 0; i < n; i++) {
            int num = nums[arr[i] + offset];
            int x = --count[num];
            result[x] = arr[i];
        }
        return result;
    }

    private int[] radixSort(int[] nums, int[] input, int n, int offset,  int max) {
        int[] cnt = new int[max + 1];
        for (int i = 0; i < n; ++i) {
            int num = nums[input[i] + offset];
            cnt[num]++;
        }
        for (int i = 0, sum = 0; i < cnt.length; ++i) {
            int t = cnt[i];
            cnt[i] = sum;
            sum += t;
        }
        int[] result = new int[n+3];
        for (int i = 0; i < n; ++i) {
            int num = nums[input[i] + offset];
            result[cnt[num]++] = input[i];
        }
        return result;
    }
}
