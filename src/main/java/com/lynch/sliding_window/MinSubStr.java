package com.lynch.sliding_window;

/**
 * 求最小覆盖子串
 * 给定一个字符串 S="ADBECFEBANC" , T="ABC" , 求在S中找到包含T中全部字母的最短子串。
 */
public class MinSubStr {
    public static void main(String[] args) {
//        String source = "ADBAXECFEBANC";
//        String target = "ABC";

//        String source = "ab";
//        String target = "b";

        String source = "oharaop";
        String target = "aop";
        String minSubStr = getMinSubStr(source, target);
        int x = find(source, target);
        System.out.print("min sub string: " + minSubStr);
    }

    private static String getMinSubStr(String source, String target) {

        int[] need = new int[128];
        int[] window = new int[128];
        for (char item : target.toCharArray()) {
            need[item] ++;
        }
        int matchCount = 0;
        int left = 0, right = 0;
        int min = Integer.MAX_VALUE;
        int start = 0;

        while (right < source.length()) {
            char current = source.charAt(right);
            // 不符合要求的字符直接忽略
            if (need[current] == 0) {
                right++;
                continue;
            }
            //当且仅当已有字符串目标字符出现的次数小于目标字符串字符的出现次数时，count才会+1
            //是为了后续能直接判断已有字符串是否已经包含了目标字符串的所有字符，不需要挨个比对字符出现的次数
            if (window[current] < need[current]) {
                matchCount++;
            }
            //已有字符串中目标字符出现的次数+1
            window[current]++;
            //移动右指针
            right++;
            //已经包含所有需要寻找的字符则开始缩小窗口
            while (matchCount == target.length()) {
                //比较窗口长度，遇到更小的窗口则更新
                if (right - left < min) {
                    min = right - left;
                    start = left;
                }
                char item = source.charAt(left);
                //对不符合的字符跳过
                if (need[item] == 0) {
                    left++;
                    continue;
                }
                //如果左边即将要去掉的字符被目标字符串需要，且出现的频次正好等于指定频次，那么如果去掉了这个字符，
                //就不满足覆盖子串的条件，此时要破坏循环条件跳出循环，即控制目标字符串指定字符的出现总频次(count）-1
                if (window[item] == need[item]) {
                    matchCount--;
                }
                window[item]--;
                left++;
            }
        }
        if(min == Integer.MAX_VALUE){
            return "";
        }
        // substring 内部实现导致 end 必须加 1
        return source.substring(start, start + min);
    }

    /**
     * 来自左神题解
     * @param source
     * @param target
     * @return
     */
    private static int find(String source, String target) {
        int[] count = new int[128];
        for (int i = 0; i < target.length(); i++) {
            count[target.charAt(i)]++;
        }

        // 记录无效字符串个数
        int invalidTimes = 0;

        int n = target.length();
        int len = source.length();
        int right = 0;
        for (right = 0; right < n; right++) {
            char x = source.charAt(right);
            if (count[x] <= 0) {
                invalidTimes++;
            }
            count[x]--;
        }

        for (; right < len; right++) {
            // 已收集到所有有效字符即可返回起始位置索引
            if (invalidTimes == 0) {
                return right - n;
            }

            // 从右边开始扩大窗口, 若遇到无效字符则记录
            char x = source.charAt(right);
            if (count[x]-- <= 0) {
                invalidTimes++;
            }

            // 从左边开始收缩窗口, 若出现的字符经过++操作后还是小于 0 说明该字符原来存放的无效字符
            x = source.charAt(right - n);
            if (count[x]++ < 0) {
                invalidTimes--;
            }
        }

        return invalidTimes == 0 ? (len - n) : -1;
    }


}
