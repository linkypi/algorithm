/*
 * @Author: LinkyPi trouble.linky@gmail.com
 * @Date: 2023-11-02 09:42:19
 * @LastEditors: LinkyPi trouble.linky@gmail.com
 * @LastEditTime: 2024-01-26 11:54:52
 * @FilePath: /algorithm/src/main/java/com/lynch/random/GoogleRandom.java
 * @Description: 这是默认设置,请设置`customMade`, 打开koroFileHeader查看配置 进行设置: https://github.com/OBKoro1/koro1FileHeader/wiki/%E9%85%8D%E7%BD%AE
 */
package com.lynch.random;

/**
 * 谷歌面试题扩展版:
 * 面值为1~N的牌组成一组每次你从组里等概率的抽出1~N中的一张
 * 下次抽会换一个新的组，有无限组
 * 当累加和 < a 时，你将一直抽牌
 * 当累加和 >= a 且 < b 时，你将获胜
 * 当累加和 >= b 时，你将失败
 * 返回获胜的概率，给定的参数为 N，a，b
 * @author: lynch
 * @description:
 * @date: 2023/8/9 21:48
 */
public class GoogleRandom {

    /**
     * 面值为1~10的牌组成一组,每次你从组里等概率的抽出1~10中的一张
     * 下次抽会换一个新的组，有无限组,
     * 当累加和 <17 时，你将一直抽牌
     * 当累加和 >=17 且 <21 时，你将获胜
     * 当累加和 >=21 时，你将失败
     * 返回获胜的概率
     * @return
     */
    public double find(int cur) {

        if (cur >= 17 && cur < 21) {
            return 1.0;
        }
        if (cur > 21) {
            return 0.0;
        }

        int w = 0;
        for (int i = 1; i <= 10; i++) {
            w += find(cur + i);
        }
        return w / 10.0;
    }
}
