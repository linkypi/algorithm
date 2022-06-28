package com.lynch;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author: linxueqi
 * @Description:
 * @Date: create in 2022/3/23 14:27
 */
public class PrintABUseReentrantLock {
    static ReentrantLock reentrantLock = new ReentrantLock();
    static Condition conditionA = reentrantLock.newCondition();
    static Condition conditionB = reentrantLock.newCondition();

    public static void main(String[] args) {
        new Thread(new TaskA()).start();
        new Thread(new TaskB()).start();
    }

    static class TaskA implements Runnable {
        String val = "A";

        public TaskA() {
        }

        @Override
        public void run() {

            while(true) {
                reentrantLock.lock();
                System.out.println(val);
                conditionB.signal();
                try {
                    conditionA.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    reentrantLock.unlock();
                }
            }
        }
    }

    static class TaskB implements Runnable {
        String val = "B";

        public TaskB() {
        }

        @Override
        public void run() {
            while(true) {
                reentrantLock.lock();
                System.out.println(val);
                conditionA.signal();
                try {
                    conditionB.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    reentrantLock.unlock();
                }
            }
        }
    }
}