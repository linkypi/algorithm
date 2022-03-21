package com.lynch;

/**
 * 有一些拍成一行的正方形，每个正方形已经被染成红色或绿色，现在可以
 * 选择任意一个正方形然后用这两种颜色的任意一种进行染色，这个正放心的颜色
 * 将会被覆盖。目标是在完成染色后，每个红色都比每个绿色距离左侧都近。
 * 返回最少需要涂几个正方形。
 * 如 RGRGR , 涂染后为 RRRGG 即满足要求，涂然个数为 2
 * @author: linxueqi
 * @Description:
 * @createTime: 2022/3/21 22:07
 */
public class RGRG {
    public static void main(String[] args) {

    }

    private static int find(String str) {
        char[] arr = str.toCharArray();

        int n = arr.length;

        // 记录从倒数第i个位置到末尾位置右边有多少个R
        int rCount = 0;
        for (int i = n - 2; i >= 0; i--) {
            rCount += arr[i] == 'R' ? 1 : 0;
        }

        // 由于目标要求左侧全为 R，右侧全为 G
        // 所以可以分左右两侧来处理，左侧存在G的则需要涂为R，右侧出现R的则需要涂为G
        // 从左侧开始遍历，不断扩大左侧，同时右侧也会相应缩小，在每次遍历中记录下左右
        // 两侧各自需要涂抹数量的总和。最后求出每次遍历总和的最小值即为答案
        // 但是需要注意 1. 左侧没有右侧全包含 及 2. 左侧全包含右侧没有 的边界情况

        // 默认开始位置左边没有，右边全是全体（全是G），
        // 此时需要涂的数量就是 rCount
        int result = rCount;

        int left = 0;
        // 注意这里循环终止条件是 n-2，因为 n-1 是左边为全体，右边没有的情况，需要单独处理
        for (int i = 0; i < n - 1; i++) {
            left += arr[i] == 'G' ? 1 : 0;
            rCount -= arr[i] == 'R' ? 1 : 0;
            //需要涂的数量就是 左边G的数量及右边R的数量
            result = Math.min(result, left + rCount);
        }

        // 处理左边是全体，右边没有的情况，
        result = Math.min(result, result + (arr[n - 1] == 'G' ? 1 : 0));
        return result;
    }
}
