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

        String source = "aa";
        String target = "aa";
        String minSubStr = getMinSubStr(source, target);
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


}
