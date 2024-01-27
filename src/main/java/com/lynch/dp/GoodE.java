package com.lynch.dp;

import org.junit.Test;

/**
 * 来自网易
 * 小红拿到了一个仅由r、e、d组成的字符串
 * 她定义一个字符e为"好e” : 当且仅当这个e字符和r、d相邻
 * 例如"reeder"只有一个"好e"，前两个e都不是"好e"，只有第三个e是"好e
 * 小红每次可以将任意字符修改为任意字符，即三种字符可以相互修改
 * 她希望"好e"的数量尽可能多，小红想知道，自己最少要修改多少次
 * 输入一个只有r、e、d三种字符的字符串，长度 <= 2*10^5
 * //输出最小修改次数
 *
 * @author: lynch
 * @description:
 * @date: 2023/10/22 22:20
 */
public class GoodE {
    @Test
    public void test() {

    }

    public class Info {
        /**
         * 好e的数量
         */
        public int mostGoodE;
        /**
         * 最小代价
         */
        public int minCost;

        public Info(int good, int cost) {
            this.minCost = cost;
            this.mostGoodE = good;
        }
    }

    public Info find(char[] str, int i, char prepre, char pre) {
        if (i == str.length) {
            return new Info(0, 0);
        }

        // 情况一：str[i] 变成 r, 此时有可能与 i-1 组成好e
        int curValue1 = prepre == 'd' && pre == 'e' ? 1 : 0;
        int curCost1 = str[i] == 'r' ? 0 : 1;
        Info info1 = find(str, i + 1, pre, 'r');
        int p1Value = curValue1 + info1.mostGoodE;
        int p1Cost = curCost1 + info1.minCost;

        // 情况二：str[i] 变成 e
        int curValue2 = 0;
        int curCost2 = str[i] == 'e' ? 0 : 1;
        Info info2 = find(str, i + 1, pre, 'e');
        int p2Value = curValue2 + info2.mostGoodE;
        int p2Cost = curCost2 + info2.minCost;

        // 情况三：str[i] 变成 d
        int curValue3 = prepre == 'r' && pre == 'e' ? 1 : 0;
        int curCost3 = str[i] == 'd' ? 0 : 1;
        Info info3 = find(str, i + 1, pre, 'd');
        int p3Value = curValue3 + info3.mostGoodE;
        int p3Cost = curCost3 + info3.minCost;

        int mostE = 0;
        int minCost = Integer.MAX_VALUE;
        if (mostE < p1Value) {
            mostE = p1Value;
            minCost = p1Cost;
        } else if (mostE == p1Value) {
            minCost = Math.min(minCost, p1Cost);
        }

        if (mostE < p2Value) {
            mostE = p2Value;
            minCost = p2Cost;
        } else if (mostE == p2Value) {
            minCost = Math.min(minCost, p2Cost);
        }

        if (mostE < p3Value) {
            mostE = p3Value;
            minCost = p3Cost;
        } else if (mostE == p3Value) {
            minCost = Math.min(minCost, p3Cost);
        }

        return new Info(mostE, minCost);
    }
}
