package com.lynch;

public class PrintAB {
    public static void main(String[] args) {
        Print print=new Print();
        new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i=0;i<10;i++){
                    print.printA();
                }

            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i=0;i<10;i++){
                    print.printB();
                }

            }
        }).start();;
    }

    static class Print{
        boolean nowA=true;

        synchronized void printA(){
            while(!nowA){
                try {
                    this.wait();
                } catch (Exception e) {
                }
            }
            nowA=false;
            System.out.println(Thread.currentThread()+" "+"A");
            this.notify();
        }

        synchronized void printB(){
            while(nowA){
                try {
                    this.wait();
                } catch (Exception e) {
                }
            }
            nowA=true;
            System.out.println(Thread.currentThread()+" "+"B");
            this.notify();
        }

    }

//    public static void main(String... args) throws InterruptedException {
//        final byte[] lock = new byte[0];
//        final Thread thread1 = new Thread("thread1") {
//            @Override
//            public void run() {
//                System.out.println("thread1 is ready!");
//                try {
//                    Thread.sleep(1000 * 5);
//                } catch (InterruptedException e) {
//                }
//                synchronized (lock) {
//                    lock.notify();
//                    System.out.println("thread1 is notify, but not exit synchronized!");
//                    try {
//                        Thread.sleep(1000);
//                    } catch (InterruptedException e) {
//                    }
//                    System.out.println("thread1 is exit synchronized!");
//                }
//            }
//        };
//        final Thread thread2 = new Thread("thread2") {
//            @Override
//            public void run() {
//                System.out.println("thread2 is ready!");
//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                }
//                synchronized (lock) {
//                    try {
//                        System.out.println("thread2 is waiting!");
//                        lock.wait();
//                        System.out.println("thread2 is awake!");
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        };
//        thread1.start();
//        thread2.start();
//    }
}


