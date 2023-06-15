package com.lynch;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedList;

/**
 * 使用栈实现队列
 * @Author: linxueqi
 * @Description:
 * @Date: create in 2023/3/22 16:59
 */
public class QueueByStack {
    private Deque<Integer> inDeq = new ArrayDeque<>();
    private Deque<Integer> outDeq = new ArrayDeque<>();
    private boolean useStack1= true;

    public void push(int item){
        inDeq.push(item);
    }

    private int pop() {
        if(outDeq.isEmpty()){
            inToOUT();
        }
        return inDeq.pop();
    }

    private int peek(){
        if(outDeq.isEmpty()){
            inToOUT();
        }
        return outDeq.peek();
    }

    private void inToOUT(){
        while (!inDeq.isEmpty()) {
            outDeq.push(inDeq.pop());
        }
    }
}
