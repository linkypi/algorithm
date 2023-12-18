package com.lynch.hard;

import org.junit.Test;

/**
 * 给定一个数组 Arr, 请返回数组排序后相邻两数的最大差值
 * 要求时间复杂度为 O(N), 不能使用基于比较的排序
 *
 * https://leetcode.cn/problems/maximum-gap/
 *
 * https://www.bilibili.com/video/BV1EG411s7hq/?p=6&spm_id_from=333.880.my_history.page.click&vd_source=b18b986d88b1e28ae58093d2cf0dbf05
 *
 * @author: leo
 * @description:
 * @ClassName: com.lynch.hard
 * @date: 2023/12/15 10:07
 */
public class MaxDifferenceBetweenTwoAdjacentNumbers {

    /**
     * 1. 数组中有 N 个元素, 那么需准备 N+1 个空桶, 桶中记录三个属性: 桶是否有元素, 最大值, 最小值
     * 2. 找出数组的最大值与最小值, 若 min 与 max 想同则直接返回 0, 否则将最大值与最小值的差值做 N+1 等分
     * 3. 开始遍历数组元素, 将每一个元素按 N+1 等分放入每一个桶中, 记录其最大值与最小值, 标记桶有元素
     * 4. 最后遍历 N+1 个桶, 从第一个元素开始, 最终结果就可以从后一个元素的最小值减去当前元素的最大值求出
     */
    @Test
    public void test(){

    }
}
