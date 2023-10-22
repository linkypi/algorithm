package com.lynch.random;

import org.junit.Test;

/**
 * 给定一个等概率随机产生1-5的随机函数 rand1To5 :
 * public int rand1To5(){
 *     return (int)(Math.random()*5) + 1;
 * }
 * 除此以外不能使用任何额外的随机机制，请用 rand1To5 实现等概率随机
 * 产生 1-7的随机函数 rand1To7
 * @author: lynch
 * @description:
 * @date: 2023/8/24 21:42
 */
public class Rand1To7 {

    @Test
    public void test() {
    }

    public int rand1To7() {
        int num = 0;
        //  (randX() - 1)*Y + randY() 可以等概率的生成 [1, X * Y] 范围的随机数
        // 1. rand1To5 等概率随机产生 1,2,3,4,5
        // 2. rand1To5 - 1 等概率随机产生 0,1,2,3,4
        // 3. (rand1To5-1)*5 等概率随机产生 0,5,10,15,20
        // 4. (rand1To5-1)*5 + (rand1To5-1) 等概率随机产生 0,1,2...22,23,24。注意 (rand1To5-1) 不可化简 ！！！
        // 5. 若步骤4结果大于20 则重复步骤4，直到产生的结果在0-20之间
        // 6. 对步骤5的结果进行 %7，即可等概率随机产生 0~6，最后加 1 即为答案
        do {
            num = (rand1To5() - 1) * 5 + rand1To5() -1;
        } while (num > 20);
        return num % 7 + 1;
    }

    public int rand1To5() {
        return (int) (Math.random() * 5) + 1;
    }

}
