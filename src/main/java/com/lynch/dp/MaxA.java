package com.lynch.dp;

/**
 * 假设你有一个特殊的键盘包含下面的按键：
 *
 * A：在屏幕上打印一个 'A'。
 * Ctrl-A：选中整个屏幕。
 * Ctrl-C：复制选中区域到缓冲区。
 * Ctrl-V：将缓冲区内容输出到上次输入的结束位置，并显示在屏幕上。
 * 现在，你可以 最多 按键 n 次（使用上述四种按键），返回屏幕上最多可以显示 'A' 的个数 。
 *
 * 示例 1:
 *
 * 输入: n = 3
 * 输出: 3
 * 解释:
 * 我们最多可以在屏幕上显示三个'A'通过如下顺序按键：
 * A, A, A
 * 示例 2:
 *
 * 输入: n = 7
 * 输出: 9
 * 解释:
 * 我们最多可以在屏幕上显示九个'A'通过如下顺序按键：
 * A, A, A, Ctrl A, Ctrl C, Ctrl V, Ctrl V
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode.cn/problems/4-keys-keyboard
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @Author: linxueqi
 * @Description:
 * @Date: create in 2023/6/12 17:38
 */
public class MaxA {
    public static void main(String[] args) {

        // 待完成
    }
    
    public static int maxA(int n) {

        // dp[i] 表示按键i次最多显示A的数量
        // 要想显示的A最多 那必然存在这样的组合
        // 1. A ，表示打印 x 个A
        // 2. Ctrl-A + Ctrl-C + Ctrl-V，
        //   进行一次复制粘贴， 此时屏幕上共有 2x 个A，按键次数增加 3,
        //   此处假设进行了 y 次的全选复制粘贴， 那么共操作了 3*y
        // 3. Ctrl-V 再次粘贴，此时屏幕上共有 3x 个 A, 假设此处一共进行了 z 次操作
        // 即必然存在以上三个步骤的组合，至少需要五次按键才可以完成一次完整的操作
        // x + 3*y + z = n, x + 2^y*x + 2^y*x*z  最大
        // dp[i]
        int[] dp = new int[n + 1];

        dp[0] = 1;

        for (int i = 1; i <= n; i++) {
            for (int j = 0; j < 4; j++) {
                // A
                if (j == 0) {
                    dp[i] += 1;
                }
                // Ctrl-A + Ctrl-C + Ctrl-V
                if (j == 1) {
                    dp[i] = 2 * dp[i - 1];
                    i += 2;
                }
            }
        }
        return 0;
    }
}
