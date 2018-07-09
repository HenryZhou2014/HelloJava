package com.concurrency;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierTest {
    private static final CyclicBarrier c = new CyclicBarrier(3, new Runnable() {
        @Override
        public void run() {
            System.out.println("I'm the last task" +"当前已有" + c.getNumberWaiting() + "个已经到达，正在等候");
        }
    });
    private static Set set = new HashSet<Object>();

    public static void main(String[] args){
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() +" start doing part one!");
                for(int i=0; i<10; i++ ){
                    set.add(Thread.currentThread().getName()+" "+i);
                }
                System.out.println(Thread.currentThread().getName() +" complete job"+"当前已有" + c.getNumberWaiting() + "个已经到达，正在等候");
                try {
                    c.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() +" Quit !");
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() +" start doing part two!");
                for(int i=11; i<20; i++ ){
                    set.add(Thread.currentThread().getName()+" "+i);
                }
                System.out.println(Thread.currentThread().getName() +" complete job"+"当前已有" + c.getNumberWaiting() + "个已经到达，正在等候");
                try {
                    c.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() +" Quit !");
            }
        }).start();

        System.out.println("Main thread warting for all result" +"当前已有" + c.getNumberWaiting() + "个已经到达，正在等候");
        try {
            c.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
        System.out.println("Main thread get all result");
        System.out.println(Arrays.toString(set.toArray()));
        System.out.println("结束："+c.getNumberWaiting() +" " +c.isBroken());
    }
}
