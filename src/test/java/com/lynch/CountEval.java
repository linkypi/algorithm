package com.lynch;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: linxueqi
 * @Description:
 * @Date: create in 2022/5/6 17:13
 */
public class CountEval {
    public static void main(String[] args) {

        String input = "0&0&0&1^1|0";
        int result = 1;
        List<Integer> list = getCount(input);
        int count = 0;
        for (Integer item : list) {
            if (item == result) {
                count++;
            }
        }
        System.out.println("result: " + count);
    }

    public static List<Integer> getCount(String input) {
        int index = 0;
        int temp = 0;
        while (index < input.length() && !isOperator(input.charAt(index))) {
            temp = temp * 10 + (input.charAt(index) - '0');
            index++;
        }
        List<Integer> list = new ArrayList<>();
        if (index == input.length()) {
            list.add(temp);
            return list;
        }

        for (int i = 0; i < input.length(); i++) {
            if (isOperator(input.charAt(i))) {
                List<Integer> result1 = getCount(input.substring(0, i));
                List<Integer> result2 = getCount(input.substring(i + 1));
                for (Integer item1 : result1) {
                    for (Integer item2 : result2) {
                        list.add(cal(item1, input.charAt(i), item2));
                    }
                }
            }
        }
        return list;
    }

    public static Integer cal(Integer item1, char op, Integer item2) {
        switch (op) {
            case '&':
                return item1 & item2;
            case '|':
                return item1 | item2;
            case '^':
                return item1 ^ item2;
        }
        return -1;
    }

    public static boolean isOperator(char op) {
        return op == '&' || op == '|' || op == '^';
    }
}
