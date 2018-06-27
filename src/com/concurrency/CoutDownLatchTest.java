package com.concurrency;

import java.util.concurrent.CountDownLatch;

public class CoutDownLatchTest {

    public long timeTasks(int nTheads,final Runnable task) throws InterruptedException {
        final CountDownLatch startGate = new CountDownLatch(1);
        final CountDownLatch endGate = new CountDownLatch(nTheads);

        for(int i = 0; i< nTheads; i++ ){
            final Thread t = new Thread(){
                public void run(){
                    try {
                        startGate.await();
                        try {
                            task.run();
                        }finally {
                            endGate.countDown();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            };
            t.start();
        }

        long start = System.nanoTime();
        System.out.println("sleep 1s !");
        Thread.sleep(1000);
        System.out.println("All task start!");
        startGate.countDown(); //所以线程的统一启动开关
        endGate.await();//等待所有线程执行减计数器，然后唤醒
        System.out.println("All task done!");
        long end = System.nanoTime();
        return end-start;
    }

    public static void main(String[] args){
        try {
            new CoutDownLatchTest().timeTasks(10, new Runnable() {
                @Override
                public void run() {
                    System.out.println(Thread.currentThread().getName()+" run");
                }
            });
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
