package com.lynch.string;

/**
 * @author leo
 * @ClassName KMP
 * @description: TODO
 * @date 7/29/23 11:29 AM
 */
public class KMP {

    public static void main(String[] args) {
        String a = "ambxam";
        int[] next = getNext(a.toCharArray());
        System.out.println("");
    }

    public static int kmp(String source, String match) {
        int i = 0, j = 0;
        int m = source.length();
        int n = match.length();
        int[] next = getNext(match.toCharArray());

        while (i < m && j < n) {
            if (j == -1 || source.charAt(i) == match.charAt(j)) {
                i++;
                j++;
            } else {
                j = next[j]; // 回退
            }
        }
        if (j >= n) {
            return i - n + 1;
        }
        return -1;
    }

    public static int[] getNext(char[] str) {

        int n = str.length;
        // next[i] 表示原串 s[i] 与 匹配串 m[i] 不相等时需要回退的位置
        // 假设 next[i]=k , 则表示匹配串 i 位置之前那部分字符串 t 的前缀
        // 与 t 的后缀所拥有的最长公共子串长度为 k :
        // 假设匹配串为: t0 t1 t2 ... t(i-1) t(i), 那么 next[i] = k 即可理解为
        // i 位置之前的子串 t 中, 其前缀与后缀最长公共子串长度为 k
        // t0  t1  t2  ...  t(k-1) == t(i-k)  t(i-k+1) ...  t(i-1)
        // └┈┈┈┈ 前缀 k 个字符  ┈┈┈┘   └┈┈┈┈┈┈ 后缀 k 个字符 ┈┈┈┈┈┈┈┘
        // 那么当 t(k) 位置字符等于 t(i) 位置字符, 即 t(k) == t(i) 时, 那么必定有
        //     next[i+1] = k + 1
        // 若 t(k) 位置字符不等于 t(i) 位置字符, 即 t(k) != t(i) 时, 那么只能回退
        // 即缩小前后缀的比较范围
        //     k = next[k]
        // 个字符与原始串 j 位置之前的k 个字符相同
        // m...m(i-1) = s...s(j-1)
        int[] next = new int[n + 1];
        // 前后缀不可取自身,故首位为-1
        next[0] = -1;

        int k = -1;
        int i = 0;
        while (i < n) {
            if (k == -1 || str[i] == str[k]) {
                next[i + 1] = k + 1;
                k++;
                i++;
            } else {
                k = next[k];
            }
        }
        return next;
    }

    public static int[] getNext2(char[] str){
        int n = str.length;
        int[] next = new int[n+1];
        int i = 2; int k = 0;
        next[0] = -1;
        next[1] = 0;
        while (i<n){
            if(str[i] == str[k]){

            }
        }
        return next;
    }

}