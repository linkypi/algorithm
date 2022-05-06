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
       Utils.printArr("diff ways result", result);
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
