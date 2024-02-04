package com.lynch.suffix_array;

import org.junit.Test;

import java.util.*;

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
        // important !!!
        // 使用n02作为S12的长度是因为考虑到后面S0会基于S1来计算，即S1的长度必须与S0长度匹配，
        // 而实际S0的个数很有可能大于S1的个数。故为了处理此边界问题，可以直接使用S0的长度来代替S1的长度，
        // 即使S1的长度没有S0长，S1同样的可以通过后面补0来的方式来处理，其结果对最终的排名并未影响
        int n02 = n0 + n2;

        // 为处理边界问题，他们长度都需加上3，防止越界
        int[] s12 = new int[n02 + 3];
        int[] sa12 = new int[n02 + 3];

        // 找到 S12 下标, 基于基数排序, 首先求出S12排名
        //  important !!! 同样是为配合后面S0的求解，长度必须多出 n0-n1 个 即S1的长度必须与S0长度匹配，不匹配的则补0
        for (int i = 0, j = 0; i < n + (n0 - n1); i++) {
            if (i % 3 != 0) {
                s12[j++] = i;
            }
        }

        System.out.printf(" custom dc3, before radix sort: %s\n", Arrays.toString(s12));
        sa12 = radixSort(nums, s12, n02, 2, max);
        s12 = radixSort(nums, sa12, n02, 1, max);
        sa12 = radixSort(nums, s12, n02, 0, max);

        System.out.printf(" custom dc3, after radix sort: %s\n", Arrays.toString(sa12));
        // 判断排名是否存在重复, 即是否存在相同的子串
        int range = 0;
        int c0 = -1, c1 = -1, c2 = -1;
        for (int i = 0; i < n02; i++) {
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
                // S1|S2 由于n1的长度已经使用n0代替，故此处同样需改为 n0
                s12[sa12[i] / 3 + n0] = range;
            }
        }

        // 存在重复则递归重排
        if (range < n02) {
            System.out.printf(" custom dc3, before radix re-sort: %s\n", Arrays.toString(s12));
            sa12 = skew(s12, n02, range);
            for (int i = 0; i < n02; i++) {
                int index = i * 3 + 1;
                if (index >= n + n0 - n1) {
                    index = (i - n0) * 3 + 2;
                }
                s12[sa12[i]] = index;
            }
        } else {
            int[] temp = new int[n02];
            for (int i = 0; i < n02; i++) {
                int index = i * 3 + 1;
                if (index >= n + n0 - n1) {
                    index = (i - n0) * 3 + 2;
                }
                temp[s12[i] - 1] = index;
            }
            s12 = temp;
        }

        System.out.printf(" custom dc3, s12 final range: %s\n", Arrays.toString(s12));

        // 求出S0排名
        // 此处是利用了S12的排名来直接计算结果。正如前面计算S12排名一样，通常
        // 需要三个维度逐维度计算，而到此S12已经有结果，所有可以直接使用S12的结果作为第0维的计算入参即可
        // 所以此处需要对0 3 6 9 等模3余0的索引顺序根据S12的结果来排名，随后才可以做基数排序
        int[] s0 = new int[n0];
        // 仅需遍历 S1 区域得到排名
        int x = 0;
        for (int j = 0; j < n02; j++) {
            if (s12[j] % 3 == 1) {
                s0[x++] = s12[j] - 1;
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

        // 由于前面为了使用S12结果里求解S0，所以S12实际长度需要补充 n0-n1
        // 但最终结果需要移除多余的这几个数，因为这几个数都是0值，故后面的排名必定排在最前面
        // 最后的结果只要移除这几个数即可
        int[] temp = new int[n + n0 - n1];
        range = 0;
        int i = 0, j = 0;
        for (; i < n0 && j < n02; ) {
            int index0 = s0[i];
            int index12 = s12[j];
            if (nums[index0] < nums[index12]) {
                temp[range++] = index0;
                i++;
                continue;
            }
            if (nums[index0] > nums[index12]) {
                temp[range++] = index12;
                j++;
                continue;
            }

            int i0 = index0, i12 = index12;
            // 头一个字符相同则依次往后对比,最多比较三次即可得到结果
            while (nums[i0] == nums[i12]) {
                i0++;
                i12++;
                // 只要一个出现在 S0 区别，那么只能逐个字符比较，无法利用S12的结果比较
                if (i0 % 3 == 0 || i12 % 3 == 0) {
                    if (nums[i0] < nums[i12]) {
                        temp[range++] = index0;
                        i++;
                        break;
                    }
                    if (nums[i0] > nums[i12]) {
                        temp[range++] = index12;
                        j++;
                        break;
                    }
                    continue;
                }

                int r0 = 0, r12 = 0;
                for (int k = 0; k < n02; k++) {
                    if (s12[k] == i0) {
                        r0 = k;
                    }
                    if (s12[k] == i12) {
                        r12 = k;
                    }
                }
                if (r0 < r12) {
                    temp[range++] = index0;
                    i++;
                } else {
                    temp[range++] = index12;
                    j++;
                }
                break;
            }
        }

        while (i < n0) {
            temp[range++] = s0[i++];
            i++;
        }
        while (j < n02) {
            temp[range++] = s12[j++];
        }
        int[] sa = new int[n];
        // 移除开头的几个补0的数
        for (int k = n0 - n1, idx = 0; k < n + n0 - n1; k++) {
            sa[idx++] = temp[k];
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
