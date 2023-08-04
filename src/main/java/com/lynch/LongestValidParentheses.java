package com.lynch;

import org.junit.Test;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Stack;

/**
 * https://leetcode.cn/problems/longest-valid-parentheses/?envType=study-plan-v2&envId=bytedance-2023-spring-sprint
 *
 * 给你一个只包含 '(' 和 ')' 的字符串，找出最长有效（格式正确且连续）括号子串的长度。
 *
 * 示例 1：
 * 输入：s = "(()"
 * 输出：2
 * 解释：最长有效括号子串是 "()"
 *
 * 示例 2：
 * 输入：s = ")()())"
 * 输出：4
 * 解释：最长有效括号子串是 "()()"
 *
 * @Author: linxueqi
 * @Description:
 * @Date: create in 2023/8/2 10:05
 */
public class LongestValidParentheses {
    @Test
    public void test(){
        String str = ")()())";
        String str2 = "(()";
        String str3 = "(())))";
        String str4 = "(()())))";
        int len = find(str2);
        len = find(str4);
        int lenX = longestValidParentheses(str);
        System.out.println("max len: "+ len);
    }

    /**
     * leecode摘录的题解
     * @param s
     * @return
     */
    public int longestValidParentheses(String s) {
        /*
        方法2:栈(具体思路参考"笨猪爆破组")
        思路非常巧妙,核心是保持垫底的是最后一个未被匹配的右括号的索引
        1.遇到左括号,直接入栈
        2.遇到右括号,先弹出栈顶的左括号索引;
            接着判断栈是否为空,若为空说明右括号冗余,直接入栈作为标记
            若不为空说明当前i与前面的组成有效括号,可以更新维护最大值maxLen
        举例:()(()()))() 当去到i=8时,-1出栈,栈为空8入栈
        9入栈,10进来时9出栈,栈底为8不为空,10-8=2更新maxLen,至此遍历完毕
        i-sack.peek()会依次按照2,2,4,8,2顺序更新
        说白了就是维护垫底的是有效括号的起点索引-1,即最后一个未被匹配的右括号的索引
        通过i-垫底的元素就可以得出有效括号的总长度
        时间复杂度:O(N),空间复杂度:O(N)
        */
        if(s == null || s.length() < 2) {
            return 0;
        }
        int len = s.length();
        int maxLen = 0;
        Stack<Integer> stack = new Stack<>();
        // -1入栈初始化应对开始部分的有效括号
        // 例如(),1-(-1)=2
        stack.push(-1);
        for(int i = 0; i < len; i++) {
            // 遇到'('直接入栈
            if(s.charAt(i) == '(') {
                stack.push(i);
            }else {
                // 遇到')'先出栈抵消左括号
                stack.pop();
                // 若栈为空说明右括号冗余
                if(stack.empty()) {
                    // 冗余的右括号充当下一个-1
                    stack.push(i);
                }else {
                    // 若栈不为空说明匹配到有效的括号序列,更新即可
                    maxLen = Math.max(maxLen, i - stack.peek());
                }
            }
        }
        return maxLen;
    }

    private int find(String str){
        // 使用栈的方式求解
        Deque<Integer> stack = new ArrayDeque<>();
        stack.push(-1);

        int max = 0;
        for (int i = 0; i < str.length(); i++) {
            char item = str.charAt(i);
            if(item == '('){
                stack.push(i);
            }else{
                // 遇到右括号时，此时需要弹出左括号与之抵消，栈顶始终保留着有效括号的起始位置
                stack.pop();

                // 若此时栈为空，说明括号存在冗余，因为stack已经提前放入了一个元素 -1
                // 此时需要记录当前括号索引，等匹配到下一个更长的有效括号时用于计算当前长度
                if(stack.isEmpty()){
                    stack.push(i);
                }else{
                    // 若当前栈不为空，则说明存在有效括号，此时的长度为当前位置与栈顶元素的距离
                    max = Math.max(max, i - stack.peek());
                }
            }
        }
        return max;
    }
}
