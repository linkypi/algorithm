package com.lynch.random;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Random;

/**
 * 给你一个整数数组 nums ，设计算法来打乱一个没有重复元素的数组。打乱后，数组的所有排列应该是 等可能 的。
 *
 * 实现 Solution class:
 *
 * Solution(int[] nums) 使用整数数组 nums 初始化对象
 * int[] reset() 重设数组到它的初始状态并返回
 * int[] shuffle() 返回数组随机打乱后的结果
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode.cn/problems/shuffle-an-array
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 * 
 * @Author: linxueqi
 * @Description:
 * @Date: create in 2023/6/13 17:48
 */
public class ShuffleArray {
    public static void main(String[] args) {

    }

    /**
     * shuffle 算法的核心在于能等概率的产生所有可能的排列情况。
     * 所有的排列是什么样的呢？其实学过初等数学的一定知道排列组合这样的基本问题。对 n 个不同元素进行排列，
     * 我们可以从第一位开始枚举，选择 1～n 中的某一个，然后进行第二位的枚举，选择剩下的 n-1 个中的某一个... 直到选取最后一个元素。
     * 这样的排列总数为 n!.
     *
     * 那 shuffle 的时候如何保证等可能的选择其中某一种呢？ 第一直觉可能是对这 n! 排列方式的每一种遍一个号，
     * 从 n! 中随机选取一个编号，找到对应的方案即可。如何编号呢，也很简单，我们可以直接按照字典序排列。
     *
     * 但如果你从排列的方式思考一下从 n! 中选随机数的方案，另一种符合直觉的方式其实也很容易想到。
     * n! = n * (n-1)! 这里的 n 就是你先挑选出第一个元素的种类数；然后 (n-1)! 就是对其他元素的排列。
     * 所以我们要选一种洗牌方案，就可以先等概率的从 n 个元素中挑选一个作为第一个元素；然后再对剩下的 (n-1) 个
     * 元素作类似的选择。这样就相当于把 n! 分成 n 段，先选择其中一段，里面有 (n-1)! 个元素，我们把这 (n-1)!
     * 个情况分成 (n-1) 段，再随机选一个，以此类推。 这样的策略是可以做到从 (n-1)! 中随机选数的
     *
     * 我们如何严格证明这样的洗牌算法是等概率的呢？ 其实我们只要看每个位置出现各个元素的概率是不是都是 1/n 即可。
     * 我们来看第一个位置，由于我们是从 n 个元素中随机选择一个，每个元素出现的概率显然是 1/n。
     * 而对于第二个位置，我们要考虑这个元素没有出现在之前的选择中，然后是从剩下 n-1 个元素中随机选择一个，
     * 所以任意一个元素出现的概率是 ((n-1)/n) * (1/(n-1)) = 1/n 。
     * 同样，第三个位置，任意元素出现的概率应该保证前面两个位置都没有选中，那是 (n-1/n)*(n-2/n-1)*(1/n-2) = 1/n 。
     * 依次递推，所以你会发现每个位置任意元素出现的概率都是相等的， 1/n 。 这就可以严格的证明我们的算法是正确的。
     *
     * 作者：wfnuser
     * 链接：https://leetcode.cn/problems/shuffle-an-array/solution/wei-rao-li-lun-jing-dian-xi-pai-suan-fa-11ona/
     * 来源：力扣（LeetCode）
     * 著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
     */
    public static class Solution {
        private int[] nums = null;
        private Random random = null;

        public Solution(int[] nums) {
            this.nums = nums;
            random = new Random(nums.length);
        }

        public int[] reset() {
            return this.nums;
        }

        public int[] shuffle() {
            int n = nums.length;
            int[] copy = Arrays.copyOf(nums, n);

            for (int i = 0; i < n; i++) {
                int index = i + random.nextInt(n - i);
                swap(copy, i, index);
            }
            return copy;
        }

        private void swap(int[] arr, int i, int j) {
            int temp = arr[i];
            arr[i] = arr[j];
            arr[j] = temp;
        }
    }
}
