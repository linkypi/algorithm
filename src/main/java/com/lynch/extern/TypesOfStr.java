package com.lynch.extern;

import java.util.HashSet;

/**
 * 若两个字符串所含字符种类完全一样就算作一类，只由小写字母(a-z)组成的一批字符串
 * 都放在字符类型的数组String[] arr中，返回arr中一共有多少类？
 */
public class TypesOfStr {
    public static void main(String[] args) {

    }

    private static int getTypes(String[] arr) {
        HashSet<Integer> set = new HashSet<>();
        for (String item : arr) {
            // 记录每个字符串的摘要，小写字母有26个，使用32位的int记录
            int type = 0;
            for (char x : item.toCharArray()) {
                type |= (1 << (x - 'a'));
            }
            set.add(type);
        }
        return set.size();
    }
}
