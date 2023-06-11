package com.lynch;

/**
 * @Author: linxueqi
 * @Description:
 * @Date: create in 2022/3/23 14:27
 */
public class PrintABUseWait {
    public static void main(String[] args) {
        Task taska = new Task("A");
        Task taskb = new Task("B");
        new Thread(taska).start();
        new Thread(taskb).start();
    }

    static Object lock = new Object();
    static class Task implements Runnable {
        String val;

        public Task(String val) {
            this.val = val;
        }

        @Override
        public void run() {
            while(true) {
                synchronized (lock) {
                    System.out.println(val);
                    lock.notify();
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}