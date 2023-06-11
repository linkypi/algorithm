package com.lynch.extern;

/**
 * N 皇后问题: 在 N*N的棋盘上需要摆放 N 个皇后, 要求任何两个皇后不同行,不同列,同时不能处在同一条斜线上.
 */
public class NQueues {

    public static void main(String[] args) {
        int n = 14;
        // 记录第 i 行的哪一列放置上了皇后
        int[] records = new int[n];
        int result = process(0, n, records);
        System.out.println("result: " + result);

        // 优化后的解法
        result = getCountByBinary(n);
        System.out.println("result after optimize: " + result);
    }

    private static int getCountByBinary(int n) {
        if (n < 1 || n > 32) {
            return 0;
        }

        int limit = n == 32 ? -1 : ((1 << n) - 1);
        return processOptimize(limit, 0, 0, 0);
    }

    /**
     * 使用移位运算, 极力缩短运算时间
     * @param limit    初始可以放置的位置, 1 表示可放置, 0 表示不可放
     * @param colLimit 记录皇后位置
     * @param leftLimit  用于记录对角线左边位置
     * @param rightLimit 用于记录对角线右边位置
     * @return
     */
    private static int processOptimize(int limit, int colLimit, int leftLimit, int rightLimit) {
        if (limit == colLimit) {
            return 1;
        }

        int position = limit & (~(colLimit | leftLimit | rightLimit));
        int res = 0;
        // 最右边的 1
        int rightMostOne = 0;
        while (position != 0) {
            // 获取最右边的 1
            rightMostOne = position & (~position + 1);
            // 从右边每次消除一个 1
            position = position - rightMostOne;
            // 由于是从右开始不断消除 1 , 那么 colLimit 则会从右边低位开始向高位写 1 (因 colLimit | rightMostOne),
            // 直到 N 位全部写满即表明 N 个皇后全部已放置完毕
            // 此时 leftLimit 即可以理解为在第 i 个位置放置皇后以后其下面一行的皇后必不可以放在此行的左下方对角线
            // 同理 rightLimit 即可以理解为在第 i 个位置放置皇后以后其下面一行的皇后必不可以放在此行的右下方对角线, 注意右移不能带符号
            res += processOptimize(limit, colLimit | rightMostOne,
                    (leftLimit | rightMostOne) << 1, (rightLimit | rightMostOne) >>> 1);

        }
        return res;
    }

    private static int process(int row, int n, int[] records) {

        if (row == n) {
            return 1;
        }

        int res = 0;
        // 遍历当前行所有 col 位置的可能性
        for (int col = 0; col < n; col++) {
            // 判断位置是否合法
            if (!isValid(row, col, records)) {
                continue;
            }
            // 合法则记录当前行的 col 列摆放了皇后
            records[row] = col;
            // 在当前行的位置确定后 找到下面一行摆放皇后的可能性
            res += process(row + 1, n, records);
        }
        return res;
    }

    /**
     * 主循环仅需 records[0...row]
     * @param row
     * @param col
     * @param records
     * @return
     */
    private static boolean isValid(int row, int col, int[] records) {
        // 检查前面 row 行哪个位置放置了皇后, 因为行号是逐层递增
        for (int k = 0; k < row; k++) {
            // 判断是否同行, 同列 及 同一对角线
            // 1. 因为 row是不断递增的,所有前后两个皇后必定不同行,故仅需判断是否同列
            //    判断第 k 行 第 records[k] 列的位置是否摆放了皇后
            // 2. 同一对角线使用等边三角形判断
            if (records[k] == col || (Math.abs(row - k) == Math.abs(records[k] - col))) {
                return false;
            }
        }
        return true;
    }
}
