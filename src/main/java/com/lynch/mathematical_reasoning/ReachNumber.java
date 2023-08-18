package com.lynch.mathematical_reasoning;

import org.junit.Test;

/**
 * https://leetcode.cn/problems/reach-a-number/
 *
 * 在一根无限长的数轴上，你站在 0 的位置。终点在 target 的位置。
 *
 * 你可以做一些数量的移动 numMoves :
 *
 * 每次你可以选择向左或向右移动。
 * 第 i 次移动（从  i == 1 开始，到 i == numMoves ），在选择的方向上走 i 步。
 * 给定整数 target ，返回 到达目标所需的 最小 移动次数 (即最小 numMoves ) 。
 *
 * 示例 1:
 * 输入: target = 2
 * 输出: 3
 * 解释:
 * 第一次移动，从 0 到 1 。
 * 第二次移动，从 1 到 -1 。
 * 第三次移动，从 -1 到 2 。
 *
 * @Author: linxueqi
 * @Description:
 * @Date: create in 2023/8/14 10:22
 */
public class ReachNumber {

    @Test
    public void test(){
        int number = reachNumber(2);
        System.out.println("number: "+ number);
    }

    /**
     * 通过分析可知，每次走的步数正式等差为1的递增数列，假设向右走的数是正数, 其累加和为 P，
     * 那么向右走的数就是负数，其不带符合的累加和为 N，那么问题就可以转变为在等差数列中变化正负号，
     * 以使得其累加和为 target，即 P - N = target。假设 P + N = S，那么两式相减必有 S-target=2N
     * 即 S-target 必为偶数，因为 S 是可以通过等差数列求和公式求得：i*(i+1)/2
     * 最终的问题就转变为 求出最小的 i 使得 i*(i+1)/2 - target 为偶数即可
     * @param target
     * @return
     */
    public int reachNumber(int target) {
        int i = 1;
        // 向左或者向右呈对称关系，故只需计算其中一种即可
        target = Math.abs(target);
        int x = i * (i + 1) / 2 - target;
        // 因为仅需要考虑正数情况，故需注意 x 小于 0的情况
        while (x % 2 != 0 || x < 0) {
            i++;
            x = i * (i + 1) / 2 - target;
        }
        return i;
    }

}
