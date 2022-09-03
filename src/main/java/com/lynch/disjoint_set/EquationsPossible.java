package com.lynch.disjoint_set;

/**
 * 给定一个由表示变量之间关系的字符串方程组成的数组，每个字符串方程 equations[i] 的长度为 4，
 * 并采用两种不同的形式之一："a==b" 或"a!=b"。在这里，a 和 b 是小写字母（不一定不同），表示单字母变量名。
 * 只有当可以将整数分配给变量名，以便满足所有给定的方程时才返回true，否则返回 false。
 *
 * 链接：https://leetcode.cn/problems/satisfiability-of-equality-equations
 * 
 * @author: linxueqi
 * @Description:
 * @createTime: 2022/7/30 16:35
 */
public class EquationsPossible {
    public static void main(String[] args) {
        String[] arr = {
//                "a==b", "b!=c", "c==a"
//                "a==b", "b!=a"
                "a==b", "b==c", "a==c"
        };
        char[][] chars = new char[arr.length][4];
        for (int i = 0; i < chars.length; i++) {
            chars[i] = arr[i].toCharArray();
        }
        boolean result = equationsPossible(chars);
        System.out.println("equal result: " + result);
    }

    static boolean equationsPossible(char[][] equations) {
        UnionFind unionFind = new UnionFind(equations.length * 2);
        for (char[] item : equations) {
            if (item[1] == '=') {
                unionFind.union(item[0] - 'a', item[3] - 'a');
            }
        }

        for (char[] item : equations) {
            if (item[1] == '!') {
                boolean connected = unionFind.isConnected(item[0] - 'a', item[3] - 'a');
                return !connected;
            }
        }
        return true;
    }
}
