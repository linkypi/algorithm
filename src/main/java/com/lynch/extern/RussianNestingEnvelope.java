package com.lynch.extern;

import java.util.Arrays;

/**
 *  https://leetcode-cn.com/problems/russian-doll-envelopes/
 * 给你一个二维整数数组 envelopes ，其中 envelopes[i] = [wi, hi] ，表示第 i 个信封的宽度和高度。
 * 当另一个信封的宽度和高度都比这个信封大的时候，这个信封就可以放进另一个信封里，如同俄罗斯套娃一样。
 * 请计算 最多能有多少个 信封能组成一组 “俄罗斯套娃” 信封（即可以把一个信封放到另一个信封里面）。
 * 注意：不允许旋转信封。
 *
 * 示例 1：
 *
 * 输入：envelopes = [[5,4],[6,4],[6,7],[2,3]]
 * 输出：3
 * 解释：最多信封的个数为 3, 组合为: [2,3] => [5,4] => [6,7]。
 * 示例 2：
 *
 * 输入：envelopes = [[1,1],[1,1],[1,1]]
 * 输出：1
 *
 * @Author: linxueqi
 * @Description:
 * @Date: create in 2022/5/7 16:12
 */
public class RussianNestingEnvelope {
    public static void main(String[] args) {
        int[][] envelopes = {{5, 4}, {6, 4}, {6, 7}, {2, 3}};
    }

    public static int maxEnvelopes(int[][] envelopes) {
        Arrays.sort(envelopes, (a, b) -> {
            if (a[0] != b[0]) {
                return a[0] - b[0];
            }
            // 宽度相同则按高度降序排序
            return b[1] - a[1];
        });
        return 0;
    }
}
