package com.lynch.string;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

/**
 * RabinKarp 算法是一个利用滑动窗口 + 哈希的方式来实现快速匹配模式串的算法
 * 将每次需要匹配的子串映射为指定哈希值，减少字符串的截取及存储的开销
 *
 * DNA 序列 由一系列核苷酸组成，缩写为 'A', 'C', 'G' 和 'T'.。
 *
 * 例如，"ACGAATTCCG" 是一个 DNA 序列 。
 * 在研究 DNA 时，识别 DNA 中的重复序列非常有用。
 * 给定一个表示 DNA 序列 的字符串 s ，返回所有在 DNA 分子中出现不止一次的 长度为 10 的序列 (子字符串)。你可以按 任意顺序 返回答案。
 *
 * 示例 1：
 * 输入：s = "AAAAACCCCCAAAAACCCCCCAAAAAGGGTTT"
 * 输出：["AAAAACCCCC","CCCCCAAAAA"]
 *
 * @Author: linxueqi
 * @Description:
 * @Date: create in 2023/8/11 10:44
 */
public class RabinKarp {

    @Test
    public void test(){
        String str = "AAAAACCCCCAAAAACCCCCCAAAAAGGGTTT";
        int n = 10;
        HashSet<String> set = new HashSet<String>();
        for (int i = 0; i + n < str.length(); i++) {
            String math = str.substring(i, n + i);
            int count = find(str, math);
            int res = rabinKarp(str, math);
            if(count > 1){
                set.add(math);
            }
        }

        if(set.isEmpty()){
            System.out.println("not match string");
        }else{
            ArrayList<String> list = new ArrayList<>(set);
            for (String s : list) {
                System.out.println("item: " + s);
            }
        }
    }

    public int find(String source, String match) {
        // 使用通用的1e9+7, 一个较大的质数取模运算，防止溢出
        int mod = 1000000007;
        // ascii码默认有256个字符，可以理解为将子串映射为 256 进制数
        int x = 256;

        int matchLen = match.length();
        // 最高位的值，用于后面缩小窗口时减去最高位数, 必须从 1 开始
        long highValue = 1;
        // 哈希值需不断累加，需从 0 开始
        long matchHash = 0;
        for (int i = 0; i < matchLen; i++) {
            matchHash = ((matchHash * x) % mod + match.charAt(i)) % mod;
            if (i < matchLen - 1) {
                highValue = (highValue * x) % mod;
            }
        }

        int left = 0;
        int right = 0;
        // 哈希值需不断累加，需从 0 开始
        long windowHash = 0;

        // 记录当前匹配串 match 在source串出现的次数
        int count = 0;

        while (right < source.length()) {

            windowHash = ((windowHash * x) % mod + source.charAt(right)) % mod;
            right++;

            if (right - left == matchLen) {
                if (windowHash == matchHash) {
                    if (match.equals(source.substring(left, right))) {
                        count++;
                    }
                }
                // 开始缩小窗口
                long temp = (highValue * source.charAt(left)) % mod;
                // 此处加一个mod是为了防止 windowHash - temp 出现负数
                windowHash = (windowHash - temp + mod) % mod;
                left++;
            }
        }
        return count;
    }

    int rabinKarp(String txt, String pat) {
        // 位数
        int L = pat.length();
        // 进制（只考虑 ASCII 编码）
        int R = 256;
        // 取一个比较大的素数作为求模的除数
        long Q = 1000000007;// 1658598167;
        // R^(L - 1) 的结果
        long RL = 1;
        for (int i = 1; i <= L - 1; i++) {
            // 计算过程中不断求模，避免溢出
            RL = (RL * R) % Q;
        }
        // 计算模式串的哈希值，时间 O(L)
        long patHash = 0;
        for (int i = 0; i < pat.length(); i++) {
            patHash = (R * patHash + pat.charAt(i)) % Q;
        }

        // 滑动窗口中子字符串的哈希值
        long windowHash = 0;

        // 滑动窗口代码框架，时间 O(N)
        int left = 0, right = 0;
        int count = 0;
        while (right < txt.length()) {
            // 扩大窗口，移入字符
            windowHash = ((R * windowHash) % Q + txt.charAt(right)) % Q;
            right++;

            // 当子串的长度达到要求
            if (right - left == L) {
                // 根据哈希值判断是否匹配模式串
                if (windowHash == patHash) {
                    // 当前窗口中的子串哈希值等于模式串的哈希值
                    // 还需进一步确认窗口子串是否真的和模式串相同，避免哈希冲突
                    if (pat.equals(txt.substring(left, right))) {
                        count ++;
                    }
                }
                // 缩小窗口，移出字符
                windowHash = (windowHash - (txt.charAt(left) * RL) % Q + Q) % Q;
                // X % Q == (X + Q) % Q 是一个模运算法则
                // 因为 windowHash - (txt[left] * RL) % Q 可能是负数
                // 所以额外再加一个 Q，保证 windowHash 不会是负数

                left++;
            }
        }
        // 没有找到模式串
        return count;
    }
}
