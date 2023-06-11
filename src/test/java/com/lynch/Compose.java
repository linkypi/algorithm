package com.lynch;

/**
 * @author: linxueqi
 * @Description:
 * @createTime: 2022/3/10 23:15
 */
public class Compose {
    public static void main(String[] args) {
//       getCompose()
    }

    private static long getCompose(int m, int n) {
        long numerator = 1;
        long denominator = 1;
        int count = m - 1;
        int t = m + n - 2;
        while ((count--) > 0) {
            numerator = numerator * (t--);
            while (denominator != 0 && numerator % denominator == 0) {
                numerator = numerator/denominator;
                denominator--;
            }
        }
        return numerator;
    }
}
