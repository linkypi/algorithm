package com.lynch.string;

import org.junit.Test;

public class KMP {
    @Test
    public void test() {

//         String source = "abgabeabeabxgt";
//         String match = "abeabx";

        String source =  "aabaaabaaac";
        String match = "aabaaac";
        int index = find(source.toCharArray(), match.toCharArray());
        System.out.println("index: "+ index);
    }

    /**
     * 判断某个元素字符串是否包含字串
     * @param source 原始字符串
     * @param match 需要匹配的子串
     * @return
     */
    private int find(char[] source, char[] match) {
        int[] next = getNextArr(match);

        int p1 = 0, p2 = 0;
        while (p1 < source.length && p2 < match.length) {
            if (source[p1] == match[p2]) {
                p1++;
                p2++;
            }else if(next[p2] == -1){
                // 若next数组位置已经走到最开始的位置则说明，str1当前位置已经
                // 无法匹配整个 str2, 所以str1需要挪动位置往下匹配
                p1 ++;
            } else {
                p2 = next[p2];
            }
        }
        return p2 == match.length ? p1 -p2 : -1;
    }

    /**
     * next数组每一个元素存放的是某个位置前面字符串中前缀与后缀中最长公共字串的长度
     * 如需要匹配的字符串为 “abcstgjabcstf”，记作 str,默认第一个位置的公共字串的长度为 -1，第二个位置默认为 0
     * 1. a 为第一个字符，其前方没有字符，公共字串的长度为 -1
     * 2. ab，b位置前方只有一个字符 a， 值为 0
     * 3. abc, c位置前方是 ab, 其前后缀没有公共字串，值为0
     * 4. abcs，s位置前方是 abc, 其前后缀没有公共字串，值为0
     * 5. abcst，t位置前方是 abcs, 其前后缀没有公共字串，值为0
     *  6，7，8 同上 ..... 其前后缀没有公共字串，值为 0
     * 9. abcstgjab, b位置前方是 abcstgja, 其前后缀有一个最长公共字串串a，值为1
     * 10. abcstgjabc, c位置前方是 abcstgjab, 其前后缀有一个最长公共字串串ab，值为2
     * 11. abcstgjabcs, s位置前方是 abcstgjabc, 其前后缀有一个最长公共字串串abc，值为3
     * 12. abcstgjabcst, t位置前方是 abcstgjabcs, 其前后缀有一个最长公共字串串abcs，值为4
     * 13. abcstgjabcstf, f位置前方是 abcstgjabcst, 其前后缀有一个最长公共字串串abcst，值为5
     * 最后 字符串 “abcstgjabcstf” 对应的next数组为 [-1, 0, 0, 0, 0, 0, 0, 0, 1, 2, 3, 4, 5]
     *
     * 在求解next数组时会发现，对任意位置 i 的求解都会依赖于前一个位置 i-1 的值
     * 1. 从第9步开始可以看出， 最后一个字符b的值依赖于前一个位置a在next数组中的值
     * 此时a在next数组的值为 0， 记为 cn，而此时 str[cn] 恰好等于 str[i-1]，两者长度都是1，所以此时next数组的值为 1.
     * 2. 第十步最后一个字符c的前一个字符b对应的next数组值为 1， 即cn=1 （上一步已求出），而此时 str[cn] 恰好等于 str[i-1]
     * 所以此时next数组值为next数组在 i-1位置的值加一。
     * 3. 同理，后面的每个步骤的next值都是依赖于前一个步骤值，只要str[cn] 恰好等于 str[i-1]
     * 如此next数组便很快可以求出，无需使用复杂度过高的算法来实现
     * @param arr 待查找的字符串
     * @return
     */
    public int[] getNextArr(char[] arr) {

        int N = arr.length;
        if (N == 1) {
            return new int[]{-1};
        }

        int[] next = new int[N];
        next[0] = -1;
        next[1] = 0;

        int i = 2, nextValue = 0;
        while (i < N) {
            if (arr[i - 1] == arr[nextValue]) {
                next[i++] = ++nextValue;
            } else if (nextValue > 0) {
                nextValue = next[nextValue];
            } else {
                next[i++] = 0;
            }
        }
        return next;
    }
}
