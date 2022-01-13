package com.lynch.sliding_window;

/**
 * 求最小覆盖子串
 * 给定一个字符串 S="ADBECFEBANC" , T="ABC" , 求在S中找到包含T中全部字母的最短子串。
 */
public class Window {
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

    static class Info {
        private int start;
        private int end;
        private int length;

        public Info(int length, int start, int end) {
            this.length = length;
            this.start = start;
            this.end = end;
        }
    }

    private static String getMinSubStr(String source, String target) {

        int[] tmap = new int[128];
        for (char item : target.toCharArray()) {
            tmap[item] = 1;
        }
        int matchCount = 0;
        int left = 0, right = 0;
        Info minInfo = new Info(Integer.MAX_VALUE, 0, 0);

        while (right < source.length()) {
            char current = source.charAt(right);
            if (tmap[current] == 1) {
                matchCount++;
                tmap[current]++;
            } else if (tmap[current] > 1) {
                tmap[current]++;
            }

            //已经包含所有需要寻找的字符则开始缩小窗口
            if (matchCount == target.length()) {
                // 开始缩小窗口，直到窗口内的字符不再符合要求
                while (left <= right) {
                    char item = source.charAt(left);
                    // 不符合的字符则忽略，继续缩小窗口
                    if(tmap[item]==0){
                        left++;
                        continue;
                    }
                    // 已经缩小到最小窗口则记录最小覆盖子串
                    if (tmap[item] == 2 && matchCount <= target.length()) {

                        int len = right - left + 1;
                        if (len < minInfo.length) {
                            minInfo.length = len;
                            minInfo.start = left;
                            minInfo.end = right;
                        }

                        // 丢弃窗口左边符合要求的字符继续向前，同时更新匹配数量
                        left ++;
                        matchCount --;
                        tmap[item] --;
                        break;
                    }
                    left++;
                    if (tmap[item] > 1) {
                        tmap[item]--;
                    }
                    if (tmap[item] == 1) {
                        matchCount--;
                    }
                }


            }
            right++;
        }
        if(minInfo.length == Integer.MAX_VALUE){
            return "";
        }
        // substring 内部实现导致 end 必须加 1
        return source.substring(minInfo.start, minInfo.end + 1);
    }
}
