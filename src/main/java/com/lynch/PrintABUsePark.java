package com.lynch;

import java.util.concurrent.locks.LockSupport;

/**
 * @Author: linxueqi
 * @Description:
 * @Date: create in 2022/3/23 14:27
 */
public class PrintABUsePark {
    public static void main(String[] args) {

        Task a = new Task("A");
        Task b = new Task("B");
        a.target = b;
        b.target = a;
        a.start();
        b.start();
    }

    static class Task extends Thread {
        String val ;
        Thread target = null;

        public Task(String val){
            this.val = val;
        }
        @Override
        public void run() {
            while (true) {
                System.out.println(val);
                LockSupport.unpark(target);
                LockSupport.park();
            }
        }
    }
}