package com.lynch.dp;

import java.util.Arrays;

/**
 * https://leetcode.cn/problems/race-car/
 *
 * 你的赛车可以从位置 0 开始，并且速度为 +1 ，在一条无限长的数轴上行驶。赛车也可以向负方向行驶。
 * 赛车可以按照由加速指令 'A' 和倒车指令 'R' 组成的指令序列自动行驶。
 * 当收到指令 'A' 时，赛车这样行驶：
 *    position += speed
 *    speed *= 2
 * 当收到指令 'R' 时，赛车这样行驶：
 *    如果速度为正数，那么 speed = -1
 *    否则 speed = 1
 *    当前所处位置不变。
 * 例如，在执行指令 "AAR" 后，赛车位置变化为 0 --> 1 --> 3 --> 3 ，速度变化为 1 --> 2 --> 4 --> -1 。
 *
 * 给你一个目标位置 target ，返回能到达目标位置的最短指令序列的长度。
 *
 * 示例 1：
 *     输入：target = 3
 *     输出：2
 *     解释：
 *     最短指令序列是 "AA" 。
 *     位置变化 0 --> 1 --> 3 。
 *
 * @Author: linxueqi
 * @Description:
 * @Date: create in 2023/6/7 16:54
 */
public class RacingCar {
    public static void main(String[] args) {
        int result = find(6);
        System.out.println("result: "+ result);
    }

    // 从速度speed与位置position中找到两者关系, position = 2^n - 1
    //    step:  0 1 2 3  4  5
    //    speed: 1 2 4 8 16 32
    // position: 0 1 3 7 15 31
    // 赛车走到 target位置需要的最少指令数可以分三种情况
    // 1. 直接走 n 步就可以到达 target, 即使用 n 个A指令即可到达
    // 2. 走完 n 步后，未越过 target 位置，此种情况之所以讨论回头是因为在第一种情况已经讨论直达的情况，
    //    直达的情况下指令数最少。只有无法直到的情况下才会讨论该情况！！！
    //    走完 n 步后，未越过 target 位置，基于该情况分析此时不能再往前，只能先回退再往前。
    //    至于回退多少步目前未知，可以设置回退的步数为 back, 那么回退back步后，
    //    需要再走 x = target - (2^n-1) + (2^back-1) 距离才可以到达 target
    //    那么该种情况总共需要走的步数为： (n+1) + (back+1) + f(x) , 这里假设 f(x) 为走x距离需要的最少指令数
    // 3. 走完 n 步后，越过了 target 位置，此时需要返回走 x = (2^n-1)-target 距离才可到达 target
    //    故该种情况需要走的步数为：(n+1) + f(x)
    // 综上三种情况，可以令 dp[i] 为走到i位置需要的最少指令数，那么三种情况的dp如下
    //    1. dp[i] = n
    //    2. dp[i] = min(dp[i], n + 1 + back + 1 + dp[target - (2^n-1) + (2^back-1)]);
    //       其中  dp[target - (2^n-1) + (2^back-1)] 需要通过计算方才知道
    //    3. dp[i] = min(dp[i], n + 1 + dp[(2^n-1)-i])
    public static int find(int target) {

        if (target <= 0) {
            return 0;
        }

        int[] dp = new int[target + 1];
        Arrays.fill(dp, Integer.MAX_VALUE);

        for (int i = 1; i <= target; i++) {
            // 此处有一个base case， 即 2^n -1 < 2*i
            // 该判断是由公式推导得出(该推导有异议，因为第x-1步所在的位置有可能在i之后！！)：假设走第 x 步的距离刚好超过 i, 即 2^x - 1 >= i,
            // 那么必然存在 x-1 步时当前位置处在 i 以内，即 2^(x-1) -1 < i
            // 故推导出 2^x - 1 < 2*i + 1。
            for (int forward = 1; (1 << forward) - 1 < 2 * i; forward++) {
                int forwardDistance = (1 << forward) - 1;
                // 走了 forward 直达
                if (forwardDistance == i) {
                    dp[i] = forward;
                } else if (forwardDistance > i) { // 针对第三种情况 越过了 i
                    dp[i] = Math.min(dp[i], forward + 1 + dp[forwardDistance - i]);
                } else {
                    //  dp[i] = min(dp[i], forward + 1 + back + 1 + dp[target - (2^forward-1) + (2^back-1)]);
                    // back 回头走的步数必定小于之前走过的 forward 步，要不赛车已回到原始位置
                    for (int backward = 0; backward < forward; backward++) {
                        int backwardDistance = ( 1 << backward) - 1;
                        dp[i] = Math.min(dp[i], forward + 1 + backward + 1 + dp[i - forwardDistance + backwardDistance]);
                    }
                }
            }
        }

        return dp[target];
    }

}
