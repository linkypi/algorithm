package com.lynch.tecent;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 给定一个由0和1组成得字符串，要求第一位(index=0)字符串的价值为 1
 * 从第二位开始:
 *  1. 若该位字符与前一位字符相同则该位字符的价值为：前一位字符的价值加一
 *  2. 若该位字符与前一位字符不同则该位字符的价值为 1
 *  请求出给定字符串的总价值
 * @Author: linxueqi
 * @Description:
 * @Date: create in 2022/4/18 9:38
 */
public class MaxValue {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int nextInt = input.nextInt();
        String str = input.next();
        int maxValue = findMaxValue(str);
        System.out.println("max value: " + maxValue);
    }

    static int findMaxValue(String str) {
        List<Integer> onePos = new ArrayList<>();
        List<Integer> zeroPos = new ArrayList<>();
        char[] chars = str.toCharArray();
        for (int i = 1; i < chars.length; i++) {
            if (chars[i] == '1') {
                onePos.add(i);
            } else {
                zeroPos.add(i);
            }
        }

        // 首位是0，后面也是0，则第二位的价值为 2
        int pre0 = chars[0] == '0' ? 2 : 1;

        // 1.  删除 1 保留 0
        // 等差数列求和
        int last0 = pre0 + zeroPos.size() - 1;
        int sum0 = last0 * (last0 + 1) / 2;
        // 判断最后出现0的位置是否是str最后一位，不是最后一位则从一开始继续累加
        if (zeroPos.size() > 0) {
            int last = zeroPos.get(zeroPos.size() - 1);
            if (last != chars.length - 1) {
                int len = chars.length - 1 - last;
                sum0 += len * (len + 1) / 2;
            }
        }

        // 1.  删除 0 保留 1
        int pre1 = chars[0] == '1' ? 2 : 1;
        // 等差数列求和
        int last1 = pre1 + onePos.size() - 1;
        int sum1 = last1 * (last1 + 1) / 2;
        // 判断最后出现0的位置是否是str最后一位，不是最后一位则从一开始继续累加
        if (onePos.size() > 0) {
            int last = onePos.get(onePos.size() - 1);
            if (last != chars.length - 1) {
                int len = chars.length - 1 - last;
                sum1 += len * (len + 1) / 2;
            }
        }
        return Math.max(sum0, sum1);
    }
}
