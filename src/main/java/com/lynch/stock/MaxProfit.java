package com.lynch.stock;

/**
 * 求买卖股票的最佳时机
 * Created by troub on 2022/1/26 9:13
 */
public class MaxProfit {
    public static void main(String[] args) {
        int[] arr = new int[]{8, 9, 1, 2, 5, 7, 3, 6};
        final Info bestTime = getBestTime(arr);
        System.out.printf("max values: %d, index: %d%n", bestTime.value, bestTime.index);

        final Info max = getBestTime2(arr);
        System.out.printf("optimize max values: %d, index: %d%n", max.value, max.index);
    }

    /**
     * 每次计算时记录下最佳买入的价格（最低价），然后与之后的价格
     * 做对比即可得到最大利润，整个过程只需遍历一编，时间复杂度 O(N)
     *
     * @param arr
     * @return
     */
    private static Info getBestTime2(int[] arr) {
        if (arr == null || arr.length == 0) {
            return new Info(-1, 0);
        }
        int min = arr[0];
        int maxIndex = -1, maxValue = 0;
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] < min) {
                min = arr[i];
            }
            if (arr[i] - min > maxValue) {
                maxIndex = i;
                maxValue = arr[i] - min;
            }
        }
        return new Info(maxIndex, maxValue);
    }

    /**
     * 逐个遍历，以当前价格为买入基准，其最佳卖出时间即后续价格中的最大值。
     * 比较完成后再将从每个最值中求最大即为所求，双重循环，时间复杂度 O(N2)
     *
     * @param arr
     * @return
     */
    private static Info getBestTime(int[] arr) {
        Info info = new Info(-1, 0);
        for (int x = 0; x < arr.length; x++) {
            int current = arr[x];
            Info max = getMax(arr, x + 1);
            int value = max.value - current;
            if (value > 0 && info.value < value) {
                info.value = value;
                info.index = max.index;
            }
        }
        return info;
    }

    private static Info getMax(int[] arr, int i) {
        if (i > arr.length - 1) {
            return new Info(-1, 0);
        }
        Info info = new Info(-1, 0);
        for (int k = i; k < arr.length; k++) {
            if (info.value < arr[k]) {
                info.value = arr[k];
                info.index = k;
            }
        }
        return info;
    }

    private static class Info {
        private int index;
        private int value;

        public Info(int index, int value) {
            this.index = index;
            this.value = value;
        }
    }

}
