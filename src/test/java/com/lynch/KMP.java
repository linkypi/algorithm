package com.lynch;

/**
 * 给定一个字符串 str 及 一个匹配串 match ,
 * 求match串在str出现的起始位置，没有则返回 -1
 * @author: linxueqi
 * @Description:
 * @createTime: 2022/4/6 22:34
 */
public class KMP {
    public static void main(String[] args) {
         String str = "aabaastaabaade";
        int[] nextArray = getNextArray(str);
        int index = find(str, "aade");
        System.out.println("index: "+ index);
    }

    /**
     *
     * @param str
     * @param match
     * @return
     */
    private static int find(String str, String match) {
        if (str == null || str.length() == 0) {
            return -1;
        }
        if (match == null || match.length() == 0) {
            return -1;
        }

        int[] nextArray = getNextArray(match);
        int x = 0, y = 0;
        char[] arr1 = str.toCharArray();
        char[] arr2 = match.toCharArray();
        while (x < str.length() && y < match.length()) {
            if (arr1[x] == arr2[y]) {
                x++;
                y++;
                continue;
            }

            // match 串已经跳到了0的位置，说明当前已经无法匹配下去
            // 只能调整 str 从下一个字符开始匹配
            if (y == 0) {
                x++;
            } else {
                // 该位置字符不匹配则从下一个位置开始匹配
                y = nextArray[y];
            }
        }

        // 1. x 越界， y 未越界 即 str 已匹配完但match仍未匹配完，故返回 -1
        // 2. x 未越界， y 越界，说明match已经匹配完成，对应的起始位置就是 x 减去match当前匹配的长度 y
        // 3. x 越界， y 越界, 如 match="aab" str="ertgaab" 两者最后同时越界
        return y == match.length() ? x - y : -1;
    }

    /**
     * 求字符串每个位置前最长前缀与后缀的公共长度的数组
     * @param match
     * @return
     */
    private static int[] getNextArray(String match) {
        if (match == null || match.length() == 0) {
            return null;
        }

        if(match.length() ==1){
            return new int[]{ -1 };
        }

        int[] next = new int[match.length()];
        next[0] = -1;
        next[1] = 0;

        int index = 2;
        char[] arr = match.toCharArray();

        // x 表示往前跳的位置信息
        int x = next[index - 1];

        while (index < match.length()) {
            // 若 index位置的字符与 x位置字符不匹配则继续从next数组获取下一个往前的位置
            // 直到往前跳到 next数组的首个元素 -1 后没有
            if (x > -1 && arr[x] != arr[index - 1]) {
                x = next[x];
            } else {
                // 若两者位置所在字符匹配则最长前缀与后缀的长度为 前一个加一
                next[index++] = x + 1;
                x = next[index - 1];
            }
        }

        return next;
    }
}
