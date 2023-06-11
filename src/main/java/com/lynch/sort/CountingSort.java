package com.lynch.sort;

/**
 *  计数排序，主要用于对较小数值做排序，如对几百万高考考生按考试分数做排序
 *  由于考分总分只有 750 分，所以可以将相同分数的考生放到一个桶中
 * Created by troub on 2022/2/25 17:49
 */
public class CountingSort {
    public static void main(String[] args) {

    }

    public int[] sort(int[] sourceArray) {
        if (sourceArray == null || sourceArray.length <= 1) {
            return new int[0];
        }
        // 1.查找数列最大值
        int max = sourceArray[0];
        for (int value : sourceArray) {
            max = Math.max(max, value);
        }
        // 2.根据数据最大值确定统计数组长度
        int[] countArray = new int[max + 1];
        // 3. 遍历原始数组映射到统计数组中,统计元素的个数
        for (int value : sourceArray) {
            countArray[value]++;
        }
        // 4.统计数组变形，后面的元素等于前面元素之和。目的是定位在结果数组中的排位
        for (int i = 1; i <= max; i++) {
            countArray[i] += countArray[i - 1];
        }
        // 5.倒序遍历原始数组，从统计数组查找对应的正确位置，输出到结果表
        int[] sortedArray = new int[sourceArray.length];
        for (int i = sourceArray.length - 1; i >= 0; i--) {
            int value = sourceArray[i];
            // 分数在 countArray 中的排名, - 1 则是结果数组的下标
            int index = countArray[value] - 1;
            sortedArray[index] = value;
            countArray[value]--;
        }
        return sortedArray;
    }
}
