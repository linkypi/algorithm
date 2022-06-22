package com.lynch.extern;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * 素数伴侣：
 *
 * 若两个正整数的和为素数，则这两个正整数称之为 “素数伴侣”，如 2 和 5、6 和 13，它们能应用于通信加密。
 * 现在密码学会请你设计一个程序，从已有的 N （ N 为偶数）个正整数中挑选出若干对组成 “素数伴侣”，挑选
 * 方案多种多样，例如有 4 个正整数：2，5，6，13，如果将 5 和 6 分为一组中只能得到一组 “素数伴侣”，
 * 而将 2 和 5、6 和 13 编组将得到两组 “素数伴侣”，能组成 “素数伴侣” 最多的方案称为 “最佳方案”，当然密码学会希望你寻找出 “最佳方案”。
 *
 * 输入: 有一个正偶数 n ，表示待挑选的自然数的个数。后面给出 n 个具体的数字。
 * 输出: 输出一个整数 K ，表示你求得的 “最佳方案” 组成 “素数伴侣” 的对数。
 * 数据范围： 1 ≤ n ≤ 100  ，输入的数据大小满足 2 ≤ val ≤ 30000
 * @Author: linxueqi
 * @Description:
 * @Date: create in 2022/6/22 17:16
 */
public class PrimeMate {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int count = scanner.nextInt();
        int[] arr = new int[count];
        int i = 0;
        while (scanner.hasNext()) {
            String value = scanner.next();
            arr[i++] = Integer.parseInt(value);
            if (i == count) {
                break;
            }
        }

        int count1 = getCount(arr);
        System.out.println("result: "+ count1);
    }

    public static int getCount(int[] arr) {
        List<Integer> evens = new ArrayList<>();
        List<Integer> odds = new ArrayList<>();

        // 区分奇偶数
        for (int item : arr) {
            if ((item & 1) == 0) {
                evens.add(item);
            } else {
                odds.add(item);
            }
        }

        int count = 0;
        for (int item : evens) {
            for (int child : odds) {
                int sum = item + child;
                if ((sum & 1) == 0) {
                    continue;
                }
                if (isPrime(sum)) {
                    count++;
                }
            }
        }
        return count;
    }

    public static boolean isPrime(int value){
         for(int i=2;i<Math.sqrt(value);i++){
             if(value % i == 0){
                 return false;
             }
         }
         return true;
    }
}
