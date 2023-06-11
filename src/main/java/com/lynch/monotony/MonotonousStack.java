package com.lynch.monotony;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

/**
 * 给定一个数组，求每个位置左边离它最近的且比它小的数，及右边离它最近的且比它小的数
 * @author: linxueqi
 * @Description:
 * @createTime: 2022/4/4 21:33
 */
public class MonotonousStack {
    public static void main(String[] args) {
        int[] arr = {3, 4, 2, 5, 6, 0, 1};
        int[][] ints = findInNotDuplicateArr(arr);

        int[] arr2 = {3, 4, 4, 3, 6, 3, 1};
        int[][] ints2 = findInDuplicateArr(arr2);

        System.out.println("result: " + ints);
    }

    /**
     *
     * @param notDuplicateArr 数组元素不重复
     * @return
     */
    static int[][] findInNotDuplicateArr(int[] notDuplicateArr) {

        if (notDuplicateArr == null || notDuplicateArr.length == 0) {
            return new int[][]{};
        }

        // 栈底到栈顶由小到大
        Stack<Integer> stack = new Stack<>();
        int[][] result = new int[notDuplicateArr.length][2];

        for (int i = 0; i< notDuplicateArr.length; i++) {
            int item = notDuplicateArr[i];
            while (!stack.empty() && notDuplicateArr[stack.peek()] > item) {
                int top = stack.peek();
                result[top][1] = item;
                stack.pop();
                result[top][0] = stack.empty() ? -1 : notDuplicateArr[stack.peek()];
            }
            stack.push(i);
        }

        while(!stack.empty()){
            int top = stack.pop();
            result[top][0] = stack.empty() ? -1 : notDuplicateArr[stack.peek()];
            result[top][1] = -1;
        }
        return result;
    }

    static int[][] findInDuplicateArr(int[] duplicateArr) {

        if (duplicateArr == null || duplicateArr.length == 0) {
            return new int[][]{};
        }

        // 栈底到栈顶由小到大, 对于重复元素使用 List来存放其下标
        Stack<Deque<Integer>> stack = new Stack<>();
        int[][] result = new int[duplicateArr.length][2];

        for (int i = 0; i< duplicateArr.length; i++) {
            int item = duplicateArr[i];
            while (!stack.empty()) {
                Deque<Integer> queue = stack.peek();
                // 栈底到栈顶由小到大的顺序存放，只有不符合要求的才需要弹出并记录
                if (duplicateArr[queue.getLast()] <= item) {
                    break;
                }
                while(!queue.isEmpty()) {
                    int top = queue.getLast();
                    // 1. 记录右边距离最近的比它小的数
                    result[top][1] = item;

                    // 2. 弹出元素，若队列已空则将队列在栈中弹出
                    queue.pollLast();
                    if (queue.isEmpty()) {
                        stack.pop();
                    }

                    result[top][0] = -1;
                    if (!stack.empty()) {
                        if (stack.size() == 1) {
                            Deque<Integer> topQueue = stack.peek();
                            result[top][0] = topQueue == queue ? -1 : duplicateArr[topQueue.getLast()];
                        } else {
                            // 若栈顶弹出后还有元素则继续从栈顶获取
                            Deque<Integer> topQueue = stack.pop();
                            Deque<Integer> temp = stack.peek();
                            result[top][0] = duplicateArr[temp.getLast()];
                            stack.push(topQueue);
                        }
                    }
                }
            }

            Deque<Integer> queue = stack.empty() ? null : stack.peek();
            // 若遇到相同元素则将他们下标保存到同一个队列中
            if (queue != null && duplicateArr[queue.getLast()] == item) {
                queue.addLast(i);
            } else {
                // 栈已空则直接入栈
                queue = new LinkedList<>();
                queue.addFirst(i);
                stack.push(queue);
            }
        }

        while(!stack.empty()){
            Deque<Integer> queue = stack.pop();
            while(!queue.isEmpty()){
                int last = queue.getLast();
                queue.pollLast();
                result[last][0] = queue.isEmpty() ? -1: duplicateArr[queue.getLast()];
                result[last][1] = -1;
            }
        }
        return result;
    }
}
