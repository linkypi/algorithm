package com.lynch.random;

/**
 * 谷歌面试题扩展版面值为1~N的牌组成一组每次你从组里等概率的抽出1~N中的一张下次抽会换一个新的组，
 * 有无限组当累加和<a时，你将一直抽牌当累加和>=a且<b时，
 * 你将获胜当累加和>=b时，你将失败返回获胜的概率，给定的参数为 N，a，b
 * @author: lynch
 * @description:
 * @date: 2023/8/9 21:48
 */
public class GoogleRandom {

    /**
     * 面值为1~10的牌组成一组,每次你从组里等概率的抽出1~10中的一张
     * 下次抽会换一个新的组，有无限组,
     * 当累加和<17时，你将一直抽牌
     * 当累加和>=17且<21时，你将获胜
     * 当累加和>=21时，你将失败返回获胜的概率
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
