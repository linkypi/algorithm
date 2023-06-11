package com.lynch;

import com.lynch.tools.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: linxueqi
 * @Description:
 * @Date: create in 2022/5/6 16:19
 */
public class DiffWaysToCompute {
   public static void main(String[] args) {
//       String input = "25643";
       String input = "2*3-4*5";
       List<Integer> result = getResult(input);
       List<Integer> list = diffWaysToCompute(input);
       Utils.printArr("diff ways result", result);
   }

    public static List<Integer> diffWaysToCompute(String input) {
        List<Integer> numList = new ArrayList<>();
        List<Character> opList = new ArrayList<>();
        char[] array = input.toCharArray();
        int num = 0;
        for (int i = 0; i < array.length; i++) {
            if (isOperator(array[i])) {
                numList.add(num);
                num = 0;
                opList.add(array[i]);
                continue;
            }
            num = num * 10 + array[i] - '0';
        }
        numList.add(num);
        int N = numList.size(); // 数字的个数

        // 一个数字
        ArrayList<Integer>[][] dp = (ArrayList<Integer>[][]) new ArrayList[N][N];
        for (int i = 0; i < N; i++) {
            ArrayList<Integer> result = new ArrayList<>();
            result.add(numList.get(i));
            dp[i][i] = result;
        }
        // 2 个数字到 N 个数字
        for (int n = 2; n <= N; n++) {
            // 开始下标
            for (int i = 0; i < N; i++) {
                // 结束下标
                int j = i + n - 1;
                if (j >= N) {
                    break;
                }
                ArrayList<Integer> result = new ArrayList<>();
                // 分成 i ~ s 和 s+1 ~ j 两部分
                for (int s = i; s < j; s++) {
                    ArrayList<Integer> result1 = dp[i][s];
                    ArrayList<Integer> result2 = dp[s + 1][j];
                    for (int x = 0; x < result1.size(); x++) {
                        for (int y = 0; y < result2.size(); y++) {
                            // 第 s 个数字下标对应是第 s 个运算符
                            char op = opList.get(s);
                            result.add(cal(result1.get(x), op, result2.get(y)));
                        }
                    }
                }
                dp[i][j] = result;

            }
        }
        return dp[0][N-1];
    }


   public static List<Integer> getResult(String input) {

       if (input == null || input.length() == 0) {
           return new ArrayList<>();
       }

       List<Integer> list = new ArrayList<>();
       // 若字符去全是数字
       int index = 0;
       int temp = 0;
       while (index < input.length()&& !isOperator(input.charAt(index))) {
           temp = temp * 10 + (input.charAt(index) - '0');
           index++;
       }
       if (index == input.length()) {
           list.add(temp);
           return list;
       }

       for (int i = 0; i < input.length(); i++) {
           if (isOperator(input.charAt(i))) {
               List<Integer> result = getResult(input.substring(0, i));
                List<Integer> result2 = getResult(input.substring(i + 1));

               for (Integer item1 : result) {
                   for (Integer item2 : result2) {
                       list.add(cal(item1, input.charAt(i), item2));
                   }
               }
           }
       }

       return list;
   }

   public static Integer cal(Integer item1, char op, Integer item2){
       switch (op){
           case '*':
               return item1 * item2;
           case '-':
               return item1 - item2;
           case '+':
               return item1 + item2;
       }
       return -1;
   }

   public static boolean isOperator(char op) {
       return op == '*' || op == '+' || op == '-';
   }
}
