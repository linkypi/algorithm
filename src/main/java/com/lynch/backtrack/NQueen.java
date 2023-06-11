package com.lynch.backtrack;

/**
 * N 皇后问题，在一个 NxN 的棋盘中摆放N个皇后，求一共有多少种摆放方式
 * 要求：与皇后的同一行同一列的不能摆，与皇后的左右上角，左右下角所形成的对角线不能摆
 * 使用'Q'表示当前位置已摆放皇后，'.'表示没有摆放
 * Created by troub on 2022/3/1 16:36
 */
public class NQueen {
    public static void main(String[] args) {
        final int N = 8;

        // 最优求解 使用二进制位存储放皇后的位置
//        int count = bestSolution(N);
//        System.out.println(N + " queen result: " + count);
//
//
//        count = solve(N);
//        System.out.println(N + " queen result: " + count);


//        char[][] board = new char[N][N];
//        for (int r = 0; r < N; r++) {
//            for (int c = 0; c < N; c++) {
//                board[r][c] = '.';
//            }
//        }
//        backtrack(board, 0);

        int[] records = new int[N];
        test(records,0);
        System.out.println("result: " + COUNT);
    }

    private static int solve(int n) {
        if (n < 1) {
            return 0;
        }
        int[] records = new int[n];
        return _solve(records, 0);
    }

    /**
     * 尝试从第 row 行放置皇后
     * @param record
     * @param row
     * @return
     */
    private static int _solve(int[] record, int row) {
        int n = record.length;
        // 已经遍历完成所有行，即已经放置完成所有皇后即可得到一种放置方式
        if (row == n) {
            return 1;
        }

        int res = 0;
        for (int col = 0; col < n; col++) {
            if (isValid2(record, row, col)) {
                // 记录第row行第col列放置皇后
                record[row] = col;
                // 从下一行开始放置
                res += _solve(record, row + 1);
            }
        }
        return res;
    }

    private static boolean isValid2(int[] record, int row, int col) {
        // 由于皇后都是放在不同行，故无需校验行是否存在皇后，
        // 只需要校验是否同一列及同一对角线
        for (int k = 0; k < row; k++) {
            // 是否同一列
            if (record[k] == col) {
                return false;
            }
            // 是否同一对角线，两个左边(a,b),(c,d)，判断两者是否在同一对角线的方式为 |a-c|==|b-d|
            if (Math.abs(row - k) == Math.abs(col - record[k])) {
                return false;
            }
        }
        return true;
    }

    /**
     * N皇后问题最优求解方式，使用二进制位求解
     * @param n
     * @return
     */
    private static int bestSolution(int n) {
        if(n<1 || n > 32){
            return 0;
        }
        int limit = n == 32 ? -1 : ((1 << n) - 1);
        return process(limit, 0, 0, 0);
    }

    /**
     * @param limit      总限制，固定不变，有多少个皇后就有多少个1，N个皇后就有 N 个1
     * @param colLimit   列限制，即表示某一列被放置了皇后，放置皇后的位置就置为 1
     * @param leftLimit  左边限制，即左对角线的位置是否放置了皇后，放置皇后的位置就置为 1
     * @param rightLimit 右边限制，即右边对角线位置是否放置了皇后，放置皇后的位置就置为 1
     * @return 实际放置皇后的方法数
     */
    private static int process(int limit, int colLimit, int leftLimit, int rightLimit) {
        if (limit == colLimit) {
            return 1;
        }

        int pos = ~(colLimit | leftLimit | rightLimit);
        // 与上limit是为了防止前面取反后高位不相干的位置都变成了 1 造成干扰
        pos = limit & pos;
        int count = 0;
        int mostRightOne = 0;
        while (pos != 0) {
            // 获取最右边的 1，即从最右边开始放置皇后，不断遍历直到 pos 为 0
            mostRightOne = pos & (~pos + 1);
            pos = pos - mostRightOne;
            // colLimit | mostRightOne 表示当前放置皇后之后的列限制
            // (leftLimit | mostRightOne) << 1 表示在当前最右边可用位置放皇后之后，其左对角线限制整体左移即可
            // (rightLimit | mostRightOne) >>> 1 表示在当前最右边可用位置放皇后之后，其右对角线限制整体右移即可
            int res = process(limit, colLimit | mostRightOne,
                    (leftLimit | mostRightOne) << 1,
                    (rightLimit | mostRightOne) >>> 1);
            count += res;
        }
        return count;
    }

    static int COUNT = 0;

    /**
     * 对棋盘的每一行进行尝试
     * @param board
     * @param row
     * @return
     */
    static boolean backtrack(char[][] board, int row) {
        int n = board.length;

        // 所有行列都已尝试过，没有遇到不符合的情况说明求得一解
        if (row == n) {
            COUNT++;
            return true;
        }

        // 对当前行所有列进行尝试
        for (int col = 0; col < n; col++) {
            if (!isValid(board, row, col)) {
                continue;
            }
            board[row][col] = 'Q';
            backtrack(board, row + 1);
            board[row][col] = '.';
        }
        return false;
    }

    private static boolean isValid(char[][] board, int row, int col) {
        int n = board.length;
        // 无需检测是否在同一行，因为放置皇后总会以新行开始
        // 检测同一行是否已经摆放皇后
//        for (int c = 0; c < col; c++) {
//            if (board[row][c] == 'Q') {
//                return false;
//            }
//        }
        // 检测同一列是否已经摆放皇后
        for (int r = 0; r < row; r++) {
            if (board[r][col] == 'Q') {
                return false;
            }
        }

        // 检测左上角
        for (int r = row - 1, c = col - 1; r > -1 && c > -1; r--, c--) {
            if (board[r][c] == 'Q') {
                return false;
            }
        }

        // 检测右上角
        for (int r = row - 1, c = col + 1; r > -1 && c < n; r--, c++) {
            if (board[r][c] == 'Q') {
                return false;
            }
        }

        return true;
    }

    static void test(int[] record, int row) {
        int n = record.length;
        if (n == row) {
            COUNT++;
            return;
        }

        for (int col = 0; col < n; col++) {
            if (!testValid(record, row, col)) {
                continue;
            }
            record[row] = col;
            test(record, row + 1);
        }
    }

    static boolean testValid(int[] record, int row, int col) {
        int n = record.length;
        for (int r = 0; r < row; r++) {
            if (record[r] == col) {
                return false;
            }
        }

        //(a,b) 与 (c,d) 处在对角线的计算方式 |(a-c)|==|(b-d)|
        // 如 (2,3) 其对角线即行和列各自加减一行，如 (3,4), (4,5), (3,2)
        for (int r = 0; r < row; r++) {
            if (Math.abs(row - r) == Math.abs(col - record[r])) {
                return false;
            }
        }
        return true;
    }
}
