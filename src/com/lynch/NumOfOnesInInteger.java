package com.lynch;

public class NumOfOnesInInteger {

    public static void main(String[] args) {
        int numOfOnes = numOfOnes(1410065408);
        System.out.println("numOfOnes: "+ numOfOnes);
    }

    public static int numOfOnes(int n){
        // 将一个整数其中一位设置为1，则此时1出现的次数可理解为
        // 高位部分的出现的次数 + 低位部分出现的次数
        // 如 23105，如指定1的位置为1，则高位是23，即有23+1种变化，
        // 高位 -- 指定位 -- 低位
        //  23 ---  1   --- 05
        // 低位为05，此时低位需要考虑三种情况：
        // 1. 若该指定位置的数值大于1，例子中若指定位置为7，大于1，则低位可以在0-9中随意变换，故低位有10^2种变化
        // 2. 若该指定位置的数值等于1，则1出现的次数有：
        //    2.1 高位出现总数 * （低位最大值 + 1）即案例中的：（23+1）*（5+1）
        //    2.2 高位出现总数 * （低位最大值到下一个进位的总数），即案例：23*（10^2-5）
        //        该次计算最容易遗漏，除计算 (23+1)*（5+1） 以外，仍需要计算低位
        //        6到100中存在的95个数字，因为头一次计算仅计算了0到5一共5个数字出现的变化，对于高位来说
        //        6到100中存在的95个数字并未计算在内，故有 23 * (10^2-5)
        // 3. 若该指定位置的数值等于0，该位置出现1的情况只有高位降位的情况，如 23105 中的0，若要该位为1则，需要将高一位1借1，
        //    故此时出现的次数为： （231-1+1）* 10^1

        int count = 0;
        long precise = 1;        // 使用long型防止溢出，使用int类型时在leedcode提交会报错
        while(n / precise != 0) {
            long high = n / (10 * precise);  // 高位
            long cur = (n / precise) % 10;   // 当前位
            long low = n - (n / precise) * precise;

            if(cur == 0) {
                count += high * precise;
            }else if(cur == 1) {
                count += high * precise + (low + 1);
            }else {
                count += (high + 1) * precise;
            }
            precise = precise * 10;
        }
        return count;
    }
}
