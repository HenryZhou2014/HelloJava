package com.concurrency;

public class ThreadTest extends  Thread{

    public static void main(String[] args){
        new ThreadTest().start();
    }

    public void run(){
        System.out.println("Started");
        setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler(){

            @Override
            public void uncaughtException(Thread t, Throwable e) {
                System.out.println("uncaughtException:"+e.getMessage());
            }
        });

        System.out.println("occured error");
        int bb=1/0;

    }
}
