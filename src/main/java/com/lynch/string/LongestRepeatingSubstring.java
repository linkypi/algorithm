package com.lynch.string;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * 求给定字符串的最长重复子串
 * @author: linxueqi
 * @Description:
 * @createTime: 2022/3/18 21:29
 */
public class LongestRepeatingSubstring {
    public static void main(String[] args) {
//        String source = "banana";
//        String source = "aa";
//        String source = "nnpxouomcofdjuujloanjimymadkuepightrfodmauhrsy";
        String source = "okmzpmxzwjbfssktjtebhhxfphcxefhonkncnrumgduoaeltjvwqwydpdsrbxsgmcdxrthilniqxkqzuuqzqhlccmqcmccfqddncchadnthtxjruvwsmazlzhijygmtabbzelslebyrfpyyvcwnaiqkkzlyillxmkfggyfwgzhhvyzfvnltjfxskdarvugagmnrzomkhldgqtqnghsddgrjmuhpgkfcjkkkaywkzsikptkrvbnvuyamegwempuwfpaypmuhhpuqrufsgpiojhblbihbrpwxdxzolgqmzoyeblpvvrnbnsdnonhpmbrqissifpdavvscezqzclvukfgmrmbmmwvzfpxcgecyxneipexrzqgfwzdqeeqrugeiupukpveufmnceetilfsqjprcygitjefwgcvqlsxrasvxkifeasofcdvhvrpmxvjevupqtgqfgkqjmhtkyfsjkrdczmnettzdxcqexenpxbsharuapjmdvmfygeytyqfcqigrovhzbxqxidjzxfbrlpjxibtbndgubwgihdzwoywqxegvxvdgaoarlauurxpwmxqjkidwmfuuhcqtljsvruinflvkyiiuwiiveplnxlviszwkjrvyxijqrulchzkerbdyrdhecyhscuojbecgokythwwdulgnfwvdptzdvgamoublzxdxsogqpunbtoixfnkgbdrgknvcydmphuaxqpsofmylyijpzhbqsxryqusjnqfikvoikwthrmdwrwqzrdmlugfglmlngjhpspvnfddqsvrajvielokmzpmxzwjbfssktjtebhhxfphcxefhonkncnrumgduoaeltjvwqwydpdsrbxsgmcdxrthilniqxkqzuuqzqhlccmqcmccfqddncchadnthtxjruvwsmazlzhijygmtabbzelslebyrfpyyvcwnaiqkkzlyillxmkfggyfwgzhhvyzfvnltjfxskdarvugagmnrzomk";
        String result = search(source);
        System.out.println("result: "+ result);
    }

    private static String search(String source) {
        int left = 1;
        int right = source.length();
        int maxLen = 0;
        while (left < right) {
            int mid = left + ((right - left) >> 1);
            if (rabinKarp(source, mid) != -1) {
                // mid 长度的重复子串存在则往上增加搜索长度
                maxLen= mid;
                left = mid + 1;
            } else {
                // mid 长度的重复子串不存在则缩小
                right = mid;
            }
        }

        if (maxLen != 0) {
            int endIndex = rabinKarp(source, maxLen);
            return source.substring(endIndex, endIndex+ maxLen);
        }
        return "";
    }

    private static int rabinKarp(String source, int len) {
        // 模数，用于对防止溢出，可取一个较大的素数
        int mod = 1000000007;
        // 进制数
        int base = 31;

        // 记录最高位，方便滑动窗口移动时直接使用减法消除左边的字符，
        // 然后加上最新加入窗口的字符， pow 必须使用long型
        long pow = 1;
        for (int i = 0; i < len; i++) {
            pow = (pow * base) % mod;
        }

        //  用于记录hashCode及其出现的起始位置
        HashMap<Long, Integer> map = new HashMap<>();
        // hashCode 必须是 long
        long hashCode = 0;
        for (int i = 0; i < source.length(); i++) {
            hashCode = (hashCode * base + source.charAt(i)) % mod;
            if (i < len - 1) {
                continue;
            }

            // 已达到窗口最大值则移除窗口左边字符，加入右边字符
            if (i >= len) {
                hashCode -= (source.charAt(i - len) * pow) % mod;
                // 必须判断hashCode小于0的情况，防止溢出，否则对于过长的字符串用例将无法通过 ！！！
                hashCode += hashCode < 0 ? mod : 0;
            }
            int beginIndex = i - len + 1;
            // 若存在hashCode相同的则说明可能存在相同字符串，此时还需要做实际比较
            if (map.containsKey(hashCode)) {
                String s1 = source.substring(map.get(hashCode), map.get(hashCode) + len);
                String s2 = source.substring(beginIndex, i + 1);
                // 若字符串都相同则说明存在长度为len的重复子串
                if (s1.equals(s2)) {
                    return beginIndex;
                }
            }
            map.put(hashCode, beginIndex);
        }
        return -1;
    }

}
